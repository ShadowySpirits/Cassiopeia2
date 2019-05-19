package co.bangumi.common.utils.helper


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.bangumi.common.model.entity.Bangumi
import co.bangumi.common.model.entity.Episode

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

fun <VH : RecyclerView.ViewHolder> RecyclerView.setUpWithBangumiEpisode(
    createViewHolder: (view: View) -> VH,
    layoutID: Int,
    bind: VH.(index: Int, item: Episode) -> Unit
): ListAdapter<Episode, VH> {
    val listAdapter = object : ListAdapter<Episode, VH>(object : DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
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