package com.test.presentation.base

import com.google.android.material.appbar.AppBarLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem

interface ToolbarManager {
    val toolbar: Toolbar?
    val toolBarId: Int
    var toolbarTitle: String
        get() = toolbar?.title.toString()
        set(value) {
            toolbar?.title = value
        }

    fun attachToScroll(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val appBar = toolbar?.parent
                val scrollItem = if (appBar != null && appBar is AppBarLayout) appBar else toolbar
            }
        })
    }

    fun attachMenu(menuId: Int) {
        if (menuId == 0) {
            return
        }
        toolbar?.inflateMenu(menuId)
    }

    fun changeMenuItemVisibility(menuId: Int, visibility: Boolean) {
        toolbar?.menu?.findItem(menuId)?.isVisible = visibility
    }

    fun registerOnMenuClickListener(callback: (menuItem: MenuItem) -> Boolean) {
        toolbar?.setOnMenuItemClickListener {
            callback(it)
        }
    }
}