// ConsumoRepository.kt - Data access for consumption data
// Justification: Handles consumption records with custom queries for analytics.
// Uses composite key, so extends JpaRepository with ConsumoId.

package com.avicola.corefarm.repository

import com.avicola.corefarm.domain.Consumo
import com.avicola.corefarm.domain.ConsumoId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface ConsumoRepository : JpaRepository<Consumo, ConsumoId> {

    // Find consumption by lot
    fun findByIdLoteId(loteId: Long): List<Consumo>

    // Find consumption between dates
    @Query("SELECT c FROM Consumo c WHERE c.id.loteId = :loteId AND c.id.fecha BETWEEN :start AND :end")
    fun findByLoteIdAndDateRange(loteId: Long, start: LocalDate, end: LocalDate): List<Consumo>

    // Calculate total feed consumption for a lot
    @Query("SELECT SUM(c.alimentoKg) FROM Consumo c WHERE c.id.loteId = :loteId")
    fun getTotalFeedConsumption(loteId: Long): Double?
}