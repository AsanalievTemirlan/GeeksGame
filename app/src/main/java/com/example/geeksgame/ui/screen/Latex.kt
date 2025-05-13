import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun MathFormulaView(
    latex: String,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                setBackgroundColor(0x00000000) // Прозрачный фон

                loadDataWithBaseURL(
                    null,
                    """
                    <!DOCTYPE html>
                    <html>
                    <head>
                      <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/katex@0.16.0/dist/katex.min.css">
                      <script src="https://cdn.jsdelivr.net/npm/katex@0.16.0/dist/katex.min.js"></script>
                      <style>
                        body {
                          margin: 0;
                          padding: 0;
                          background-color: transparent;
                          display: flex;
                          justify-content: center;
                          align-items: center;
                          height: 100%;
                          font-size: 22px;
                          color: white;
                        }
                      </style>
                    </head>
                    <body>
                      <div id="math"></div>
                      <script>
                        katex.render(String.raw`${latex}`, document.getElementById('math'), {
                          throwOnError: false
                        });
                      </script>
                    </body>
                    </html>
                    """.trimIndent(),
                    "text/html",
                    "utf-8",
                    null
                )
            }
        },
        modifier = modifier
    )
}
