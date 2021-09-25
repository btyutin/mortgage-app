package com.wavesplatform.we.app.mortgage

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MortgageApplication

fun main(args: Array<String>) {
    SpringApplication.run(MortgageApplication::class.java, *args)
}
