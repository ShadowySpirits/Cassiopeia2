package co.bangumi.common.utils.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.bangumi.common.model.entity.Bangumi
import co.bangumi.common.model.entity.Episode

inline fun <VH : RecyclerView.ViewHolder> RecyclerView.setUpWithBangumi(
    crossinline createViewHolder: (view: View) -> VH,
    layoutID: Int,
    crossinline bind: VH.(index: Int, item: Bangumi) -> Unit
): ListAdapter<Bangumi, VH> {
    return setUp(
        createViewHolder,
        layoutID,
        object : DiffUtil.ItemCallback<Bangumi>() {
            override fun areItemsTheSame(oldItem: Bangumi, newItem: Bangumi): Boolean {
                return oldItem.id === newItem.id
            }

            override fun areContentsTheSame(oldItem: Bangumi, newItem: Bangumi): Boolean {
                return oldItem == newItem
            }
        }, bind
    )
}

inline fun RecyclerView.setUpWithBangumiEpisode(
    layoutID: Int,
    crossinline bind: RecyclerView.ViewHolder.(index: Int, item: Episode) -> Unit
): ListAdapter<Episode, RecyclerView.ViewHolder> {
    return setUp(
        { view -> object : RecyclerView.ViewHolder(view) {} },
        layoutID,
        object : DiffUtil.ItemCallback<Episode>() {
            override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem.id === newItem.id
            }

            override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem == newItem
            }
        },
        bind
    )
}

inline fun <T, VH : RecyclerView.ViewHolder> RecyclerView.setUp(
    crossinline createViewHolder: (view: View) -> VH,
    layoutID: Int,
    diffCallback: DiffUtil.ItemCallback<T>,
    crossinline bind: VH.(index: Int, item: T) -> Unit
): ListAdapter<T, VH> {
    return object : ListAdapter<T, VH>(diffCallback) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val inflater = LayoutInflater.from(parent.context)
            return createViewHolder(inflater.inflate(layoutID, parent, false))
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.apply {
                bind(position, getItem(position))
            }
        }
    }.also {
        adapter = it
    }
}