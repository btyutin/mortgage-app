package com.wavesplatform.we.app.mortgage.config

import com.wavesplatform.we.app.mortgage.contract.MortgageContract
import com.wavesplatform.we.app.mortgage.contract.impl.MortgageContractImpl
import com.wavesplatform.we.starter.contract.annotation.Contract
import com.wavesplatform.we.starter.contract.annotation.EnableContracts
import org.springframework.context.annotation.Configuration

@Configuration
@EnableContracts(
        contracts = [Contract(
                api = MortgageContract::class,
                impl = MortgageContractImpl::class,
                name = "mortgage"
        )]
)
class MortgageContractConfig
