// LoteService.kt - Business logic for lot management
// Justification: Encapsulates business rules like lot creation validation.
// Uses caching for performance on frequently accessed data.
// Handles transactions for data consistency.

package com.avicola.corefarm.service

import com.avicola.corefarm.domain.Lote
import com.avicola.corefarm.repository.LoteRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class LoteService(
    private val loteRepository: LoteRepository
) {

    @Transactional
    fun createLote(lote: Lote): Lote {
        // Business rule: Validate lot name uniqueness per farm
        if (loteRepository.findByGranjaId(lote.granjaId).any { it.nombre == lote.nombre }) {
            throw IllegalArgumentException("Lot name must be unique per farm")
        }
        return loteRepository.save(lote)
    }

    @Cacheable("activeLots")
    fun getActiveLots(): List<Lote> = loteRepository.findActiveLots()

    fun getLotsByFarm(granjaId: Long): List<Lote> = loteRepository.findByGranjaId(granjaId)

    fun getLotById(id: Long): Lote? = loteRepository.findById(id).orElse(null)

    @Transactional
    fun updateLot(id: Long, updatedLote: Lote): Lote {
        val existing = getLotById(id) ?: throw IllegalArgumentException("Lot not found")
        val merged = existing.copy(
            nombre = updatedLote.nombre,
            updatedAt = LocalDate.now()
        )
        return loteRepository.save(merged)
    }

    @Transactional
    fun deleteLot(id: Long) {
        if (!loteRepository.existsById(id)) throw IllegalArgumentException("Lot not found")
        loteRepository.deleteById(id)
    }
}