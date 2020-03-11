package br.com.kaikeventura.generators.utils

import br.com.kaikeventura.generators.model.BankSlip
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class BankSlipUtils()

    fun generateBankSlip(bankCode: String, dueDate: LocalDate, totalValue: Double): BankSlip {
        return BankSlip(
                null,
                createTypedBarcode(bankCode),
                null,
                bankCode,
                dueDate,
                LocalDateTime.now(),
                totalValue
        )
    }

    fun generateGenericBarcode(): String{
        val numbers = arrayOf<String>("1", "2", "3", "4", "5", "6", "7", "8", "9")
        var bankSlipNumbers = String()
        for(n in 0..47){
            bankSlipNumbers += numbers[(Math.random()*numbers.size).toInt()]
        }
        return bankSlipNumbers
    }

    fun createTypedBarcode(bankCode: String): String{
        var genericBarcode = generateGenericBarcode()
        var barcodeWithBankCode = bankCode+genericBarcode.substring(3, 48)

        return barcodeWithBankCode
    }

    fun main(args : Array<String>) {
        createTypedBarcode("341")
    }