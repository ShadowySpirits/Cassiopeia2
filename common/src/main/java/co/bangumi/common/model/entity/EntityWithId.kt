package co.bangumi.common.model.entity

abstract class EntityWithId {
    abstract val id: String

    override fun equals(other: Any?): Boolean {
        return when (other) {
            !is EntityWithId -> false
            else -> this === other || id == other.id
        }
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    abstract fun areContentsTheSame(other: EntityWithId): Boolean
}