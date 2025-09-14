// Ave.kt - Domain entity for individual birds
// Justification: Tracks each bird for detailed traceability.
// Embedded in Lote for performance, but can be separate if needed.
// Includes RFID tag for modern tracking.

package com.avicola.corefarm.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

@Entity
@Table(name = "aves")
data class Ave(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @NotNull
    val loteId: Long,

    @NotBlank
    val rfidTag: String, // For RFID tracking

    @NotNull
    val fechaNacimiento: LocalDate,

    @NotBlank
    val estirpe: String,

    @NotNull
    val pesoActual: Double = 0.0, // in grams

    @Enumerated(EnumType.STRING)
    val estado: EstadoAve = EstadoAve.VIVA,

    val localizacion: String? = null, // e.g., "Galpon A, Sector 1"

    // Audit
    val createdAt: LocalDate = LocalDate.now(),
    val updatedAt: LocalDate = LocalDate.now()
)

enum class EstadoAve {
    VIVA, MUERTA, VENDIDA, DESCARTADA
}