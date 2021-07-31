package com.adewijayanto.defilmsapp3.ui

import com.adewijayanto.defilmsapp3.data.entity.MovieCatalogueEntity
import com.adewijayanto.defilmsapp3.data.entity.TvShowCatalogueEntity

interface ItemClickCallbackMovie {
    fun onItemClick(data: MovieCatalogueEntity)
}

interface ItemClickCallbackTvShow {
    fun onItemClick(data: TvShowCatalogueEntity)
}