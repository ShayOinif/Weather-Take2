package com.shayo.weather.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.shayo.news.utils.imageloader.ImageLoader
import com.shayo.weather.R
import com.shayo.weather.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeFragmentViewModel: HomeFragmentViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //setupMenu()

        lifecycleScope.launchWhenResumed {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                homeFragmentViewModel.localWeatherFlow.collectLatest { localWeather ->
                    when (localWeather) {
                        is LocalWeather.NotEmpty -> {
                            val weather = localWeather.weatherWithAddress.weather
                            val address = localWeather.weatherWithAddress.address

                            with(binding) {
                                imageLoader.load(weather.fullIcon,
                                    homeFragmentWeatherImageView
                                )

                                Log.d("Shay", weather.icon)
                                Log.d("Shay", weather.fullIcon)
                                homeFragmentTempTextView.text = weather.temperature.toString()
                                homeFragmentDescTextView.text = weather.summary
                                homeFragmentLocationTextView.text = address
                            }
                        }
                        is LocalWeather.Empty -> binding.homeFragmentDescTextView.text = "No data yet"
                    }
                }
            }
        }
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

                menuInflater.inflate(R.menu.home_fragment_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                return when (menuItem.itemId) {
                    R.id.action_maps -> {
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}