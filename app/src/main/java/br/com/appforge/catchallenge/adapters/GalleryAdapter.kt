package br.com.appforge.catchallenge.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.appforge.catchallenge.R
import br.com.appforge.catchallenge.databinding.ItemGalleryBinding
import com.squareup.picasso.Picasso


class GalleryAdapter:
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    companion object {
        private const val TAG = "info_gallery_adapter"
    }

    private var images = (emptyList <String>())

    fun addList(newImages: List<String>) {
        images = newImages
        notifyDataSetChanged()
    }

    inner class GalleryViewHolder(val binding: ItemGalleryBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(url:String){
            Log.d(Companion.TAG, "Binding image URL: $url")
            Picasso.get()
                .load(url)
                .error(R.drawable.ic_image_broken_24)
                .placeholder(R.drawable.ic_image_24)
                .resize(120,120)
                .into(binding.imageView)
            //binding.textview.text = image.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        Log.d(TAG, "Creating new ViewHolder")
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = ItemGalleryBinding.inflate(layoutInflater, parent, false)
        return GalleryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        Log.d(TAG, "Binding ViewHolder at position $position")
        val image = images[position]
        holder.bind(image)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "Total item count: ${images.size}")
        return images.size
    }
}