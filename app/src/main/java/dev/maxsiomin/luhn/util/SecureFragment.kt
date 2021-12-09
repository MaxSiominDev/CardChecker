package dev.maxsiomin.luhn.util

import android.content.Context
import androidx.annotation.LayoutRes
import dev.maxsiomin.luhn.extensions.disableScreenshots
import dev.maxsiomin.luhn.extensions.enableScreenshots
import dev.maxsiomin.luhn.fragments.base.BaseFragment

abstract class SecureFragment(@LayoutRes layoutRes: Int) : BaseFragment(layoutRes) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().disableScreenshots()
    }

    override fun onDetach() {
        requireActivity().enableScreenshots()
        super.onDetach()
    }
}
