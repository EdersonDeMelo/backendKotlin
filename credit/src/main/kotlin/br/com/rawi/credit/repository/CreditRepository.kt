package br.com.rawi.credit.repository

import br.com.rawi.credit.model.Credit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CreditRepository: JpaRepository<Credit, Long> {
    fun findByCreditCode(creditCode: UUID): Credit?

    @Query(value = "SELECT * FROM CREDIT WHERE CUSTOMER_ID = ?", nativeQuery = true)
    fun findAllByCustomerId(customerId: Long):List<Credit>
}
