package ru.aphanite.pikabu_test.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.aphanite.pikabu_test.R
import ru.aphanite.pikabu_test.databinding.PreviewFragmentBinding
import ru.aphanite.pikabu_test.di.activityComponent
import ru.aphanite.pikabu_test.di.qualifier.StoryId
import ru.aphanite.pikabu_test.ui.story.item.StoryBinder
import ru.aphanite.pikabu_test.utils.ColorConst
import ru.aphanite.pikabu_test.utils.ModelMapper
import ru.aphanite.pikabu_test.viewmodel.ActivityViewModel
import ru.aphanite.pikabu_test.viewmodel.PreviewViewModel
import ru.aphanite.pikabu_test.viewmodel.ViewModelFactory
import javax.inject.Inject

class PreviewFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var colors: ColorConst
    @Inject
    lateinit var displaySize: Size

    private lateinit var activityViewModel: ActivityViewModel
    private lateinit var viewModel: PreviewViewModel

    private lateinit var binding: PreviewFragmentBinding
    private lateinit var binder: PreviewStoryBinder

    private var toast: Toast? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        activityComponent().previewComponentBuilder()
            .storyId(arguments!!.getLong(STORY_ID))
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PreviewFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activityViewModel = ViewModelProvider(requireActivity()).get(ActivityViewModel::class.java)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PreviewViewModel::class.java)

        binder = PreviewStoryBinder(binding, colors, displaySize)
        binder.favoriteListener = viewModel.favoriteListener

        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        viewModel.story.observe(viewLifecycleOwner) {
            val data = ModelMapper.mapEntityToModel(it)
            binder.bind(data)

            viewModel.updateStory(data.id)
        }

        viewModel.updateResult.observe(viewLifecycleOwner) { result ->
            result.getContentIfNotHandled()?.let { success ->
                toast?.cancel()

                if (!success) {
                    toast = Toast.makeText(
                        requireContext(),
                        R.string.no_internet_connection_preview,
                        Toast.LENGTH_LONG
                    ).apply { show() }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        activityViewModel.state = ActivityViewModel.State.PREVIEW
    }

    private inner class PreviewStoryBinder(
        binding: PreviewFragmentBinding,
        colors: ColorConst,
        displaySize: Size
    ) : StoryBinder(colors, displaySize) {

        override val title = binding.title
        override val body = binding.body
        override val images = binding.images
        override val mark = binding.mark
        override val favoriteButton = binding.favoriteButton
    }

    companion object {
        private const val STORY_ID = "STORY_ID"

        fun newInstance(@StoryId storyId: Long): PreviewFragment {
            return PreviewFragment().apply {
                arguments = bundleOf(
                    STORY_ID to storyId
                )
            }
        }
    }
}