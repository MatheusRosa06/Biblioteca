package br.pucpr.authserver.library

import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/livros")
@Validated
class LivroController(
    private val livroService: LivroService
) {
    @GetMapping
    fun list(
        @RequestParam(required = false) titulo: String?,
        @RequestParam(required = false, defaultValue = "titulo") sortBy: String
    ): ResponseEntity<List<LivroResponse>> {
        val livros = livroService.findAllFiltered(titulo, sortBy).map { LivroResponse(it) }
        return ResponseEntity.ok(livros)
    }

    @PostMapping
    fun insert(
        @Valid @RequestBody request: LivroRequest
    ): ResponseEntity<LivroResponse> {
        val livro = livroService.insert(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(LivroResponse(livro))
    }

    @GetMapping("/{id}")
    fun getById(
        @Min(1) @PathVariable id: Long
    ): ResponseEntity<LivroResponse> {
        val livro = livroService.findByIdOrNull(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(LivroResponse(livro))
    }

    @DeleteMapping("/{id}")
    fun delete(
        @Min(1) @PathVariable id: Long
    ): ResponseEntity<Void> {
        livroService.deleteById(id)
        return ResponseEntity.ok().build()
    }
}