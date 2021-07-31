package com.adewijayanto.defilmsapp3.ui.favorite.tvshow

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.adewijayanto.defilmsapp3.R
import com.adewijayanto.defilmsapp3.data.entity.TvShowCatalogueEntity
import com.adewijayanto.defilmsapp3.databinding.ListItemBinding
import com.adewijayanto.defilmsapp3.ui.favorite.movie.FavMovieAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FavTvShowAdapter :
    PagedListAdapter<TvShowCatalogueEntity, FavTvShowAdapter.TvShowViewHolder>(DIFF_CALLBACK) {

    companion object {
        const val TAG = "ADAPTER"
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowCatalogueEntity>() {
            override fun areItemsTheSame(
                oldItem: TvShowCatalogueEntity,
                newItem: TvShowCatalogueEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TvShowCatalogueEntity,
                newItem: TvShowCatalogueEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun getSwipedData(swipedPosition: Int): TvShowCatalogueEntity? = getItem(swipedPosition)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val itemMovieBinding =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(itemMovieBinding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.bind(tvShow)
        }
    }

    inner class TvShowViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShowCatalogueEntity) {
            val poster =
                StringBuilder("${FavMovieAdapter.IMAGE_URL}${tvShow.poster_path}").toString()
            with(binding) {
                Glide.with(itemView.context)
                    .load(poster)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .centerCrop()
                    .into(imgPoster)
                Log.d(TAG, tvShow.name)

                tvTitle.text = tvShow.name
                tvDeskripsi.text = tvShow.overview
                tvDate.text = tvShow.first_air_date
                barRating.rating = tvShow.vote_average

                itemView.setOnClickListener { onItemClickCallback.onItemClicked(tvShow.id) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(id: String)
    }
}