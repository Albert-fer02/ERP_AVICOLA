// KpiService.kt - Business logic for calculating KPIs
// Justification: Centralizes KPI calculations for FCR, mortality rate, etc.
// Uses caching for expensive calculations.
// Promotes reusability across controllers and reports.

package com.avicola.corefarm.service

import com.avicola.corefarm.repository.ConsumoRepository
import com.avicola.corefarm.repository.LoteRepository
import com.avicola.corefarm.repository.MortalidadRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class KpiService(
    private val loteRepository: LoteRepository,
    private val consumoRepository: ConsumoRepository,
    private val mortalidadRepository: MortalidadRepository
) {

    @Cacheable("fcrKpi")
    fun calculateFCR(loteId: Long): Double? {
        val totalFeed = consumoRepository.getTotalFeedConsumption(loteId) ?: return null
        // Simplified: Assume weight gain is tracked separately
        // In real implementation, would calculate from weight records
        val weightGain = 1000.0 // Placeholder
        return if (weightGain > 0) totalFeed / weightGain else null
    }

    @Cacheable("mortalityKpi")
    fun calculateMortalityRate(loteId: Long): Double? {
        val lote = loteRepository.findById(loteId).orElse(null) ?: return null
        val totalMortality = mortalidadRepository.getTotalMortality(loteId) ?: 0
        return (totalMortality.toDouble() / lote.cantidadInicial) * 100
    }

    fun getLotSummary(loteId: Long): LotSummary? {
        val lote = loteRepository.findById(loteId).orElse(null) ?: return null
        val fcr = calculateFCR(loteId)
        val mortalityRate = calculateMortalityRate(loteId)
        return LotSummary(lote.nombre, lote.getEdadDias(), fcr, mortalityRate)
    }
}

data class LotSummary(
    val nombre: String,
    val edadDias: Long,
    val fcr: Double?,
    val mortalityRate: Double?
)