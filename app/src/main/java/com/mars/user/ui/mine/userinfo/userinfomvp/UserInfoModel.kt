package com.mars.user.ui.mine.userinfo.userinfomvp

import cn.nekocode.rxlifecycle.LifecycleEvent
import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.bean.BaseIntResBean
import com.mars.user.bean.BaseNomalResBean
import com.mars.user.retrofit.RetrofitAPIManager
import com.mars.user.ui.mine.userinfo.bean.UploadResBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

/**
 * Created by gu on 2018/07/27
 * desc: ${desc}
 */
object UserInfoModel : UserInfoContract.Model {
    override fun fileUpLoad(file:MultipartBody.Part, rxLifecycle: RxLifecycle): Observable<UploadResBean> {
        return RetrofitAPIManager.getProvideClientApi().fileUpload(file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }

    override fun updateImg(faceimg: String, littleImg: String, userid: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean> {
        return RetrofitAPIManager.getProvideClientApi().updateImg(faceimg, littleImg, userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }

    override fun updateNickName(nickname: String, userid: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean> {
        return RetrofitAPIManager.getProvideClientApi().updateNickname(nickname, userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }

    override fun updateSex(sex: Int, userid: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean> {
        return RetrofitAPIManager.getProvideClientApi().updateSex(sex, userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }

    override fun updateBirthday(birthday: String, userid: Int, rxLifecycle: RxLifecycle): Observable<BaseIntResBean> {
        return RetrofitAPIManager.getProvideClientApi().updateBirthday(birthday, userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }

    override fun updateWxno(wxno: String, userid: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean> {
        return RetrofitAPIManager.getProvideClientApi().updateWxno(wxno, userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }
}