package com.mars.user.ui.login.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.R
import com.mars.user.base.BaseActivity
import com.mars.user.constant.*
import com.mars.user.ui.login.forgetpwd.ForgetPwdActivity
import com.mars.user.ui.login.login.bean.UserLoginBean
import com.mars.user.ui.login.login.loginmvp.LoginContract
import com.mars.user.ui.login.login.loginmvp.LoginPresenter
import com.mars.user.ui.main.MainActivity
import com.mars.user.utils.SpUtil
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by gu on 2018/7/18
 * Desc: 用户登录
 */

class LoginActivity : BaseActivity(), LoginContract.View {

    private var qmuiTipDialog: QMUITipDialog? = null
    private var mPresenter: LoginPresenter? = null

    override fun onLoginSuccess(bean: UserLoginBean) {
        showSuccessAlert(bean.msg).setOnDismissListener {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            removeAct(this)
            finish()
        }
    }

    override fun onLoginIdentityError(bean: UserLoginBean) {
        showCenterToast("请使用用户账号登陆")
    }

    override fun onLoginFail(msg: String) {
        showFailAlert(msg)
    }

    override fun onServerResError(t: Throwable) {
        showFailAlert(t.message!!)
    }

    override fun onLoaddingShow() {
        qmuiTipDialog?.show()
    }

    override fun onLoaddingDismiss() {
        qmuiTipDialog?.dismiss()
    }

    override fun getRxLiftCycle(): RxLifecycle {
        return RxLifecycle.bind(context!! as Activity)
    }

    override fun saveToSp(bean: UserLoginBean) {
        val dataBean = bean.data[0]
        val userid = dataBean.userid
        val uname = dataBean.uname
        val telphone = dataBean.telphone
        val uimage = dataBean.uimage
        val unionid = dataBean.unionid
        val islhb = dataBean.islhb
        val mdid = dataBean.mdid
        val storename = dataBean.storename
        val saddress = dataBean.saddress
        val password = dataBean.password

        SpUtil.getInstance().putInt(USER_ID, userid)
        SpUtil.getInstance().putInt(MD_ID, mdid)
        SpUtil.getInstance().putInt(ISLJB, islhb)
        SpUtil.getInstance().putString(U_NAME, uname)
        SpUtil.getInstance().putString(TELEPHONE, telphone)
        SpUtil.getInstance().putString(U_IMAGE, uimage)
        SpUtil.getInstance().putString(UNIONID, unionid)
        SpUtil.getInstance().putString(STORE_NAME, storename)
        SpUtil.getInstance().putString(S_ADDRESS, saddress)
        SpUtil.getInstance().putString(PASSWORD, password)
        SpUtil.getInstance().putBoolean(IS_LOGIN, true)
    }

    companion object {
        fun startSelf(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun setLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun configViews() {
        /*hide statusBar and actionBar*/
        QMUIStatusBarHelper.translucent(this)
        swipeBackLayout.setEnableGesture(false)
        mPresenter = LoginPresenter(this)
        qmuiTipDialog = QMUITipDialog.Builder(context!!)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在登录")
                .create()
        initListener()
    }

    /**
     * click listener
     */
    private fun initListener() {
        /*忘记密码*/
        forget_pwd.setOnClickListener {
            startActivity(Intent(context!!, ForgetPwdActivity::class.java))
        }

        /*登录*/
        login.setOnClickListener {
            val phone = input_phone.text.toString()
            val pwd = input_pwd.text.toString()
            if (TextUtils.isEmpty(phone)) {
                showFailAlert("请输入手机号")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(pwd)) {
                showFailAlert("请输入密码")
                return@setOnClickListener
            }

            mPresenter?.login(phone, pwd)
        }

        /*注册*/
        register.setOnClickListener {

        }
    }
}
