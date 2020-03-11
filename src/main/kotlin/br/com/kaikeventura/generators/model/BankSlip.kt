package br.com.kaikeventura.generators.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class BankSlip(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @Column(unique = true)
        val typedBarcode: String? = null,
        @Column(unique = true)
        var readBarcode: String? = null,
        val bankCode: String? = null,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        val dueDate: LocalDate? = null,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="GMT-3")
        val creationDate: LocalDateTime? = null,
        val totalValue: Double? = null
)