package br.com.rawi.credit.repository

import br.com.rawi.credit.model.Address
import br.com.rawi.credit.model.Credit
import br.com.rawi.credit.model.Customer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Month
import java.util.*

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditRepositoryTest {
    @Autowired lateinit var creditRepository: CreditRepository
    @Autowired lateinit var testEntityManager: TestEntityManager

    private lateinit var customer: Customer
    private lateinit var credit1: Credit
    private lateinit var credit2: Credit

    @BeforeEach fun setUp(){
        customer = testEntityManager.persist(buildCustomer())
        credit1 = testEntityManager.persist(buildCredit(customer = customer))
    }

}

private fun buildCredit(
        creditValue: BigDecimal = BigDecimal.valueOf(1000.0),
        dayFirstInstallment: LocalDate = LocalDate.of(2023, Month.APRIL, 22),
        numberOfInstallments: Int = 5,
        customer: Customer
): Credit = Credit(
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        numberOffInstallment = numberOfInstallments,
        customer = customer
)
private fun buildCustomer(
        firstName: String = "Ravi",
        lastName: String = "X. Melo",
        cpf: String = "12345678901",
        email: String = "ravi@rawi.com.br",
        password: String = "123456",
        zipCode: String = "12345678",
        street: String = "Rua 1",
        income: BigDecimal = BigDecimal.valueOf(1000.0)
) = Customer(
        firstname = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        password = password,
        address = Address(
                zipCode = zipCode,
                street = street
        ),
        income = income
)

