package com.mars.user.api

import com.mars.user.bean.BaseIntResBean
import com.mars.user.bean.BaseNomalResBean
import com.mars.user.ui.home.ympackage.list.bean.GetPackageDetailResBean
import com.mars.user.ui.home.ympackage.list.bean.GetPackageListNewGroupPariidResBean
import com.mars.user.ui.home.ympackage.list.bean.GetPackageListNewGroupResBean
import com.mars.user.ui.login.login.bean.UserLoginBean
import com.mars.user.ui.main.bean.GetUpdateAppResBean
import com.mars.user.ui.main_frg.cart.bean.GetMyGwcListResBean
import com.mars.user.ui.main_frg.cart.bean.GwcJiaJianReqBean
import com.mars.user.ui.main_frg.cart.bean.GwcJiaJianResBean
import com.mars.user.ui.main_frg.home.bean.*
import com.mars.user.ui.main_frg.store.bean.GetProductListByPTypeNewResBean
import com.mars.user.ui.main_frg.store.bean.GetPtypeListResBean
import com.mars.user.ui.mine.myaccount.bean.GetMyJinBiResBean
import com.mars.user.ui.mine.userinfo.bean.UploadResBean
import com.mars.user.ui.store.prodetail.bean.GetProDetailInfoResBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * Created by gu on 2018/07/17
 * desc: 接口地址
 */
interface ApiService {

    /**
     * GET user/GetLogin?telphone={telphone}&pass={pass}
     * 用户登录
     */
    @GET("user/GetLogin?")
    fun getLogin(@Query("telphone") telphone: String,
                 @Query("pass") pass: String): Observable<UserLoginBean>

    /**
     * user/GetUpdateApp?type={type}
     * 获取更新记录
     * type  0-用户端，1-美疗师，2-督导，3-总监
     */
    @GET("user/GetUpdateApp?")
    fun getUpdateApp(@Query("type") type: Int): Observable<GetUpdateAppResBean>

    /**
     * GET MeiLShi/GetYmmxList/{typeid}/{page}/{limit}
     * 云猫美学列表
     * （0-云猫助手，1-专业学社，2-品羡库，3-企业微刊，4美丽篇 5健康篇 6品牌篇）
     */
    @GET("MeiLShi/GetYmmxList/{typeid}/{page}/{limit}")
    fun getYmmxList(@Path("typeid") typeid: Int, @Path("page") page: Int, @Path("limit") limit: Int): Observable<GetYmmxListResBean>

    /**
     * user/Gett_waddress
     * 三方url地址 如果读取状态为0的时候，不要加载，前端显示，维护中，敬请期待
     */
    @GET("user/Gett_waddress")
    fun gett_waddress(): Observable<Gett_waddressResBean>

    /**
     * GET Packgae/GetDownMoney?userid={userid}&storeid={storeid}
     * 得到倒计钱
     */
    @GET("Packgae/GetDownMoney?")
    fun getDownMoney(@Query("userid") userid: Int, @Query("storeid") storeid: Int): Observable<GetDownMoneyResBean>

    /**
     * Packgae/Guangaowei?userid=3197
     * 首页广告位
     */
    @GET("Packgae/Guangaowei?")
    fun getGuanggaowei(@Query("userid") userid: Int): Observable<GetGangGaoweiResBean>

    /**
     * GET user/GetStoreBanner?userid={userid}
     * 获取首页banner(新)按门店显示
     * 3197 为未登录状态传入的默认值
     */
    @GET("user/GetStoreBanner?")
    fun getStoreBanner(@Query("userid") userid: Int): Observable<GetStoreBannerResBean>

    /**
     * GET user/updateLHB?userid={userid}
     * 新用户领红包
     */
    @GET("user/updateLHB?")
    fun updateLHB(@Query("userid") userid: Int): Observable<BaseNomalResBean>

    /**
     * GET Packgae/getNtypeOneCount?user/GetMy?={userid}&type={type}
     * 根据用户类型 获取通知类型第一级是否带小红点
     */
    @GET("Packgae/getNtypeOneCount?")
    fun getNtypeOneCount(@Query("userid") userid: Int, @Query("type") type: Int): Observable<BaseIntResBean>

    /**
     * GET user/GetMy?userid={userid}
     * 获取用户信息
     */
    @GET("user/GetMy?")
    fun getMy(@Query("userid") userid: Int): Observable<UserLoginBean>

    /**
     * GET user/GetMygwcList/{userid}/{page}/{limit}
     * 获取我的购物车
     */
    @GET("user/GetMygwcList/{userid}/{page}/{limit}")
    fun getMyGwcList(@Path("userid") userid: Int, @Path("page") page: Int, @Path("limit") limit: Int): Observable<GetMyGwcListResBean>

    /**
     * POST user/GetMygwcjiajian
     * 购物车加减或者删除获取价格
     */
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("user/GetMygwcjiajian")
    fun gwcJiajian(@Body bean: GwcJiaJianReqBean): Observable<GwcJiaJianResBean>

    /**
     * GET user/Deletegwcforid/{gwcid}
     * 删除购物车
     */
    @GET("user/Deletegwcforid/{gwcid}")
    fun deletegwcforid(@Path("gwcid") gwcid: Int): Observable<BaseNomalResBean>

    /**
     * GET user/DeleteALLgwcforuserid/{userid}
     * 清空购物车
     */
    @GET("user/DeleteALLgwcforuserid/{userid}")
    fun deleteAllgwcForUserid(@Path("userid") userid: Int): Observable<BaseNomalResBean>

