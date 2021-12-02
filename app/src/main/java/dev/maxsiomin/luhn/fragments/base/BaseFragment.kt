package dev.maxsiomin.luhn.fragments.base

import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.core.view.allViews
import androidx.fragment.app.Fragment
import dev.maxsiomin.luhn.extensions.imm

abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {

    abstract val mRoot: View

    override fun onStart() {
        super.onStart()
        mRoot.setOnClickListener { root ->
            onRootClicked(root)
        }
    }

    protected open fun onRootClicked(root: View) {
        root.allViews.forEach { v -> v.clearFocus() }
        imm.hideSoftInputFromWindow(root.windowToken, 0)
    }
}
