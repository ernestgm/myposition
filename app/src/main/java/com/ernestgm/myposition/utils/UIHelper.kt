package com.ernestgm.myposition.utils

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.ernestgm.myposition.R
import com.google.android.material.snackbar.Snackbar

private const val DEFAULT_MAX_LINES = 3

object UIHelper {


    fun showDialog(
        context: Context,
        @StringRes title: Int,
        message: String,
        positiveButtonLbl: Int,
        next: (() -> Unit)? = null
    ) {
        if (context is FragmentActivity) {
            val builder = AlertDialog.Builder(context)
                .setTitle(context.resources.getString(title))
                .setMessage(message)
                .setNegativeButton(context.resources.getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(context.resources.getString(positiveButtonLbl)) { dialog, _ ->
                    dialog.dismiss()

                    next?.let {
                        next()
                    }
                }
            builder.create().show()
        } else {
            Log.e(
                UIHelper::class.java.name,
                "showDialog debe ser invocado desde una FragmentActivity"
            )
        }
    }

    fun showSnackbar(context: Context?, view: View, message: String) {
        context?.run {
            val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            snackBar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.accent))
            snackBar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines =
                DEFAULT_MAX_LINES
            snackBar.show()
        }
    }
}

