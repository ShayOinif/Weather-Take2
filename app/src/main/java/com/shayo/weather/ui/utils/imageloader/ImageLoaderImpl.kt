package com.shayo.weather.ui.utils.imageloader

import android.content.Context
import android.widget.ImageView
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.shayo.news.utils.imageloader.ImageLoader
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class ImageLoaderImpl @Inject constructor(
    @ApplicationContext val appContext: Context
) : ImageLoader {
    private val imageLoader = coil.ImageLoader.Builder(appContext)
        .components {
            add(SvgDecoder.Factory())
        }
        .build()


    override fun load(imageResource: String, target: ImageView) {
        val request = ImageRequest.Builder(appContext)
            .data(imageResource)
            .crossfade(true)
            .target(target)
            .build()

        imageLoader.enqueue(
            request
        )

    }
}
