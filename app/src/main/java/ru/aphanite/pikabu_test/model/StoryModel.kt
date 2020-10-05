package ru.aphanite.pikabu_test.model

data class StoryModel(val id: Long, val title: String, val body: String?, val urls: List<String>?, var isFavorite: Boolean) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StoryModel

        if (id != other.id) return false
        if (title != other.title) return false
        if (body != other.body) return false
        if (urls != other.urls) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + (body?.hashCode() ?: 0)
        result = 31 * result + (urls?.hashCode() ?: 0)
        return result
    }
}