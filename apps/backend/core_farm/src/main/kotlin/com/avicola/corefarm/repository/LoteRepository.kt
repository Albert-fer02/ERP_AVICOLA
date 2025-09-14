// LoteRepository.kt - Data access layer for Lote entities
// Justification: Uses Spring Data JPA for CRUD operations.
// Includes custom queries for KPIs like active lots.
// Separation of concerns: Repository handles data persistence only.

package com.avicola.corefarm.repository

import com.avicola.corefarm.domain.Lote
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface LoteRepository : JpaRepository<Lote, Long> {

    // Find active lots (not yet sold or processed)
    @Query("SELECT l FROM Lote l WHERE l.fechaIngreso <= :date")
    fun findActiveLots(date: LocalDate = LocalDate.now()): List<Lote>

    // Find lots by farm
    fun findByGranjaId(granjaId: Long): List<Lote>

    // Find lots by strain
    fun findByEstirpe(estirpe: String): List<Lote>
}