package br.com.rawi.credit.dto

import br.com.rawi.credit.model.Address
import br.com.rawi.credit.model.Customer
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.br.CPF
import java.math.BigDecimal

class CustomerDto(
    @field:NotEmpty(message = "Campo nome vazio") val firstName: String,
    @field:NotEmpty(message = "Invalid input") val lastName: String,
    @field:NotEmpty(message = "Invalid input")
    val cpf: String,
    @field:NotNull(message = "Invalid income") val income: BigDecimal,
    @field:Email(message = "Email invalido")
    @field:NotEmpty(message = "Invalid input") val email: String,
    @field:NotEmpty(message = "Invalid input") val password: String,
    @field:NotEmpty(message = "Invalid input") val zipCode: String,
    @field:NotEmpty(message = "Invalid input") val street: String
) {

    fun toEntity(): Customer = Customer(
        firstname = this.firstName,
        lastName = this.lastName,
        cpf = this.cpf,
        income = this.income,
        email = this.email,
        password = this.password,
        address = Address(
            zipCode = this.zipCode,
            street = this.street
        )
    )

}
