package com.capermint.testgullychamps.presentation.utility

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.StyleSpan
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.capermint.testgullychamps.R
import com.capermint.testgullychamps.presentation.core.BaseActivity
import com.capermint.testgullychamps.presentation.core.GlideApp
import com.capermint.testgullychamps.presentation.utility.AppConstant.Companion.INPUTFORMAT
import com.capermint.testgullychamps.presentation.utility.libraries.toasty.Toasty
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.makeramen.roundedimageview.RoundedImageView
import de.hdodenhof.circleimageview.CircleImageView
import gun0912.tedbottompicker.TedBottomPicker
import java.text.SimpleDateFormat
import java.util.*


fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

fun <T> lazyFast(operation: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) {
    operation()
}

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

fun Activity.startActivityCustom(intent: Intent, requestCode: Int? = 0) {
    if (requestCode != null) {
        this.startActivityForResult(intent, requestCode)
    } else {
        this.startActivity(intent)
    }
}

fun String.getChatDate(): String {
    return if (equals(TimeHelper.today)) "Today"
    else if (equals(TimeHelper.yesterday)) "Yesterday"
    else this
}

fun Fragment.startActivityCustom(intent: Intent, requestCode: Int? = 0) {
    if (requestCode != null) {
        this.startActivityForResult(intent, requestCode)
    } else {
        this.startActivity(intent)
    }
}

@BindingAdapter("cornerImageUrl")
fun loadCornerImageBanner(imageView: ImageView, url: String?) {
    GlideApp.with(imageView.context).load(url)
        .placeholder(R.drawable.splash_logo)
        .transform(RoundedCorners(15)).into(imageView)
}

fun AppCompatImageView.loadImage(
    image: Any?,
    placeholder: Int? = R.drawable.icn_placeholder_user
) {
    GlideApp.with(this.context)
        .load(image)
        .placeholder(placeholder!!)
        .into(this)
}

fun AppCompatImageView.loadImageBlueAvatar(
    image: Any?,
    placeholder: Int? = R.drawable.blur_avatar
) {
    GlideApp.with(this.context)
        .load(image)
        .placeholder(placeholder!!)
        .into(this)
}

fun AppCompatImageView.loadImagePreviewTeamA(
    image: Any?,
    placeholder: Int? = R.drawable.preview_team_a
) {
    GlideApp.with(this.context)
        .load(image)
        .placeholder(placeholder!!)
        .into(this)
}

fun AppCompatImageView.loadImagePreviewTeamB(
    image: Any?,
    placeholder: Int? = R.drawable.preview_team_b
) {
    GlideApp.with(this.context)
        .load(image)
        .placeholder(placeholder!!)
        .into(this)
}

fun AppCompatImageView.loadImagePlayer(
    image: Any?,
    placeholder: Int? = R.drawable.player_blue
) {
    GlideApp.with(this.context)
        .load(image)
        .placeholder(placeholder!!)
        .into(this)
}

fun AppCompatImageView.loadImagePlayerPink(
    image: Any?,
    placeholder: Int? = R.drawable.pink_avatar
) {
    GlideApp.with(this.context)
        .load(image)
        .placeholder(placeholder!!)
        .into(this)
}


fun AppCompatImageView.loadImageRound(
    image: Any?,
    placeholder: Int? = R.drawable.icn_placeholder_square
) {
    GlideApp.with(this.context)
        .load(image)
        .placeholder(placeholder!!)
        .circleCrop()
        .into(this)
}

fun RoundedImageView.loadImageRound(
    image: Any,
    placeholder: Int? = R.drawable.icn_placeholder_square
) {
    GlideApp.with(this.context)
        .load(image)
        .placeholder(placeholder!!)
        .circleCrop()
        .into(this)
}

fun CircleImageView.loadImageRound(
    image: Any,
    placeholder: Int? = R.drawable.icn_placeholder_user
) {
    GlideApp.with(this.context)
        .load(image)
        .placeholder(placeholder!!)
        .circleCrop()
        .into(this)
}

fun CircleImageView.loadImageRoundPlayer(
    image: Any,
    placeholder: Int? = R.drawable.player_blue
) {
    GlideApp.with(this.context)
        .load(image)
        .placeholder(placeholder!!)
        .circleCrop()
        .into(this)
}


fun RoundedImageView.loadImageRoundPlayer(
    image: Any,
    placeholder: Int? = R.drawable.player_blue
) {
    GlideApp.with(this.context)
        .load(image)
        .placeholder(placeholder!!)
        .circleCrop()
        .into(this)
}

