package com.wavesplatform.we.app.mortgage.contract

import com.wavesplatform.vst.contract.spring.annotation.EnableContractHandlers
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@EnableContractHandlers
class MortgageContractStarter

fun main() {
    SpringApplication.run(MortgageContractStarter::class.java)
}
