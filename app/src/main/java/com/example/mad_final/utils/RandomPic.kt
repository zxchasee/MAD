package com.example.mad_final.utils

import com.example.mad_final.R

class RandomPic {
    companion object {
        fun getRandomImage(): Int {
            val images = listOf(
                R.drawable.thumbnail,
                R.drawable.superlight,
                R.drawable.benq,
                R.drawable.dxracer,
                R.drawable.mousepad_removebg_preview,
                R.drawable.varmillo,
                R.drawable.webcam,
            )

            return images.random()
        }
    }
}
