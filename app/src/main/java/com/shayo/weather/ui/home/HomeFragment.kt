package com.shayo.weather.ui.home

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.shayo.news.utils.imageloader.ImageLoader
import com.shayo.weather.R
import com.shayo.weather.databinding.FragmentHomeBinding
import com.shayo.weather.ui.main.MainActivityViewModel
import com.shayo.weather.ui.utils.navigator.NavigatorParams
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeFragmentViewModel: HomeFragmentViewModel by viewModels()

    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

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

        setupMenu()

       /* lifecycleScope.launchWhenResumed {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                homeFragmentViewModel._status.collectLatest {
                    if (it is HomeFragmentState.OK) {
                        homeFragmentViewModel.getLocalWeather().collect {
                            with(binding) {
                                imageLoader.load(
                                    it.weather.icon,
                                    homeFragmentWeatherImageView
                                )
                                homeFragmentTempTextView.text = it.weather.temperature.toString()
                                homeFragmentDescTextView.text = it.weather.summary
                                homeFragmentLocationTextView.text = it.address
                            }
                        }
                    }
                }
            }
        }*/
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
                        mainActivityViewModel.resolveNavigation(
                            NavigatorParams(
                                this@HomeFragment,
                            )
                        )

                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onResume() {
        super.onResume()
       // homeFragmentViewModel.checkPermissions(this@HomeFragment)
    }
}