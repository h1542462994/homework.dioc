package org.tty.dioc.core

import org.tty.dioc.core.declare.ServiceAware
import org.tty.dioc.core.lifecycle.ScopeAware

/**
 * represents a container for ability of getting service.
 *
 */
sealed interface ApplicationContext : ServiceAware, ScopeAware
