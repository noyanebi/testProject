package com.test.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.test.R
import com.test.data.local.database.product.ProductEntity
import com.test.data.model.Product
import com.test.data.model.ProductResponse
import com.test.databinding.FragmentHomeBinding
import com.test.presentation.base.BaseFragment
import com.test.util.SnackbarMessage
import com.test.util.SnackbarUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override var title: String = "Home"
    override var menuId: Int = 0
    override val toolBarId: Int = 0
    override val layoutId: Int = R.layout.fragment_home
    override val progressBar: ProgressBar? = null
    override val viewModel: HomeViewModel by viewModels()

    private val mListAdapter: HomeAdapter? by lazy { HomeAdapter(ArrayList(0)){product -> } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModelHome = viewModel

        with(viewModel) {
            start()

            mSnackbarText.observe(viewLifecycleOwner, object : SnackbarMessage.SnackbarObserver {

                override fun onNewMessage(snackbarMessageResourceId: Int) {
                    SnackbarUtils.showSnackbar(view, getString(snackbarMessageResourceId))
                }
            })
        }
        binding.recyclerFragmentMain.adapter = mListAdapter
        binding.recyclerFragmentMain.hasFixedSize()
        mListAdapter?.callback= { contact ->


            val action = HomeFragmentDirections.actionHomeFragmentToProductFragment(arrayOf(contact))
            findNavController().navigate(action)


        }

    }
}