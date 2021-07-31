package com.adewijayanto.defilmsapp3.utils

import com.adewijayanto.defilmsapp3.BuildConfig.IMAGE_URL
import com.adewijayanto.defilmsapp3.data.entity.MovieCatalogueEntity
import com.adewijayanto.defilmsapp3.data.entity.TvShowCatalogueEntity

object DataDummy {
    fun generateDummyMovies(): List<MovieCatalogueEntity> {
        val movies = ArrayList<MovieCatalogueEntity>()

        movies.add(
                MovieCatalogueEntity(
                        "19404",
                        "Hindi",
                        "Raj is a rich, carefree, happy-go-lucky second generation NRI. Simran is the daughter of Chaudhary Baldev Singh, who in spite of being an NRI is very strict about adherence to Indian values. Simran has left for India to be married to her childhood fiancé. Raj leaves for India with a mission at his hands, to claim his lady love under the noses of her whole family. Thus begins a saga.",
                        "Dilwale Dulhania Le Jayenge",
                        "$IMAGE_URL/2CAL2433ZeIihfX1Hb2139CX0pW.jpg",
                        "$IMAGE_URL/gNBCvtYyGPbjPCT1k3MvJuNuXR6.jpg",
                        "1995-10-20",
                        8.7F,
                        "Comedy, Drama, Romance",
                        "",
                        false,
                )
        )
        movies.add(
                MovieCatalogueEntity(
                        "724089",
                        "English",
                        "Professor Gabriel Emerson finally learns the truth about Julia Mitchell's identity, but his realization comes a moment too late. Julia is done waiting for the well-respected Dante specialist to remember her and wants nothing more to do with him. Can Gabriel win back her heart before she finds love in another's arms?",
                        "Gabriel's Inferno Part II",
                        "$IMAGE_URL/x5o8cLZfEXMoZczTYWLrUo1P7UJ.jpg",
                        "$IMAGE_URL/jtAI6OJIWLWiRItNSZoWjrsUtmi.jpg",
                        "2020-07-31",
                        8.7F,
                        "Romance",
                        "https://watch.passionflix.com/watch/f299fa17-5a2b-4fee-b53a-a4651747431b",
                        false
                )
        )
        movies.add(
                MovieCatalogueEntity(
                        "278",
                        "English",
                        "Framed in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.",
                        "The Shawshank Redemption",
                        "$IMAGE_URL/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg",
                        "$IMAGE_URL/iNh3BivHyg5sQRPP1KOkzguEX0H.j",
                        "1994-09-23",
                        8.7F,
                        "Drama, Crime",
                        "",
                        false
                )
        )
        movies.add(
                MovieCatalogueEntity(
                        "238",
                        "English",
                        "Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito Corleone barely survives an attempt on his life, his youngest son, Michael steps in to take care of the would-be killers, launching a campaign of bloody revenge.",
                        "The Godfather",
                        "$IMAGE_URL/x5o8cLZfEXMoZczTYWLrUo1P7UJ.jpg",
                        "$IMAGE_URL/rSPw7tgCH9c6NqICZef4kZjFOQ5.jpg",
                        "1972-03-14",
                        8.7F,
                        "Drama, Crime",
                        "http://www.thegodfather.com/",
                        false
                )
        )
        movies.add(
                MovieCatalogueEntity(
                        "761053",
                        "English",
                        "The final part of the film adaption of the erotic romance novel Gabriel's Inferno written by an anonymous Canadian author under the pen name Sylvain Reynard.",
                        "Gabriel's Inferno Part III",
                        "$IMAGE_URL/fYtHxTxlhzD4QWfEbrC1rypysSD.jpg",
                        "$IMAGE_URL/fQq1FWp1rC89xDrRMuyFJdFUdMd.jpg",
                        "2020-11-19",
                        8.7F,
                        "Romance, Comedy",
                        "",
                        false
                )
        )
        return movies
    }

