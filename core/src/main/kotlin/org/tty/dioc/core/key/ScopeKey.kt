package org.tty.dioc.core.key

import org.tty.dioc.annotation.Component
import org.tty.dioc.annotation.Lifecycle
import org.tty.dioc.core.lifecycle.Scope
import kotlin.reflect.KClass

/**
 * the identifier of the service which lifecycle is [Lifecycle.Scoped]
 * @see [Lifecycle]
 */
@Component(lifecycle = Lifecycle.Scoped)
data class ScopeKey(
    /**
     * the type of service (runtime)
     */
    override val indexType: KClass<*>,
    /**
     * the scope
     */
    override val scope: Scope
): TypedComponentKey, ScopedComponentKey