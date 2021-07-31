package com.adewijayanto.defilmsapp3.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.adewijayanto.defilmsapp3.R
import com.adewijayanto.defilmsapp3.databinding.FragmentFavoriteBinding
import com.adewijayanto.defilmsapp3.ui.favorite.movie.FavMovieFragment
import com.adewijayanto.defilmsapp3.ui.favorite.tvshow.FavTvShowFragment
import com.adewijayanto.defilmsapp3.ui.home.HomeActivity
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteFragment : Fragment() {
    private var _favoriteFragmentBinding: FragmentFavoriteBinding? = null
    private val binding get() = _favoriteFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _favoriteFragmentBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            (activity as HomeActivity).supportActionBar?.title = "Favorite"

            setViewPager()
        }
    }

    private fun setViewPager() {
        val fragmentList = listOf(FavMovieFragment(), FavTvShowFragment())
        val tabTitle =
                listOf(resources.getString(R.string.movie), resources.getString(R.string.tv_show))

        binding?.viewpager?.adapter =
                SectionsPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)

        binding?.let {
            TabLayoutMediator(it.tabLayout2, it.viewpager) { tab, position ->
                tab.text = tabTitle[position]
            }.attach()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _favoriteFragmentBinding = null
    }
}