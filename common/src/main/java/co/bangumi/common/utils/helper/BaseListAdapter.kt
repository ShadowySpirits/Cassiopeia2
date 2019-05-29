package co.bangumi.common.utils.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.bangumi.common.model.entity.EntityWithId

inline fun <T : EntityWithId, VH : RecyclerView.ViewHolder> RecyclerView.setUpWithEntityWithId(
    crossinline createViewHolder: (view: View) -> VH,
    layoutID: Int,
    crossinline bind: VH.(index: Int, item: T) -> Unit
): ListAdapter<T, VH> {
    return setUp(
        createViewHolder,
        layoutID,
        object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
                return oldItem.areContentsTheSame(newItem)
            }
        }, bind
    )
}

inline fun <T : EntityWithId> RecyclerView.setUpWithEntityWithId(
    layoutID: Int,
    crossinline bind: RecyclerView.ViewHolder.(index: Int, item: T) -> Unit
): ListAdapter<T, RecyclerView.ViewHolder> {
    return setUp(
        { view -> object : RecyclerView.ViewHolder(view) {} },
        layoutID,
        object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
                return oldItem.areContentsTheSame(newItem)
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