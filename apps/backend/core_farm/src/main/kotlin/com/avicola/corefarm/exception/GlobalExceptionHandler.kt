// GlobalExceptionHandler.kt - Centralized error handling
// Justification: Provides consistent error responses.
// Logs errors for monitoring and debugging.
// Returns user-friendly messages without exposing internals.

package com.avicola.corefarm.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        logger.warn("Illegal argument: ${ex.message}")
        return ResponseEntity(
            ErrorResponse("Invalid input", ex.message ?: "Bad request"),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationErrors(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errors = ex.bindingResult.fieldErrors.joinToString { "${it.field}: ${it.defaultMessage}" }
        logger.warn("Validation errors: $errors")
        return ResponseEntity(
            ErrorResponse("Validation failed", errors),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ErrorResponse> {
        logger.error("Unexpected error", ex)
        return ResponseEntity(
            ErrorResponse("Internal server error", "An unexpected error occurred"),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}

data class ErrorResponse(
    val error: String,
    val message: String
)