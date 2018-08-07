package com.mars.user.ui.mine.userinfo

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.View
import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.R
import com.mars.user.base.BaseActivity
import com.mars.user.bean.BaseIntResBean
import com.mars.user.bean.BaseNomalResBean
import com.mars.user.constant.USER_ID
import com.mars.user.ui.login.login.bean.UserLoginBean
import com.mars.user.ui.main_frg.mine.minemvp.MineContract
import com.mars.user.ui.main_frg.mine.minemvp.MinePresenter
import com.mars.user.ui.mine.userinfo.bean.UploadResBean
import com.mars.user.ui.mine.userinfo.userinfomvp.UserInfoContract
import com.mars.user.ui.mine.userinfo.userinfomvp.UserInfoPresenter
import com.mars.user.utils.SpUtil
import com.mars.user.utils.glideLoad
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.scwang.smartrefresh.layout.footer.FalsifyFooter
import com.scwang.smartrefresh.layout.header.FalsifyHeader
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_user_info.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * Created by gu on 2018/8/6
 * Desc: 用户个人信息
 */
class UserInfoActivity : BaseActivity(), MineContract.View, UserInfoContract.View, View.OnClickListener {
    override fun updateImageLoaddingStart() {
        tipDialog = QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在上传...")
                .create()
        tipDialog?.show()
    }

    override fun updateImageLoaddingFinish() {
        tipDialog?.dismiss()
        tipDialog?.setOnDismissListener {

        }
    }

    override fun fileUpLoadSuccess(bean: UploadResBean) {
        presenterUserInfo?.updateImg(bean.data[0].BigFileName, bean.data[0].SmallFileName, userid!!)
    }

    override fun fileUpLoadFail(msg: String) {
        showFailAlert("上传头像失败")
    }

    override fun updateImgSuccess(bean: BaseNomalResBean, littleImg: String) {
        showSuccessAlert("更新头像成功")
        glideLoad(this, "http://www.yuekee.com.cn:8080/Upload/$littleImg", userImg)
    }

    override fun updateImgFail(bean: BaseNomalResBean) {
        showFailAlert("更新头像失败")
    }

