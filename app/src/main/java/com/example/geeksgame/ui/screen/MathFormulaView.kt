import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun MathFormulaView(
    latex: String,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = 100.dp),
    fontSizePx: Int = 18,
    textColor: String = "#FFFFFF",
    backgroundColor: String = "transparent",
    paddingPx: Int = 8,
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                setBackgroundColor(0x00000000)
            }
        },
        update = { webView ->
            val htmlContent = """
                <!DOCTYPE html>
                <html>
                <head>
                  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/katex@0.16.0/dist/katex.min.css">
                  <script src="https://cdn.jsdelivr.net/npm/katex@0.16.0/dist/katex.min.js"></script>
                  <style>
                    body {
                      margin: 0;
                      padding: ${paddingPx}px;
                      background-color: $backgroundColor;
                      font-size: ${fontSizePx}px;
                      color: $textColor;
                      white-space: normal !important;
                      overflow-wrap: break-word;
                      word-wrap: break-word;
                      hyphens: auto;
                      word-break: break-word;
                      max-width: 100%;
                    }
                    #math {
                      display: block;
                      white-space: normal !important;
                      overflow-wrap: break-word;
                      word-wrap: break-word;
                      hyphens: auto;
                      word-break: break-word;
                      max-width: 100%;
                    }
                  </style>
                </head>
                <body>
                  <div id="math"></div>
                  <script>
                    katex.render(String.raw`${latex}`, document.getElementById('math'), {
                      throwOnError: false,
                      displayMode: true
                    });
                  </script>
                </body>
                </html>
            """.trimIndent()

            webView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null)
        },
        modifier = modifier
    )
}
