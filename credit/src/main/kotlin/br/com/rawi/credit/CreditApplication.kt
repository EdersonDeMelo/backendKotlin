package br.com.rawi.credit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CreditApplication

fun main(args: Array<String>) {
	runApplication<CreditApplication>(*args)
}
