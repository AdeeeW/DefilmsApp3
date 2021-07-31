package com.adewijayanto.defilmsapp3.ui.detail.movie

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.adewijayanto.defilmsapp3.BuildConfig.IMAGE_URL
import com.adewijayanto.defilmsapp3.R
import com.adewijayanto.defilmsapp3.data.entity.MovieCatalogueEntity
import com.adewijayanto.defilmsapp3.databinding.ActivityDetailMovieBinding
import com.adewijayanto.defilmsapp3.databinding.ContentDetailMovieBinding
import com.adewijayanto.defilmsapp3.ui.settings.SettingsActivity
import com.adewijayanto.defilmsapp3.viewmodel.ViewModelFactory
import com.adewijayanto.defilmsapp3.vo.StatusMessage
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class DetailMovieActivity : AppCompatActivity(){

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var contentDetailBinding: ContentDetailMovieBinding
    private lateinit var activityDetailBinding: ActivityDetailMovieBinding
    private lateinit var viewModelMovie: DetailMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = ActivityDetailMovieBinding.inflate(layoutInflater)
        contentDetailBinding = activityDetailBinding.icContent
        setContentView(activityDetailBinding.root)

        supportActionBar?.title = getString(R.string.detail_movie)

        val factory = ViewModelFactory.getInstance(this)
        viewModelMovie = ViewModelProvider(this, factory)[DetailMovieViewModel::class.java]


        val extras = intent.extras
        if (extras != null) {
            val movieId = extras.getString(EXTRA_ID)

            viewModelMovie.setSelectedMovie(movieId.toString())

            activityDetailBinding.progressBar.visibility = View.VISIBLE
            contentDetailBinding.svContent.visibility = View.GONE
            activityDetailBinding.fbShare.visibility = View.GONE

            viewModelMovie.movie.observe(this@DetailMovieActivity, { movies ->
                        when (movies.status) {
                            StatusMessage.LOADING -> showLoading(true)
                            StatusMessage.SUCCESS -> {
                                if (movies.data != null) {
                                    showLoading(false)
                                    contentDetailBinding.svContent.visibility = View.VISIBLE
                                    activityDetailBinding.fbShare.visibility = View.VISIBLE
                                    populateMoviesDetail(movies.data)
                                }
                            }
                            StatusMessage.ERROR -> {
                                showLoading(false)
                                Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })

        }

    }

    private fun shareApp(moviesEntity: MovieCatalogueEntity) {
        val mimeType = "text/plain"
        moviesEntity.apply {
            ShareCompat.IntentBuilder
                    .from(this@DetailMovieActivity)
                    .setType(mimeType)
                    .setChooserTitle("Bagikan Film: \"${title}\", sekarang.")
                    .setText("Judul Film: ${title}\n" +
                            "Deskripsi Film: ${overview}\n" +
                            "Link: ${homepage}")
                    .startChooser()
        }
    }

    private fun populateMoviesDetail(moviesEntity: MovieCatalogueEntity) {
        var status = moviesEntity.favorite
        setFavorite(status)
        moviesEntity.apply {
            val imagePath = StringBuilder("${IMAGE_URL}${poster_path}").toString()
            val imageBg = StringBuilder("${IMAGE_URL}${backdrop_path}").toString()
                with(contentDetailBinding) {
                    title.also { tvDetailJudul.text = it }
                    overview.also { tvDetailDeskripsi.text = it }
                    (vote_average).also { barRating.rating = it }
                    release_date.also { tvDetailDate.text = it }
                    genres.also { tvDetailGenre.text = it }
                    spoken_languages.also { tvDetailBahasa.text = it }
                }
                    if (backdrop_path == null) {
                        with(contentDetailBinding) {
                            Glide.with(this@DetailMovieActivity)
                                    .load(imagePath)
                                    .transform(RoundedCorners(20))
                                    .apply(
                                            RequestOptions.placeholderOf(R.drawable.ic_loading)
                                                    .error(R.drawable.ic_error)
                                    )
                                    .into(ivPoster)

                            Glide.with(this@DetailMovieActivity)
                                    .load(imagePath)
                                    .apply(
                                            RequestOptions.placeholderOf(R.drawable.ic_loading)
                                                    .error(R.drawable.ic_error)
                                    )
                                    .thumbnail()
                                    .centerCrop()
                                    .into(imgBg)
                        }
                } else {
                with(contentDetailBinding) {
                    Glide.with(this@DetailMovieActivity)
                            .load(imagePath)
                            .transform(RoundedCorners(20))
                            .apply(
                                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                                            .error(R.drawable.ic_error)
                            )
                            .into(ivPoster)

                    Glide.with(this@DetailMovieActivity)
                            .load(imageBg)
                            .apply(
                                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                                            .error(R.drawable.ic_error)
                            )
                            .thumbnail()
                            .centerCrop()
                            .into(imgBg)
                }
            }
        }
        activityDetailBinding.fbShare.setOnClickListener {
            shareApp(moviesEntity)
        }
        contentDetailBinding.btnFavorite.setOnClickListener {
            status = !status
            setFavorite(status)
            viewModelMovie.setFavoriteMovie()
        }
    }

    private fun setFavorite(state: Boolean) {
        if (state) {
            contentDetailBinding.btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_favorite_red
                    )
            )
        } else {
            contentDetailBinding.btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_favorite_border
                    )
            )
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            activityDetailBinding?.progressBar?.visibility = View.VISIBLE
        } else {
            activityDetailBinding?.progressBar?.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(itemId: Int) {
        when (itemId) {
            R.id.settings -> {
                val mIntent = Intent(this, SettingsActivity::class.java)
                startActivity(mIntent)
            }
        }
    }
}