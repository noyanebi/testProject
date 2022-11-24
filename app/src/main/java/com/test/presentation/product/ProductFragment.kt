package com.test.presentation.product

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.test.R

import com.test.databinding.FragmentProductBinding
import com.test.presentation.base.BaseFragment
import com.test.presentation.home.HomeFragmentDirections
import com.test.util.SnackbarMessage
import com.test.util.SnackbarUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ProductFragment : BaseFragment<ProductViewModel, FragmentProductBinding>() {
    override var title: String = "product"
    override var menuId: Int = 0
    override val toolBarId: Int = 0
    override val layoutId: Int = R.layout.fragment_product
    override val progressBar: ProgressBar? = null
    override val viewModel: ProductViewModel by viewModels()
    private val args: ProductFragmentArgs by navArgs()

    private val mListAdapter: ProductAdapter? by lazy { ProductAdapter(ArrayList(0)){product -> } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModelProduct = viewModel

        val subProduct = args.subProducts?.toList()

        with(viewModel) {
            subProduct?.let { start(it) }

            mSnackbarText.observe(viewLifecycleOwner, object : SnackbarMessage.SnackbarObserver {

                override fun onNewMessage(snackbarMessageResourceId: Int) {
                    SnackbarUtils.showSnackbar(view, getString(snackbarMessageResourceId))
                }
            })

        }
        binding.recyclerFragmentProduct.adapter = mListAdapter
        binding.recyclerFragmentProduct.hasFixedSize()
        mListAdapter?.callback= { contact ->

            val action = ProductFragmentDirections.actionProductFragmentToDetailFragment(contact.url as String)
            findNavController().navigate(action)

        }

    }
}