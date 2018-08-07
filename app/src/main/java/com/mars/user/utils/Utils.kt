package com.mars.user.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.mars.user.R
import com.mars.user.app.GlideApp
import com.mars.user.constant.IS_LOGIN
import com.mars.user.constant.USER_ID
import com.mars.user.ui.login.login.LoginActivity
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by gu on 2018/07/18
 * desc: 通用的方法
 */


/**
 * 隐藏输入键盘
 */
fun hideSoftInput(act: Activity) {
    var imm: InputMethodManager? = act.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (imm != null) {
        imm.hideSoftInputFromWindow(act.window.decorView.windowToken, 0)
    }
}

/**
 * 显示输入键盘
 */
fun showSoftInput(activity: Activity) {
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
}

/**
 * 获取版本名
 *
 * @param context 上下文
 * @return 版本号
 */
fun getVersionName(context: Context): String {
    val verNameError = "get verName error"
    //获取包管理器
    val pm = context.packageManager
    //获取包信息
    try {
        val packageInfo = pm.getPackageInfo(context.packageName, 0)
        //返回版本号
        return packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }

    return verNameError
}

/**
 * 获取版本号
 *
 * @param context 上下文
 * @return 版本号
 */
fun getVersionCode(context: Context): Int {
    //获取包管理器
    val pm = context.packageManager
    //获取包信息
    try {
        val packageInfo = pm.getPackageInfo(context.packageName, 0)
        //返回版本号
        return packageInfo.versionCode
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }

    return 0
}

/**
 * 拨打电话
 *
 * @param telphone
 */
fun callTelphone(telphone: String, activity: Activity) {
    val intent3 = Intent(Intent.ACTION_DIAL)
    intent3.data = Uri.parse("tel:$telphone")
    activity.startActivity(intent3)
}

/**
 * 发送短信
 *
 * @param number
 * @param message
 */
fun sendMessage(number: String, message: String, activity: Activity) {
    val uri = Uri.parse("smsto:$number")
    val sendIntent = Intent(Intent.ACTION_VIEW, uri)
    sendIntent.putExtra("sms_body", message)
    activity.startActivity(sendIntent)
}

/**
 * 获取当月月末是日期
 */
fun getMonthEndTime(formatStr: String): String {
    val format = SimpleDateFormat(formatStr, Locale("UTF-8", "+86"))
    val cale = Calendar.getInstance()
    cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH))
    return format.format(cale.time)
}

/**
 * 获取当前系统时间
 */
fun getSystemCurrentTime(formatStr: String): String {
    val sysTime = System.currentTimeMillis()
    return android.text.format.DateFormat.format(formatStr, sysTime).toString() + ""
}


//========================================================================================================
//Glide use
//========================================================================================================
fun glideLoad(context: Context?, url: Any, view: ImageView) {
    GlideApp.with(context!!).load(url).placeholder(R.mipmap.banner_defult).into(view)
}

fun selfAdaptionImageView(context: Context, imgWidth: Int, imgHeight: Int, img: ImageView) {
    val screenWidth = getScreenWidth(context)
    val ratio = imgWidth.toFloat() / imgHeight.toFloat()
    val height = (screenWidth / ratio).toInt()
    val params = img.layoutParams
    params.height = height
    params.width = screenWidth
    img.layoutParams = params
}

/**
 * 获取屏幕宽度(px)
 */
private fun getScreenWidth(context: Context?): Int {
    return context!!.resources.displayMetrics.widthPixels
}

/**
 * 是否是登录状态
 */
fun getUserid(): Int {
    return SpUtil.getInstance().getInt(USER_ID, 0)
}

/**
 * 是否是登录状态
 */
fun isLogin(): Boolean {
    return SpUtil.getInstance().getBoolean(IS_LOGIN)
}

/**
 * 登陆对话框
 */
fun showLoginAlert(context: Context?) {
    val alert = AlertDialog.Builder(context!!).create()
    alert.show()
    //加载自定义dialog
    alert.window!!.setContentView(R.layout.login_alert)
    alert.window!!.setBackgroundDrawableResource(android.R.color.transparent)
    //取消
    alert.window!!.findViewById<Button>(R.id.cart_delete_cancle).setOnClickListener(View.OnClickListener {
        alert.dismiss()
        return@OnClickListener
    })
    val content = alert.window!!.findViewById<TextView>(R.id.cart_delete_content)
    content.text = "请先登录哦"
    //确认登录
    alert.window!!.findViewById<Button>(R.id.cart_delete_confirm).setOnClickListener(View.OnClickListener {
        LoginActivity.startSelf(context)
        alert.dismiss()
    })
}

/**
 * 根据用户生日计算年龄
 */
fun getAgeByBirthday(context: Context, birthday: Date): Int {
    val cal = Calendar.getInstance()

    if (cal.before(birthday)) {
        return -1
    }

    val yearNow = cal.get(Calendar.YEAR)
    val monthNow = cal.get(Calendar.MONTH) + 1
    val dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH)

    cal.time = birthday
    val yearBirth = cal.get(Calendar.YEAR)
    val monthBirth = cal.get(Calendar.MONTH) + 1
    val dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH)

    var age = yearNow - yearBirth

    if (monthNow <= monthBirth) {
        if (monthNow == monthBirth) {
            // monthNow==monthBirth
            if (dayOfMonthNow < dayOfMonthBirth) {
                age--
            }
        } else {
            // monthNow>monthBirth
            age--
        }
    }
    return age
}