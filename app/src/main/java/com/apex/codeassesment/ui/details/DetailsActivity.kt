package com.apex.codeassesment.ui.details

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.apex.codeassesment.R
import com.apex.codeassesment.data.model.Coordinates
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.databinding.ActivityDetailsBinding
import com.apex.codeassesment.ui.location.LocationActivity
import com.apex.codeassesment.utils.loadImage
import com.apex.codeassesment.utils.openActivity
import dagger.hilt.android.AndroidEntryPoint

// TODO (3 points): Convert to Kotlin
// TODO (3 points): Remove bugs or crashes if any
// TODO (1 point): Add content description to images
// TODO (2 points): Add tests
@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailsBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)
        val user = intent.getParcelableExtra<User>("saved-user-key")

        // TODO (1 point): Use Glide to load images
        user?.picture?.large?.let {
            binding.detailsImage.loadImage(it)
        }
        val name = user?.name
        binding.detailsName.text = getString(R.string.details_name, name?.first, name?.last)
        binding.detailsEmail.text = getString(R.string.details_email, user?.gender)
        val coordinates = user?.location?.coordinates
        binding.detailsLocation.text =
            getString(R.string.details_location, coordinates?.latitude, coordinates?.longitude)
        binding.detailsLocationButton.setOnClickListener { x: View? ->
            coordinates?.let {
                navigateLocation(it)
            }
        }
    }

    private fun navigateLocation(coordinates: Coordinates) {
        openActivity(LocationActivity::class.java) {
            putString("user-latitude-key", coordinates.latitude)
            putString("user-longitude-key", coordinates.longitude)
        }
    }
}