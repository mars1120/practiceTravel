package com.travel.app

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.travel.app.databinding.FragmentAttractionsBinding
import com.travel.app.homepage.HomepageViewModel
import com.travel.app.ui.attractionsdetail.AttractionsDetailScreen
import com.travel.app.ui.components.LoadingScreen
import com.travel.app.ui.newsdetail.ErrorScreen

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AttractionsFragment : Fragment(), MenuProvider {

    private var _binding: FragmentAttractionsBinding? = null
    private val homepageViewModel: HomepageViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAttractionsBinding.inflate(inflater, container, false)
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

        when {
            (uiState.isLoading || uiState.clickedItem == -1) -> {
                LoadingScreen()
            }

            else -> {
                uiState.attractions?.data?.getOrNull(uiState.clickedItem)?.let { item ->
                    AttractionsDetailScreen(
                        imageList = item.images.map { it.src },
                        name = item.name,
                        url = item.url,
                        desc = item.introduction,
                        modifier = Modifier.height(160.dp)
                    )
                } ?: ErrorScreen("Invalid data")
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