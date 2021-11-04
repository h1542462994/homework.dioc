package org.tty.dioc.core.key

import org.tty.dioc.annotation.Component
import org.tty.dioc.annotation.Lifecycle
import kotlin.reflect.KClass

/**
 * the identifier of the service which lifeCycle is [Lifecycle.Singleton]
 * @see [Lifecycle]
 */
@Component(lifecycle = Lifecycle.Singleton)
data class SingletonKey(
    /**
     * the type of the service (runtime)
     */
    override val indexType: KClass<*>
): TypedComponentKey