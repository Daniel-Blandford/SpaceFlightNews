package com.danielblandford.spaceflightnews.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Path
import android.widget.ImageView
import kotlinx.coroutines.*
import java.io.IOException
import java.net.URL
import kotlin.math.min

object UrlBitmap {

    /**
     * Download an image from a URL and assign it to an ImageView
     * Commented out but remains here for reference
     */
    /*fun urlToBitmap(url: String, imageView: ImageView) {

        // url of image
        val urlImage = URL(url)

        // async task to get bitmap from url
        val result: Deferred<Bitmap?> = GlobalScope.async {
            urlImage.toBitmap()
        }

        GlobalScope.launch(Dispatchers.Main) {
            // show bitmap on image view when available
            val bitmap: Bitmap? = result.await()
            imageView.setImageBitmap(bitmap)
        }
    }*/

    /**
     * Download an image from a URL, modify it to be displayed as a circular image and assign it to an ImageView
     */
    @DelicateCoroutinesApi
    fun urlToBitmapCircle(url: String, imageView: ImageView) {

        // url of image
        val urlImage = URL(url)

        // async task to get bitmap from url
        val result: Deferred<Bitmap?> = GlobalScope.async {
            urlImage.toBitmap()
        }

        GlobalScope.launch(Dispatchers.Main) {
            // show bitmap on image view when available
            val bitmap: Bitmap? = result.await()
            // crop specified size circular area from bitmap
            bitmap?.cropCircularArea(1000)?.apply {
                imageView.setImageBitmap(this)
            }
        }
    }

    private fun Bitmap.cropCircularArea(
        diameter:Int = min(width,height)
    ): Bitmap?{
        val bitmap = Bitmap.createBitmap(
            width, // width in pixels
            height, // height in pixels
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)

        // create a circular path
        val path = Path()
        val length = min(diameter, min(width,height))
        val radius = length / 2F // in pixels
        path.apply {
            addCircle(
                width/2f,
                height/2f,
                radius,
                Path.Direction.CCW
            )
        }

        // draw circular bitmap on canvas
        canvas.clipPath(path)
        canvas.drawBitmap(this,0f,0f,null)

        val x = (width - length)/2
        val y = (height - length)/2

        // return cropped circular bitmap
        return Bitmap.createBitmap(
            bitmap, // source bitmap
            x, // x coordinate of the first pixel in source
            y, // y coordinate of the first pixel in source
            length, // width
            length // height
        )
    }

    private fun URL.toBitmap(): Bitmap?{
        return try {
            BitmapFactory.decodeStream(openStream())
        }catch (e: IOException){
            null
        }
    }
}