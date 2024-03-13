package br.com.rawi.credit.service

import br.com.rawi.credit.model.Address
import br.com.rawi.credit.model.Customer
import br.com.rawi.credit.repository.CustomerRepository
import br.com.rawi.credit.service.impl.CustomerService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.lang.RuntimeException
import java.math.BigDecimal
import java.util.*

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CustomerServiceTest {
    @MockK lateinit var customerRepository: CustomerRepository
    @InjectMockKs lateinit var  customerService: CustomerService

    @Test
    fun should_create_customer(){
        val fakeCustomer: Customer = buildCustomer()
        every { customerService.save(any()) }returns fakeCustomer

        val actual:Customer = customerService.save(fakeCustomer)

        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(fakeCustomer)
        verify(exactly = 1){customerRepository.save(fakeCustomer)}

    }

    @Test
    fun should_find_customer_by_id(){
        val fakeId: Long = Random().nextLong()
        val fakeCustomer: Customer = buildCustomer(id = fakeId)
        every { customerRepository.findById(fakeId) } returns Optional.of(fakeCustomer)

        val actual: Customer = customerService.findById(fakeId)

        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isExactlyInstanceOf(Customer::class.java)
        Assertions.assertThat(actual).isSameAs(fakeCustomer)
        verify(exactly = 1){ customerRepository.findById(fakeId)}
    }

    @Test
    fun should_not_find_customer_by_invalid_id_and_throw_BusinessException(){
        val fakeId: Long = Random().nextLong()
        every { customerRepository.findById(fakeId) } returns Optional.empty()

        Assertions.assertThatExceptionOfType(RuntimeException::class.java)
            .isThrownBy { customerRepository.findById(fakeId) }
            .withMessage("id $fakeId not found")
        verify(exactly = 1) { customerRepository.findById(fakeId)}
    }

    @Test
    fun shouldDeleteCustomerById(){
        val fakeId: Long = Random().nextLong()
        val fakeCustomer: Customer = buildCustomer(id = fakeId)
        every { customerRepository.findById(fakeId) } returns Optional.of(fakeCustomer)
        every{ customerRepository.delete(fakeCustomer)} just runs

        customerService.delete(fakeId)

        verify(exactly = 1) { customerRepository.findById(fakeId)}
        verify (exactly = 1){ customerRepository.delete(fakeCustomer) }

    }
}

private fun buildCustomer(
    firstName: String = "Ravi",
    lastName:String = "X. Melo",
    cpf:String = "1224646648",
    email:String = "ravi@rawi.com",
    password:String = "123456",
    zipCode: String = "45654825",
    street:String = "casa",
    income:BigDecimal = BigDecimal.valueOf(5000.0),
    id:Long = 1L
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
    income = income,
    id = id
)