package net.mindzone.robroo.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import java.util.*
abstract class CustomTextWatcher(search_bar_parent: EditText) : TextWatcher {
    // Notice abstract class so we leave abstract method textWasChanged() for implementing class to define it
    private var myTextView // Remember EditText is a TextView, so this works for EditText also
            : TextView? = null
    fun AddressTextWatcher(tView: TextView?) { // Notice I'm passing a view at the constructor, but you can pass other variables or whatever you need
        myTextView = tView
    }
    private var timer = Timer()
    private val DELAY = 500 // Milliseconds of delay for timer
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(s: Editable?) {
        timer.cancel()
        timer = Timer()
        timer.schedule(
            object : TimerTask() {
                override fun run() {
                    textWasChanged()
                }
            },
            DELAY .toLong()
        )
    }

    abstract fun textWasChanged() // Notice the abstract method to leave the
    // implementation to the implementing class
}