    fun generateDummyTvShow(): List<TvShowCatalogueEntity> {
        val tvShow = ArrayList<TvShowCatalogueEntity>()

        tvShow.add(
                TvShowCatalogueEntity(
                        "100",
                        "English",
                        "2004-05-10",
                        "I Am Not An Animal is an animated comedy series about the only six talking animals in the world, whose cosseted existence in a vivisection unit is turned upside down when they are liberated by animal rights activists.",
                        "I Am Not an Animal",
                        "$IMAGE_URL/qG59J1Q7rpBc1dvku4azbzcqo8h.jpg",
                        "",
                        9.4F,
                        "Animation, Comedy",
                        "https://www.bbc.co.uk/programmes/p011fgk5",
                        "",
                        false
                )
        )
        tvShow.add(
                TvShowCatalogueEntity(
                        "88040",
                        "Japanese",
                        "2004-05-10",
                        "Tightly clutching his Gibson guitar, Mafuyu Satou steps out of his dark apartment to begin another day of his high school life. While taking a nap in a quiet spot on the gymnasium staircase, he has a chance encounter with fellow student Ritsuka Uenoyama, who berates him for letting his guitar's strings rust and break. Noticing Uenoyama's knowledge of the instrument, Satou pleads for him to fix it and to teach him how to play. Uenoyama eventually agrees and invites him to sit in on a jam session with his two band mates: bassist Haruki Nakayama and drummer Akihiko Kaji.",
                        "Given",
                        "$IMAGE_URL/pdDCcAq8RNSZNk81PXYoHNUPHjn.jpg",
                        "$IMAGE_URL/7gbmM2NWcqZONbp65HUWDf4wr0Q.jpg",
                        9.2F,
                        "Animation, Drama",
                        "http://given-anime.com/",
                        "Japan",
                        false
                )
        )
        tvShow.add(
                TvShowCatalogueEntity(
                        "83097",
                        "Japanese",
                        "2019-01-11",
                        "Surrounded by a forest and a gated entrance, the Grace Field House is inhabited by orphans happily living together as one big family, looked after by their \"Mama,\" Isabella. Although they are required to take tests daily, the children are free to spend their time as they see fit, usually playing outside, as long as they do not venture too far from the orphanage — a rule they are expected to follow no matter what. However, all good times must come to an end, as every few months, a child is adopted and sent to live with their new family... never to be heard from again.\n\nHowever, the three oldest siblings have their suspicions about what is actually happening at the orphanage, and they are about to discover the cruel fate that awaits the children living at Grace Field, including the twisted nature of their beloved Mama.",
                        "The Promised Neverland",
                        "$IMAGE_URL/oBgRCpAbtMpk1v8wfdsIph7lPQE.jpg",
                        "$IMAGE_URL/uAjMQlbPkVHmUahhCouANlHSDW2.jpg",
                        9.1F,
                        "Animation, Mystery, Sci-Fi & Fantasy, Action & Adventure, Drama",
                        "https://neverland-anime.com",
                        "Japan",
                        false
                )
        )
        tvShow.add(
                TvShowCatalogueEntity(
                        "83095",
                        "Japanese",
                        "2019-01-09",
                        "Iwatani Naofumi was summoned into a parallel world along with 3 other people to become the world's Heroes. Each of the heroes respectively equipped with their own legendary equipment when summoned, Naofumi received the Legendary Shield as his weapon. Due to Naofumi's lack of charisma and experience he's labeled as the weakest, only to end up betrayed, falsely accused, and robbed by on the third day of adventure. Shunned by everyone from the king to peasants, Naofumi's thoughts were filled with nothing but vengeance and hatred. Thus, his destiny in a parallel World begins...",
                        "The Rising of the Shield Hero",
                        "$IMAGE_URL/6cXf5EDwVhsRv8GlBzUTVnWuk8Z.jpg",
                        "$IMAGE_URL/qSgBzXdu6QwVVeqOYOlHolkLRxZ.jpg",
                        9.1F,
                        "Animation, Action & Adventure, Sci-Fi & Fantasy",
                        "http://shieldhero-anime.jp/",
                        "Japan",
                        false
                )
        )
        tvShow.add(
                TvShowCatalogueEntity(
                        "61663",
                        "Japanese",
                        "2014-10-10",
                        "Kousei Arima was a genius pianist until his mother's sudden death took away his ability to play. Each day was dull for Kousei. But, then he meets a violinist named Kaori Miyazono who has an eccentric playing style. Can the heartfelt sounds of the girl's violin lead the boy to play the piano again?",
                        "Your Lie in April",
                        "$IMAGE_URL/IGbeFv5Ji4W0x530JcSHiQpamY.jpg",
                        "$IMAGE_URL/x6jWDL4H9TaBLGEvyej0qKiirBU.jpg",
                        9F,
                        "Animation, Comedy, Drama",
                        "http://www.kimiuso.jp/",
                        "Japan",
                        false
                )
        )
        return tvShow
    }

    fun generateDummyDetailTvShow(): TvShowCatalogueEntity {
        return TvShowCatalogueEntity(
                "100",
                "English",
                "2004-05-10",
                "I Am Not An Animal is an animated comedy series about the only six talking animals in the world, whose cosseted existence in a vivisection unit is turned upside down when they are liberated by animal rights activists.",
                "I Am Not an Animal",
                "$IMAGE_URL/qG59J1Q7rpBc1dvku4azbzcqo8h.jpg",
                "",
                9.4F,
                "Animation, Comedy",
                "https://www.bbc.co.uk/programmes/p011fgk5",
                "",
                false
        )
    }

    fun generateDummyDetailMovie(): MovieCatalogueEntity {
        return MovieCatalogueEntity(
                "19404",
                "Hindi",
                "Raj is a rich, carefree, happy-go-lucky second generation NRI. Simran is the daughter of Chaudhary Baldev Singh, who in spite of being an NRI is very strict about adherence to Indian values. Simran has left for India to be married to her childhood fiancé. Raj leaves for India with a mission at his hands, to claim his lady love under the noses of her whole family. Thus begins a saga.",
                "Dilwale Dulhania Le Jayenge",
                "$IMAGE_URL/2CAL2433ZeIihfX1Hb2139CX0pW.jpg",
                "$IMAGE_URL/gNBCvtYyGPbjPCT1k3MvJuNuXR6.jpg",
                "1995-10-20",
                8.7F,
                "Comedy, Drama, Romance",
                "",
                false,
        )
    }
}