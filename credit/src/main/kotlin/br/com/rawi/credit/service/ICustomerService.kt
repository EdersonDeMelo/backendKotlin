package br.com.rawi.credit.service

import br.com.rawi.credit.model.Customer

interface ICustomerService {

    fun save(customer: Customer): Customer
    fun findById(id: Long): Customer
    fun delete(id: Long)
}