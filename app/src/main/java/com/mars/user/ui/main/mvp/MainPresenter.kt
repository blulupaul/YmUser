package com.mars.user.ui.main.mvp

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.mars.user.R
import com.mars.user.ui.main_frg.cart.CartFrag
import com.mars.user.ui.main_frg.home.HomeFrag
import com.mars.user.ui.main_frg.mine.MineFrag
import com.mars.user.ui.main_frg.store.StoreFrag

/**
 * Created by gu on 2018/07/18
 * desc: ${desc}
 */
class MainPresenter(var view: MainContract.View, var supportFragmentManager: FragmentManager?) : MainContract.Presenter {
    private var mainModel = MainModel

    private var homeFrag: HomeFrag? = null
    private var storeFrag: StoreFrag? = null
    private var cartFrag: CartFrag? = null
    private var mineFrag: MineFrag? = null

    override fun updateAppInfo(type: Int) {
        mainModel.getUpdateApp(type, view.getRxLifeCycle())
                .subscribe({
                    if (it.success) {
                        view.onUpdateSuccess(it)
                    } else {
                        view.onUpdateFail(it.msg)
                    }
                }, {
                    view.onServerResError(it)
                })
    }

    override fun changeFragment(itemId: Int) {
        val transaction = supportFragmentManager!!.beginTransaction()
        hideFragments(transaction)
        when (itemId) {
            R.id.fragment_home -> {
                if (homeFrag == null) {
                    homeFrag = HomeFrag.newInstence()
                    transaction.add(view.getContentId(), homeFrag!!)
                } else
                    transaction.show(homeFrag)
            }

            R.id.fragment_store -> {
                if (storeFrag == null) {
                    storeFrag = StoreFrag.newInstence()
                    transaction.add(view.getContentId(), storeFrag!!)
                } else
                    transaction.show(storeFrag)
            }

            R.id.fragment_cart -> {
                if (cartFrag == null) {
                    cartFrag = CartFrag.newInstence()
                    transaction.add(view.getContentId(), cartFrag!!)
                } else
                    transaction.show(cartFrag)
            }

            R.id.fragment_mine -> {
                if (mineFrag == null) {
                    mineFrag = MineFrag.newInstence()
                    transaction.add(view.getContentId(), mineFrag!!)
                } else
                    transaction.show(mineFrag)
            }
        }
        transaction.commitAllowingStateLoss()
    }

    /**
     * 显示之前隐藏所有fragment
     *
     * @param transaction
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        if (homeFrag != null)
            transaction.hide(homeFrag)
        if (storeFrag != null)
            transaction.hide(storeFrag)
        if (cartFrag != null)
            transaction.hide(cartFrag)
        if (mineFrag != null)
            transaction.hide(mineFrag)
    }
}