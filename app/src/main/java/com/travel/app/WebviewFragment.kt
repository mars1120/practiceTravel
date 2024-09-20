package com.travel.app


import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.travel.app.databinding.FragmentWebviewBinding
import com.travel.app.homepage.HomepageViewModel
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class WebviewFragment : Fragment(), MenuProvider {

    private var _binding: FragmentWebviewBinding? = null
    private val homepageViewModel: HomepageViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var  webView:WebView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWebviewBinding.inflate(inflater, container, false)
        _binding?.webview?.let {
            webView=it
            var webSettings = it.getSettings()
            webSettings.setJavaScriptEnabled(true)
            it.setWebViewClient(WebViewClient())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            homepageViewModel.uiState.collect { state ->
                state.selectedUrl?.let { webView.loadUrl(it) }
            }
        }

        return binding.root

    }

    override fun onResume() {
        super.onResume()
        if (homepageViewModel.uiState.value.clickedItem == -1) {
            findNavController().popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        homepageViewModel.setCurrentTitle(getString(R.string.text_attractions))
        return false

    }
}