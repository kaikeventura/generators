package br.com.kaikeventura.generators.model

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class BankSlip(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        @Column(unique = true)
        var readBarcode: String,
        @Column(unique = true)
        val typedBarcode: String,
        val bankCode: String,
        val dueDate: LocalDate,
        val creationDate: LocalDateTime,
        val totalValue: Double
)