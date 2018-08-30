package com.mars.user.ui.home.article

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import com.mars.user.R
import com.mars.user.base.act.BaseActivity
import com.mars.user.ui.home.article.frag.ArticleListFrg
import kotlinx.android.synthetic.main.activity_article.*

/**
 * Created by gu on 2018/8/29
 * Desc: 云猫美学
 */
class ArticleActivity : BaseActivity() {

    companion object {
        fun startSelf(context: Context) {
            val intent = Intent(context, ArticleActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_article
    }

    override fun configViews() {
        val titleArr = arrayOf("美丽篇", "健康篇", "品牌篇")
        val fragArr = ArrayList<Fragment>()
        fragArr.add(ArticleListFrg.newInstence(4))
        fragArr.add(ArticleListFrg.newInstence(5))
        fragArr.add(ArticleListFrg.newInstence(6))

        tabArticle.setViewPager(vpArticle, titleArr, this, fragArr)
    }
}
