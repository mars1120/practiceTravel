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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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

        homepageViewModel.currentTitle.observe(viewLifecycleOwner) { title ->
            (requireActivity() as MainActivity).updateTitle(title)
        }

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
        val clickedItemIndex by viewModel.clickedItem.observeAsState()
        val travelNewsResult by viewModel.travelnewsResult.observeAsState()

        when {
            clickedItemIndex == null || clickedItemIndex == -1 -> {
                LoadingScreen()
            }

            travelNewsResult == null -> {
                ErrorScreen("No data available")
            }

            else -> {
                var currentContext = LocalContext.current
                travelNewsResult?.getOrNull()?.data?.getOrNull(clickedItemIndex!!)?.let { item ->
                    NewsDetailScreen(item, onClickLink = {
                        val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
                        val customTabsIntent: CustomTabsIntent = builder.build()
                        customTabsIntent.launchUrl(currentContext, Uri.parse(it))
                    })
                } ?: ErrorScreen("Invalid data")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (homepageViewModel.clickedItem.value == -1) {
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
        (requireActivity() as MainActivity).updateTitle(getString(R.string.title_travel_taipei))
        return false

    }
}