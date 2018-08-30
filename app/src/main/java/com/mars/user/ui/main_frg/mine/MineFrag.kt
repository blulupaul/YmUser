package com.mars.user.ui.main_frg.mine

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.lesincs.simpleread.base.BaseFrag
import com.mars.user.R
import com.mars.user.bean.BaseIntResBean
import com.mars.user.constant.IS_LOGIN
import com.mars.user.ui.login.login.LoginActivity
import com.mars.user.ui.login.login.bean.UserLoginBean
import com.mars.user.ui.main_frg.mine.adapter.MineModuleEnterRvAdapter
import com.mars.user.ui.main_frg.mine.minemvp.MineContract
import com.mars.user.ui.main_frg.mine.minemvp.MinePresenter
import com.mars.user.ui.mine.userinfo.UserInfoActivity
import com.mars.user.utils.SpUtil
import com.mars.user.utils.glideLoad
import com.mars.user.utils.isLogin
import com.scwang.smartrefresh.layout.footer.FalsifyFooter
import kotlinx.android.synthetic.main.frag_mine.*

/**
 * Created by gu on 2018/07/18
 * desc: 我的fragment
 */
class MineFrag : BaseFrag(), MineContract.View, View.OnClickListener {
    /**
     * 点击监听
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.toLogin -> {
                if (isLogin()) {
                    UserInfoActivity.startSelf(context!!)
                } else {
                    LoginActivity.startSelf(context!!)
                }
            }

//            R.id.tongzhi -> {
//
//            }

            R.id.collect -> {

            }

            R.id.qrcode -> {

            }

            R.id.duihuanCode -> {

            }

            R.id.modifyPwd -> {

            }

            R.id.mdTouSu -> {

            }

            R.id.cleanCache -> {

            }

            R.id.oboutUs -> {

            }
        }
    }

    override fun onRefreshStart() {
        refreshLayout.autoRefresh()
    }

    override fun onRefreshDismiss() {
        refreshLayout.finishRefresh()
    }

    override fun onGetMyInfoSuccess(bean: UserLoginBean) {
        loginState(bean.data[0])
    }

    override fun onGetMyInfoFail(msg: String) {
        loginState(null)
    }

    override fun onUpdateNotiCountSuccess(bean: BaseIntResBean) {
//        mQBadgeView?.badgeNumber = bean.data
    }

    override fun onUpdateNotiCountFail(msg: String) {
//        mQBadgeView?.badgeNumber = 0
    }

//    private var mQBadgeView: QBadgeView? = null
    private var presenter = MinePresenter(this)
    private var adapter = MineModuleEnterRvAdapter()

    companion object {
        fun newInstence(): MineFrag {
            val mineFrag = MineFrag()
            val bundle = Bundle()
            mineFrag.arguments = bundle
            return mineFrag
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.frag_mine
    }

    override fun configViews() {
//        configQBadgeView()
        configRv()
        configRefreshLayout()
        initListener()
    }

    override fun onResume() {
        super.onResume()
        presenter.getUserInfo()
        presenter.updateNotiCount()
    }

    private fun configRv() {
        adapter.setNewData(presenter.getMineModuleEnterBeanList())
        rv_mine.layoutManager = GridLayoutManager(context!!, 5, GridLayoutManager.VERTICAL, false)
        rv_mine.adapter = adapter
    }


    /**
     * SmartRefreshLayout 配置
     */
    private fun configRefreshLayout() {
        refreshLayout.isEnableLoadMore = false
        refreshLayout.setRefreshFooter(FalsifyFooter(context!!))
        refreshLayout.isEnablePureScrollMode = false
    }


    private fun initListener() {
        toLogin.setOnClickListener(this)
//        tongzhi.setOnClickListener(this)

        collect.setOnClickListener(this)
        qrcode.setOnClickListener(this)
        duihuanCode.setOnClickListener(this)
        modifyPwd.setOnClickListener(this)
        mdTouSu.setOnClickListener(this)
        cleanCache.setOnClickListener(this)
        oboutUs.setOnClickListener(this)

        adapter.setOnItemClickListener { _, _, position ->
            showCenterToast("$position")
        }

        refreshLayout.setOnRefreshListener {
            presenter.onRefresh()
        }

    }

    /**
     * 配置QBadgeView
     */
//    private fun configQBadgeView() {
//        mQBadgeView = QBadgeView(context!!)
//        mQBadgeView?.bindTarget(tongzhi)
//        mQBadgeView?.setGravityOffset(8f, 2f, true)
//    }

    /**
     * 是否登录状态
     */
    private fun loginState(data: UserLoginBean.Data? = null) {
        val isLogin = SpUtil.getInstance().getBoolean(IS_LOGIN)
        if (isLogin) {
            loginStr.visibility = View.GONE
            name.text = data?.uname
            phone.text = data?.telphone
            glideLoad(context!!, data?.littleimg!!, header)
        } else {
            loginStr.visibility = View.VISIBLE
            name.text = ""
            phone.text = ""
            header.setImageResource(R.mipmap.header_defult_new)
        }
    }
}