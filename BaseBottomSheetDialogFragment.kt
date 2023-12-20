package com.presentation.bottomsheet

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import com.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * [BottomSheetDialogFragment] that uses a custom
 * theme which sets a rounded background to the dialog
 * and doesn't dim the navigation bar
 */
abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme)
    }

//    lateinit var params: CoordinatorLayout.LayoutParams
//    lateinit var view1: View


    abstract fun isTitleAvailable(): Boolean

//    abstract fun onReadyForView()

//    abstract fun getBinding(): View

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        if (isTitleAvailable()) {

        } else {

        }


//        view1 = getBinding()
//        dialog.setContentView(view1)

        //Set the coordinator layout behavior
//        params = (view1.parent as View).layoutParams as CoordinatorLayout.LayoutParams
//        val behavior = params.behavior

        //Set callback
//        if (behavior != null && behavior is BottomSheetBehavior<*>) {
//            behavior.setBottomSheetCallback(mBottomSheetBehaviorCallback)
//            //behavior.state = BottomSheetBehavior.PEEK_HEIGHT_AUTO
//        }

//        onReadyForView()
    }

    open val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    fun showMessage(message: String) {


    }
}
