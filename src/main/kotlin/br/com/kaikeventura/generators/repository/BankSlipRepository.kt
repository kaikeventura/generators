package br.com.kaikeventura.generators.repository

import br.com.kaikeventura.generators.model.BankSlip
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BankSlipRepository: JpaRepository<BankSlip, Long> {
}