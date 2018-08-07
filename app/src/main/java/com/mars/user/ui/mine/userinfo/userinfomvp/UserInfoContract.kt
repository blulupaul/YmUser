package com.mars.user.ui.mine.userinfo.userinfomvp

import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.bean.BaseIntResBean
import com.mars.user.bean.BaseNomalResBean
import com.mars.user.ui.mine.userinfo.bean.UploadResBean
import io.reactivex.Observable
import okhttp3.MultipartBody

/**
 * Created by gu on 2018/07/27
 * desc: ${desc}
 */
interface UserInfoContract {
    interface View {
        fun updateImageLoaddingStart()
        fun updateImageLoaddingFinish()

        fun fileUpLoadSuccess(bean: UploadResBean)
        fun fileUpLoadFail(msg: String)

        fun updateImgSuccess(bean: BaseNomalResBean, littleImg: String)
        fun updateImgFail(bean: BaseNomalResBean)
        fun getImgForCamera()
        fun getImgForPhoto()

        fun updateNickNameSuccess(bean: BaseNomalResBean,nickname: String)
        fun updateNickNameFail(msg: String)

        fun updateSexSuccess(bean: BaseNomalResBean,sex:Int)
        fun updateSexFail(msg: String)

        fun updateBirthdaySuccess(bean: BaseIntResBean)
        fun updateBirthdayFail(msg: String)

        fun updateWxNoSuccess(bean: BaseNomalResBean)
        fun updateWxnoFail(msg: String)

        fun onServerError(t: Throwable)

        fun getRxLifecycle(): RxLifecycle
    }

    interface Model {
        fun fileUpLoad(file: MultipartBody.Part, rxLifecycle: RxLifecycle): Observable<UploadResBean>
        fun updateImg(faceimg: String, littleImg: String, userid: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean>
        fun updateNickName(nickname: String, userid: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean>
        fun updateSex(sex: Int, userid: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean>
        fun updateBirthday(birthday: String, userid: Int, rxLifecycle: RxLifecycle): Observable<BaseIntResBean>
        fun updateWxno(wxno: String, userid: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean>
    }

    interface Presenter {
        fun fileUpLoad(file: MultipartBody.Part)
        fun updateImg(faceimg: String, littleImg: String, userid: Int)
        fun updateNickName(nickname: String, userid: Int)
        fun updateSex(sex: Int, userid: Int)
        fun updateBirthday(birthday: String, userid: Int)
        fun updateWxno(wxno: String, userid: Int)

        fun showUpdateImgAlert()
        fun showUpdateNickNameAlert(userid: Int)
        fun showUpdateSexAlert(userid: Int)
        fun showUpdateBirthdayAlert(userid: Int)
        fun showUpdateWxnoAlert(userid: Int)

        fun loginExit()
    }
}