package org.tty.dioc.core.basic

import org.tty.dioc.core.declare.ComponentDeclare
import org.tty.dioc.annotation.InternalComponent

/**
 * the resolver for declare of the component.
 */
@Deprecated("use ComponentDeclareScanner instead")
interface ComponentDeclareResolver {
    fun getDeclarations(): List<ComponentDeclare>
}