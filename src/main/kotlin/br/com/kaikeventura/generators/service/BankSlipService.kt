package br.com.kaikeventura.generators.service

import br.com.kaikeventura.generators.dto.BankSlipDTO
import br.com.kaikeventura.generators.model.BankSlip
import br.com.kaikeventura.generators.repository.BankSlipRepository
import br.com.kaikeventura.generators.utils.BankSlipUtils
import org.springframework.stereotype.Service

@Service
class BankSlipService(val bankSlipRepository: BankSlipRepository, val bankSlipUtils: BankSlipUtils) {

    fun save(bankSlipDTO: BankSlipDTO): BankSlip {
        return bankSlipRepository.save(bankSlipUtils.generateBankSlip(bankSlipDTO))
    }
}