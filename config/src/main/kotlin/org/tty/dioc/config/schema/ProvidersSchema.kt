package org.tty.dioc.config.schema

import kotlin.reflect.KClass

/**
 * a provider schema, binds the schema between
 */
class ProvidersSchema<T: Any>(
    override val name: String,
    val interfaceType: KClass<T>,
    val default: List<KClass<out T>> = listOf(),
    override val rule: ConfigRule
): ConfigSchema {
    override fun toString(): String {
        return "provider[$name] ${interfaceType.simpleName} <--- ${default.map { it.simpleName }}"
    }
}