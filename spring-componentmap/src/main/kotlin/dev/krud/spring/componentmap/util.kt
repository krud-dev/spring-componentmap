package dev.krud.spring.componentmap

import org.apache.commons.lang3.ClassUtils
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.WildcardType

internal fun Field.getGenericClass(index: Int): Class<*>? {
    return try {
        val genericType = this.genericType as ParameterizedType
        val typeArgument = genericType.actualTypeArguments[index]
        if (typeArgument is WildcardType) {
            val upperBound = typeArgument.upperBounds[0]
            return if (upperBound is ParameterizedType) {
                upperBound.rawType as Class<*>
            } else {
                upperBound as Class<*>
            }
        }
        return typeArgument as Class<*>
    } catch (e: ArrayIndexOutOfBoundsException) { null }
}

/**
 * Resolve a sublevel generic type (For example Map<*, List<Child>>)
 */
fun Field.resolveNestedGeneric(parentIndex: Int, childIndex: Int = 0): Class<*> {
    val genericType = this.genericType
    if (genericType !is ParameterizedType) {
        error("${this.type} is not a parameterized type")
    }
    var childType = genericType.actualTypeArguments[parentIndex]
    while (childType is WildcardType) {
        childType = childType.upperBounds[0]
    }

    if (childType is ParameterizedType) {
        var returnValue = childType.actualTypeArguments[childIndex]
        while (returnValue is WildcardType) {
            returnValue = returnValue.upperBounds[0]
        }
        return returnValue as Class<*>
    }

    return childType as Class<*>
}

fun getFields(type: Class<*>): List<Field> {
    val fields: MutableList<Field> = ArrayList()
    var classToGetFields: Class<*>? = type
    while (classToGetFields != null) {
        fields.addAll(listOf(*classToGetFields.declaredFields))
        classToGetFields = classToGetFields.superclass
    }
    return fields
}

fun getMethods(type: Class<*>): List<Method> {
    val methods: MutableList<Method> = ArrayList()
    var classToGetMethods: Class<*>? = type
    while (classToGetMethods != null) {
        methods.addAll(listOf(*classToGetMethods.declaredMethods))
        classToGetMethods = classToGetMethods.superclass
    }
    return methods
}

fun getMethodDeclarer(method: Method): List<Class<*>> {
    var declaringClass: Class<*> = method.declaringClass
    val methodName: String = method.name
    val parameterTypes: Array<Class<*>> = method.parameterTypes
    val allInterfaces = ClassUtils.getAllInterfaces(declaringClass).toMutableList()
    val possibleDeclarers = mutableListOf<Class<*>>()
    var methodFound = false
    for (interfaceType in allInterfaces) {
        try {
            interfaceType.getMethod(methodName, *parameterTypes)
            methodFound = true
        } catch (ex: NoSuchMethodException) {
            if (methodFound) {
                break
            }
        }
        possibleDeclarers.add(interfaceType)
    }
    while (true) {
        declaringClass = declaringClass.superclass ?: break
        try {
            declaringClass.getMethod(methodName, *parameterTypes)
            methodFound = true
        } catch (ex: NoSuchMethodException) {
            if (methodFound) {
                break
            }
        }
        possibleDeclarers.add(declaringClass)
    }
    return possibleDeclarers
}