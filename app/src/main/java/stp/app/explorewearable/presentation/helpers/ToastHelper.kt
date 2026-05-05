package stp.app.explorewearable.presentation.helpers

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import kotlin.time.Duration

object ToastHelper {
    fun show(context: Context, text: String, duration: Int = Toast.LENGTH_SHORT) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(
                context,
                text,
                duration
            ).show()
        }
    }
}