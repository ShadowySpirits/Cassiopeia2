package co.bangumi.cassiopeia.ui

import android.content.Intent
import android.net.Uri
import android.view.MenuItem
import android.view.View
import androidx.core.net.toUri
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.bangumi.cassiopeia.R
import co.bangumi.cassiopeia.databinding.FragmentDetailBinding
import co.bangumi.cassiopeia.viewmodel.DetailViewModel
import co.bangumi.common.BGM_DETAIL
import co.bangumi.common.DETAIL_URL_PREFIX
import co.bangumi.common.DYNAMIC_LINK_PREFIX
import co.bangumi.common.annotation.*
import co.bangumi.common.model.entity.Bangumi
import co.bangumi.common.model.entity.Episode
import co.bangumi.common.network.FavoriteChangeRequest
import co.bangumi.common.utils.dayOfWeek
import co.bangumi.common.utils.extension.setUp
import co.bangumi.common.utils.loadImage
import co.bangumi.framework.base.BaseFragment
import co.bangumi.framework.util.extension.*
import co.bangumi.framework.util.getVersionCode
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment
import com.yalantis.contextmenu.lib.MenuObject
import com.yalantis.contextmenu.lib.MenuParams
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_episode.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class DetailFragment : BaseFragment<FragmentDetailBinding>(), OnMenuItemClickListener {

    private val mViewModel: DetailViewModel by viewModel()
    private val args: DetailFragmentArgs by navArgs()
    private val homeActivity: HomeActivity by lazy { activity as HomeActivity }
    private lateinit var listAdapter: ListAdapter<Episode, RecyclerView.ViewHolder>
    private lateinit var mMenuDialogFragment: ContextMenuDialogFragment

    override fun getLayoutId(): Int = R.layout.fragment_detail

    override fun initView() {
        mBinding.vm = mViewModel
        mBinding.presenter = this

        setHasOptionsMenu(true)
        homeActivity.setSupportActionBar(mBinding.toolbar)
        homeActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        homeActivity.toolbar.visibility = View.GONE

        val bangumi = args.bangumi
        mBinding.toolbar.title = bangumi.localName
        loadImage(this, mBinding.image, bangumi)
        mBinding.subtitle.text = bangumi.subTitle
        mBinding.info.text = getString(R.string.update_info)
            .format(
                bangumi.eps, dayOfWeek(bangumi.air_weekday),
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

        mBinding.episodeList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        listAdapter = mBinding.episodeList.setUp(R.layout.item_episode) { _, item ->
            loadImage(
                this@DetailFragment,
                itemView.image,
                item.thumbnail,
                item.thumbnail_color ?: "#00000000"
            )
            itemView.title.text = item.localName
            itemView.progress.setProgress(item.watch_progress?.percentage ?: 0f)
        }

        initMenuFragment()

        mBinding.collectionStatus.setOnClickListener {
            if (fragmentManager?.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                mMenuDialogFragment.show(fragmentManager!!, ContextMenuDialogFragment.TAG)
            }
        }

        mBinding.btnBgmTv.setOnClickListener {
            onClick(it) {
                if (bangumi.bgm_id <= 0) {
                    return@onClick
                }
                val i = Intent(Intent.ACTION_VIEW)
                i.data = (BGM_DETAIL + bangumi.bgm_id).toUri()
                startActivity(i)
            }
        }

        mBinding.imgShare.setOnClickListener {
            onClick(it) {
                if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(homeActivity) != ConnectionResult.SUCCESS) {
                    val textIntent = Intent(Intent.ACTION_SEND)
                    textIntent.type = "text/plain"
                    textIntent.putExtra(Intent.EXTRA_TEXT, DETAIL_URL_PREFIX + bangumi.id)
                    startActivity(Intent.createChooser(textIntent, bangumi.localName))
                    return@onClick
                }
                FirebaseDynamicLinks.getInstance().createDynamicLink()
                    .setLink(Uri.parse(DETAIL_URL_PREFIX + bangumi.id))
                    .setDomainUriPrefix(DYNAMIC_LINK_PREFIX)
                    .setAndroidParameters(
                        DynamicLink.AndroidParameters.Builder(homeActivity.packageName)
                            .setMinimumVersion(getVersionCode(homeActivity))
                            .build()
                    )
                    .setSocialMetaTagParameters(
                        DynamicLink.SocialMetaTagParameters.Builder()
                            .setTitle(bangumi.localName)
                            .setDescription(bangumi.summary)
                            .setImageUrl(Uri.parse(bangumi.image))
                            .build()
                    )
                    .buildShortDynamicLink(ShortDynamicLink.Suffix.SHORT)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val shortLink = it.result!!.shortLink
                            val textIntent = Intent(Intent.ACTION_SEND)
                            textIntent.type = "text/plain"
                            textIntent.putExtra(Intent.EXTRA_TEXT, shortLink.toString())
                            startActivity(Intent.createChooser(textIntent, bangumi.localName))
                        } else {
                            homeActivity.toastError(getString(R.string.network_error))
                        }
                    }
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

    private fun initMenuFragment() {
        val menuParams = MenuParams()
        menuParams.actionBarSize = resources.getDimension(R.dimen.context_menu_height).toInt()
        val collectionStatusArray = resources.getStringArray(R.array.array_favorite)
        collectionStatusArray[0] = getString(R.string.delete_collection)
        val contextMenuList = ArrayList<MenuObject>()
        collectionStatusArray.forEachIndexed { index, value ->
            val menuObject = MenuObject(value)
            menuObject.resource = when (index) {
                DEFAULT -> R.drawable.ic_delete
                WISH -> R.drawable.ic_wish
                WATCHED -> R.drawable.ic_watched
                WATCHING -> R.drawable.ic_watching
                PAUSED -> R.drawable.ic_paused
                ABANDONED -> R.drawable.ic_abandoned
                else -> R.drawable.ic_wish
            }
            contextMenuList.add(menuObject)
        }
        menuParams.menuObjects = contextMenuList
        menuParams.isClosableOutside = true
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams)
        mMenuDialogFragment.setItemClickListener(this)
    }

    override fun onMenuItemClick(v: View?, position: Int) {
        val array = resources.getStringArray(R.array.array_favorite)
        if (mBinding.collectionStatusText.text == array[position]) {
            return
        }
        if (position == DEFAULT) {
            mBinding.collectionStatusText.text = getString(R.string.collect)
        } else {
            mBinding.collectionStatusText.text = array[position]
        }
        requestAsync(
            { mViewModel.uploadFavoriteStatusAsync(args.bangumi.id, FavoriteChangeRequest(position)) },
            { args.bangumi.favorite_status = position },
            {
                activity?.dispatchFailure(it)
                if (args.bangumi.favorite_status == DEFAULT) {
                    mBinding.collectionStatusText.text = getString(R.string.collect)
                } else {
                    mBinding.collectionStatusText.text = array[args.bangumi.favorite_status]
                }
            })
        mBinding.collectionStatusText.adaptWidth()
    }

    override fun loadData(isRefresh: Boolean) {
        request({ mViewModel.getBangumiDetailAsync(args.bangumi.id) }) {
            listAdapter.submitList(it.episodes)
        }
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