package com.mars.user.ui.main_frg.mine.minemvp

import cn.nekocode.rxlifecycle.LifecycleEvent
import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.R
import com.mars.user.bean.BaseIntResBean
import com.mars.user.retrofit.RetrofitAPIManager
import com.mars.user.ui.login.login.bean.UserLoginBean
import com.mars.user.ui.main_frg.home.bean.HomeModelEnterBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by gu on 2018/07/23
 * desc: ${desc}
 */
object MineModel : MineContract.Model {
    override fun sendGetMyInfo(userid: Int, rxLifecycle: RxLifecycle): Observable<UserLoginBean> {
        return RetrofitAPIManager.getProvideClientApi().getMy(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }

    override fun sendUpdateNotiCount(userid: Int, type: Int, rxLifecycle: RxLifecycle): Observable<BaseIntResBean> {
        return RetrofitAPIManager.getProvideClientApi().getNtypeOneCount(userid, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }

    override fun getMineModuleEnterDataList(): List<HomeModelEnterBean> {
        val list = ArrayList<HomeModelEnterBean>()
        val mStrArr = arrayOf("我的订单", "我的拼团", "我的账户", "地址管理", "我的美圈")
        val mDrawableArr = intArrayOf(R.mipmap.mine_order_icon, R.mipmap.mine_pintuan_icon,
                R.mipmap.mine_account_icon, R.mipmap.mine_address_icon,
                R.mipmap.mine_meiquan_icon)

        for (i in mStrArr.indices) {
            val bean = HomeModelEnterBean(mStrArr[i], mDrawableArr[i],0)
            list.add(bean)
        }
        return list
    }
}