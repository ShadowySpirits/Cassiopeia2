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
import co.bangumi.common.model.entity.Bangumi
import co.bangumi.common.utils.dayOfWeek
import co.bangumi.common.utils.extension.setUpWithViewHolder
import co.bangumi.common.utils.loadImage
import co.bangumi.framework.base.BaseFragment
import co.bangumi.framework.util.extension.loadDataBundleAsync
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val mViewModel: HomeViewModel by viewModel()
    private val homeActivity: HomeActivity by lazy { activity as HomeActivity }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun onStart() {
        super.onStart()
        homeActivity.toolbar.visibility = View.VISIBLE
    }

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
            mBinding.listRecommended.setUpWithViewHolder<Bangumi, LargeCardHolder>(
                ::LargeCardHolder,
                R.layout.item_bangumi_large
            ) { _, item ->
                loadImage(this@HomeFragment, image, item.cover_image.url, item.cover_color)
                title.text = item.localName
                subtitle.text = item.summary
                eps.text = getString(R.string.eps_all).format(item.eps)
                itemView.setOnClickListener {
                    findNavController().navigate(HomeFragmentDirections.actionToDetailFragment(item))
                }
            }
        mViewModel.announceList.observe(this) { list ->
            if (list.isNotEmpty()) {
                adapter.submitList(list)
                mBinding.layoutRecommended.visibility = View.VISIBLE
            } else {
                mBinding.layoutRecommended.visibility = View.GONE
            }
        }
    }

    private fun setUpWatching() {
        mBinding.listWatching.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapter =
            mBinding.listWatching.setUpWithViewHolder<Bangumi, MediumCardHolder>(
                ::MediumCardHolder,
                R.layout.item_bangumi_medium
            ) { _, item ->
                loadImage(this@HomeFragment, image, item)
                title.text = item.localName
                new.text = getString(R.string.unwatched).format(item.unwatched_count)
                itemView.setOnClickListener {
                    findNavController().navigate(HomeFragmentDirections.actionToDetailFragment(item))
                }
            }
        mViewModel.watchingList.observe(this) { list ->
            if (list.isNotEmpty()) {
                adapter.submitList(list)
                mBinding.layoutWatching.visibility = View.VISIBLE
            } else {
                mBinding.layoutWatching.visibility = View.GONE
            }
        }
    }

    private fun setUpReleasing() {
        mBinding.listReleasing.layoutManager = LinearLayoutManager(context)
        val adapter = mBinding.listReleasing.setUpWithViewHolder<Bangumi, WideCardHolder>(
            ::WideCardHolder,
            R.layout.item_bangumi_wide
        ) { _, item ->
            loadImage(this@HomeFragment, image, item)
            title.text = item.localName
            subtitle.text = item.subTitle
            info.text = getString(R.string.update_info)
                .format(
                    item.eps, dayOfWeek(item.air_weekday),
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
                adapter.submitList(list)
                mBinding.layoutReleasing.visibility = View.VISIBLE
            } else {
                mBinding.layoutReleasing.visibility = View.GONE
            }
        }
    }

    override fun loadData(isRefresh: Boolean) {
        mBinding.swipeRefresh.isRefreshing = true
        loadDataBundleAsync(
            mViewModel::getAnnounceBangumiAsync,
            mViewModel::getMyBangumiAsync,
            mViewModel::getOnAirAsync
        ) { mBinding.swipeRefresh.isRefreshing = false }
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
