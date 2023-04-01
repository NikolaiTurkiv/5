package com.test.a5.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.test.a5.utils.viewBinding

import com.test.a5.R
import com.test.a5.databinding.FragmentWebViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : Fragment(R.layout.fragment_web_view) {

    private val binding by viewBinding<FragmentWebViewBinding>()
    private var fileChooserCallback: ValueCallback<Array<Uri>?>? = null

    private val args: WebViewFragmentArgs by navArgs()

    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uriVk = "https://vk.com"
//        val home = Uri.parse("https://www.youtube.com/")
        binding.webView.settings.allowContentAccess = true
        binding.webView.settings.allowFileAccess = true
        binding.webView.settings.databaseEnabled = true
        binding.webView.settings.domStorageEnabled = true
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.setSupportZoom(true)
        binding.webView.settings.builtInZoomControls = true
        binding.webView.settings.displayZoomControls = false
        binding.webView.settings.loadsImagesAutomatically = true
        binding.webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        binding.webView.settings.setSupportMultipleWindows(true)


        binding.webView.webViewClient =
            object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    vw: WebView,
                    request: WebResourceRequest
                ): Boolean {
                    vw.loadUrl(request.url.toString())
                    return false
                }
            }

        binding.webView.webChromeClient =
            object : WebChromeClient() {
                override fun onPermissionRequest(request: PermissionRequest) {
                    request.grant(request.resources)
                }


                override fun onShowFileChooser(
                    vw: WebView?, filePathCallback: ValueCallback<Array<Uri?>?>,
                    fileChooserParams: FileChooserParams?
                ): Boolean {
                    fileChooserCallback?.onReceiveValue(null)
                    fileChooserCallback = filePathCallback as ValueCallback<Array<Uri>?>?
                    val selectionIntent = Intent(Intent.ACTION_GET_CONTENT)
                    selectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
                    selectionIntent.type = "*/*"
                    val chooserIntent = Intent(Intent.ACTION_CHOOSER)
                    chooserIntent.putExtra(Intent.EXTRA_INTENT, selectionIntent)
                    startActivityForResult(chooserIntent, 0)
                    return true
                }

                override fun onShowCustomView(
                    view: View?,
                    callback: CustomViewCallback?
                ) {
                    super.onShowCustomView(view, callback)
                    binding.webView.visibility = View.GONE
                    binding.customView.visibility = View.VISIBLE
                    binding.customView.addView(view)
                }

                override fun onHideCustomView() {
                    super.onHideCustomView()
                    binding.webView.visibility = View.VISIBLE
                    binding.customView.visibility = View.GONE
                }
            }
        binding.webView.setOnKeyListener { v, keyCode, event ->
            val vw = v as WebView
            if (event.action === KeyEvent.ACTION_DOWN && keyCode === KeyEvent.KEYCODE_BACK && vw.canGoBack()) {
                vw.goBack()
                return@setOnKeyListener true
            }
            false
        }
        binding.webView.setDownloadListener { uri, userAgent, contentDisposition, mimetype, contentLength ->
            handleURI(
                uri
            )
        }

        binding.webView.setOnLongClickListener { v ->
            handleURI((v as WebView).hitTestResult.extra)
            true
        }

//        binding.webView.setOnTouchListener { view, motionEvent ->
//            if(motionEvent.action == MotionEvent.ACTION_CANCEL){
//                handleURI((view as WebView).hitTestResult.extra)
//            }
//            Log.d("EEVENT", motionEvent.action.toString())
//
//            false
//        }

        if (args.site.isNotEmpty())
            binding.webView.loadUrl(args.site)

    }

    private fun handleURI(uri: String?) {
        if (uri != null) {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(uri.replaceFirst("^blob:".toRegex(), ""))
            startActivity(i)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fileChooserCallback?.onReceiveValue(arrayOf<Uri>(Uri.parse(data?.dataString)))
        fileChooserCallback = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            WebViewFragment()
    }
}
