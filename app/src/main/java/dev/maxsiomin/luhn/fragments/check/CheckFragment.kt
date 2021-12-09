package dev.maxsiomin.luhn.fragments.check

import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.text.isDigitsOnly
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.maxsiomin.luhn.R
import dev.maxsiomin.luhn.databinding.FragmentCheckBinding
import dev.maxsiomin.luhn.extensions.clearError
import dev.maxsiomin.luhn.extensions.disableScreenshots
import dev.maxsiomin.luhn.extensions.enableScreenshots
import dev.maxsiomin.luhn.extensions.isNotNullOrBlank
import dev.maxsiomin.luhn.fragments.base.BaseViewModel
import dev.maxsiomin.luhn.util.Luhn
import dev.maxsiomin.luhn.util.SecureFragment
import timber.log.Timber

@AndroidEntryPoint
class CheckFragment : SecureFragment(R.layout.fragment_check) {

    private lateinit var binding: FragmentCheckBinding

    override val mRoot get() = binding.root

    private val mViewModel by viewModels<BaseViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().disableScreenshots()

        binding = FragmentCheckBinding.bind(view)

        with (binding) {

            cardNumberText.addTextChangedListener {
                cardNumberTextLayout.clearError()
                // Hide textView that shows checking result
                resultTextView.visibility = View.INVISIBLE
            }

            checkButton.setOnClickListener {
                super.onRootClicked(root)
                onCheckButtonClicked()
            }
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (binding.cardNumberText.text.isNotNullOrBlank())
            onCheckButtonClicked()
    }

    private fun onCheckButtonClicked() {
        Timber.d("onCheckButtonClicked called")
        var cardNumber = binding.cardNumberText.text.toString()

        // Allowed symbols. Add more if required
        setOf("-", " ").forEach {
            do {
                cardNumber = cardNumber.replace("-", "")
            } while (it in cardNumber)
        }


        if (cardNumber.length == 16 && cardNumber.isDigitsOnly()) {
            val isValid = Luhn.validate(cardNumber)

            @ColorRes
            val color: Int

            @StringRes
            val text: Int

            if (isValid) {
                color = R.color.green
                text = R.string.card_ok
            } else {
                color = R.color.red
                text = R.string.card_wrong
            }

            binding.resultTextView.apply {
                setTextColor(mViewModel.getColor(color))
                setText(text)
                visibility = View.VISIBLE
            }
        } else {
            binding.cardNumberTextLayout.error = mViewModel.getString(R.string.incorrect_format)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().enableScreenshots()
    }
}
