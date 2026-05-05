package stp.app.explorewearable.presentation.features.next

import android.content.Context
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.CoroutineScope

class STPNextController {
    fun checkConnection(context: Context): Boolean {
        return try {
            val nodes = Tasks.await(
                Wearable.getNodeClient(context).connectedNodes
            )
            nodes.isNotEmpty()
        } catch (e: Exception) {
            Log.e("WATCH_APP", "Failed pair to device: $e")
            false
        }
    }

    fun getConnectedDeviceNames(context: Context): List<String> {
        return try {
            val nodes = Tasks.await(
                Wearable.getNodeClient(context).connectedNodes
            )

            Log.d("WATCH_APP", "Paired to devices: $nodes")

            nodes.map { it.displayName }
        } catch (e: Exception) {
            Log.e("WATCH_APP", "Failed get paired device name: $e")
            emptyList()
        }
    }
    fun sendNext(
        context: Context,
        scope: CoroutineScope,
        snackbarHostState: SnackbarHostState
    ) {
        Log.d("WATCH_APP", "sendNext() called")

        val nodeClient = Wearable.getNodeClient(context)

        Thread {
            try {
                val nodes = Tasks.await(nodeClient.connectedNodes)
                Log.d("WATCH_APP", "Connected nodes count: ${nodes.size}")
                if (nodes.isEmpty()) {
                    Log.w("WATCH_APP", "No connected nodes found")
                    // ToastHelper.show(context, "Watch belum terhubung ke phone")
                    SnackbarHelper.show(
                        scope,
                        snackbarHostState,
                        "Watch belum terhubung"
                    )
                    return@Thread
                }

                nodes.forEach { node ->
                    Log.d("WATCH_APP", "Sending message to: ${node.displayName}")
                    Wearable.getMessageClient(context)
                        .sendMessage(
                            node.id,
                            "/next_step",
                            "next234".toByteArray()
                        )
                    Log.d("WATCH_APP", "Message sent to: ${node.displayName}")
                }
                // ToastHelper.show(context, "Next berhasil dikirim")
                SnackbarHelper.show(
                    scope,
                    snackbarHostState,
                    "Next berhasil dikirim"
                )
            } catch (e: Exception) {
                Log.e("WATCH_APP", "Error sending message: ${e.message}", e)
            }
        }.start()
    }
}