    override fun getImgForCamera() {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        val file = File(Environment.getExternalStorageDirectory().absolutePath + "/yunmao/" + System.currentTimeMillis() + ".jpg")
//        file.parentFile.mkdirs()
//        //改变Uri
//        cameraUri = FileProvider.getUriForFile(this, "com.mars.user.fileprovider", file)
//        //添加权限
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri)
//        startActivityForResult(intent, 222)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        /***
         * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的
         * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
         * 如果不使用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
         */
        val values = ContentValues()
        cameraUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri)
        startActivityForResult(intent, 222)
    }

    override fun getImgForPhoto() {
        val intent = Intent()
        // 如果要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
        intent.type = "image/*"
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT) {
            intent.action = Intent.ACTION_GET_CONTENT
        } else {
            intent.action = Intent.ACTION_PICK
            intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        startActivityForResult(intent, 333)
    }

    override fun updateNickNameSuccess(bean: BaseNomalResBean, nickname: String) {
        showSuccessAlert(bean.msg)
        userName.text = nickname
    }

    override fun updateNickNameFail(msg: String) {
        showFailAlert(msg)
    }

    override fun updateSexSuccess(bean: BaseNomalResBean, sex: Int) {
        showSuccessAlert(bean.msg)
        userSex.text = if (sex == 0) "男" else "女"
    }

    override fun updateSexFail(msg: String) {
        showFailAlert(msg)
    }

    override fun updateBirthdaySuccess(bean: BaseIntResBean) {
        showSuccessAlert(bean.msg)
        userAge.text = bean.data.toString()
    }

    override fun updateBirthdayFail(msg: String) {
        showFailAlert(msg)
    }

    override fun updateWxNoSuccess(bean: BaseNomalResBean) {
        showSuccessAlert(bean.msg)
    }

    override fun updateWxnoFail(msg: String) {
        showFailAlert(msg)
    }

    override fun onServerError(t: Throwable) {
        showCenterToast("服务器返回错误：${t.message}")
    }

    override fun onRefreshStart() {

    }

    override fun onRefreshDismiss() {
    }

    override fun onGetMyInfoSuccess(bean: UserLoginBean) {
        bindView(bean)
    }

    override fun onGetMyInfoFail(msg: String) {

    }

    override fun onUpdateNotiCountSuccess(bean: BaseIntResBean) {

    }

    override fun onUpdateNotiCountFail(msg: String) {

    }

    override fun onServerReqError(t: Throwable) {
        showCenterToast("服务器返回错误：${t.message}")
    }

    override fun getRxLifecycle(): RxLifecycle {
        return RxLifecycle.bind(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rlUserImg -> {
                val rxPermissions = RxPermissions(this)
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(object : Observer<Boolean> {
                            override fun onComplete() {

                            }

                            override fun onSubscribe(d: Disposable) {

                            }

                            override fun onNext(t: Boolean) {
                                presenterUserInfo?.showUpdateImgAlert()
                            }

                            override fun onError(e: Throwable) {

                            }

                        })
            }

            R.id.rlUserName -> {
                presenterUserInfo?.showUpdateNickNameAlert(userid!!)
            }

            R.id.rlUserSex -> {
                presenterUserInfo?.showUpdateSexAlert(userid!!)
            }

            R.id.rlUserAge -> {
                presenterUserInfo?.showUpdateBirthdayAlert(userid!!)
            }

            R.id.rlWxNum -> {
                presenterUserInfo?.showUpdateWxnoAlert(userid!!)
            }

            R.id.loginExit -> {
                presenterUserInfo?.loginExit()
            }
        }
    }

    private val presente = MinePresenter(this)
    private var presenterUserInfo: UserInfoPresenter? = null
    private var userid: Int? = null
    private var tipDialog: QMUITipDialog? = null
    private var cameraUri: Uri? = null

    companion object {
        fun startSelf(context: Context) {
            val intent = Intent(context, UserInfoActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_user_info
    }

    override fun configViews() {
        userid = SpUtil.getInstance().getInt(USER_ID)
        presenterUserInfo = UserInfoPresenter(this, this)
        refreshLayout.setRefreshHeader(FalsifyHeader(this))
        refreshLayout.setRefreshFooter(FalsifyFooter(this))

        rlUserImg.setOnClickListener(this)
        rlUserName.setOnClickListener(this)
        rlUserSex.setOnClickListener(this)
        rlUserAge.setOnClickListener(this)
        rlWxNum.setOnClickListener(this)
        loginExit.setOnClickListener(this)

        presente.getUserInfo()
    }

    private fun bindView(bean: UserLoginBean) {
        val data = bean.data[0]
        val littleimg = data.littleimg
        val uname = data.uname
        val sex = data.sex
        val nl = data.nl
        val telphone = data.telphone
        val wxno = data.wxno
        val storename = data.storename
        val saddress = data.saddress

        glideLoad(this, littleimg, userImg)
        userName.text = uname
        when (sex) {
            0 -> userSex.text = "男"
            1 -> userSex.text = "女"
            else -> userSex.text = "请选择性别"
        }
        userAge.text = nl.toString()
        userPhone.text = telphone
        userWxNum.text = wxno
        userMdName.text = storename
        userMdAddress.text = saddress
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                222 -> { // 拍照
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data, requestCode)
                    } else {
                        handleImageBeforeKitKat(data, requestCode)
                    }
                }

                333 -> { // 选择照片
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data, requestCode)
                    } else {
                        handleImageBeforeKitKat(data, requestCode)
                    }
                }
            }
        }
    }

    @TargetApi(19)
    private fun handleImageOnKitKat(data: Intent?, requestCode: Int) {
        var imagePath: String? = null
        var uri: Uri? = null
        if (requestCode == 333) {
            uri = data?.data
        } else if (requestCode == 222) {
            uri = cameraUri
        }
        if (DocumentsContract.isDocumentUri(this, uri!!)) {
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri.authority) {
                val id = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                val selection = MediaStore.Images.Media._ID + "=" + id
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
            } else if ("com.android.providers.downloads.documents" == uri.authority) {
                val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(docId))
                imagePath = getImagePath(contentUri, null)
            }
        } else if ("content".equals(uri!!.scheme, ignoreCase = true)) {
            imagePath = getImagePath(uri, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            imagePath = uri.path
        }

        val file = File(imagePath)
//        val map = HashMap<String, MultipartBody.Part>()
        if (file.exists()) {
            // 创建 RequestBody，用于封装构建RequestBody
            val requestFile = RequestBody.create(MediaType.parse("image/png"), file)
            // MultipartBody.Part  和后端约定好Key，这里的partName是用image
            val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
//            map.put("image[]\"; filename=\"" + file.name,body)
            // map["image[]\"; filename=\"" + file.name] = requestFile
//        displayImage(imagePath!!)
            presenterUserInfo?.fileUpLoad(body)
        }
    }

    private fun handleImageBeforeKitKat(data: Intent?, requestCode: Int) {
        var imagePath: String? = null
        var uri: Uri? = null
        if (requestCode == 333) {
            uri = data?.data
        } else if (requestCode == 222) {
            uri = cameraUri
        }
        imagePath = getImagePath(uri!!, null)

        val file = File(imagePath)
//        val map = HashMap<String, MultipartBody.Part>()
        if (file.exists()) {
            // 创建 RequestBody，用于封装构建RequestBody
            val requestBody = RequestBody.create(MediaType.parse("image/png"), file)
            // MultipartBody.Part  和后端约定好Key，这里的partName是用image
            val body = MultipartBody.Part.createFormData("image", file.name, requestBody)
//            map["image[]\"; filename=\"" + file.name] = body
//        displayImage(imagePath!!)
            presenterUserInfo?.fileUpLoad(body)
        }

    }


    private fun displayImage(path: String?) {
        val bm = BitmapFactory.decodeFile(path)
        userImg.setImageBitmap(bm)
    }


    private fun getImagePath(uri: Uri, selection: String? = null): String? {
        var path: String? = null
        val cursor = contentResolver.query(uri, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path
    }
}
