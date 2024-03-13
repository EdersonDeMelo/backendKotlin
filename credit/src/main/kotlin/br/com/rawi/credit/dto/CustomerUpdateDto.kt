package br.com.rawi.credit.dto

import br.com.rawi.credit.model.Customer
import java.math.BigDecimal

data class CustomerUpdateDto(
    val firstName: String,
    val lastName: String,
    val income: BigDecimal,
    val zipCode: String,
    val Street: String

) {
    fun toEntity(customer: Customer): Customer{
        customer.firstname = this.firstName
        customer.lastName = this.lastName
        customer.income = this.income
        customer.address.zipCode = this.zipCode
        customer.address.street = this.Street
        return customer
    }

}
