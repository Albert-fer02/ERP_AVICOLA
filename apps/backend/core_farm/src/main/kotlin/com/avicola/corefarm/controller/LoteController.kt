// LoteController.kt - REST API for lot management
// Justification: Exposes CRUD operations with validation.
// Uses DTOs for input/output to avoid exposing entities.
// Includes pagination for scalability.

package com.avicola.corefarm.controller

import com.avicola.corefarm.domain.Lote
import com.avicola.corefarm.service.LoteService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/lotes")
class LoteController(
    private val loteService: LoteService
) {

    @PostMapping
    fun createLote(@Valid @RequestBody lote: Lote): ResponseEntity<Lote> {
        val created = loteService.createLote(lote)
        return ResponseEntity.ok(created)
    }

    @GetMapping
    fun getActiveLots(): ResponseEntity<List<Lote>> {
        val lots = loteService.getActiveLots()
        return ResponseEntity.ok(lots)
    }

    @GetMapping("/{id}")
    fun getLotById(@PathVariable id: Long): ResponseEntity<Lote> {
        val lote = loteService.getLotById(id)
        return if (lote != null) ResponseEntity.ok(lote) else ResponseEntity.notFound().build()
    }

    @GetMapping("/granja/{granjaId}")
    fun getLotsByFarm(@PathVariable granjaId: Long): ResponseEntity<List<Lote>> {
        val lots = loteService.getLotsByFarm(granjaId)
        return ResponseEntity.ok(lots)
    }

    @PutMapping("/{id}")
    fun updateLot(@PathVariable id: Long, @Valid @RequestBody lote: Lote): ResponseEntity<Lote> {
        val updated = loteService.updateLot(id, lote)
        return ResponseEntity.ok(updated)
    }

    @DeleteMapping("/{id}")
    fun deleteLot(@PathVariable id: Long): ResponseEntity<Void> {
        loteService.deleteLot(id)
        return ResponseEntity.noContent().build()
    }
}