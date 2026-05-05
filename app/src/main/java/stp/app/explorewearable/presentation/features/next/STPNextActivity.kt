package stp.app.explorewearable.presentation.features.next

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MainScreen() {
    val context = LocalContext.current

    var receiverDeviceName by remember { mutableStateOf<String?>(null) }
    var isConnected by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            val deviceName = withContext(Dispatchers.IO) {
                STPNextController().getConnectedDeviceName(context)
            }

            receiverDeviceName = deviceName
            delay(2000)
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            val result = withContext(Dispatchers.IO) {
                STPNextController().checkConnection(context)
            }

            isConnected = result
            delay(2000)
        }
    }


    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold { padding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if (isConnected) "🟢 Paired ($receiverDeviceName)" else "🔴 Not Paired"
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        Log.d("WATCH_APP", "Next button clicked")
                        STPNextController().sendNext(context, scope, snackbarHostState)

                        CoroutineScope(Dispatchers.IO).launch {
                            val result = STPNextController().checkConnection(context)
                            withContext(Dispatchers.Main) {
                                isConnected = result
                            }
                        }
                    },
                ) {
                    Text("Next")
                }
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}