fun CircleImageView.loadImagePlayer(
    image: Any,
    placeholder: Int? = R.drawable.player_blue
) {
    GlideApp.with(this.context)
        .load(image)
        .placeholder(placeholder!!)
        .circleCrop()
        .into(this)
}

fun CircleImageView.loadImageCreateTeamA(
    image: Any,
    placeholder: Int? = R.drawable.create_team_a
) {
    GlideApp.with(this.context)
        .load(image)
        .placeholder(placeholder!!)
        .circleCrop()
        .into(this)
}

fun CircleImageView.loadImageCreateTeamB(
    image: Any,
    placeholder: Int? = R.drawable.create_team_b
) {
    GlideApp.with(this.context)
        .load(image)
        .placeholder(placeholder!!)
        .circleCrop()
        .into(this)
}

fun Context.showToastNormal(message: String = "", longTime: Boolean = false) {
    if (longTime) {
        Toasty.normal(this, message, Toast.LENGTH_LONG).show()
    } else {
        Toasty.normal(this, message, Toast.LENGTH_SHORT).show()
    }
}

fun Context.showToastSuccess(message: String = "", longTime: Boolean = false) {
    if (longTime) {
        Toasty.success(this, message, Toast.LENGTH_LONG, false).show()
    } else {
        Toasty.success(this, message, Toast.LENGTH_SHORT, false).show()
    }
}



fun Context.showToastError(message: String = "", longTime: Boolean = false) {
    if (longTime) {
        Toasty.error(this, message, Toast.LENGTH_LONG, true).show()
    } else {
        Toasty.error(this, message, Toast.LENGTH_SHORT, true).show()
    }
}


/**
 * For show dialog
 *
 * @param title - title which shown in dialog (application name)
 * @param msg - message which shown in dialog
 * @param positiveText - positive button text
 * @param listener - positive button listener
 * @param negativeText - negative button text
 * @param negativeListener - negative button listener
 * @param icon - drawable icon which shown is dialog
 */
fun Context.showDialog(
    title: String? = this.resources.getString(R.string.app_name),
    msg: String,
    positiveText: String? = this.resources.getString(R.string.ok),
    listener: DialogInterface.OnClickListener? = null,
    negativeText: String? = this.resources.getString(R.string.cancel),
    negativeListener: DialogInterface.OnClickListener? = null,
    icon: Int? = null
) {
    if (BaseActivity.dialogShowing) {
        return
    }
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
    builder.setMessage(msg)
    builder.setCancelable(false)
    builder.setPositiveButton(positiveText) { dialog, which ->
        BaseActivity.dialogShowing = false
        listener?.onClick(dialog, which)
    }
    if (negativeListener != null) {
        builder.setNegativeButton(negativeText) { dialog, which ->
            BaseActivity.dialogShowing = false
            negativeListener.onClick(dialog, which)
        }
    }
    if (icon != null) {
        builder.setIcon(icon)
    }
    builder.create().show()
    BaseActivity.dialogShowing = true
}

/**
 * For validate email, mobile, password
 */
fun Context.isValidEmail(text: String): Boolean {
    return !TextUtils.isEmpty(text)
            && Patterns.EMAIL_ADDRESS.matcher(text).matches()
            && (text.length >= this.resources.getInteger(R.integer.min_length_email))
            && (text.length <= this.resources.getInteger(R.integer.max_length_email))
}

fun Context.isValidPhone(text: String): Boolean {
    return !TextUtils.isEmpty(text)
            && Patterns.PHONE.matcher(text).matches()
            && (text.length >= this.resources.getInteger(R.integer.min_length_mobile))
            && (text.length <= this.resources.getInteger(R.integer.max_length_mobile))
}

fun Context.isValidIFSCCode(text: String): Boolean {
    val regExp = "^[A-Za-z]{4}[a-zA-Z0-9]{7}\$"
    return !TextUtils.isEmpty(text)
            && text.matches(regExp.toRegex())
}

fun Context.isValidUPI(text: String): Boolean {
    val regExp = "^[a-zA-Z0-9.-]{2,256}@[a-zA-Z][a-zA-Z]{2,64}$"
    return !TextUtils.isEmpty(text)
            && text.matches(regExp.toRegex())
}

fun Context.isValidPassword(text: String): Boolean {
    return !TextUtils.isEmpty(text)
            && (text.length >= this.resources.getInteger(R.integer.min_length_password))
            && (text.length <= this.resources.getInteger(R.integer.max_length_password))
}

