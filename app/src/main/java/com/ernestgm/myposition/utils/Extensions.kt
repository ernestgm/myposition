package com.ernestgm.myposition.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.collection.SparseArrayCompat
import androidx.collection.forEach
//import androidx.databinding.DataBindingUtil
//import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import java.text.SimpleDateFormat
import java.util.*


inline fun <reified T : Activity> Activity.goToActivity(noinline init: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.init()
    startActivity(intent)
}

inline fun <reified T : Activity> Activity.goToActivityResult(
    requestCode: Int,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = Intent(this, T::class.java)
    intent.init()
    startActivityForResult(intent, requestCode)
}

inline fun <reified T : Activity> Fragment.goToActivityResult(
    requestCode: Int,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = Intent(activity, T::class.java)
    intent.init()
    activity?.startActivityForResult(intent, requestCode)
}

inline fun <reified T : Activity> Activity.goToNewActivity(noinline init: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    intent.init()
    startActivity(intent)
}

//inline fun <I, O, M : ResultMapper<I, O>> Data<I>.mapWith(factory: () -> M): Result<O> {
//    return when (this) {
//        is Data.Success -> factory().map(this.data!!)
//        is Data.Error -> factory().mapError(this.error)
//    }
//}
//
//inline fun <I, O, M : Mapper<I, O>> I.mapWith(factory: () -> M): O {
//    return factory().map(this)
//}
//
//fun String.tryParseInt(): Int? {
//    return try {
//        this.toInt()
//    } catch (ex: NumberFormatException) {
//        null
//    }
//}
//
//fun String.getInitials(default: String, delimiter: String): String {
//
//    var initials: String = default
//
//    if (this.isNotEmpty()) {
//        val aux = this.split(delimiter).toTypedArray()
//
//        aux.let {
//            val firstChar = aux[0].first().toUpperCase().toString()
//            initials = if (aux.count() > 1) {
//                val secondChar = aux[1].first().toUpperCase().toString()
//                firstChar.plus(secondChar)
//
//            } else {
//                firstChar
//            }
//        }
//    }
//
//    return initials
//}
//
//fun TabLayout.Tab.setStyleText(selected: Boolean) {
//    if (!selected) {
//        this.customView = null
//    } else {
//        if (this.customView == null) {
//            this.setCustomView(R.layout.layout_custom_selected_tab_text)
//        }
//    }
//}
//
//fun TabLayout.setStyleText(tab: TabLayout.Tab, typeFace: Typeface) {
//    if (tab.customView == null) {
//        tab.setCustomView(R.layout.layout_custom_tab_text)
//    }
//    val textView: TextView = tab.customView!!.findViewById(android.R.id.text1)
//    textView.typeface = typeFace
//}
//
//fun Date.getHourFormat(): String? {
//    return DatetimeUtils.formatHour(this)
//}
//
//fun <T> SparseArrayCompat<T>.values(): List<T> {
//    val list = ArrayList<T>()
//    forEach { _, value ->
//        list.add(value)
//    }
//    return list.toList()
//}
//
//fun Date.plusDays(days: Int): Date {
//    val calendar = Calendar.getInstance()
//    calendar.time = this
//    calendar.add(Calendar.DATE, days)
//    return calendar.time
//}
//
//fun Date.getNameDay(context: Context, showLabelToday: Boolean = true): String {
//    return if (showLabelToday && DateUtils.isToday(this.time)) {
//        context.getString(R.string.epg_label_today)
//    } else {
//        SimpleDateFormat(UtilsDate.DAYOFWEEK_PATTERN, Constants.Defaults.LOCALE_UY).format(this)
//            .capitalize()
//    }
//}
//
//fun Date.getFullNameDate(context: Context, showHours: Boolean = false): String {
//    val day = this.date.toString()
//    val mounth = SimpleDateFormat("MMMM", Constants.Defaults.LOCALE_UY).format(this)
//    val hours = SimpleDateFormat("HH:mm").format(this)
//
//    return if (showHours) {
//        val strDate = context.getString(R.string.coming_soon_publication_date_format, day, mounth)
//        context.getString(R.string.coming_soon_publication_date_v2_lbl, strDate, hours)
//    } else {
//        context.getString(R.string.coming_soon_publication_date_format, day, mounth)
//    }
//}
//
//fun Date.getDateAfterMonth(): Date {
//    val tomorrow = Calendar.getInstance()
//    tomorrow.time = this
//    tomorrow.add(Calendar.MONTH, 1)
//
//    return tomorrow.time
//}
//
//fun Boolean.toInt(): Int {
//    return if (this) {
//        1
//    } else {
//        0
//    }
//}
//
//fun List<Any>.hasSingleItem(): Boolean {
//    return this.size == 1
//}
//
////Returns date -x Mins in millis
//fun Date.beforeMins(prevReminder: Int): Long {
//    val calendar = Calendar.getInstance()
//    calendar.time = this
//    calendar.add(Calendar.MINUTE, prevReminder)
//    return calendar.timeInMillis
//}
//
//fun ViewGroup.inflate(@LayoutRes layout: Int): View {
//    return LayoutInflater.from(context).inflate(layout, this, false)
//}
//
//fun ViewGroup.inflateBinding(@LayoutRes layout: Int): ViewDataBinding {
//    return DataBindingUtil.inflate(LayoutInflater.from(context), layout, this, false)
//}
//
//fun View.setDimensions(width: Int, height: Int) {
//    this.layoutParams.width = width
//    this.layoutParams.height = height
//}
//
//fun Int.isEven(): Boolean {
//    return this % 2 == 0
//}
//
//fun Fragment.navigateTo(actionId: Int, args: Bundle? = null) {
//    activity?.let {
//        if (it is IFragmentNavigationHandler) {
//            it.goToFragment(actionId, args)
//        }
//    }
//}
//
//fun Fragment.navigateDirection(direction: NavDirections) {
//    findNavController().navigate(direction)
//}
//
//fun View.removeForeground() {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//        foreground = null
//    }
//}
//
//inline fun withGmsEnvironment(block: () -> Unit) {
//    if (BuildConfig.ENVIRONMENT == Environment.GPS) {
//        block()
//    }
//}
//
//inline fun <T> withGmsEnvironment(block: () -> T): T? {
//    return if (BuildConfig.ENVIRONMENT == Environment.GPS) {
//        block()
//    } else {
//        null
//    }
//}
//
//fun SplashActivity.getPushData(): String {
//    return this.intent.extras!!.getString(PUSH_CONTENT_EXTRA)!!
//}
//
//fun SplashActivity.hasPushData(): Boolean {
//    return intent.extras?.getString(PUSH_CONTENT_EXTRA)?.isNotEmpty() ?: false
//}

