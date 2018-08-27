package com.mars.user.ui.main_frg.store

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.lesincs.simpleread.base.BaseFrag
import com.mars.user.R
import com.mars.user.constant.MD_ID
import com.mars.user.ui.main_frg.store.adapter.StoreLeftAdapter
import com.mars.user.ui.main_frg.store.adapter.StoreRightAdapter
import com.mars.user.ui.main_frg.store.bean.GetProductListByPTypeNewResBean
import com.mars.user.ui.main_frg.store.bean.GetPtypeListResBean
import com.mars.user.ui.main_frg.store.storemvp.StoreContract
import com.mars.user.ui.main_frg.store.storemvp.StorePresenter
import com.mars.user.ui.store.prodetail.ProDetailActivity
import com.mars.user.utils.SpUtil
import com.mars.user.utils.getUserid
import com.mars.user.utils.isLogin
import com.qmuiteam.qmui.widget.QMUIEmptyView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.footer.FalsifyFooter
import com.scwang.smartrefresh.layout.header.FalsifyHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.frag_store.*

/**
 * Created by gu on 2018/07/18
 * desc: 商城fragment
 */
class StoreFrag : BaseFrag(), StoreContract.View {
    override fun onRefreshStart() {
        refreshRight.autoRefresh()
    }

    override fun onRefreshFinish() {
        refreshRight.finishRefresh()
    }

    override fun onLoadMoreFinish() {
        refreshRight.finishLoadMore()
    }

    override fun onGetPtypeListSuccess(bean: GetPtypeListResBean) {
        leftList.clear()
        bean.data[leftClickPosition].isCheck = true
        leftList.addAll(bean.data)
        ptypeid = leftList[leftClickPosition].ptypeid
        leftAdapter.setNewData(leftList)
        presenter.getProListByPTypeNew(page, limit, ptypeid, mdid!!, userid!!)
    }

    override fun onGetPtypeListFail(msg: String) {

    }

    override fun onGetProListByPTypeNewSuccess(bean: GetProductListByPTypeNewResBean) {
        isLoadingFinish = true
        if (page == 1) {
            rightAdapter.setNewData(bean.data)
        } else {
            rightAdapter.addData(bean.data)
        }
//        if (page == 1) {
//            rightList.clear()
//        }
//        rightList.addAll(bean.data)
//        rightAdapter.setNewData(rightList)
    }

    override fun onGetProListByPTypeNewFail(msg: String) {
        isLoadingFinish = true
        if (page == 1) {
            viewEmpty?.setTitleText("还没有商品哦")
        } else {
            page -= 1
        }
    }

    private val leftAdapter = StoreLeftAdapter()
    private val rightAdapter = StoreRightAdapter()
    private var viewEmpty: QMUIEmptyView? = null
    private val presenter = StorePresenter(this)
    private val leftList = ArrayList<GetPtypeListResBean.Data>()
    private var page = 1
    private val limit = 10
    private var ptypeid = 0
    private var leftClickPosition = 0 //左侧列表选择的位置
    private var mdid: Int? = null
    private var userid: Int? = null
    private var isLoadingFinish = false

    companion object {
        fun newInstence(): StoreFrag {
            val storeFrag = StoreFrag()
            val bundle = Bundle()
            storeFrag.arguments = bundle
            return storeFrag
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.frag_store
    }

    override fun configViews() {
        viewEmpty = QMUIEmptyView(context!!)
        configRvAndRefresh()
        initListener()
    }

    override fun onResume() {
        super.onResume()
        isLoadingFinish = false
        storeContent.visibility = View.VISIBLE
        if (isLogin()) {
            mdid = SpUtil.getInstance().getInt(MD_ID)
            userid = getUserid()
            presenter.getPTypeList()
        } else {
            storeContent.visibility = View.GONE
        }
    }

    private fun initListener() {
        leftAdapter.setOnItemClickListener { _, _, position ->
            page = 1
            leftClickPosition = position
            for (i in leftList.indices) {
                when (i == position) {
                    true -> {
                        leftList[i].isCheck = true
                        ptypeid = leftList[i].ptypeid
                    }
                    false -> leftList[i].isCheck = false
                }
            }
            isLoadingFinish = false
            leftAdapter.setNewData(leftList)
            presenter.getProListByPTypeNew(page, limit, ptypeid, mdid!!, userid!!)
        }

        rightAdapter.setOnItemClickListener { _, _, position ->
            if (isLoadingFinish) {
                ProDetailActivity.startSelf(context!!, rightAdapter.data[position].pid, rightAdapter.data[position].canbuy.toInt())
            }
        }

        refreshRight.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout?) {
                isLoadingFinish = false
                page += 1
                presenter.getProListByPTypeNew(page, limit, ptypeid, mdid!!, userid!!)
            }

            override fun onRefresh(refreshLayout: RefreshLayout?) {
                isLoadingFinish = false
                page = 1
                presenter.getProListByPTypeNew(page, limit, ptypeid, mdid!!, userid!!)
            }
        })
    }

    private fun configRvAndRefresh() {
        rvLeft.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        rvRight.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        rvLeft.adapter = leftAdapter
        rvRight.adapter = rightAdapter
        rightAdapter.emptyView = viewEmpty

        refreshLeft.apply {
            setRefreshHeader(FalsifyHeader(context!!))
            setRefreshFooter(FalsifyFooter(context!!))
        }

        refreshRight.apply {
            setRefreshFooter(ClassicsFooter(context!!))
        }

    }
}