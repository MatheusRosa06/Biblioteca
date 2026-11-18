package br.pucpr.authserver.library

import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/livros")
@Validated
class LivroController(
    private val livroService: LivroService
) {
    private val logger = LoggerFactory.getLogger(LivroController::class.java)

    @GetMapping
    fun list(
        @RequestParam(required = false) titulo: String?,
        @RequestParam(required = false, defaultValue = "titulo") sortBy: String
    ): ResponseEntity<List<LivroResponse>> {
        val livros = livroService.findAllFiltered(titulo, sortBy).map { LivroResponse(it) }
        return ResponseEntity.ok(livros)
    }

    @GetMapping("/{id}")
    fun getById(@Min(1) @PathVariable id: Long): ResponseEntity<LivroResponse> {
        val livro = livroService.findByIdOrNull(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(LivroResponse(livro))
    }
  
    @PostMapping
    fun insert(@Valid @RequestBody request: LivroRequest): ResponseEntity<LivroResponse> {
        val livro = livroService.insert(request)
        return ResponseEntity.status(201).body(LivroResponse(livro))
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    fun delete(@Min(1) @PathVariable id: Long): ResponseEntity<Void> {
        livroService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/{livroId}/generos/{generoId}")
    fun addGenero(
        @Min(1) @PathVariable livroId: Long,
        @Min(1) @PathVariable generoId: Long
    ): ResponseEntity<Void> {
        val associado = livroService.addGenero(livroId, generoId)
        return if (associado) ResponseEntity.ok().build() else ResponseEntity.badRequest().build()
    }
}