package com.adewijayanto.defilmsapp3.ui.favorite.tvshow

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adewijayanto.defilmsapp3.R
import com.adewijayanto.defilmsapp3.data.entity.TvShowCatalogueEntity
import com.adewijayanto.defilmsapp3.databinding.FragmentFavTvShowBinding
import com.adewijayanto.defilmsapp3.ui.detail.tvshow.DetailTvShowActivity
import com.adewijayanto.defilmsapp3.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class FavTvShowFragment : Fragment(), FavTvShowAdapter.OnItemClickCallback {

    companion object{
        const val TAG = "FAVORITE TV SHOW"
    }

    private var _fragmentTvShowBinding: FragmentFavTvShowBinding? = null
    private val binding get() = _fragmentTvShowBinding

    private lateinit var viewModel: TvShowFavViewModel
    private lateinit var adapterTvShow: FavTvShowAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        _fragmentTvShowBinding = FragmentFavTvShowBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavTvShows().observe(viewLifecycleOwner, { favTvShow ->
            if (favTvShow != null) {
                adapterTvShow.submitList(favTvShow)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemTouchHelper.attachToRecyclerView(binding?.rvFavTvshow)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireContext())
            viewModel = ViewModelProvider(this, factory)[TvShowFavViewModel::class.java]

            adapterTvShow = FavTvShowAdapter()
            adapterTvShow.setOnItemClickCallback(this)

            viewModel.getFavTvShows().observe(viewLifecycleOwner, { favTvShow ->
                if (favTvShow != null) {
                    showFavTvShow(favTvShow)
                }
            })
        }
    }

    private fun showFavTvShow(favTvShow: PagedList<TvShowCatalogueEntity>) {
        adapterTvShow.submitList(favTvShow)
        Log.d(TAG, favTvShow.toString())
        when (favTvShow.size) {
            0 -> {
                binding?.apply {
                    imgErrorFav.visibility = View.VISIBLE
                    textErrorFav.visibility = View.VISIBLE
                }
            }
            else -> {
                binding?.apply {
                    imgErrorFav.visibility = View.INVISIBLE
                    textErrorFav.visibility = View.INVISIBLE
                }
                with(binding?.rvFavTvshow) {
                    this?.layoutManager = LinearLayoutManager(context)
                    this?.setHasFixedSize(true)
                    this?.adapter = this@FavTvShowFragment.adapterTvShow
                }
            }
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
                makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val tvShowEntity = adapterTvShow.getSwipedData(swipedPosition)
                tvShowEntity?.let { viewModel.setFavTvShow(it) }

                val snackbar =
                        Snackbar.make(view as View, R.string.message, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.oke) { _ ->
                    tvShowEntity?.let { viewModel.setFavTvShow(it) }
                }
                snackbar.show()
            }
        }
    })

    override fun onItemClicked(id: String) {
        val intent = Intent(context, DetailTvShowActivity::class.java)
        intent.putExtra(DetailTvShowActivity.EXTRA_ID, id)

        context?.startActivity(intent)
    }

}