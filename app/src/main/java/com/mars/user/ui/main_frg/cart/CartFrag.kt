package com.mars.user.ui.main_frg.cart

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.pedant.SweetAlert.SweetAlertDialog
import com.lesincs.simpleread.base.BaseFrag
import com.mars.user.R
import com.mars.user.bean.BaseNomalResBean
import com.mars.user.constant.USER_ID
import com.mars.user.event.MainTabChangeEvent
import com.mars.user.ui.main_frg.cart.adapter.CartRvAdapter
import com.mars.user.ui.main_frg.cart.bean.GetMyGwcListResBean
import com.mars.user.ui.main_frg.cart.bean.GwcJiaJianResBean
import com.mars.user.ui.main_frg.cart.cartmvp.CartContract
import com.mars.user.ui.main_frg.cart.cartmvp.CartPresenter
import com.mars.user.utils.*
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.frag_cart.*
import org.greenrobot.eventbus.EventBus


/**
 * Created by gu on 2018/07/18
 * desc: 购物车
 */
class CartFrag : BaseFrag(), CartContract.View {
    override fun onRefreshStart() {
        refreshLayout.autoRefresh()
    }

    override fun onRefreshDismiss() {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    override fun onGetMyGwcListSuceess(bean: GetMyGwcListResBean) {
        cartList.visibility = View.VISIBLE
        cartBottom.visibility = View.VISIBLE
        checkAll.isChecked = false
        if (page == 1) {
            adapter?.setNewData(bean.data)
        } else {
            adapter?.addData(bean.data)
        }
        dataBeanList.clear()
        dataBeanList.addAll(adapter?.data!!)
    }

    override fun onGetMyGwcListFail(msg: String) {
        if (page == 1) {
            dataBeanList.clear()
            adapter?.setNewData(dataBeanList)
            emptyView.setTitleText("购物车是空的")
            cartBottom.visibility = View.GONE
        } else
            page -= 1
    }

    override fun onGetMyGwcJiaJianSuccess(bean: GwcJiaJianResBean) {
        adapter?.setNewData(dataBeanList)
        totalPrice.text = bean.data[0].totalprice
    }

    override fun onGetMyGwcJiaJianFail(msg: String) {
        totalPrice.text = "0.0"
    }

    override fun onDeleteGwcForIdSuccess(bean: BaseNomalResBean, position: Int) {
        adapter?.remove(position)
        dataBeanList.removeAt(position)
    }

    override fun onDeleteGwcForIdFail(msg: String) {
        T.showFailAlert(context!!, msg)
    }

    override fun onClearGwcSuccess(bean: BaseNomalResBean, dialog: SweetAlertDialog) {
        cartList.visibility = View.GONE
        cartBottom.visibility = View.GONE
        dataBeanList.clear()
        adapter?.setNewData(dataBeanList)
        dialog.apply {
            titleText = bean.msg
            contentText = ""
            confirmText = "OK"
            showCancelButton(false)
            setCancelClickListener(null)
            setConfirmClickListener(null)
            changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
        }
    }

    override fun onClearGwcFail(msg: String, dialog: SweetAlertDialog) {
        dialog.apply {
            titleText = msg
            contentText = ""
            confirmText = "OK"
            showCancelButton(false)
            setCancelClickListener(null)
            setConfirmClickListener(null)
            changeAlertType(SweetAlertDialog.ERROR_TYPE)
        }
    }

    override fun onAddCollectSuccess(bean: BaseNomalResBean) {
        T.showSuccessAlert(context!!, bean.msg)
    }

    override fun onAddCollectFail(msg: String) {
        T.showFailAlert(context!!, msg)
    }

    private val presenter = CartPresenter(this)
    private var userid: Int = 0
    private var adapter: CartRvAdapter? = null
    private var page = 1
    private val limit = 20
    private val dataBeanList = ArrayList<GetMyGwcListResBean.Data>()

    companion object {
        fun newInstence(): CartFrag {
            val cartFrag = CartFrag()
            val bundle = Bundle()
            cartFrag.arguments = bundle
            return cartFrag
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.frag_cart
    }

    override fun configViews() {
        configRv()
        initListener()
    }

    override fun onResume() {
        super.onResume()
        userid = SpUtil.getInstance().getInt(USER_ID)
        cartList.visibility = View.GONE
        if (isLogin()) {
            presenter.getMyGwcList(userid, page, limit)
        } else {
            emptyView.setTitleText("您还没有登录，请先登录哦")
            cartBottom.visibility = View.GONE
        }
    }

    private fun initListener() {
        clearCart.setOnClickListener {
            if (isLogin()) {
                SweetAlertDialog(context!!, SweetAlertDialog.WARNING_TYPE).apply {
                    titleText = "是否确认清空购物车？"
                    contentText = "清空后不能恢复，只能重新添加到购物车"
                    cancelText = "取消"
                    confirmText = "确认"
                    showCancelButton(true)
                    setCancelClickListener(null)
                    setConfirmClickListener {
                        presenter.clearGwc(userid, it)
                    }
                    show()
                }
            } else {
                showLoginAlert(context!!)
            }
        }


        refreshLayout.run {
            setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
                override fun onLoadMore(refreshLayout: RefreshLayout?) {
                    if (isLogin()) {
                        page += 1
                        presenter.onRefresh(userid, page, limit)
                    } else {
                        showLoginAlert(context!!)
                        finishLoadMore()
                    }
                }

                override fun onRefresh(refreshLayout: RefreshLayout?) {
                    cartList.visibility = View.GONE
                    if (isLogin()) {
                        page = 1
                        presenter.onRefresh(userid, page, limit)
                    } else {
                        showLoginAlert(context!!)
                        finishRefresh()
                    }
                }
            })
        }

        adapter?.apply {
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.collect -> {
                        presenter.addColect(userid, dataBeanList[position].pid, 0)
                    }
                    R.id.toStore -> {
                        val event = MainTabChangeEvent()
                        event.id = R.id.fragment_store
                        EventBus.getDefault().post(event)
                    }
                    R.id.delete -> {
                        presenter.deleteGwcForId(dataBeanList[position].gwcid, position)
                    }
                    R.id.btn_subtract -> {
                        var counts = dataBeanList[position].counts
                        if (counts > 1) {
                            counts -= 1
                            dataBeanList[position].counts = counts
                            presenter.getMyGwcJiaJian(presenter.getMyGwcJiaJianReqBean(dataBeanList, position, getUserid(), 1, 0))
                        }
                    }

                    R.id.btn_add -> {
                        var counts = dataBeanList[position].counts
                        counts += 1
                        dataBeanList[position].counts = counts
                        presenter.getMyGwcJiaJian(presenter.getMyGwcJiaJianReqBean(dataBeanList, position, getUserid(), 1, 1))
                    }

                    R.id.checkItem -> {
                        var isCheckAll = true
                        var checkCount = 0
                        dataBeanList[position].isCheck = !dataBeanList[position].isCheck
                        for (item in dataBeanList) {
                            if (!item.isCheck) {
                                isCheckAll = false
                            } else {
                                checkCount += 1
                            }
                        }
                        checkAll.isChecked = isCheckAll
                        cartProCount.text = "$checkCount"
                        presenter.getMyGwcJiaJian(presenter.getMyGwcJiaJianReqBean(dataBeanList, position, getUserid(), 0, 0))
                    }
                }
            }

//            setOnItemClickListener { adapter, view, position ->
//                showCenterToast(data[position].gwcid.toString())
//            }
        }

        checkAll.setOnClickListener {
            for (item in dataBeanList) {
                item.isCheck = checkAll.isChecked
            }
            adapter?.setNewData(dataBeanList)

            if (checkAll.isChecked) {
                cartProCount.text = dataBeanList.size.toString()
                presenter.getMyGwcJiaJian(presenter.getMyGwcJiaJianReqBean(dataBeanList, 0,
                        getUserid(), 0, 0))
            } else {
                totalPrice.text = "0.0"
                cartProCount.text = "0"
            }
        }
    }

    private fun configRv() {
        adapter = CartRvAdapter(context!!)
        cartList.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        cartList.adapter = adapter
    }
}