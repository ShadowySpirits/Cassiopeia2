package co.bangumi.cassiopeia.ui

import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.navArgs
import co.bangumi.cassiopeia.R
import co.bangumi.cassiopeia.databinding.FragmentDetailBinding
import co.bangumi.cassiopeia.viewmodel.DetailViewModel
import co.bangumi.common.annotation.RAW
import co.bangumi.common.model.entity.Bangumi
import co.bangumi.common.utils.ImageUtil
import co.bangumi.common.utils.StringUtil
import co.bangumi.framework.base.BaseFragment
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val mViewModel: DetailViewModel by viewModel()
    private val args: DetailFragmentArgs by navArgs()
    private val homeActivity: HomeActivity by lazy { activity as HomeActivity }

    override fun getLayoutId(): Int = R.layout.fragment_detail

    override fun initView() {
        mBinding.vm = mViewModel
        mBinding.presenter = this

        setHasOptionsMenu(true)
        homeActivity.setSupportActionBar(mBinding.toolbar)
        homeActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        homeActivity.toolbar.visibility = View.GONE

        val bangumi = args.bangumi
        mBinding.toolbar.title = bangumi.localName()
        ImageUtil.loadImage(this, mBinding.image, bangumi)
        mBinding.subtitle.text = bangumi.subTitle()
        mBinding.info.text = getString(R.string.update_info)
            .format(
                bangumi.eps, bangumi.air_weekday.let { StringUtil.dayOfWeek(it) },
                if (bangumi.isOnAir()) getString(R.string.on_air) else getString(R.string.finished)
            )
        if (bangumi.type == RAW) {
            mBinding.typeRaw.visibility = View.VISIBLE
            mBinding.typeSub.visibility = View.GONE
        } else {
            mBinding.typeSub.visibility = View.VISIBLE
            mBinding.typeRaw.visibility = View.GONE
        }
        less(bangumi)
        mBinding.summaryLayout.setOnClickListener {
            if (mBinding.btnMore.tag == 0) {
                less(bangumi)
            } else {
                more(bangumi)
            }
        }
    }

    private fun less(bangumi: Bangumi) {
        mBinding.summary.text = bangumi.summary.replace("\n", "\t")
        mBinding.summary.maxLines = 1
        mBinding.summary.post {
            mBinding.summary2.text = mBinding.summary.text.toString().substring(mBinding.summary.layout.getLineEnd(0))
        }
        mBinding.btnMore.setText(R.string.more)
        mBinding.btnMore.tag = 1
    }

    private fun more(bangumi: Bangumi) {
        mBinding.summary.text = bangumi.summary
        mBinding.summary2.text = ""
        mBinding.summary.maxLines = 20
        mBinding.btnMore.setText(R.string.less)
        mBinding.btnMore.tag = 0
    }

    override fun loadData(isRefresh: Boolean) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDebouncedClick(view: View) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                homeActivity.onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        homeActivity.toolbar.visibility = View.VISIBLE
    }
}