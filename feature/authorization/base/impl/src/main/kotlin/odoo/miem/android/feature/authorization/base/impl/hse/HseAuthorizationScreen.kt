package odoo.miem.android.feature.authorization.base.impl.hse

import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import timber.log.Timber

// TODO Description
@Composable
fun HseAuthorizationScreen(
    baseUrl: String,
    exitCondition: (currentUrl: String?, cookie: String?) -> Boolean = { _, _ -> false },
    setInvisible: () -> Unit = {}
) {
    val cookieManager by lazy { CookieManager.getInstance() }
    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null

    AndroidView(
        factory = { context ->
            WebView(context).apply {

                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        Timber.d("onPageStarted(): url - $url")
                        backEnabled = view.canGoBack()
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        cookieManager.getCookie(url).also { cookie ->
                            Timber.d("onPageFinished(): current url - $url, cookie - $cookie")
                            if (exitCondition(url, cookie)) {
                                removeAllViews()
                                destroy()
                            }
                        }
                    }
                }

                settings.apply {
                    setJavaScriptEnabled(true);
                    setDomStorageEnabled(true);
                    setDatabaseEnabled(true);
                }

                loadUrl(baseUrl)
                webView = this
            }
        },
        update = {
            it.loadUrl(baseUrl)
        },
        modifier = Modifier.fillMaxSize()
    )

    BackHandler {
        if (backEnabled) {
            webView?.goBack()
        } else {
            webView?.let {
                setInvisible()
                it.removeAllViews()
                it.destroy()
            }
        }
    }
}