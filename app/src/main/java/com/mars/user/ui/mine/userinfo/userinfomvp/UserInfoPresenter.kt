package com.mars.user.ui.mine.userinfo.userinfomvp

import android.content.Context
import android.text.InputType
import android.util.Log
import com.mars.user.R
import com.mars.user.constant.IS_LOGIN
import com.mars.user.constant.USER_ID
import com.mars.user.ui.mine.userinfo.UserInfoActivity
import com.mars.user.utils.DatePickerUtil
import com.mars.user.utils.SpUtil
import com.mars.user.utils.T
import com.mars.user.utils.T.showFailAlert
import com.mars.user.utils.getAgeByBirthday
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import okhttp3.MultipartBody
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by gu on 2018/07/30
 * desc: ${desc}
 */
class UserInfoPresenter(val context: Context, val view: UserInfoContract.View) : UserInfoContract.Presenter {
    private val mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog

    private val model = UserInfoModel

    override fun fileUpLoad(file: MultipartBody.Part) {
        view.updateImageLoaddingStart()
        model.fileUpLoad(file, view.getRxLifecycle())
                .subscribe({
                    if (it.success) {
                        view.fileUpLoadSuccess(it)
                    } else {
                        view.fileUpLoadFail(it.msg)
                        view.updateImageLoaddingFinish()
                    }
                }, {
                    view.onServerError(it)
                    view.updateImageLoaddingFinish()
                })
    }

    override fun updateImg(faceimg: String, littleImg: String, userid: Int) {
        model.updateImg(faceimg, littleImg, userid, view.getRxLifecycle())
                .subscribe({
                    view.updateImageLoaddingFinish()
                    if (it.success) {
                        view.updateImgSuccess(it, littleImg)
                    } else {
                        view.updateImgFail(it)
                    }
                }, {
                    view.onServerError(it)
                    view.updateImageLoaddingFinish()
                })
    }

    override fun updateNickName(nickname: String, userid: Int) {
        model.updateNickName(nickname, userid, view.getRxLifecycle())
                .subscribe({
                    if (it.success) {
                        view.updateNickNameSuccess(it,nickname)
                    } else {
                        view.updateNickNameFail(it.msg)
                    }
                }, {
                    view.onServerError(it)
                })
    }

    override fun updateSex(sex: Int, userid: Int) {
        model.updateSex(sex, userid, view.getRxLifecycle())
                .subscribe({
                    if (it.success) {
                        view.updateSexSuccess(it,sex)
                    } else {
                        view.updateSexFail(it.msg)
                    }
                }, {
                    view.onServerError(it)
                })
    }

    override fun updateBirthday(birthday: String, userid: Int) {
        model.updateBirthday(birthday, userid, view.getRxLifecycle())
                .subscribe({
                    if (it.success) {
                        view.updateBirthdaySuccess(it)
                    } else {
                        view.updateBirthdayFail(it.msg)
                    }
                }, {
                    view.onServerError(it)
                })
    }

    override fun updateWxno(wxno: String, userid: Int) {
        model.updateWxno(wxno, userid, view.getRxLifecycle())
                .subscribe({
                    if (it.success) {
                        view.updateWxNoSuccess(it)
                    } else {
                        view.updateWxnoFail(it.msg)
                    }
                }, {
                    view.onServerError(it)
                })
    }

    override fun showUpdateImgAlert() {
        val CAMERA = 0
        val PHOTO = 1
        val builder = QMUIBottomSheet.BottomGridSheetBuilder(context)
        builder.addItem(R.mipmap.camera_icon, "拍摄", CAMERA, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.photo_icon, "照片", PHOTO, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .setOnSheetItemClickListener { dialog, itemView ->
                    dialog.dismiss()
                    val tag = itemView.tag as Int
                    when (tag) {
                        CAMERA -> view.getImgForCamera()
                        PHOTO -> view.getImgForPhoto()
                    }
                }.build().show()
    }

    override fun showUpdateNickNameAlert(userid: Int) {
        showInputDialog(0, userid)
    }

    override fun showUpdateSexAlert(userid: Int) {
        showSexDialog(userid)
    }

    override fun showUpdateBirthdayAlert(userid: Int) {
        showDatePickerDialog(userid)
    }

    override fun showUpdateWxnoAlert(userid: Int) {
        showInputDialog(1, userid)
    }

    override fun loginExit() {
        showConfirmMessageDialog()
    }

    private fun showConfirmMessageDialog() {
        QMUIDialog.CheckBoxMessageDialogBuilder(context).apply {
            setTitle("退出后是否删除账号信息?")
            setMessage("删除账号信息")
            isChecked = true
            addAction("取消") { dialog, index ->
                dialog.dismiss()
            }
            addAction("退出") { dialog, index ->
                if (this.isChecked) { // 清除数据
                    val editor = SpUtil.getInstance().editor
                    editor.clear()
                    editor.commit()
                    var boolean = SpUtil.getInstance().getBoolean(IS_LOGIN)
                    var int = SpUtil.getInstance().getInt(USER_ID)
                    Log.e("asf", boolean.toString())
                    Log.e("asf", int.toString())
                    (context as UserInfoActivity).finish()
                } else { // 不清除数据
                    T.showToastCenter(context, "不清除")
                }
                dialog.dismiss()
            }
            create(mCurrentDialogStyle)
            show()
        }

    }

    /**
     * @param type
     * 0:修改姓名
     * 1:修改微信名
     */
    private fun showInputDialog(type: Int, userid: Int?) {
        var placeholder: String? = null
        var title: String? = null
        when (type) {
            0 -> {
                placeholder = "在此输入您的姓名"
                title = "修改姓名"
            }

            1 -> {
                placeholder = "在此输入您的微信号"
                title = "修改微信号"
            }
        }
        val builder = QMUIDialog.EditTextDialogBuilder(context)
        builder.setTitle(title)
                .setPlaceholder(placeholder)
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消") { dialog, _ -> dialog.dismiss() }
                .addAction("确定") { dialog, _ ->
                    val text = builder.editText.text
                    if (text != null && !text.isEmpty()) {
                        when (type) {
                            0 -> updateNickName(text.toString(), userid!!)
                            1 -> updateWxno(text.toString(), userid!!)
                        }
                        dialog.dismiss()
                    } else {
                        showFailAlert(context, "内容不能为空哦")
                    }
                }
                .create(mCurrentDialogStyle)
                .show()
    }

    private fun showSexDialog(userid: Int) {
        val items = arrayOf("男", "女")
        //val checkedIndex = 1
        QMUIDialog.CheckableDialogBuilder(context)
                //.setCheckedIndex(checkedIndex)
                .addItems(items) { dialog, which ->
                    updateSex(which, userid)
                    dialog.dismiss()
                }
                .create(mCurrentDialogStyle)
                .show()
    }

    private fun showDatePickerDialog(userid: Int) {
        DatePickerUtil.getInstance(context).pickerDate(3, DatePickerUtil.OnDateCallBack {
            val birthday = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(it)
            val age = getAgeByBirthday(context, it)
            if (age != -1) {
                updateBirthday(birthday.toString(), userid)
            } else {
                T.showToastCenter(context, "您的生日超过当前时间，请您重新选择")
            }
        })
    }
}