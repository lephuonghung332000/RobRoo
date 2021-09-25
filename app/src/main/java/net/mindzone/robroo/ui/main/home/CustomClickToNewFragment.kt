package net.mindzone.robroo.ui.main.home

import android.content.Context
import androidx.fragment.app.Fragment

interface CustomClickToNewFragment {
    fun onClickNewFragment (newFragment: Fragment?, context: Context)
}