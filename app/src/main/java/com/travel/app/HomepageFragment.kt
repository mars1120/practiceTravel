package com.travel.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import com.travel.app.databinding.FragmentHomepageBinding
import com.travel.app.homepage.HomepageViewModel
import com.travel.app.ui.overview.OverviewScreen
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomepageFragment : Fragment(), MenuProvider {

    private var _binding: FragmentHomepageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val homepageViewModel: HomepageViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomepageBinding.inflate(inflater, container, false)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        homepageViewModel.currentTitle.observe(viewLifecycleOwner) { title ->
            (requireActivity() as MainActivity).updateTitle(title)
        }
        homepageViewModel.setCurrentTitle(getString(R.string.title_travel_taipei))
        binding.composeView.apply {

            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // In Compose world
                MaterialTheme {
                    initData(homepageViewModel)
                }
            }
        }
        return binding.root

    }

    @Composable
    fun initData(viewModel: HomepageViewModel) {
        var isLoading by remember { mutableStateOf(true) }
        val dataStateA by viewModel.travelnewsResult.observeAsState()
        val dataStateB by viewModel.attractionsResult.observeAsState()

        LaunchedEffect(Unit) {
            viewModel.fetchData()
        }

        val dataA = remember(dataStateA) {
            dataStateA?.getOrNull()?.data
        }
        val dataB = remember(dataStateB) {
            dataStateB?.getOrNull()?.data
        }

        LaunchedEffect(dataA, dataB) {
            isLoading = dataA == null || dataB == null
        }

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            OverviewScreen(dataA = dataA ?: emptyList(), dataB = dataB ?: emptyList(),
                { index ->
                    homepageViewModel.setCurrentTitle(getString(R.string.title_news_detail))
                    viewModel.setClickedItem(index)
                    findNavController().navigate(R.id.action_HomepageFragment_to_NewsDetailFragment)
                }
            )
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
        menuInflater.inflate(R.menu.menu_main, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.nav_host_fragment_content_main -> {
                Snackbar.make(
                    requireActivity().window.decorView.rootView,
                    "setting click",
                    Snackbar.LENGTH_LONG
                ).show();
                true

            }
            else -> false
        }
    }

}