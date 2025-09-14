// KpiController.kt - REST API for KPIs
// Justification: Provides endpoints for dashboard data.
// Uses caching to reduce load on database.
// Returns structured data for frontend consumption.

package com.avicola.corefarm.controller

import com.avicola.corefarm.service.KpiService
import com.avicola.corefarm.service.LotSummary
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/kpis")
class KpiController(
    private val kpiService: KpiService
) {

    @GetMapping("/lote/{loteId}")
    fun getLotSummary(@PathVariable loteId: Long): ResponseEntity<LotSummary> {
        val summary = kpiService.getLotSummary(loteId)
        return if (summary != null) ResponseEntity.ok(summary) else ResponseEntity.notFound().build()
    }

    @GetMapping("/fcr/{loteId}")
    fun getFCR(@PathVariable loteId: Long): ResponseEntity<Double> {
        val fcr = kpiService.calculateFCR(loteId)
        return if (fcr != null) ResponseEntity.ok(fcr) else ResponseEntity.notFound().build()
    }

    @GetMapping("/mortality/{loteId}")
    fun getMortalityRate(@PathVariable loteId: Long): ResponseEntity<Double> {
        val rate = kpiService.calculateMortalityRate(loteId)
        return if (rate != null) ResponseEntity.ok(rate) else ResponseEntity.notFound().build()
    }
}