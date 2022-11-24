package com.test.presentation.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.test.R
import com.test.databinding.FragmentDetailBinding

import com.test.databinding.FragmentProductBinding
import com.test.presentation.base.BaseFragment
import com.test.presentation.product.ProductFragmentArgs
import com.test.util.SnackbarMessage
import com.test.util.SnackbarUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailViewModel, FragmentDetailBinding>() {
    override var title: String = "product"
    override var menuId: Int = 0
    override val toolBarId: Int = 0
    override val layoutId: Int = R.layout.fragment_detail
    override val progressBar: ProgressBar? = null
    override val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModelDetail = viewModel
        val url = args.url
        with(viewModel) {

            start(url)

            mSnackbarText.observe(viewLifecycleOwner, object : SnackbarMessage.SnackbarObserver {

                override fun onNewMessage(snackbarMessageResourceId: Int) {
                    SnackbarUtils.showSnackbar(view, getString(snackbarMessageResourceId))
                }
            })
        }



    }
}