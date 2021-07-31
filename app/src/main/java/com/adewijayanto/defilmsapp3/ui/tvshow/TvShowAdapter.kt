package com.adewijayanto.defilmsapp3.ui.tvshow

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
import com.adewijayanto.defilmsapp3.ui.ItemClickCallbackTvShow
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TvShowAdapter : PagedListAdapter<TvShowCatalogueEntity, TvShowAdapter.TvShowViewHolder>(DIFF_CALBACK) {
    companion object {
        const val TAG = "ADAPTER"
        const val IMAGE_URL = BuildConfig.IMAGE_URL
        private val DIFF_CALBACK = object : DiffUtil.ItemCallback<TvShowCatalogueEntity>() {
            override fun areItemsTheSame(oldItem: TvShowCatalogueEntity, newItem: TvShowCatalogueEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                    oldItem: TvShowCatalogueEntity,
                    newItem: TvShowCatalogueEntity,
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    private var onItemClickCallback: ItemClickCallbackTvShow? = null

    fun setOnItemClick(onItemClick: ItemClickCallbackTvShow) {
        this.onItemClickCallback = onItemClick
    }


    inner class TvShowViewHolder(private val binding: ListItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(shows: TvShowCatalogueEntity) {
            val poster = StringBuilder("${IMAGE_URL}${shows.poster_path}").toString()
            with(binding) {
                Glide.with(itemView.context)
                        .load(poster)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                                .error(R.drawable.ic_error))
                        .centerCrop()
                        .into(imgPoster)
                Log.d(TAG, shows.first_air_date)

                tvTitle.text = shows.name
                tvDeskripsi.text = shows.overview
                tvDate.text = shows.first_air_date
                barRating.rating = shows.vote_average/2

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClick(shows)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val itemTvshowsBinding =
                ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(itemTvshowsBinding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.bind(tvShow)
        }
    }

}