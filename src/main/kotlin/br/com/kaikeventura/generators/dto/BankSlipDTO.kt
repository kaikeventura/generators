package br.com.kaikeventura.generators.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class BankSlipDTO(
        @JsonProperty("bank_code")
        val bankCode: String,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR")
        @JsonProperty("due_date")
        val dueDate: LocalDate,
        @JsonProperty("total_value")
        val totalValue: Double
)