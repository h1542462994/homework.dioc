package org.tty.dioc.core.test.services.dynamic

import org.tty.dioc.annotation.Inject
import org.tty.dioc.base.InitializeAware
import org.tty.dioc.core.test.services.Logger

class AddServiceStep2: AddService, InitializeAware {
    var value: Int = 0

    @Inject
    lateinit var logger: Logger

    override fun add() {
        value += 2
    }

    override fun current(): Int {
        return value
    }

    override fun onInit() {
        logger.d("AddServiceStep2", "init")
    }
}