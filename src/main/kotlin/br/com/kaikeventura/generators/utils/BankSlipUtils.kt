package br.com.kaikeventura.generators.utils

import br.com.kaikeventura.generators.model.BankSlip
import br.com.kaikeventura.generators.constants.BankSlipConstants
import br.com.kaikeventura.generators.dto.BankSlipDTO
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime

@Component
class BankSlipUtils() {

    fun generateBankSlip(bankSlipDTO: BankSlipDTO): BankSlip {
        val typedBarcode = generateTypedBarcode(
                bankSlipDTO.bankCode,
                bankSlipDTO.dueDate,
                bankSlipDTO.totalValue
        )

        return BankSlip(
                null,
                typedBarcode,
                converterBankSlipTypedForRead(typedBarcode),
                bankSlipDTO.bankCode,
                bankSlipDTO.dueDate,
                LocalDateTime.now(),
                bankSlipDTO.totalValue
        )
    }

    private fun generateTypedBarcode(bankCode: String, dueDate: LocalDate, totalValue: Double): String {
        val blockOne = calculateBlockCheckDigitWithNinePositions(
                bankCode + BankSlipConstants.BRAZILIAN_CURRENCY_IDENTIFIER.number + generateRandomNumbers(5)
        )
        val blockTwo = calculateBlockCheckDigitWithTenPositions(
                generateRandomNumbers(10)
        )
        val blockThree = calculateBlockCheckDigitWithTenPositions(
                generateRandomNumbers(10)
        )
        val generalDigitVerification = blockOne[4]
        val blockFour = calculateDueDays(dueDate) + getTotalValue(totalValue)

        return blockOne
                .plus(blockTwo)
                .plus(blockThree)
                .plus(generalDigitVerification)
                .plus(blockFour)
    }

    private fun calculateBlockCheckDigitWithNinePositions(block: String): String {
        val zero = Character.getNumericValue(block[0].toChar()) * 2
        val one = Character.getNumericValue(block[1].toChar()) * 1
        val two = Character.getNumericValue(block[2].toChar()) * 2
        val three = Character.getNumericValue(block[3].toChar()) * 1
        val four = Character.getNumericValue(block[4].toChar()) * 2
        val five = Character.getNumericValue(block[5].toChar()) * 1
        val six = Character.getNumericValue(block[6].toChar()) * 2
        val seven = Character.getNumericValue(block[7].toChar()) * 1
        val eight = Character.getNumericValue(block[8].toChar()) * 2

        val valuesConcat = zero.toString() + one + two + three + four + five + six + seven + eight
        val result = valuesConcat.sumBy { a -> Character.getNumericValue(a).toInt() }

        var verifyDigit = 10 - (result % 10)
        if (verifyDigit == 10) {
            verifyDigit = 0
        }
        return block.plus(verifyDigit)
    }

    private fun calculateBlockCheckDigitWithTenPositions(block: String): String {
        var zero = Character.getNumericValue(block[0].toChar()) * 1
        var one = Character.getNumericValue(block[1].toChar()) * 2
        var two = Character.getNumericValue(block[2].toChar()) * 1
        var three = Character.getNumericValue(block[3].toChar()) * 2
        var four = Character.getNumericValue(block[4].toChar()) * 1
        var five = Character.getNumericValue(block[5].toChar()) * 2
        var six = Character.getNumericValue(block[6].toChar()) * 1
        var seven = Character.getNumericValue(block[7].toChar()) * 2
        var eight = Character.getNumericValue(block[8].toChar()) * 1
        var nine = Character.getNumericValue(block[9].toChar()) * 1

        var valuesConcat = zero.toString() + one + two + three + four + five + six + seven + eight + nine
        val result = valuesConcat.sumBy { a -> Character.getNumericValue(a).toInt() }
        var verifyDigit = 10 - (result % 10)

        if (verifyDigit == 10) {
            verifyDigit = 0
        }
        return block.plus(verifyDigit)
    }

    private fun generateRandomNumbers(amountNumbers: Int): String {
        val numbers = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        var bankSlipNumbers = String()
        for (n in 0 until amountNumbers) {
            bankSlipNumbers += numbers[(Math.random() * numbers.size).toInt()]
        }
        return bankSlipNumbers
    }

    private fun calculateDueDays(dueDate: LocalDate): String {
        val dateBase = LocalDate.of(1997, 7, 10)
        return dateBase.datesUntil(dueDate).count().toString()
    }

    private fun getTotalValue(totalValue: Double): String {
        val value = totalValue.toString().replace(".", "")
        val totalDigits = value.length
        if (totalDigits > 9) {
            throw Exception("Limit!")
        }
        var totalValueFormat = String()
        for (n in 0..9 - totalDigits) {
            totalValueFormat += "0"
        }
        return totalValueFormat.plus(value)
    }

    fun converterBankSlipTypedForRead(barcodeTyped: String): String {
        val orderedBarcode = barcodeTyped.substring(0, 9)
                .plus(barcodeTyped.substring(10, 20))
                .plus(barcodeTyped.substring(21, 31))
                .plus(barcodeTyped.substring(32, 47))

        return orderedBarcode.substring(0, 4)
                .plus(orderedBarcode.substring(29, 44))
                .plus(orderedBarcode.substring(4, 9))
                .plus(orderedBarcode.substring(9, 19))
                .plus(orderedBarcode.substring(19, 29))
    }

    fun converterBankSlipReadForTyped(barcodeRead: String): String {
        val blockOne = calculateBlockCheckDigitWithNinePositions(
                barcodeRead.substring(0, 4).plus(barcodeRead.substring(19, 24))
        )
        val blockTwo = calculateBlockCheckDigitWithTenPositions(barcodeRead.substring(24, 34))
        val blockThree = calculateBlockCheckDigitWithTenPositions(barcodeRead.substring(34, 44))
        val blockFour = barcodeRead.substring(4, 19)

        return blockOne
                .plus(blockTwo)
                .plus(blockThree)
                .plus(blockFour)
    }
}