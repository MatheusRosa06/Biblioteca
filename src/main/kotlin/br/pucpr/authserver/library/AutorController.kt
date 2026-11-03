package br.pucpr.authserver.library

import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/autores")
@Validated
class AutorController(
    private val autorService: AutorService
) {
    @GetMapping
    fun list(): ResponseEntity<List<AutorResponse>> {
        val autores = autorService.findAll().map { AutorResponse(it) }
        return ResponseEntity.ok(autores)
    }

    @PostMapping
    fun insert(
        @Valid @RequestBody request: AutorRequest
    ): ResponseEntity<AutorResponse> {
        val autor = autorService.insert(request.toAutor())
        return ResponseEntity.status(HttpStatus.CREATED).body(AutorResponse(autor))
    }

    @GetMapping("/{id}")
    fun getById(
        @Min(1) @PathVariable id: Long
    ): ResponseEntity<AutorResponse> {
        val autor = autorService.findByIdOrNull(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(AutorResponse(autor))
    }

    @PutMapping("/{id}")
    fun update(
        @Min(1) @PathVariable id: Long,
        @Valid @RequestBody request: AutorRequest
    ): ResponseEntity<AutorResponse> {
        val autor = autorService.update(id, request)
        return ResponseEntity.ok(AutorResponse(autor))
    }

    @DeleteMapping("/{id}")
    fun delete(
        @Min(1) @PathVariable id: Long
    ): ResponseEntity<Void> {
        autorService.deleteById(id)
        return ResponseEntity.ok().build()
    }
}