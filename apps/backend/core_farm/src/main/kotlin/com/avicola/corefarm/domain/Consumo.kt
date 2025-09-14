// Consumo.kt - Domain entity for daily feed consumption
// Justification: Tracks feed and water consumption per lot.
// Critical for FCR calculation and efficiency monitoring.
// Uses composite key for loteId and date.

package com.avicola.corefarm.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import java.time.LocalDate

@Entity
@Table(name = "consumos")
data class Consumo(
    @EmbeddedId
    val id: ConsumoId,

    @NotNull
    @PositiveOrZero
    val alimentoKg: Double,

    @NotNull
    @PositiveOrZero
    val aguaLitros: Double,

    @NotNull
    @PositiveOrZero
    val energiaKwh: Double = 0.0,

    // Calculated field
    val fcrCalculado: Double? = null, // Will be calculated based on weight gain

    val createdAt: LocalDate = LocalDate.now()
)

@Embeddable
data class ConsumoId(
    @NotNull
    val loteId: Long,

    @NotNull
    val fecha: LocalDate
)