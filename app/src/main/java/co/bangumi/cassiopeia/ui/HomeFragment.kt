package co.bangumi.cassiopeia.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.bangumi.cassiopeia.R
import co.bangumi.cassiopeia.databinding.FragmentHomeBinding
import co.bangumi.cassiopeia.viewmodel.HomeViewModel
import co.bangumi.common.annotation.RAW
import co.bangumi.common.utils.ImageUtil
import co.bangumi.common.utils.StringUtil
import co.bangumi.common.utils.helper.setUpWithBangumi
import co.bangumi.framework.base.BaseFragment
import co.bangumi.framework.util.helper.request
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val mViewModel: HomeViewModel by viewModel()

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initView() {
        isLoadOnce = true
        mBinding.presenter = this
        mBinding.vm = mViewModel

        mBinding.swipeRefresh.setColorSchemeResources(R.color.cassiopeiaRed)
        mBinding.swipeRefresh.setOnRefreshListener {
            loadData(true)
        }
        setUpRecommended()
        setUpWatching()
        setUpReleasing()
    }

    private fun setUpRecommended() {
        mBinding.listRecommended.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapter =
            mBinding.listRecommended.setUpWithBangumi(::LargeCardHolder, R.layout.item_bangumi_large) { _, item ->
                ImageUtil.loadImage(this@HomeFragment, image, item.cover_image.url, item.cover_color)
                title.text = item.localName()
                subtitle.text = item.summary
                eps.text = getString(R.string.eps_all).format(item.eps)
                itemView.setOnClickListener {
                    findNavController().navigate(HomeFragmentDirections.actionToDetailFragment(item))
                }
            }
        mViewModel.announceList.observe(this) { list ->
            if (list.isNotEmpty()) {
                mBinding.layoutRecommended.visibility = View.VISIBLE
                adapter.submitList(list)
            } else {
                mBinding.layoutRecommended.visibility = View.GONE
            }
        }
    }

    private fun setUpWatching() {
        mBinding.listWatching.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapter =
            mBinding.listWatching.setUpWithBangumi(::MediumCardHolder, R.layout.item_bangumi_medium) { _, item ->
                ImageUtil.loadImage(this@HomeFragment, image, item)
                title.text = item.localName()
                new.text = getString(R.string.unwatched).format(item.unwatched_count)
                itemView.setOnClickListener {
                    findNavController().navigate(HomeFragmentDirections.actionToDetailFragment(item))
                }
            }
        mViewModel.watchingList.observe(this) { list ->
            if (list.isNotEmpty()) {
                mBinding.layoutWatching.visibility = View.VISIBLE
                adapter.submitList(list)
            } else {
                mBinding.layoutWatching.visibility = View.GONE
            }
        }
    }

    private fun setUpReleasing() {
        mBinding.listReleasing.layoutManager = LinearLayoutManager(context)
        val adapter = mBinding.listReleasing.setUpWithBangumi(::WideCardHolder, R.layout.item_bangumi_wide) { _, item ->
            ImageUtil.loadImage(this@HomeFragment, image, item)
            title.text = item.localName()
            subtitle.text = item.subTitle()
            info.text = getString(R.string.update_info)
                .format(
                    item.eps, item.air_weekday.let { StringUtil.dayOfWeek(it) },
                    if (item.isOnAir()) getString(R.string.on_air) else getString(R.string.finished)
                )
            if (item.favorite_status > 0) {
                val array = state.resources.getStringArray(R.array.array_favorite)
                if (array.size > item.favorite_status) {
                    state.text = array[item.favorite_status]
                }
            }

            if (item.type == RAW) {
                typeRaw.visibility = View.VISIBLE
                typeSub.visibility = View.GONE
            } else {
                typeSub.visibility = View.VISIBLE
                typeRaw.visibility = View.GONE
            }

            info2.text = item.summary.replace("\n", "")
            itemView.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionToDetailFragment(item))
            }
        }
        mViewModel.onAirList.observe(this) { list ->
            if (list.isNotEmpty()) {
                mBinding.layoutReleasing.visibility = View.VISIBLE
                adapter.submitList(list)
            } else {
                mBinding.layoutReleasing.visibility = View.GONE
            }
        }
    }

    private fun finishRefresh() {
        mBinding.swipeRefresh.isRefreshing = false
    }

    override fun loadData(isRefresh: Boolean) {
        mBinding.swipeRefresh.isRefreshing = true
        request(mViewModel::getAnnounceBangumiAsync, { finishRefresh() }, { finishRefresh() })
        request(mViewModel::getMyBangumiAsync) {
            finishRefresh()
        }
        request(mViewModel::getOnAirAsync) {
            finishRefresh()
        }
    }

    override fun onDebouncedClick(view: View) {}

    private class MediumCardHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imageView)
        val title: TextView = view.findViewById(R.id.title)
        val new: TextView = view.findViewById(R.id.new_count)
    }

    private class LargeCardHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imageView)
        val title: TextView = view.findViewById(R.id.title)
        val subtitle: TextView = view.findViewById(R.id.subtitle)
        val eps: TextView = view.findViewById(R.id.eps)
    }

    private class WideCardHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imageView)
        val title: TextView = view.findViewById(R.id.title)
        val subtitle: TextView = view.findViewById(R.id.subtitle)
        val info: TextView = view.findViewById(R.id.info)
        val state: TextView = view.findViewById(R.id.state)
        val info2: TextView = view.findViewById(R.id.info2)
        val typeSub: TextView = view.findViewById(R.id.type_sub)
        val typeRaw: TextView = view.findViewById(R.id.type_raw)
    }
}
