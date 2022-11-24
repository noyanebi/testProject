package com.test.presentation.base

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<V : BaseViewModel, B : ViewDataBinding> : Fragment(), BaseViewGroup<V, B>, ToolbarManager, ProgressBarManager {

    override lateinit var binding: B
    var v: View? = null
    val TAG: String = javaClass.name

    abstract var title: String
    abstract var menuId: Int
    override var toolbar: Toolbar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (v == null) {
            binding = DataBindingUtil.inflate(inflater, layoutId, null, false)
            if (menuId > 0) {
                setHasOptionsMenu(true)
            }
            binding.setLifecycleOwner(this)
            v = binding.root
        }
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (toolBarId > 0) {
            toolbar = view.findViewById(toolBarId)
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
            toolbar?.title = title
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel.progressBar = progressBar

        with(viewModel) {
            commonMessage.observe(viewLifecycleOwner) {
                it?.let { message ->
                    showMessage(message)
                }
            }

            commonMessageResource.observe(viewLifecycleOwner) {
                it?.let {
                    showMessage(
                        resources.getString(it)
                    )
                }
            }

            hideProgressBar()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        if (menuId != 0) {
            inflater.inflate(menuId, menu)
        }
        return super.onCreateOptionsMenu(menu, inflater)
    }

    fun showMessage(message: String, duration: Int = CommonToast.LENGTH_LONG) {
        context?.let {
            CommonToast.makeToast(it, message, duration).show()
        }
    }

    fun showShortSnackBar(message: String) {
        showSnackBar(message, Snackbar.LENGTH_SHORT)
    }

    fun showLongSnackBar(message: String) {
        showSnackBar(message, Snackbar.LENGTH_LONG)
    }

    fun showIndefiniteSnackBar(message: String) {
        showSnackBar(message, Snackbar.LENGTH_INDEFINITE)
    }

    fun hasPermission(vararg permissions: String): Boolean {
        if (context != null) {
            for (permission in permissions) {
                if (checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED)
                    return false
            }
        }
        return true
    }

    private fun showSnackBar(message: String, duration: Int) {
        if (view == null) return

        val snackbar: Snackbar = Snackbar.make(this.view!!, message, duration)
        ViewCompat.setLayoutDirection(snackbar.view, ViewCompat.LAYOUT_DIRECTION_RTL)
        snackbar.show()
    }

    fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val DATA_LOADED = "DATA-LOADED"
    }
}