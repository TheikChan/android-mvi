package com.consistence.pinyin.kit

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.consistence.pinyin.R

class AppTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr)

class AppButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.style.ButtonPrimary
) : AppCompatButton(context, attrs, defStyleAttr) {

    override fun drawableStateChanged() {
        super.drawableStateChanged()

        alpha = if (isEnabled) {
            1.0f
        } else {
            0.4f
        }
    }
}