package org.tty.dioc.core.test.services

import org.tty.dioc.core.declare.Lazy
import org.tty.dioc.core.declare.Component

@Component
class LazyInjectHelloServiceByConstructor2(@Lazy helloService: HelloService) {
    init {
        println(helloService.hello())
    }
}