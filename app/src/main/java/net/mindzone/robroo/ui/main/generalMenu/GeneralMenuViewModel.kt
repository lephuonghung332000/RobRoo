package net.mindzone.robroo.ui.main.generalMenu

import dagger.hilt.android.lifecycle.HiltViewModel
import net.mindzone.robroo.base.BaseFragmentVM
import net.mindzone.robroo.data.sharedpref.SharedPreferencesHelper
import javax.inject.Inject

@HiltViewModel
class GeneralMenuViewModel @Inject constructor(val sharedPreferencesHelper: SharedPreferencesHelper) :
    BaseFragmentVM() {
    fun deleteUser() {
        sharedPreferencesHelper.clearCurrentUserId()
    }

}