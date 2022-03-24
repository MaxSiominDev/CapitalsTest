package dev.maxsiomin.capitals.annotations

import android.widget.Toast
import androidx.annotation.IntDef

@Retention(AnnotationRetention.SOURCE)
@IntDef(Toast.LENGTH_SHORT, Toast.LENGTH_LONG)
annotation class ToastDuration

