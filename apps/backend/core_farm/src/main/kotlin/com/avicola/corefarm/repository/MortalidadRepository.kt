// MortalidadRepository.kt - Data access for mortality records
// Justification: Provides queries for mortality analysis and alerts.
// Supports filtering by lot and date range for health monitoring.

package com.avicola.corefarm.repository

import com.avicola.corefarm.domain.Mortalidad
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface MortalidadRepository : JpaRepository<Mortalidad, Long> {

    // Find mortality by lot
    fun findByLoteId(loteId: Long): List<Mortalidad>

    // Find mortality between dates
    @Query("SELECT m FROM Mortalidad m WHERE m.loteId = :loteId AND m.fecha BETWEEN :start AND :end")
    fun findByLoteIdAndDateRange(loteId: Long, start: LocalDate, end: LocalDate): List<Mortalidad>

    // Calculate total mortality for a lot
    @Query("SELECT SUM(m.cantidad) FROM Mortalidad m WHERE m.loteId = :loteId")
    fun getTotalMortality(loteId: Long): Int?
}