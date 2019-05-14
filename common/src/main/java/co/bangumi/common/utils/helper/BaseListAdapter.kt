package co.bangumi.common.utils.helper


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.bangumi.common.model.entity.Announce
import co.bangumi.common.model.entity.Bangumi
import co.bangumi.common.model.entity.BangumiDetail


inline fun <VH : RecyclerView.ViewHolder> RecyclerView.setUpWithAnnounce(
    crossinline createViewHolder: (view: View) -> VH,
    layoutID: Int,
    crossinline bind: VH.(index: Int, item: Announce) -> Unit
) {
    this.adapter = object : ListAdapter<Announce, VH>(object : DiffUtil.ItemCallback<Announce>() {
        override fun areItemsTheSame(oldItem: Announce, newItem: Announce): Boolean {
            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: Announce, newItem: Announce): Boolean {
            return oldItem == newItem
        }
    }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val inflater = LayoutInflater.from(parent.context)
            return createViewHolder(inflater.inflate(layoutID, parent, false))
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.apply {
                bind(position, getItem(position))
            }
        }
    }
}

fun <VH : RecyclerView.ViewHolder> RecyclerView.setUpWithBangumi(
    createViewHolder: (view: View) -> VH,
    layoutID: Int,
    bind: VH.(index: Int, item: Bangumi) -> Unit
): ListAdapter<Bangumi, VH> {
    val listAdapter = object : ListAdapter<Bangumi, VH>(object : DiffUtil.ItemCallback<Bangumi>() {
        override fun areItemsTheSame(oldItem: Bangumi, newItem: Bangumi): Boolean {
            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: Bangumi, newItem: Bangumi): Boolean {
            return oldItem == newItem
        }
    }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val inflater = LayoutInflater.from(parent.context)
            return createViewHolder(inflater.inflate(layoutID, parent, false))
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.apply {
                bind(position, getItem(position))
            }
        }
    }
    adapter = listAdapter
    return listAdapter
}

inline fun <VH : RecyclerView.ViewHolder> RecyclerView.setUpWithBangumiDetail(
    crossinline createViewHolder: (view: View) -> VH,
    layoutID: Int,
    crossinline bind: VH.(index: Int, item: BangumiDetail) -> Unit
) {
    this.adapter = object : ListAdapter<BangumiDetail, VH>(object : DiffUtil.ItemCallback<BangumiDetail>() {
        override fun areItemsTheSame(oldItem: BangumiDetail, newItem: BangumiDetail): Boolean {
            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: BangumiDetail, newItem: BangumiDetail): Boolean {
            return oldItem == newItem
        }
    }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val inflater = LayoutInflater.from(parent.context)
            return createViewHolder(inflater.inflate(layoutID, parent, false))
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.apply {
                bind(position, getItem(position))
            }
        }
    }
}