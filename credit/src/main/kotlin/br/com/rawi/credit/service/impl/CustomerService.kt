package br.com.rawi.credit.service.impl

import br.com.rawi.credit.model.Customer
import br.com.rawi.credit.repository.CustomerRepository
import br.com.rawi.credit.service.ICustomerService
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class CustomerService(private val customerRepository: CustomerRepository): ICustomerService {
    override fun save(customer: Customer): Customer {
       return this.customerRepository.save(customer)
    }

    override fun findById(id: Long): Customer {
       return this.customerRepository.findById(id).orElseThrow{
           throw RuntimeException("id $id not found")
       }
    }

    override fun delete(id: Long){
        return this.customerRepository.deleteById(id)
    }
}