    /**
     * GET Packgae/AddSCMySp/{userid}/{mid}/{type}
     * 收藏商品
     */
    @GET("Packgae/AddSCMySp/{userid}/{mid}/{type}")
    fun addCollect(@Path("userid") userid: Int, @Path("mid") mid: Int, @Path("type") type: Int): Observable<BaseNomalResBean>

    /**
     * GET Packgae/CancelSCMySP/{userid}/{spid}/{type}
     * 取消收藏商品
     */
    @GET("Packgae/CancelSCMySP/{userid}/{spid}/{type}")
    fun cancelCollect(@Path("userid") userid: Int, @Path("spid") spid: Int, @Path("type") type: Int): Observable<BaseNomalResBean>

    /**
     * GET Packgae/GetPtypeList
     * 获取商品分类信息
     */
    @GET("Packgae/GetPtypeList")
    fun getptyptList(): Observable<GetPtypeListResBean>

    /**
     * GET Packgae/GetPeoductListByPtype_New?page={page}&limit={limit}&ptype={ptype}&mdid={mdid}&userid={userid}
     * 根据商品分类查询商品
     */
    @GET("Packgae/GetPeoductListByPtype_New?")
    fun getProductListByPTypeNew(@Query("page") page: Int, @Query("limit") limit: Int,
                                 @Query("ptype") ptype: Int, @Query("mdid") mdid: Int,
                                 @Query("userid") userid: Int): Observable<GetProductListByPTypeNewResBean>

    /**
     * POST fileUpLoad/fileUpLoad
     * 图片文件上传
     */
    @Multipart
    @POST("fileUpLoad/fileUpLoad")
    fun fileUpload(@Part() fil: MultipartBody.Part): Observable<UploadResBean>

    /**
     * 用户修改头像
     */
    @GET("user/updateFaceimg?")
    fun updateImg(@Query("faceimg") faceimg: String, @Query("littleimg") littleimg: String, @Query("userid") userid: Int): Observable<BaseNomalResBean>

    /**
     * GET user/updateNickname?nickname={nickname}&userid={userid}
     * 用户修改昵称
     */
    @GET("user/updateNickname?")
    fun updateNickname(@Query("nickname") nickname: String, @Query("userid") userid: Int): Observable<BaseNomalResBean>

    /**
     * GET user/updateSex?sex={sex}&userid={userid}
     * 用户修改性别
     */
    @GET("user/updateSex?")
    fun updateSex(@Query("sex") sex: Int, @Query("userid") userid: Int): Observable<BaseNomalResBean>

    /**
     * GET user/updateBirthday?birthday={birthday}&userid={userid}
     * 用户修改生日
     */
    @GET("user/updateBirthday?")
    fun updateBirthday(@Query("birthday") birthday: String, @Query("userid") userid: Int): Observable<BaseIntResBean>

    /**
     * GET user/updateWxno?wxno={wxno}&userid={userid}
     * 用户修改微信号
     */
    @GET("user/updateWxno?")
    fun updateWxno(@Query("wxno") wxno: String, @Query("userid") userid: Int): Observable<BaseNomalResBean>

    /**
     * GET Packgae/GetPeoductDetailInfo?pid={pid}&userid={userid}
     * 获取商品数据详细信息(商城)
     */
    @GET("Packgae/GetPeoductDetailInfo?")
    fun getProDetailInfo(@Query("pid") pid: Int, @Query("userid") userid: Int): Observable<GetProDetailInfoResBean>

    /**
     * POST user/AddGouwuche
     * 加入购物车
     * {
     * "spid": 1,
     * "counts": 2,
     * "userid": 3
     * }
     */
    @FormUrlEncoded
    @POST("user/AddGouwuche")
    fun addgwc(@Field("spid") spid: Int, @Field("counts") counts: Int, @Field("userid") userid: Int): Observable<BaseNomalResBean>

    /**
     * GET user/GetIsgoumai?userid={userid}&pid={pid}&_jinbi={_jinbi}
     * 判断用户是否可以抵扣新用户专享商品
     */
    @GET("user/GetIsgoumai?")
    fun getIsGouMai(@Query("userid") userid: Int, @Query("pid") pid: Int, @Query("_jinbi") _jinbi: Int): Observable<BaseNomalResBean>

    /**
     * GET user/GetMyjinbi?userid={userid}&type={type}&page={page}&limit={limit}
     * 获取我的金币
     */
    @GET("user/GetMyjinbi?")
    fun getMyjinbi(@Query("userid") userid: Int, @Query("type") type: Int, @Query("page") page: Int, @Query("limit") limit: Int): Observable<GetMyJinBiResBean>

    /**
     * GET Packgae/GetPackageList_new_group?mdid={mdid}&page={page}&limit={limit}
     * 云猫疗程新接口(规格)
     */
    @GET("Packgae/GetPackageList_new_group?")
    fun getPackageList_new_group(@Query("mdid") mdid: Int, @Query("page") page: Int, @Query("limit") limit: Int): Observable<GetPackageListNewGroupResBean>

    /**
     * GET Packgae/GetPackageList_new_group_pariid?mdid={mdid}&parid={parid}
     * 获取全部规格疗程
     */
    @GET("Packgae/GetPackageList_new_group_pariid?")
    fun getPackageList_new_group_pariid(@Query("mdid") mdid: Int, @Query("parid") parid: Int): Observable<GetPackageListNewGroupPariidResBean>

    /**
     * Get Packgae/GetPackageDetail?yid={yid}&userid={userid}
     * 获取云猫套餐数据详细信息
     */
    @GET("Packgae/GetPackageDetail?")
    fun getPackageDetail(@Query("yid") yid: Int, @Query("userid") userid: Int): Observable<GetPackageDetailResBean>

}