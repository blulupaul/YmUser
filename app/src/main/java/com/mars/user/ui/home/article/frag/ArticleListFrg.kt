package com.mars.user.ui.home.article.frag

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.widget.ImageView
import com.lesincs.simpleread.base.LazyInitFragment
import com.mars.user.R
import com.mars.user.constant.INT_KEY
import com.mars.user.ui.home.article.ArticleDetailActivity
import com.mars.user.ui.home.article.articlelistmvp.ArticleListContract
import com.mars.user.ui.home.article.articlelistmvp.ArticleListPresenter
import com.mars.user.ui.main_frg.home.adapter.MeiWenRvAdapter
import com.mars.user.ui.main_frg.home.bean.GetYmmxListResBean
import com.mars.user.utils.glideLoad
import com.mars.user.utils.selfAdaptionImageView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.frag_article_list.*

/**
 * Created by gu on 2018/08/29
 * desc: ${desc}
 */
class ArticleListFrg : LazyInitFragment(), ArticleListContract.View {
    override fun onRefreshFinish() {
        refreshLayout.finishRefresh()
    }

    override fun onLoadMoreFinish() {
        refreshLayout.finishLoadMore()
    }

    override fun onGetYmmxListSuccess(bean: GetYmmxListResBean) {
        if (articlePresenter?.page == 1) {
            meiWenRvAdapter.setNewData(bean.data)
        } else {
            meiWenRvAdapter.addData(bean.data)
        }
        glideLoad(context!!, meiWenRvAdapter.data[0].mimages, articleImg!!)
    }

    override fun onGetYmmxListFail(msg: String) {

    }

    //首页美文列表
    private val meiWenRvAdapter = MeiWenRvAdapter()
    private var articlePresenter: ArticleListPresenter? = null
    private var articleImg: ImageView? = null

    companion object {
        fun newInstence(typeId: Int): ArticleListFrg {
            val articleFrag = ArticleListFrg()
            val bundle = Bundle()
            bundle.putInt(INT_KEY, typeId)
            articleFrag.arguments = bundle
            return articleFrag
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.frag_article_list
    }

    override fun configViews() {
        val typeId = arguments!!.getInt(INT_KEY)
        articlePresenter = ArticleListPresenter(this, typeId,1)

        initView()
        initListener()
        articlePresenter?.onStart()
    }

    private fun initView() {
        articleList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        articleList.adapter = meiWenRvAdapter

        if (!isInitData) {
            val mHeaderView = LayoutInflater.from(context).inflate(R.layout.meiwen_header_view, refreshLayout, false)
            articleImg = mHeaderView.findViewById(R.id.img_headerPic)
            selfAdaptionImageView(context!!, 650, 500, articleImg!!)
            meiWenRvAdapter.addHeaderView(mHeaderView)
        }
    }

    private fun initListener() {
        articleImg!!.setOnClickListener {
            ArticleDetailActivity.startSelf(context!!,meiWenRvAdapter.data[0].mid)
        }

        meiWenRvAdapter.setOnItemClickListener { _, _, position ->
            ArticleDetailActivity.startSelf(context!!,meiWenRvAdapter.data[position].mid)
        }

        refreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout?) {
                articlePresenter?.onLoadMore()
            }

            override fun onRefresh(refreshLayout: RefreshLayout?) {
                articlePresenter?.onRefresh()
            }
        })
    }

}
