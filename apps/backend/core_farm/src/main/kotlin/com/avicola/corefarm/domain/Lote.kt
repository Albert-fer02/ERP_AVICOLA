// Lote.kt - Domain entity for poultry lots
// Justification: Represents a batch of birds in fattening phase.
// Uses JPA annotations for ORM, validation for data integrity.
// Includes audit fields for traceability, essential for compliance.

package com.avicola.corefarm.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.time.LocalDate

@Entity
@Table(name = "lotes")
data class Lote(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @NotBlank
    val nombre: String,

    @NotNull
    val granjaId: Long,

    @NotNull
    val galponId: Long,

    @NotBlank
    val estirpe: String,

    @NotNull
    val fechaIngreso: LocalDate,

    @NotNull
    @Positive
    val cantidadInicial: Int,

    @NotNull
    @Positive
    val objetivoPeso: Double, // in kg

    @NotNull
    @Positive
    val fcrObjetivo: Double,

    // Audit fields
    @Column(updatable = false)
    val createdAt: LocalDate = LocalDate.now(),

    val updatedAt: LocalDate = LocalDate.now()
) {
    // Business logic: Calculate current age in days
    fun getEdadDias(): Long = java.time.temporal.ChronoUnit.DAYS.between(fechaIngreso, LocalDate.now())
}