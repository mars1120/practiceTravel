package com.travel.app

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.travel.app.databinding.FragmentNewsdetailBinding
import com.travel.app.homepage.HomepageViewModel
import com.travel.app.ui.newsdetail.NewsDetailScreen
import com.travel.app.ui.newsdetail.ErrorScreen
import com.travel.app.ui.components.LoadingScreen

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NewsDetailFragment : Fragment(), MenuProvider {

    private var _binding: FragmentNewsdetailBinding? = null
    private val homepageViewModel: HomepageViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNewsdetailBinding.inflate(inflater, container, false)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.composeView.apply {

            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    initData(homepageViewModel)
                }
            }
        }
        return binding.root

    }

    @Composable
    fun initData(viewModel: HomepageViewModel) {
        val uiState by viewModel.uiState.collectAsState()
        val context = LocalContext.current
        //this adjudgment like this travelNewsResult?.getOrNull()?.data?.getOrNull(clickedItemIndex!!)?.let { item ->
        val currentNews by remember {
            derivedStateOf {
                val clickedItem = uiState.clickedItem
                val newsData = uiState.travelNews?.data
                if (clickedItem != -1 && newsData != null) {
                    newsData.getOrNull(clickedItem)
                } else {
                    null
                }
            }
        }

        when {
            uiState.isLoading -> {
                LoadingScreen()
            }

            currentNews == null -> {
                ErrorScreen("No data available")
            }

            else -> {
                NewsDetailScreen(
                    item = currentNews!!,
                    onClickLink = { url ->
                        val builder = CustomTabsIntent.Builder()
                        val customTabsIntent = builder.build()
                        customTabsIntent.launchUrl(context, Uri.parse(url))
                    }
                )
            }
        }
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
        homepageViewModel.setCurrentTitle(getString(R.string.title_travel_taipei))
        return false

    }
}