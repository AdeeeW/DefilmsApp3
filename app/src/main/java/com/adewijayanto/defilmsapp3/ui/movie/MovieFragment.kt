package com.adewijayanto.defilmsapp3.ui.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adewijayanto.defilmsapp3.data.entity.MovieCatalogueEntity
import com.adewijayanto.defilmsapp3.databinding.FragmentMovieBinding
import com.adewijayanto.defilmsapp3.ui.ItemClickCallbackMovie
import com.adewijayanto.defilmsapp3.ui.detail.movie.DetailMovieActivity
import com.adewijayanto.defilmsapp3.ui.detail.movie.DetailMovieActivity.Companion.EXTRA_ID
import com.adewijayanto.defilmsapp3.utils.SortUtils.NEWEST
import com.adewijayanto.defilmsapp3.viewmodel.ViewModelFactory
import com.adewijayanto.defilmsapp3.vo.StatusMessage

class MovieFragment : Fragment() {
    private lateinit var moviesBinding: FragmentMovieBinding
    private lateinit var moviesAdapter: MovieAdapter
    private lateinit var viewModel: MovieViewModel

    companion object{
        const val TAG = "MOVIE DATA"
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        moviesBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return moviesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            setHasOptionsMenu(true)

            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

            moviesAdapter = MovieAdapter()

            viewModel.getMovie(NEWEST).observe(viewLifecycleOwner, { movies ->
                if (movies != null) {
                    when (movies.status) {
                        StatusMessage.LOADING -> showLoading(true)
                        StatusMessage.SUCCESS -> {
                            showLoading(false)
                            moviesAdapter.submitList(movies.data)
                            Log.d(TAG ,movies.data.toString())
                        }
                        StatusMessage.ERROR -> {
                            showLoading(false)
                            Toast.makeText(context, "Terdapat Kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            with(moviesBinding.rvMovie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = moviesAdapter
                showLoading(false)

                moviesAdapter.setOnItemClick(object : ItemClickCallbackMovie {
                    override fun onItemClick(data: MovieCatalogueEntity) {
                        val intent = Intent(context, DetailMovieActivity::class.java)
                        with(DetailMovieActivity::class.java) {
                            intent.putExtra(EXTRA_ID, data.id)
                            startActivity(intent)
                        }
                    }
                })
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            moviesBinding.pbMovie.visibility = View.VISIBLE
        } else {
            moviesBinding.pbMovie.visibility = View.GONE
        }
    }

}