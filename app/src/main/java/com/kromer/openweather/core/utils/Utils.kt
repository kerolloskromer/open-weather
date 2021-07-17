package com.kromer.openweather.core.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun showWarning(title: String, message: String, context: Context, okClick: () -> Unit) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)

        builder.setPositiveButton(android.R.string.ok) { dialog, which ->
            dialog.dismiss()
            okClick.invoke()
        }

        builder.setNegativeButton(android.R.string.cancel) { dialog, which ->
            dialog.dismiss()
        }

        builder.show()
    }

    fun getDate(timestamp: Long, context: Context): String? {
        val sdf = SimpleDateFormat("EEEE dd/MM/yyyy", context.resources.configuration.locale)
        val netDate = Date(timestamp * 1000)
        return sdf.format(netDate)
    }
}