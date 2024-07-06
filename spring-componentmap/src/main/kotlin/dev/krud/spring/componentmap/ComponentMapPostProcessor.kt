package dev.krud.spring.componentmap

import org.springframework.aop.framework.Advised
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.util.ReflectionUtils
import java.util.concurrent.ConcurrentHashMap

class ComponentMapPostProcessor : BeanPostProcessor {

    private val componentMaps: MutableMap<ComponentMapIdentifier, MutableMap<Any, MutableList<Any>>> = ConcurrentHashMap()

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
        registerComponentMapKey(bean)
        fillComponentMapsIfExist(bean)
        return bean
    }

    private fun fillComponentMapsIfExist(bean: Any) {
        var handler: Any = bean
        if (handler is Advised) {
            handler = handler.targetSource.target ?: error("Target is null")
        }

        val fields = getFields(handler.javaClass)

        for (field in fields) {
            if (field.isAnnotationPresent(ComponentMap::class.java)) {
                if (!Map::class.java.isAssignableFrom(field.type)) {
                    error("@ComponentMap may only be used on maps")
                }

                val mapped = field.getAnnotation(ComponentMap::class.java)
                try {
                    val keyClazz = field.getGenericClass(0)!!
                    var valueClazz = field.getGenericClass(1)!!
                    var isList = false
                    if (Collection::class.java.isAssignableFrom(valueClazz)) {
                        isList = true
                        valueClazz = field.resolveNestedGeneric(1)
                    }

                    ReflectionUtils.makeAccessible(field)
                    val map = getOrCreateComponentMap(keyClazz, valueClazz)
                    if (isList) {
                        field[handler] = map
                    } else {
                        field[handler] = SingletonComponentMap(map)
                    }
                } catch (e: Exception) {
                }
            }
        }
    }

    private fun getOrCreateComponentMap(initialKeyType: Class<*>, initialValueType: Class<*>): MutableMap<Any, MutableList<Any>> {
        val identifier = ComponentMapIdentifier(initialKeyType, initialValueType)
        var map = componentMaps[identifier]
        if (map != null) {
            return map
        }

        map = componentMaps[identifier]
        if (map != null) {
            return map
        }

        componentMaps[identifier] = mutableMapOf()
        return componentMaps[identifier]!!
    }

    private fun registerComponentMapKey(bean: Any) {
        val methods = getMethods(bean.javaClass)

        for (method in methods) {
            val annotation = AnnotationUtils.findAnnotation(method, ComponentMapKey::class.java)
            if (annotation != null) {
                val key = method.invoke(bean)
                val keyClass = key::class.java
                val valueClasses = getMethodDeclarer(method)
                valueClasses.forEach {
                    val map = getOrCreateComponentMap(keyClass, it)
                    val list = map.computeIfAbsent(key) { mutableListOf() }
                    list += bean

                }
            }
        }
    }
}

private data class ComponentMapIdentifier(val keyClazz: Class<*>, val valueClazz: Class<*>)