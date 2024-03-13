package br.com.rawi.credit.controller

import br.com.rawi.credit.dto.CreditDto
import br.com.rawi.credit.dto.CreditView
import br.com.rawi.credit.dto.CreditViewList
import br.com.rawi.credit.model.Credit
import br.com.rawi.credit.service.impl.CreditService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import java.util.stream.Collectors

@RestController
@RequestMapping("/api/credits")
class CreditResource(
    private val creditService: CreditService
) {
    @PostMapping
    fun savedCredit(@RequestBody creditDto: CreditDto): ResponseEntity<String>{
        val credit: Credit = this.creditService.save(creditDto.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body("Credit ${credit.creditCode} - Customer ${credit.customer?.firstname} saved!")
    }

    @GetMapping
    fun findAllByCustomerId(@RequestParam(value = "customerId") customerId: Long) : ResponseEntity<List<CreditViewList>>{
        val creditViewlist: List<CreditViewList> = this.creditService.findAllByCustomer(customerId).stream()
            .map{ credit: Credit -> CreditViewList(credit) }.collect(Collectors.toList())
        return ResponseEntity.status(HttpStatus.OK).body(creditViewlist)
    }

    @GetMapping("/{creditCode}")
    fun findByCreditCode(@RequestParam(value = "customerId") customerId: Long, @PathVariable creditCode: UUID ):ResponseEntity<CreditView>{
        val credit : Credit = this.creditService.findByCreditCode(customerId, creditCode)
        return ResponseEntity.status(HttpStatus.OK).body(CreditView(credit))
    }
}