
package org.tty.dioc.core.declare

import org.tty.dioc.core.error.ServiceDeclarationException
import org.tty.dioc.core.util.ServiceUtil
import kotlin.reflect.KClass

/**
 * a implementation of [MutableServiceDeclares] and [ReadonlyServiceDeclares]
 */
class ServiceDeclares(serviceDeclares: List<ServiceDeclare>) : MutableServiceDeclares, ReadonlyServiceDeclares {
    private val container = ArrayList<ServiceDeclare>()
    init {
        container.addAll(serviceDeclares)
    }

    private var forceReplaceEnabled = false

    override fun iterator(): Iterator<ServiceDeclare> {
        return container.iterator()
    }

    override fun <T : Any> addSingleton(type: KClass<T>, lazy: Boolean) {
        // use delegate.
        addDeclareByType(type, type, lifecycle = Lifecycle.Singleton, lazy)
    }

    override fun <TD : Any, TI : Any> addSingleton(
        declarationType: KClass<TD>,
        implementationType: KClass<TI>,
        lazy: Boolean
    ) {
        // use delegate.
        addDeclareByType(declarationType, implementationType, lifecycle = Lifecycle.Singleton, lazy)
    }

    override fun <T : Any> addScoped(type: KClass<T>, lazy: Boolean) {
        // use delegate.
        addDeclareByType(type, type, lifecycle = Lifecycle.Scoped, lazy)
    }

    override fun <TD : Any, TI : Any> addScoped(declarationType: KClass<TD>, implementationType: KClass<TI>, lazy: Boolean) {
        // use delegate.
        addDeclareByType(declarationType, implementationType, lifecycle = Lifecycle.Scoped, lazy)
    }

    override fun <T : Any> addTransient(type: KClass<T>) {
        // use delegate.
        addDeclareByType(type, type, lifecycle = Lifecycle.Transient, true)
    }

    override fun <TD : Any, TI : Any> addTransient(declarationType: KClass<TD>, implementationType: KClass<TI>) {
        // use delegate.
        addDeclareByType(declarationType, implementationType, lifecycle = Lifecycle.Transient, true)
    }

    override fun forceReplace(action: (ServiceDeclareAware) -> Unit) {
        forceReplaceEnabled = true
        action.invoke(this)
        forceReplaceEnabled = false
    }

    override fun singleDeclarationTypeOrNull(declarationType: KClass<*>): ServiceDeclare? {
        return this.singleOrNull { it.declarationTypes.contains(declarationType) }
    }

    /**
     * find in collection where [ServiceDeclare.declarationTypes] contains [declarationType]
     */
    override fun singleDeclarationType(declarationType: KClass<*>): ServiceDeclare {
        return this.single { it.declarationTypes.contains(declarationType) }
    }

    /**
     * find in collection where [ServiceDeclare.implementationType] == [implementationType]
     */
    override fun singleServiceType(implementationType: KClass<*>): ServiceDeclare {
        return this.single { it.implementationType == implementationType }
    }

    /**
     * add a [declarationType] with [implementationType] to [container].
     * the structure is determined by [implementationType] itself.
     */
    private fun addDeclareByType(declarationType: KClass<*>, implementationType: KClass<*>, lifecycle: Lifecycle, lazy: Boolean) {
        val l = singleDeclarationTypeOrNull(declarationType)
        // remove the existed declaration
        if (l != null && forceReplaceEnabled) {
            container.removeIf {
                it.declarationTypes.contains(declarationType)
            }
        }


        if (l == null || forceReplaceEnabled) {
            container.add(
                ServiceDeclare(
                    isInterfaceAdvice = false,
                    implementationType = implementationType,
                    declarationTypes = listOf(declarationType),
                    lifecycle = lifecycle,
                    isLazyService = lazy,
                    constructor = ServiceUtil.getInjectConstructor(implementationType),
                    components = ServiceUtil.getComponents(implementationType)
                )
            )
        } else {
            throw ServiceDeclarationException("the declaration of the type $declarationType is redundant.")
        }
    }

    /**
     * to check the structure of the service on the current [serviceDeclare]
     */
    override fun check(serviceDeclare: ServiceDeclare) {
        if (serviceDeclare.lifecycle == Lifecycle.Transient && !serviceDeclare.isLazyService) {
            throw ServiceDeclarationException("the transient service must be a lazy service.")
        } else {
            serviceDeclare.components.forEach {
                val aDeclare = this.singleDeclarationTypeOrNull(it.declareType)
                if (aDeclare != null && aDeclare.lifecycle == Lifecycle.Scoped && !it.injectLazy) {
                    throw ServiceDeclarationException("you must inject a scoped service by @Lazy")
                }
            }
        }
    }
}