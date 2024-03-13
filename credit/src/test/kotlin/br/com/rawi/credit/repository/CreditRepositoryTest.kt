package br.com.rawi.credit.repository

import br.com.rawi.credit.model.Address
import br.com.rawi.credit.model.Credit
import br.com.rawi.credit.model.Customer
import org.assertj.core.api.Assertions
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
        credit2 = testEntityManager.persist(buildCredit(customer = customer))
    }

    @Test
    fun `should find credit by credit code`(){
        val creditCode1 = UUID.fromString("f47b3b2e-3f3e-4f3e-8f3e-3f3e4f3e4f3e")
        val creditCode2 = UUID.fromString("a2bc6d16-9bbc-4664-90dc-a20c8c15f4e5")
        credit1.creditCode = creditCode1
        credit2.creditCode = creditCode2

        val fakeCredit1: Credit = creditRepository.findByCreditCode(creditCode1)!!
        val fakeCredit2: Credit = creditRepository.findByCreditCode(creditCode2)!!

        Assertions.assertThat(fakeCredit1).isNotNull
        Assertions.assertThat(fakeCredit2).isNotNull
        Assertions.assertThat(fakeCredit1).isSameAs(credit1)
        Assertions.assertThat(fakeCredit2).isSameAs(credit2)

    }

    @Test
    fun `should find all credits by customer id`(){
        val customerId: Long = 1L

        val fakeCredits: List<Credit> = creditRepository.findAllByCustomerId(customerId)

        Assertions.assertThat(fakeCredits).isNotNull
        Assertions.assertThat(fakeCredits).isNotEmpty
        Assertions.assertThat(fakeCredits.size).isEqualTo(2)
        Assertions.assertThat(fakeCredits).contains(credit1, credit2)
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

