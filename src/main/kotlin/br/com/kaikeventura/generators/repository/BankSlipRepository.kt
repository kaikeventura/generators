package br.com.kaikeventura.generators.repository

import br.com.kaikeventura.generators.model.BankSlip
import org.springframework.data.jpa.repository.JpaRepository

interface BankSlipRepository: JpaRepository<BankSlip, Long> {
}