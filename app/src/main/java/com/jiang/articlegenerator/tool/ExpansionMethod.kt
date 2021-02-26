package com.jiang.articlegenerator.tool


import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jiang.articlegenerator.ui.activity.MainActivity

fun Fragment.navigateByActionId(id: Int) {
    (requireActivity() as MainActivity).navigateByActionId(id)
}

fun Activity.toast(stringId: Int) {
    Toast.makeText(applicationContext, stringId, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(stringId: Int) {
    Toast.makeText(requireActivity().applicationContext, stringId, Toast.LENGTH_SHORT).show()
}
fun Fragment.toast(text: CharSequence) {
    Toast.makeText(requireActivity().applicationContext, text, Toast.LENGTH_SHORT).show()
}