import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object SnackbarHelper {
    fun show(
        scope: CoroutineScope,
        snackbarHostState: SnackbarHostState,
        message: String
    ) {
        scope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }
}