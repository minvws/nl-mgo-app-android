package nl.rijksoverheid.mgo.component.theme.composable.debugerror

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import java.io.PrintWriter
import java.io.StringWriter

@Composable
fun MgoDebugErrorButton(error: Throwable) {
    if (!LocalInspectionMode.current) {
        val viewModel: MgoDebugErrorButtonViewModel = hiltViewModel()
        if (viewModel.showButton) {
            MgoDebugErrorButtonContent(error)
        }
    }
}

@Composable
private fun MgoDebugErrorButtonContent(error: Throwable) {
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        Dialog(
            content = {
                Column(
                    modifier =
                        Modifier
                            .background(MaterialTheme.colors.background)
                            .padding(all = 16.dp),
                ) {
                    Text(
                        modifier =
                            Modifier
                                .weight(1f)
                                .verticalScroll(rememberScrollState()),
                        text = getStackTraceAsString(error),
                    )
                    TextButton(
                        modifier =
                            Modifier
                                .align(Alignment.End)
                                .padding(top = 16.dp),
                        onClick = { showDialog = false },
                    ) {
                        Text("Close")
                    }
                }
            },
            onDismissRequest = { showDialog = false },
        )
    }
    Button(onClick = { showDialog = true }) {
        Text(text = "Stacktrace")
    }
}

private fun getStackTraceAsString(throwable: Throwable): String {
    val stringWriter = StringWriter()
    val printWriter = PrintWriter(stringWriter)
    throwable.printStackTrace(printWriter)
    printWriter.flush()
    return stringWriter.toString()
}
