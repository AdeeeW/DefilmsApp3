package com.adewijayanto.defilmsapp3.ui.tvshow

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
import com.adewijayanto.defilmsapp3.data.entity.TvShowCatalogueEntity
import com.adewijayanto.defilmsapp3.databinding.FragmentTvShowBinding
import com.adewijayanto.defilmsapp3.ui.ItemClickCallbackTvShow
import com.adewijayanto.defilmsapp3.ui.detail.tvshow.DetailTvShowActivity
import com.adewijayanto.defilmsapp3.ui.detail.tvshow.DetailTvShowActivity.Companion.EXTRA_ID
import com.adewijayanto.defilmsapp3.viewmodel.ViewModelFactory
import com.adewijayanto.defilmsapp3.utils.SortUtils
import com.adewijayanto.defilmsapp3.vo.StatusMessage

class TvShowFragment : Fragment() {
    private lateinit var tvShowBinding: FragmentTvShowBinding
    private lateinit var tvShowAdapter: TvShowAdapter

    companion object{
        const val TAG = "TV SHOW DATA"
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        tvShowBinding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return tvShowBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            setHasOptionsMenu(true)

            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]
            tvShowAdapter = TvShowAdapter()
            viewModel.getTvShow(SortUtils.NEWEST).observe(viewLifecycleOwner, { tvShows ->
                if (tvShows != null) {
                    when (tvShows.status) {
                        StatusMessage.LOADING -> showLoading(true)
                        StatusMessage.SUCCESS -> {
                            showLoading(false)
                            tvShowAdapter.submitList(tvShows.data)
                            Log.d(TAG, tvShows.data.toString())
                        }
                        StatusMessage.ERROR -> {
                            showLoading(false)
                            Toast.makeText(context, "Terdapat Kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            with(tvShowBinding.rvTvShow) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvShowAdapter
                showLoading(false)

                tvShowAdapter.setOnItemClick(object : ItemClickCallbackTvShow {
                    override fun onItemClick(data: TvShowCatalogueEntity) {
                        val intent = Intent(context, DetailTvShowActivity::class.java)
                        with(DetailTvShowActivity::class.java) {
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
            tvShowBinding.pbTvShow.visibility = View.VISIBLE
        } else {
            tvShowBinding.pbTvShow.visibility = View.GONE
        }
    }
}