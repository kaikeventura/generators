package br.com.kaikeventura.generators.utils

import br.com.kaikeventura.generators.model.BankSlip
import br.com.kaikeventura.generators.repository.BankSlipConstants
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

class BankSlipUtils()

    fun generateBankSlip(bankCode: String, dueDate: LocalDate, totalValue: Double): BankSlip {
        return BankSlip(
                null,
                generateTypedBarcode(bankCode, dueDate, totalValue),
                null,
                bankCode,
                dueDate,
                LocalDateTime.now(),
                totalValue
        )
    }

    fun generateTypedBarcode(bankCode: String, dueDate: LocalDate, totalValue: Double): String{
        val blockOne = calculateBlockCheckDigitWithNinePositions(
                bankCode+BankSlipConstants.BRAZILIAN_CURRENCY_IDENTIFIER.number+generateRandomNumbers(5)
        )
        var blockTwo = calculateBlockCheckDigitWithTenPositions(
                generateRandomNumbers(10)
        )
        var blockThree = calculateBlockCheckDigitWithTenPositions(
                generateRandomNumbers(10)
        )
        var generalDigitVerification = blockOne[4]
        var blockFour = calculateDueDays(dueDate)+getTotalValue(totalValue)

        return blockOne+blockTwo+blockThree+generalDigitVerification+blockFour
    }

    fun calculateBlockCheckDigitWithNinePositions(block: String): String{
        var zero = Character.getNumericValue(block[0].toChar())*2
        var one = Character.getNumericValue(block[1].toChar())*1
        var two = Character.getNumericValue(block[2].toChar())*2
        var three = Character.getNumericValue(block[3].toChar())*1
        var four = Character.getNumericValue(block[4].toChar())*2
        var five = Character.getNumericValue(block[5].toChar())*1
        var six = Character.getNumericValue(block[6].toChar())*2
        var seven = Character.getNumericValue(block[7].toChar())*1
        var eight = Character.getNumericValue(block[8].toChar())*2

        var valuesConcat = zero.toString()+one+two+three+four+five+six+seven+eight
        val result = valuesConcat.sumBy { a -> Character.getNumericValue(a).toInt()}

        var verifyDigit = 10-(result%10)
        if(verifyDigit == 10) {
            verifyDigit = 0
        }
        return block+verifyDigit.toString()
    }

    fun calculateBlockCheckDigitWithTenPositions(block: String): String{
        var zero = Character.getNumericValue(block[0].toChar())*1
        var one = Character.getNumericValue(block[1].toChar())*2
        var two = Character.getNumericValue(block[2].toChar())*1
        var three = Character.getNumericValue(block[3].toChar())*2
        var four = Character.getNumericValue(block[4].toChar())*1
        var five = Character.getNumericValue(block[5].toChar())*2
        var six = Character.getNumericValue(block[6].toChar())*1
        var seven = Character.getNumericValue(block[7].toChar())*2
        var eight = Character.getNumericValue(block[8].toChar())*1
        var nine = Character.getNumericValue(block[9].toChar())*1

        var valuesConcat = zero.toString()+one+two+three+four+five+six+seven+eight+nine
        val result = valuesConcat.sumBy { a -> Character.getNumericValue(a).toInt()}
        var verifyDigit = 10-(result%10)

        if(verifyDigit == 10) {
            verifyDigit = 0
        }
        return block+verifyDigit.toString()
    }

    fun generateRandomNumbers(amountNumbers: Int): String {
        val numbers= arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        var bankSlipNumbers = String()
        for(n in 0 until amountNumbers){
            bankSlipNumbers += numbers[(Math.random()*numbers.size).toInt()]
        }
        return bankSlipNumbers
    }

    fun calculateDueDays(dueDate: LocalDate): String {
        val dateBase = LocalDate.of(1997, 7, 10)
        return dateBase.datesUntil(dueDate).count().toString()
    }

    fun getTotalValue(totalValue: Double): String{
        val value = totalValue.toString().replace(".","")
        val totalDigits = value.length
        if(totalDigits > 9){
            throw Exception("Limit!")
        }
        var totalValueFormat = String()
        var zeros = 9 - totalDigits
        for (n in 0..zeros){
            totalValueFormat += "0"
        }
        return totalValueFormat+value
    }

    fun main(args : Array<String>) {
        print(generateTypedBarcode(
                "341",
                LocalDate.of(2020, 5, 23),
                3456.45)
        )
    }