package org.tty.dioc.core.test.services.circle

import org.tty.dioc.core.declare.Inject
import org.tty.dioc.core.declare.Component

@Component
class PrintServiceTSImpl: PrintServiceTS {
    @Inject
    lateinit var helloService: HelloServiceTS

    override fun print(): String {
        val s = "print:${helloService.hello()}"
        println(s)
        return s
    }
}