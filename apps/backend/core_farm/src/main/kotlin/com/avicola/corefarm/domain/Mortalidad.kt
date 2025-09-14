// Mortalidad.kt - Domain entity for mortality records
// Justification: Tracks deaths per lot for health monitoring.
// Includes cause for analysis and biosecurity alerts.
// Links to Ave for detailed tracking.

package com.avicola.corefarm.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.time.LocalDate

@Entity
@Table(name = "mortalidades")
data class Mortalidad(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @NotNull
    val loteId: Long,

    @NotNull
    val aveId: Long? = null, // Optional, if tracking individual birds

    @NotNull
    val fecha: LocalDate,

    @NotNull
    @Positive
    val cantidad: Int,

    @NotBlank
    val causa: String, // e.g., "Enfermedad", "Accidente", "Desconocida"

    val responsable: String? = null,

    val notas: String? = null,

    val createdAt: LocalDate = LocalDate.now()
)