fun Context.isPasswordAndConfirmPasswordMatch(password: String, confirmPass: String): Boolean {
    return !TextUtils.isEmpty(password)
            && !TextUtils.isEmpty(confirmPass)
            && password.contentEquals(confirmPass)
}


fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }

                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }

                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
        return false
    }
    return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
}

fun Context.getDeviceTimeZone(): String {
    val timeZone: String = Calendar.getInstance().timeZone.id
    return timeZone
}

fun Activity.openPermissionSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts("package", this.packageName, null)
    intent.data = uri
    startActivityForResult(intent, AppConstant.INTENT_SETTINGS)
}

fun Context.getAndroidID(): String {
    return Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID)
}

fun Context.hideKeyboardFrom(view: View) {
    val imm: InputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun callFromDialer(mContext: Context, number: String) {
    try {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$number")
        mContext.startActivity(callIntent)
    } catch (e: Exception) {
        e.printStackTrace()
        mContext.showToastError(mContext.getString(R.string.something_went_wrong))
    }
}

fun getTimeAgo(time1: Long): String {
    val SECOND_MILLIS = 1000
    val MINUTE_MILLIS = 60 * SECOND_MILLIS
    val HOUR_MILLIS = 60 * MINUTE_MILLIS
    val DAY_MILLIS = 24 * HOUR_MILLIS

    var time = time1
    if (time < 1000000000000L) {
        time *= 1000
    }

    val now = Calendar.getInstance().time.time
    if (time > now || time <= 0) {
        return "in the future"
    }

    val diff = now - time
    return when {
        diff < 48 * HOUR_MILLIS -> "Yesterday"
        else -> "${diff / DAY_MILLIS} days ago"
    }
}

fun AppCompatTextView.setHtmlString(content: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.setText(HtmlCompat.fromHtml(content, HtmlCompat.FROM_HTML_MODE_LEGACY))
    } else {
        this.setText(Html.fromHtml(content))
    }
}


fun AppCompatActivity.showImagePicker(imageSelectedListener: TedBottomPicker.OnImageSelectedListener) {
    gun0912.tedbottompicker.TedBottomPicker.Builder(this)
        .setOnImageSelectedListener(imageSelectedListener)
        .setPeekHeight(600)
        .showTitle(true)
        .setPreviewMaxCount(10000)
        .setTitle(R.string.select_from_gallery as Int)
        .setCompleteButtonText(R.string.done)
        .setEmptySelectionText(R.string.select_images as Int)
        .showCameraTile(true)
        .showGalleryTile(false)
        .setImageProvider { imageView, imageUri ->
            /*  GlideApp.with(this)
                  .load(imageUri)
                  .placeholder(R.drawable.icn_placeholder_square)
                  .into(imageView)*/
        }
        .create()
        .show(this.supportFragmentManager)
}

fun String.timeDiffInMillisWithCurrent(): Long {
    val dateFormat = SimpleDateFormat(INPUTFORMAT)
    var futureDate = dateFormat.parse(this)
    return futureDate!!.time - Date().time

}


//@BindingAdapter("setDateFormate")
//internal fun parseDateToyyyy(editText: EditText, time: String): String{
//    val inputPattern = "MM-dd-yyyy"
//    val outputPattern = "yyyy"
//    val inputFormat = SimpleDateFormat(inputPattern)
//    val outputFormat = SimpleDateFormat(outputPattern)
//
//    var date: Date? = null
//    var str: String? = null
//
//    try {
//        date = inputFormat.parse(time)
//        str = outputFormat.format(date)
//    } catch (e: ParseException) {
//        e.printStackTrace()
//    }
//
//    return str!!
//}

