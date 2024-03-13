package br.com.rawi.credit.service

import br.com.rawi.credit.model.Credit
import java.util.UUID

interface ICreditService {
    fun save(credit: Credit): Credit
    fun findAllByCustomer(customerId: Long):List<Credit>
    fun findByCreditCode(customerId: Long, creditCode: UUID): Credit
}