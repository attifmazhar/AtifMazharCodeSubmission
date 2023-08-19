package com.apex.codeassesment.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.apex.codeassesment.R
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.databinding.ActivityMainBinding
import com.apex.codeassesment.ui.UserAdapter
import com.apex.codeassesment.ui.details.DetailsActivity
import com.apex.codeassesment.utils.NetworkResult
import com.apex.codeassesment.utils.loadImage
import com.apex.codeassesment.utils.onItemClickListener
import com.apex.codeassesment.utils.openActivity
import dagger.hilt.android.AndroidEntryPoint

// TODO (5 points): Move calls to repository to Presenter or ViewModel.
// TODO (5 points): Use combination of sealed/Dataclasses for exposing the data required by the view from viewModel .
// TODO (3 points): Add tests for viewmodel or presenter.
// TODO (1 point): Add content description to images
// TODO (3 points): Add tests
// TODO (Optional Bonus 10 points): Make a copy of this activity with different name and convert the current layout it is using in
//  Jetpack Compose.
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), onItemClickListener {


  val viewModel: MainViewModel by viewModels()
  private lateinit var binding: ActivityMainBinding
  private lateinit var adapter: UserAdapter
  private var randomUser: User = User()
    set(value) {
      // TODO (1 point): Use Glide to load images after getting the data from endpoints mentioned in RemoteDataSource
      // userImageView.setImageBitmap(refreshedUser.image)

      binding.mainName.text = value.name?.first
      binding.mainEmail.text = value.email
      field = value
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    // TODO (2 points): Convert to view binding
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    adapter = UserAdapter(this)
    binding.mainUserList.adapter = adapter


    binding.mainSeeDetailsButton.setOnClickListener {
      onClicked(randomUser)
    }

    binding.mainRefreshButton.setOnClickListener {
      viewModel.getUser(true)
    }

    binding.mainUserListButton.setOnClickListener {
      viewModel.getUserList()
    }

    setObservable()

    lifecycleScope.launchWhenStarted {
      viewModel.getUserList()
    }

  }

  fun setObservable() {

    viewModel.user.observe(this) {
      Log.e("atif", "atif test user data")
      // TODO (1 point): Use Glide to load images after getting the data from endpoints mentioned in RemoteDataSource
      it.picture?.medium?.let {
        binding.mainImage.loadImage(it)
      }
      binding.mainName.text = it.name?.first
      binding.mainEmail.text = it.email
      randomUser = it
    }


    viewModel.userList.observe(this) { response ->
      when (response) {
        is NetworkResult.Success -> {

          response.data?.let {
            adapter.addAllUser(it.results!!)
          }
        }

        is NetworkResult.Error -> {
          Toast.makeText(
            this,
            response.message,
            Toast.LENGTH_SHORT
          ).show()
        }
      }
    }
  }

  override fun onClicked(user: User) {
    navigateDetails(user)
  }

  // TODO (2 points): Convert to extenstion function.
  private fun navigateDetails(user: User) {
    openActivity(DetailsActivity::class.java) {
      putParcelable("saved-user-key", user)
    }
  }



}
