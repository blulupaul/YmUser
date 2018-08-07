package com.mars.user.ui.store.proevaluation

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.mars.user.R
import com.mars.user.base.BaseActivity
import com.mars.user.constant.LIST_KEY
import com.mars.user.ui.store.prodetail.adapter.ProPjRvAdapter
import com.mars.user.ui.store.prodetail.bean.GetProDetailInfoResBean
import com.scwang.smartrefresh.layout.footer.FalsifyFooter
import com.scwang.smartrefresh.layout.header.FalsifyHeader
import kotlinx.android.synthetic.main.activity_pro_evalution.*

class ProEvalutionActivity : BaseActivity() {

    companion object {
        fun startSelf(context: Context, pjDataList: ArrayList<GetProDetailInfoResBean.Data.Evaluation>) {
            val intent = Intent(context, ProEvalutionActivity::class.java)
            intent.putExtra(LIST_KEY, pjDataList)
            context.startActivity(intent)
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_pro_evalution
    }

    override fun configViews() {
        val pjDataList = intent.getSerializableExtra(LIST_KEY) as ArrayList<GetProDetailInfoResBean.Data.Evaluation>
        configView(pjDataList)
    }

    private fun configView(pjDataList: ArrayList<GetProDetailInfoResBean.Data.Evaluation>) {
        refreshLayout.setRefreshHeader(FalsifyHeader(this))
        refreshLayout.setRefreshFooter(FalsifyFooter(this))
        pjListAll.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val proPjRvAdapter = ProPjRvAdapter(true)
        pjListAll.adapter = proPjRvAdapter

        proPjRvAdapter.setNewData(pjDataList)
    }
}
