package com.apex.codeassesment.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.apex.codeassesment.di.PreferenceInfo
import com.apex.codeassesment.ui.main.MainActivity
import com.apex.codeassesment.utils.SAVE_USER
import javax.inject.Inject

// TODO (2 point): Add tests
class PreferencesManager @Inject constructor(
  val context: Context,
  @PreferenceInfo prefName: String?
) : PreferencesHelper {

  private val mPrefs: SharedPreferences =
    context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

  override fun saveUser(user: String) {
    mPrefs.edit {
      putString(SAVE_USER, user)
    }
  }
  override fun loadUser(): String? {
    return mPrefs.getString(SAVE_USER, null)
  }
}
