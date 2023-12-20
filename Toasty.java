package com.presentation.utility.libraries.toasty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.CheckResult;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.R;


@SuppressLint("InflateParams")
public class Toasty {
    private static final Typeface LOADED_TOAST_TYPEFACE = Typeface.create("sans-serif-condensed", Typeface.NORMAL);
    private static Typeface currentTypeface = LOADED_TOAST_TYPEFACE;
    private static int textSize = 16; // in SP

    private static boolean tintIcon = true;
    private static boolean allowQueue = true;
    private static int toastGravity = -1;
    private static int xOffset = -1;
    private static int yOffset = -1;
    private static boolean supportDarkTheme = true;
    private static boolean isRTL = false;

    private static Toast lastToast = null;

    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    public static final int LENGTH_LONG = Toast.LENGTH_LONG;

    private Toasty() {
        // avoiding instantiation
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return custom(context, message, ToastyUtils.getDrawable(context, R.drawable.ic_check_white_24dp),
                ToastyUtils.getColor(context, R.color.normalColor), ToastyUtils.getColor(context, R.color.colorWhite),
                duration, false, true);
    }


    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, ToastyUtils.getDrawable(context, R.drawable.ic_check_white_24dp),
                ToastyUtils.getColor(context, R.color.successColor), ToastyUtils.getColor(context, R.color.colorWhite),
                duration, withIcon, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, ToastyUtils.getDrawable(context, R.drawable.ic_toast_warning),
                ToastyUtils.getColor(context, R.color.errorColor), ToastyUtils.getColor(context, R.color.colorWhite),
                duration, withIcon, true);
    }

    @SuppressLint("ShowToast")
    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull CharSequence message, Drawable icon,
                               @ColorInt int tintColor, @ColorInt int textColor, int duration,
                               boolean withIcon, boolean shouldTint) {
        final Toast currentToast = Toast.makeText(context, "", duration);
        final View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.toast_layout, null);
        final LinearLayout toastRoot = toastLayout.findViewById(R.id.toast_root);
        final ImageView toastIcon = toastLayout.findViewById(R.id.toast_icon);
        final TextView toastTextView = toastLayout.findViewById(R.id.toast_text);
        Drawable drawableFrame;
        drawableFrame = ToastyUtils.tint9PatchDrawableFrame(context, tintColor);

        ToastyUtils.setBackground(toastLayout, drawableFrame);

        if (withIcon) {
            if (icon == null)
                throw new IllegalArgumentException("Avoid passing 'icon' as null if 'withIcon' is set to true");
            if (isRTL && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                toastRoot.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            ToastyUtils.setBackground(toastIcon, tintIcon ? ToastyUtils.tintIcon(icon, textColor) : icon);
        } else {
            toastIcon.setVisibility(View.GONE);
        }

        toastTextView.setText(message);
        toastTextView.setTextColor(textColor);
        toastTextView.setTypeface(currentTypeface);
        toastTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

        currentToast.setView(toastLayout);

        if (!allowQueue) {
            if (lastToast != null)
                lastToast.cancel();
            lastToast = currentToast;
        }

        // Make sure to use default values for non-specified ones.
        currentToast.setGravity( Gravity.TOP,0,0
//                toastGravity == -1 ? currentToast.getGravity() : toastGravity,
//                xOffset == -1 ? currentToast.getXOffset() : xOffset,
//                yOffset == -1 ? currentToast.getYOffset() : yOffset
        );

        return currentToast;
    }

    public static class Config {
        private Typeface typeface = Toasty.currentTypeface;
        private int textSize = Toasty.textSize;

        private boolean tintIcon = Toasty.tintIcon;
        private boolean allowQueue = true;
        private int toastGravity = Gravity.TOP;//Toasty.toastGravity;
        private int xOffset = Toasty.xOffset;
        private int yOffset = Toasty.yOffset;
        private boolean supportDarkTheme = true;
        private boolean isRTL = false;

        private Config() {
            // avoiding instantiation
        }

        @CheckResult
        public static Config getInstance() {
            return new Config();
        }

        public static void reset() {
            Toasty.currentTypeface = LOADED_TOAST_TYPEFACE;
            Toasty.textSize = 16;
            Toasty.tintIcon = true;
            Toasty.allowQueue = true;
            Toasty.toastGravity = -1;
            Toasty.xOffset = -1;
            Toasty.yOffset = -1;
            Toasty.supportDarkTheme = true;
            Toasty.isRTL = false;
        }

        @CheckResult
        public Config setToastTypeface(@NonNull Typeface typeface) {
            this.typeface = typeface;
            return this;
        }

        @CheckResult
        public Config setTextSize(int sizeInSp) {
            this.textSize = sizeInSp;
            return this;
        }

        @CheckResult
        public Config tintIcon(boolean tintIcon) {
            this.tintIcon = tintIcon;
            return this;
        }

        @CheckResult
        public Config allowQueue(boolean allowQueue) {
            this.allowQueue = allowQueue;
            return this;
        }

        @CheckResult
        public Config setGravity(int gravity, int xOffset, int yOffset) {
            this.toastGravity = gravity;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            return this;
        }

        @CheckResult
        public Config setGravity(int gravity) {
            this.toastGravity = gravity;
            return this;
        }

        @CheckResult
        public Config supportDarkTheme(boolean supportDarkTheme) {
            this.supportDarkTheme = supportDarkTheme;
            return this;
        }

        public Config setRTL(boolean isRTL) {
            this.isRTL = isRTL;
            return this;
        }

        public void apply() {
            Toasty.currentTypeface = typeface;
            Toasty.textSize = textSize;
            Toasty.tintIcon = tintIcon;
            Toasty.allowQueue = allowQueue;
            Toasty.toastGravity = toastGravity;
            Toasty.xOffset = xOffset;
            Toasty.yOffset = yOffset;
            Toasty.supportDarkTheme = supportDarkTheme;
            Toasty.isRTL = isRTL;
        }
    }
}
