package com.adewijayanto.defilmsapp3.ui.favorite.movie

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.adewijayanto.defilmsapp3.BuildConfig
import com.adewijayanto.defilmsapp3.R
import com.adewijayanto.defilmsapp3.data.entity.MovieCatalogueEntity
import com.adewijayanto.defilmsapp3.data.entity.TvShowCatalogueEntity
import com.adewijayanto.defilmsapp3.databinding.ListItemBinding
import com.adewijayanto.defilmsapp3.ui.ItemClickCallbackMovie
import com.adewijayanto.defilmsapp3.ui.favorite.tvshow.FavTvShowAdapter
import com.adewijayanto.defilmsapp3.ui.movie.MovieAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FavMovieAdapter : PagedListAdapter<MovieCatalogueEntity, FavMovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    companion object {
        const val TAG = "Adapter"
        const val IMAGE_URL = BuildConfig.IMAGE_URL
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieCatalogueEntity>() {
            override fun areItemsTheSame(
                oldItem: MovieCatalogueEntity,
                newItem: MovieCatalogueEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MovieCatalogueEntity,
                newItem: MovieCatalogueEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun getSwipedData(swipedPosition: Int): MovieCatalogueEntity? = getItem(swipedPosition)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemMovieBinding =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemMovieBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.bind(tvShow)
        }
    }

    inner class MovieViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieCatalogueEntity) {
            val poster =
                StringBuilder("${IMAGE_URL}${movie.poster_path}").toString()
            with(binding) {
                Glide.with(itemView.context)
                    .load(poster)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .centerCrop()
                    .into(imgPoster)
                Log.d(TAG, movie.title)

                tvTitle.text = movie.title
                tvDeskripsi.text = movie.overview
                tvDate.text = movie.release_date
                barRating.rating = movie.vote_average

                itemView.setOnClickListener { onItemClickCallback.onItemClicked(movie.id) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(id: String)
    }
}


