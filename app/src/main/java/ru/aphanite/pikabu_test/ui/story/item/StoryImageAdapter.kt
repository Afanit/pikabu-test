package ru.aphanite.pikabu_test.ui.story.item

import android.util.Size
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.aphanite.pikabu_test.databinding.StoryImageBinding
import javax.inject.Inject

class StoryImageAdapter @Inject constructor(private val imageSize: Size) :
    RecyclerView.Adapter<StoryImageViewHolder>() {
    private var urls: List<String> = emptyList()

    fun setUrls(data: List<String>?) {
        urls = data ?: emptyList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryImageViewHolder {
        val bindings = StoryImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryImageViewHolder(bindings, imageSize)
    }

    override fun onBindViewHolder(holder: StoryImageViewHolder, position: Int) {
        holder.bind(urls[position])
    }

    override fun getItemCount(): Int {
        return urls.size
    }
}