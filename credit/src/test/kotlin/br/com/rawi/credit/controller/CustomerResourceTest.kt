package br.com.rawi.credit.controller

import br.com.rawi.credit.dto.CustomerDto
import br.com.rawi.credit.model.Customer
import br.com.rawi.credit.repository.CustomerRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration
class CustomerResourceTest {
    @Autowired
    private lateinit var customerRepository: CustomerRepository
    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    companion object{
        const val URL: String = "/api/customers"
    }

    @BeforeEach fun setUp() {
        customerRepository.deleteAll()
    }
    @AfterEach fun tearDown() {
        customerRepository.deleteAll()
    }

    @Test
    fun `should create a customer and return 201 status`() {
        val customerDto: CustomerDto = builderCustomer()
        val valueAsString: String = objectMapper.writeValueAsString(customerDto)

        mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
            .contentType("application/json")
            .content(valueAsString))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(customerDto.firstName))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(customerDto.lastName))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(customerDto.cpf))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(customerDto.email))
            .andExpect(MockMvcResultMatchers.jsonPath("$.income").value(customerDto.income.toDouble()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value(customerDto.zipCode))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value(customerDto.street))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should find a customer by id and return 200 status`() {
        val customer: Customer = customerRepository.save(builderCustomer().toEntity())

        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL/${customer.id}")
            .contentType("application/json"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(customer.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(customer.firstname))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(customer.lastName))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(customer.cpf))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(customer.email))
            .andExpect(MockMvcResultMatchers.jsonPath("$.income").value(customer.income.toDouble()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value(customer.address.zipCode))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value(customer.address.street))
            .andDo(MockMvcResultHandlers.print())
    }


}

private fun builderCustomer(
    firstName: String = "Ravi",
    lastName: String = "X. Melo",
    cpf: String = "12345678901",
    email: String = "ravi@rawi.com.br",
    income: BigDecimal = BigDecimal.valueOf(1000.0),
    password: String = "123456",
    zipCode: String = "12345678",
    street: String = "Rua 1",
) = CustomerDto(
    firstName = firstName,
    lastName = lastName,
    cpf = cpf,
    email = email,
    income = income,
    password = password,
    zipCode = zipCode,
    street = street
)