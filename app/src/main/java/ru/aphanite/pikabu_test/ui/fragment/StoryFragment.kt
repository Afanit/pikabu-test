package ru.aphanite.pikabu_test.ui.fragment

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.aphanite.pikabu_test.R
import ru.aphanite.pikabu_test.databinding.StoryFragmentBinding
import ru.aphanite.pikabu_test.ui.story.list.StoryAdapter
import ru.aphanite.pikabu_test.ui.story.list.StorySpaceItemDecoration
import ru.aphanite.pikabu_test.utils.AnimationUtils
import ru.aphanite.pikabu_test.utils.ModelMapper
import ru.aphanite.pikabu_test.viewmodel.ActivityViewModel
import ru.aphanite.pikabu_test.viewmodel.StoryViewModel
import ru.aphanite.pikabu_test.viewmodel.ViewModelFactory
import javax.inject.Inject

abstract class StoryFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var layoutManager: LinearLayoutManager
    @Inject
    lateinit var storyAdapter: StoryAdapter

    private lateinit var viewModel: StoryViewModel
    protected lateinit var activityViewModel: ActivityViewModel

    private lateinit var binding: StoryFragmentBinding
    private var toast: Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = StoryFragmentBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activityViewModel = ViewModelProvider(requireActivity()).get(ActivityViewModel::class.java)
        viewModel = ViewModelProvider(this, viewModelFactory).get(StoryViewModel::class.java)

        binding.stories.adapter = storyAdapter
        binding.stories.layoutManager = layoutManager
        binding.stories.addItemDecoration(
            StorySpaceItemDecoration(
                requireContext(),
                R.dimen.feed_item_space
            )
        )
        binding.emptyMessage.text = getString(viewModel.emptyMessage)

        binding.refresh.setOnRefreshListener {
            viewModel.updateFeed()
        }

        viewModel.storyList.observe(viewLifecycleOwner) { entities ->
            val stories = ModelMapper.mapEntitiesToModels(entities)

            if (isActive()) {
                storyAdapter.setStories(stories)
            } else {
                storyAdapter.forceSetStories(stories)

                if (viewModel.scrollOnUpdate) {
                    binding.stories.layoutManager?.scrollToPosition(0)
                }
            }

            if (binding.emptyMessage.isVisible != entities.isEmpty()) {
                if (entities.isEmpty()) {
                    showEmptyMessage()
                } else {
                    hideEmptyMessage()
                }
            }
        }

        viewModel.updateResult.observe(viewLifecycleOwner) { result ->
            result.getContentIfNotHandled()?.let { success ->
                binding.refresh.isRefreshing = false
                toast?.cancel()

                if (!success) {
                    toast = Toast.makeText(
                        requireContext(),
                        R.string.no_internet_connection,
                        Toast.LENGTH_LONG
                    ).apply { show() }
                }
            }
        }

        viewModel.updateFeed()
    }

    abstract fun isActive(): Boolean

    private fun showEmptyMessage() {
        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = AnimationUtils.SHORT_DURATION
            doOnStart {
                binding.emptyMessage.isVisible = true
            }
            addUpdateListener {
                binding.emptyMessage.alpha = it.animatedValue as Float
                binding.stories.alpha = 1 - it.animatedValue as Float
            }
            doOnEnd {
                binding.stories.isVisible = true
            }
            start()
        }
    }

    private fun hideEmptyMessage() {
        ValueAnimator.ofFloat(2f, 0f).apply {
            duration = AnimationUtils.LONG_DURATION
            doOnStart {
                binding.stories.isVisible = true
                binding.stories.alpha = 0f
            }
            addUpdateListener {
                val value = it.animatedValue as Float
                if (value > 1) {
                    binding.emptyMessage.alpha = value - 1
                } else {
                    binding.stories.alpha = 1 - value
                }
            }
            doOnEnd {
                binding.emptyMessage.isVisible = false
            }
            start()
        }
    }
}