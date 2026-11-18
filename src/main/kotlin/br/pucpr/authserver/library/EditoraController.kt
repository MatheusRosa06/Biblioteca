package br.pucpr.authserver.library

import jakarta.validation.constraints.Min
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/editoras")
@Validated
class EditoraController(
    private val editoraService: EditoraService
) {
    @GetMapping
    fun list(): ResponseEntity<List<EditoraResponse>> {
        val editoras = editoraService.findAll().map { EditoraResponse(it) }
        return ResponseEntity.ok(editoras)
    }

    @GetMapping("/{id}")
    fun getById(@Min(1) @PathVariable id: Long): ResponseEntity<EditoraResponse> {
        val editora = editoraService.findByIdOrNull(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(EditoraResponse(editora))
    }
}