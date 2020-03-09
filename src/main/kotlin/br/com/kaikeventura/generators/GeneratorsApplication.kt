package br.com.kaikeventura.generators

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GeneratorsApplication

fun main(args: Array<String>) {
	runApplication<GeneratorsApplication>(*args)
}
