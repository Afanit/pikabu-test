package ru.aphanite.pikabu_test.ui.main

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.aphanite.pikabu_test.R
import ru.aphanite.pikabu_test.data.StoryModel
import ru.aphanite.pikabu_test.databinding.FragmentFeedBinding
import ru.aphanite.pikabu_test.ui.ViewModelFactory
import javax.inject.Inject

class FeedFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: FeedViewModel

    private lateinit var binding: FragmentFeedBinding
    private var snackbar: Snackbar? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        activityComponent().feedComponentBuilder().build().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(FeedViewModel::class.java)

        binding.feed.adapter = viewModel.feedAdapter
        binding.feed.addItemDecoration(
            FeedSpaceItemDecoration(
                requireContext(),
                R.dimen.feed_item_space
            )
        )

        binding.refresh.setOnRefreshListener {
            viewModel.updateFeed(object : FeedViewModel.OnResultListener {
                override fun onSuccess() {
                    binding.refresh.isRefreshing = false
                    hideSnakbar()
                }

                override fun onFailure() {
                    binding.refresh.isRefreshing = false
                    hideSnakbar()
                    showSnakbar()
                }
            })
        }

        with(viewModel) {

            storyList.observe(viewLifecycleOwner) { entities ->
                val stories = entities.map { entity ->
                    StoryModel(entity.id, entity.title, entity.body, entity.urls, entity.isFavorite)
                }
                feedAdapter.setStories(stories)
            }
            viewModel.updateFeed(object : FeedViewModel.OnResultListener {
                override fun onSuccess() {
                    binding.refresh.isRefreshing = false
                }

                override fun onFailure() {
                    binding.refresh.isRefreshing = false
                }
            })
        }
    }

    private fun showSnakbar() {
        val backgroundColor = ResourcesCompat.getColor(resources, R.color.colorDark, null)
        val actionColor = ResourcesCompat.getColor(resources, R.color.colorOrange, null)

        val ok = SpannableString(resources.getString(R.string.ok)).apply {
            setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        snackbar = Snackbar.make(
            binding.root,
            R.string.no_internet_connection,
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setBackgroundTint(backgroundColor)
            setActionTextColor(actionColor)
            setAction(ok) {
                dismiss()
            }
            show()
        }
    }

    fun hideSnakbar() {
        snackbar?.dismiss()
    }
}