// Date Formatter
fun String.getFormattedDate2(): String {
    val fromDateFormat = SimpleDateFormat(AppConstant.YYYYMMDD_HHMMSS)
    val toDateFormat = SimpleDateFormat(AppConstant.DDMMMYYYYHHMM)
    try {
        val d = fromDateFormat.parse(this)
        val formatedDateString = toDateFormat.format(d)
        return formatedDateString
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return this
}

fun String.getFormattedDate_DDMMM(): String {
    val fromDateFormat = SimpleDateFormat(AppConstant.ZZZZZ)
    val toDateFormat = SimpleDateFormat(AppConstant.DDMMM)
    try {
        val d = fromDateFormat.parse(this)
        val formatedDateString = toDateFormat.format(d)
        return formatedDateString
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return this
}

fun String.getFormattedDate_HHMMAA(): String {
    val fromDateFormat = SimpleDateFormat(AppConstant.ZZZZZ)
    val toDateFormat = SimpleDateFormat(AppConstant.HHMMAA)
    try {
        val d = fromDateFormat.parse(this)
        val formatedDateString = toDateFormat.format(d)
        return formatedDateString
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return this
}

fun String.getFormattedDate_DDMMYYYY_Joda(): String {
    val fromDateFormat = SimpleDateFormat(AppConstant.ZZZZZ)
    val toDateFormat = SimpleDateFormat(AppConstant.HHMMAA)
    try {
        val d = fromDateFormat.parse(this)
        val formatedDateString = toDateFormat.format(d)
        return formatedDateString
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return this
}

fun ChipGroup.getSingleSelectedChip(onItemClick: (id: String, value: String) -> Unit) {
    val ids: List<Int> = checkedChipIds
    if (ids.isNotEmpty()) {
        val chip = findViewById<Chip>(ids[0])
        onItemClick.invoke(chip.id.toString(), chip.text.toString())
    } else {
        onItemClick.invoke("", "")
    }
}

fun makeSelector(primColor: Int, secColor: Int): ColorStateList {
    return ColorStateList(
        arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf()),
        intArrayOf(primColor, secColor)
    )
}


fun ImageView.loadImage(
    image: Any?,
    placeholder: Int? = R.drawable.icn_placeholder_user
) {
    Glide.with(this.context)
        .load(image)
        .placeholder(placeholder!!)
        .into(this)
}


fun Fragment.startActivityCustom2(intent: Intent, requestCode: Int? = 0) {
    if (requestCode != 0) {
        this.startActivityForResult(intent, requestCode!!)
    } else {
        this.startActivity(intent)
    }
}

fun String.roundOffDecimal(): String {
    return "%.3f".format(this)
}

fun Activity.isTimeAutomatic(finish: Boolean = false): Boolean {
    val result = Settings.Global.getInt(
        this.contentResolver, Settings.Global.AUTO_TIME, 0
    ) === 1

    val result1 = Settings.Global.getInt(
        this.contentResolver, Settings.Global.AUTO_TIME_ZONE, 0
    ) === 1

    return if (result and result1) true else {
        showDialog(
            "Alert",
            "Your phone date is inaccurate! \n Adjust your clock and  try again",
            getString(R.string.ok), { dialog, which ->
                if (finish) this.finish()
                startActivity(Intent(android.provider.Settings.ACTION_DATE_SETTINGS))
            }
        )
        false
    }
}


fun ImageView.markAsSelected(context: Context, isSelected: Boolean = false) {

    if (isSelected) {
        setImageResource(R.drawable.ic_radio_selected)
//        context.createSVGDynamicTheme(
//            this, R.drawable.ic_radio_selected,
//            listOf("outline")
//        )
    } else {
        setImageResource(R.drawable.radio_unselected)
    }

}


fun Activity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}


fun Activity.showKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.toggleSoftInput(0, 0);
    }
}

// show tooltip
fun showToolTipPopup(context: Context, v: View, msg: String) {
    var view = v
    val popupView = LayoutInflater.from(view.rootView.context)
        .inflate(R.layout.custom_goal_tooltip_popup_view, null)
    var imageviewTop = popupView.findViewById<ImageView>(R.id.imageviewTop)
    var imageviewBottom = popupView.findViewById<ImageView>(R.id.imageviewBottom)
    var message = popupView.findViewById<TextView>(R.id.textviewMessage)

    message.text = msg

    var popupWindow = PopupWindow(
        popupView, WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.WRAP_CONTENT, true
    )
    popupWindow.animationStyle = R.style.ToolTipPopupAnimation
    val values = IntArray(2)
    view.getLocationInWindow(values)
    val displayMetrics = context.resources.displayMetrics
    val height = displayMetrics.heightPixels * 0.5
    popupWindow.showAsDropDown(view, -dpToPx(85), -dpToPx(75))
    imageviewTop.visibility = View.GONE
    imageviewBottom.visibility = View.VISIBLE
}

fun dpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}


fun PackageManager.getPackageInfoCompat(packageName: String, flags: Int = 0): PackageInfo =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(flags.toLong()))
    } else {
        @Suppress("DEPRECATION") getPackageInfo(packageName, flags)
    }


