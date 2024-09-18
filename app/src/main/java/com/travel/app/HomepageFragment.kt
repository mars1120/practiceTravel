package com.travel.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.travel.app.databinding.FragmentHomepageBinding
import com.travel.app.homepage.HomepageViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomepageFragment : Fragment() {

    private var _binding: FragmentHomepageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomepageBinding.inflate(inflater, container, false)

        binding.composeView.apply {

            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // In Compose world
                MaterialTheme {
                    initData(HomepageViewModel())
                }
            }
        }
        return binding.root

    }

    @Composable
    fun initData(viewModel: HomepageViewModel) {
        val dataState = viewModel.result.observeAsState()
        dataState.value?.let {
            val orNull = it.getOrNull()
            if (orNull != null) {
            Greeting(orNull.total.toString())
        }
        }

    }

    @Composable
    fun Greeting(info: String) {
        Text(info)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}