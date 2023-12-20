package com.github.dhaval2404.imagepicker.util

import android.app.Dialog
import android.content.Context
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.github.dhaval2404.imagepicker.R
import com.github.dhaval2404.imagepicker.constant.ImageProvider
import com.github.dhaval2404.imagepicker.listener.DismissListener
import com.github.dhaval2404.imagepicker.listener.ResultListener

internal object DialogHelper {

    fun showChooseAppDialog(
        context: Context,
        listener: ResultListener<ImageProvider>,
        dismissListener: DismissListener?
    ) {
        val layoutInflater = LayoutInflater.from(context)
        val customView = layoutInflater.inflate(R.layout.dialog_choose_app, null)

        var dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(customView)

        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        dialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.window?.setBackgroundDrawableResource(R.color.colorTransparent)

        /*dialog = AlertDialog.Builder(context)
            .setTitle(R.string.title_choose_image_provider)
            .setView(customView)
            .setOnCancelListener {
                listener.onResult(null)
            }
            .setNegativeButton(R.string.action_cancel) { _, _ ->
                listener.onResult(null)
            }
            .setOnDismissListener {
                dismissListener?.onDismiss()
            }
            .show()*/

        // Handle Camera option click
        customView.findViewById<View>(R.id.lytCameraPick).setOnClickListener {
            listener.onResult(ImageProvider.CAMERA)
            dialog.dismiss()
        }

        // Handle Camera option click
        customView.findViewById<ImageView>(R.id.back).setOnClickListener {
            dialog.dismiss()
        }

        // Handle Gallery option click
        customView.findViewById<View>(R.id.lytGalleryPick).setOnClickListener {
            listener.onResult(ImageProvider.GALLERY)
            dialog.dismiss()
        }

        dialog.show()
    }
}
