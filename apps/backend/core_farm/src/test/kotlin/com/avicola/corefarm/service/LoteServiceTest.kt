// LoteServiceTest.kt - Unit tests for LoteService
// Justification: Ensures business logic correctness.
// Uses Mockito for mocking dependencies.
// Covers happy path and error scenarios.

package com.avicola.corefarm.service

import com.avicola.corefarm.domain.Lote
import com.avicola.corefarm.repository.LoteRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate
import java.util.*

@ExtendWith(MockitoExtension::class)
class LoteServiceTest {

    @Mock
    private lateinit var loteRepository: LoteRepository

    @InjectMocks
    private lateinit var loteService: LoteService

    @Test
    fun `should create lote successfully`() {
        val lote = Lote(
            nombre = "Lote 1",
            granjaId = 1L,
            galponId = 1L,
            estirpe = "Ross",
            fechaIngreso = LocalDate.now(),
            cantidadInicial = 1000,
            objetivoPeso = 2.5,
            fcrObjetivo = 1.8
        )
        `when`(loteRepository.findByGranjaId(1L)).thenReturn(emptyList())
        `when`(loteRepository.save(lote)).thenReturn(lote.copy(id = 1L))

        val result = loteService.createLote(lote)

        assertNotNull(result.id)
        verify(loteRepository).save(lote)
    }

    @Test
    fun `should throw exception for duplicate lote name in farm`() {
        val lote = Lote(
            nombre = "Lote 1",
            granjaId = 1L,
            galponId = 1L,
            estirpe = "Ross",
            fechaIngreso = LocalDate.now(),
            cantidadInicial = 1000,
            objetivoPeso = 2.5,
            fcrObjetivo = 1.8
        )
        `when`(loteRepository.findByGranjaId(1L)).thenReturn(listOf(lote))

        assertThrows(IllegalArgumentException::class.java) {
            loteService.createLote(lote)
        }
    }

    @Test
    fun `should get lote by id`() {
        val lote = Lote(id = 1L, nombre = "Lote 1", granjaId = 1L, galponId = 1L, estirpe = "Ross", fechaIngreso = LocalDate.now(), cantidadInicial = 1000, objetivoPeso = 2.5, fcrObjetivo = 1.8)
        `when`(loteRepository.findById(1L)).thenReturn(Optional.of(lote))

        val result = loteService.getLotById(1L)

        assertEquals(lote, result)
    }
}