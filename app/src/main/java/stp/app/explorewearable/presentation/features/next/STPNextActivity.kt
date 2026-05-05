package stp.app.explorewearable.presentation.features.next

import android.R.attr.padding
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.Text

@Composable
fun MainScreen() {
    val context = LocalContext.current

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold { padding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Button(
                onClick = {
                    Log.d("WATCH_APP", "Next button clicked")
                    STPNextController().sendNext(context, scope, snackbarHostState)
                },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text("Next")
            }
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.Center) // ✅ OK
            )
        }
    }
}