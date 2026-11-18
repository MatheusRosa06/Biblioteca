package br.pucpr.authserver.library

import jakarta.validation.constraints.Min
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/generos")
@Validated
class GeneroController(
    private val generoService: GeneroService
) {
    @GetMapping
    fun list(): ResponseEntity<List<GeneroResponse>> {
        val generos = generoService.findAll().map { GeneroResponse(it) }
        return ResponseEntity.ok(generos)
    }

    @GetMapping("/{id}")
    fun getById(@Min(1) @PathVariable id: Long): ResponseEntity<GeneroResponse> {
        val genero = generoService.findByIdOrNull(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(GeneroResponse(genero))
    }
}