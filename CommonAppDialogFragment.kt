package com.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.databinding.DialogCommonBinding
import com.presentation.utility.gone
import com.presentation.utility.lazyFast

class CommonAppDialogFragment : BaseDialogFragment() {

    companion object {
        private const val KEY_TITLE = "title"
        private const val KEY_MESSAGE = "message"
        private const val KEY_POSITIVE = "positive"
        private const val KEY_NEGATIVE = "negative"

        fun showDialog(
            fragmentManager: FragmentManager,
            title: String?, message: String,
            positive: String, negative: String,
            positiveListener: () -> Unit = {},
            negativeListener: () -> Unit = {},
            closeListener: () -> Unit = {}
        ) {
            val generalAppDialogFragment = CommonAppDialogFragment().apply {
                arguments = bundleOf(
                    KEY_TITLE to title,
                    KEY_MESSAGE to message,
                    KEY_POSITIVE to positive,
                    KEY_NEGATIVE to negative
                )
            }

            generalAppDialogFragment.positiveListener = positiveListener
            generalAppDialogFragment.negativeListener = negativeListener
            generalAppDialogFragment.closeListener = closeListener
            generalAppDialogFragment.show(fragmentManager, CommonAppDialogFragment::class.java.simpleName)
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            manager.beginTransaction().apply {
                add(this@CommonAppDialogFragment, tag)
                commitAllowingStateLoss()
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }
    private lateinit var binding: DialogCommonBinding
    private lateinit var positiveListener: () -> Unit
    private lateinit var negativeListener: () -> Unit
    private lateinit var closeListener: () -> Unit

    private val title: String by lazyFast {
        val args = arguments ?: throw IllegalStateException("Missing arguments!")
        args.getString(KEY_TITLE, "")
    }

    private val message by lazyFast {
        val args = arguments ?: throw IllegalStateException("Missing arguments!")
        args.getString(KEY_MESSAGE, "")
    }

    private val positive by lazyFast {
        val args = arguments ?: throw IllegalStateException("Missing arguments!")
        args.getString(KEY_POSITIVE, "")
    }

    private val negative by lazyFast {
        val args = arguments ?: throw IllegalStateException("Missing arguments!")
        args.getString(KEY_NEGATIVE, "")
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        dialog?.window?.attributes?.windowAnimations = R.style.DialogSlideAnimation
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogCommonBinding.inflate(inflater, container, false)

        binding.textViewTitle.text = title
        binding.textViewMessage.text = message
        binding.btnYes.text = positive
        binding.btnNo.text = negative

        if (negative!!.isEmpty()) {
            binding.btnNo.gone()
        }

        binding.btnYes.setOnClickListener {
            positiveListener.invoke()
            dismissAllowingStateLoss()
        }

        binding.btnNo.setOnClickListener {
            negativeListener.invoke()
            dismissAllowingStateLoss()
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        closeListener.invoke()
    }
}
