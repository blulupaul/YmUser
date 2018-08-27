package com.mars.user.ui.home.ympackage.list

import android.content.Context
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.mars.user.R
import com.mars.user.base.act.BaseActivity
import com.mars.user.constant.MD_ID
import com.mars.user.ui.home.ympackage.list.adapter.YmPackageListRvAdapter
import com.mars.user.ui.home.ympackage.list.bean.GetPackageListNewGroupResBean
import com.mars.user.ui.home.ympackage.list.ympackagemvp.YmPackageListContract
import com.mars.user.ui.home.ympackage.list.ympackagemvp.YmPackageListPresenter
import com.mars.user.utils.SpUtil
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.activity_ym_package.*

/**
 * Created by gu on 2018/8/6
 * Desc: 云猫项目
 */
class YmPackageListActivity : BaseActivity(), YmPackageListContract.View {
    override fun onGetYmPackageListSuccess(bean: GetPackageListNewGroupResBean) {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
        ymPackageList.visibility = View.VISIBLE
        if (page == 1) {
            adapter.setNewData(bean.data)
        } else {
            adapter.addData(bean.data)
        }
    }

    override fun onGetYmPackageListFail(msg: String) {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
        if (page == 1) {
            ymPackageList.visibility = View.GONE
        } else {
            page--
        }
    }

    private val adapter = YmPackageListRvAdapter()
    private val presenter = YmPackageListPresenter(this)
    private var page = 1
    private val limit = 20
    private var mdid: Int? = null

    companion object {
        fun startSelf(context: Context) {
            val intent = Intent(context, YmPackageListActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_ym_package
    }

    override fun configViews() {
        mdid = SpUtil.getInstance().getInt(MD_ID, 0)
        configRvAndRefresh()
        initListener()

        presenter.getYmPackageList(mdid!!, page, limit)
    }

    private fun initListener() {
        refreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout?) {
                page = 1
                presenter.getYmPackageList(mdid!!, page, limit)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout?) {
                page += 1
                presenter.getYmPackageList(mdid!!, page, limit)
            }
        })

        adapter.setOnItemClickListener { _, _, position ->
            val yid = adapter.data[position].yid
            showCenterToast("$yid")
        }
    }

    private fun configRvAndRefresh() {
        refreshLayout.setRefreshHeader(ClassicsHeader(this))
        refreshLayout.setRefreshFooter(ClassicsFooter(this))

        ymPackageList.layoutManager = GridLayoutManager(this, 2)
        ymPackageList.adapter = adapter
    }
}
