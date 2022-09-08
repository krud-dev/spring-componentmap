package dev.krud.spring.componentmap

class SingletonComponentMap<K, V>(private val originalMap: Map<K, Collection<V>>) : Map<K, V> {
    override val size: Int
        get() = originalMap.size

    override val values: Collection<V>
        get() = originalMap.values.map { it.first() }
    override val entries: Set<Map.Entry<K, V>>
        get() = originalMap.map {
            it.key to it.value.first()
        }.toMap().entries
    override val keys: Set<K>
        get() = originalMap.keys

    override fun containsKey(key: K): Boolean {
        return originalMap.containsKey(key)
    }

    override fun containsValue(value: V): Boolean {
        return originalMap.values.any { it.first() == value }
    }

    override fun get(key: K): V? {
        return originalMap[key]?.first()
    }

    override fun isEmpty(): Boolean {
        return originalMap.isEmpty()
    }
}