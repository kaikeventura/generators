package br.com.kaikeventura.generators.controller

import br.com.kaikeventura.generators.dto.BankSlipDTO
import br.com.kaikeventura.generators.model.BankSlip
import br.com.kaikeventura.generators.service.BankSlipService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("bankslip")
class BankSlipController(val bankSlipService: BankSlipService) {

    @PostMapping("save")
    private fun save(@RequestBody bankSlipDTO: BankSlipDTO): ResponseEntity<BankSlip> {
        return ResponseEntity.ok(bankSlipService.save(bankSlipDTO))
    }
}