package br.com.rawi.credit.dto

import br.com.rawi.credit.model.Credit
import br.com.rawi.credit.model.Customer
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDto(
    val creditValue: BigDecimal,
    val dayFirstOfInstallment: LocalDate,
    val numberOfInstallments: Int,
    val customerId: Long
) {

    fun toEntity(): Credit = Credit(
        creditValue = this.creditValue,
        dayFirstInstallment = this.dayFirstOfInstallment,
        numberOffInstallment = this.numberOfInstallments,
        customer = Customer(id = this.customerId)
    )
}
