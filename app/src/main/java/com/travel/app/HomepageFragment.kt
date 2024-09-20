package com.travel.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.LocaleListCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import com.travel.app.databinding.FragmentHomepageBinding
import com.travel.app.homepage.HomepageViewModel
import com.travel.app.ui.overview.OverviewScreen
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.travel.app.ui.components.LoadingScreen
import com.travel.app.utils.toExtendedLanguageCode
import java.util.Locale

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
    private fun initData(viewModel: HomepageViewModel) {
        val isLoading by viewModel.isLoading.observeAsState(initial = true)
        val dataStateA by viewModel.travelnewsResult.observeAsState()
        val dataStateB by viewModel.attractionsResult.observeAsState()


        val dataA = remember(dataStateA) {
            dataStateA?.getOrNull()?.data
        }
        val dataB = remember(dataStateB) {
            dataStateB?.getOrNull()?.data
        }

        if (isLoading) {
            LoadingScreen()
        } else {
            OverviewScreen(dataA = dataA ?: emptyList(), dataB = dataB ?: emptyList(),
                onClickNews = { index ->
                    homepageViewModel.setCurrentTitle(getString(R.string.title_news_detail))
                    viewModel.setClickedItem(index)
                    findNavController().navigate(R.id.action_HomepageFragment_to_NewsDetailFragment)
                },
                onClickAttraction = { index ->
                    homepageViewModel.setCurrentTitle(getString(R.string.title_travel_info))
                    viewModel.setClickedItem(index)
                    findNavController().navigate(R.id.action_HomepageFragment_to_AttractionsDetailFragment)
                }
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        homepageViewModel.fetchData(Locale.getDefault().language.toExtendedLanguageCode())
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
            R.id.action_en -> {
                if (Locale.getDefault().language.toExtendedLanguageCode() == "en")
                    return false
                homepageViewModel.setLangChanging(true)
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(
                        "en"
                    )
                )
                true
            }

            R.id.action_zh -> {
                if (Locale.getDefault().language.toExtendedLanguageCode() == "zh_tw")
                    return false
                homepageViewModel.setLangChanging(true)
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(
                        "zh"
                    )
                )
                true
            }


            else -> false
        }
    }

}