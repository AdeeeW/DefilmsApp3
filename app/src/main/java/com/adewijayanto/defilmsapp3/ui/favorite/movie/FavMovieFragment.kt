package com.adewijayanto.defilmsapp3.ui.favorite.movie

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
import com.adewijayanto.defilmsapp3.data.entity.MovieCatalogueEntity
import com.adewijayanto.defilmsapp3.databinding.FragmentFavMovieBinding
import com.adewijayanto.defilmsapp3.ui.detail.movie.DetailMovieActivity
import com.adewijayanto.defilmsapp3.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class FavMovieFragment : Fragment(), FavMovieAdapter.OnItemClickCallback {

    companion object{
        const val TAG = "FAVORITE MOVIE"
    }

    private var _fragmentTvShowBinding: FragmentFavMovieBinding? = null
    private val binding get() = _fragmentTvShowBinding

    private lateinit var viewModel: MovieFavViewModel
    private lateinit var adapterMovie: FavMovieAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        _fragmentTvShowBinding = FragmentFavMovieBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavMovies().observe(viewLifecycleOwner, { favMovie ->
            if (favMovie != null) {
                adapterMovie.submitList(favMovie)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemTouchHelper.attachToRecyclerView(binding?.rvFavMovie)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireContext())
            viewModel = ViewModelProvider(this, factory)[MovieFavViewModel::class.java]

            adapterMovie = FavMovieAdapter()
            adapterMovie.setOnItemClickCallback(this)

            viewModel.getFavMovies().observe(viewLifecycleOwner, { favMovie ->
                if (favMovie != null) {
                    showFavMovie(favMovie)
                }
            })
        }
    }

    private fun showFavMovie(favMovie: PagedList<MovieCatalogueEntity>) {
        adapterMovie.submitList(favMovie)
        Log.d(TAG, favMovie.toString())
        when (favMovie.size) {
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
                with(binding?.rvFavMovie) {
                    this?.layoutManager = LinearLayoutManager(context)
                    this?.setHasFixedSize(true)
                    this?.adapter = this@FavMovieFragment.adapterMovie
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
                val tvShowEntity = adapterMovie.getSwipedData(swipedPosition)
                tvShowEntity?.let { viewModel.setFavMovie(it) }

                val snackbar =
                        Snackbar.make(view as View, R.string.message, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.oke) { _ ->
                    tvShowEntity?.let { viewModel.setFavMovie(it) }
                }
                snackbar.show()
            }
        }
    })

    override fun onItemClicked(id: String) {
        val intent = Intent(context, DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.EXTRA_ID, id)

        context?.startActivity(intent)
    }

}