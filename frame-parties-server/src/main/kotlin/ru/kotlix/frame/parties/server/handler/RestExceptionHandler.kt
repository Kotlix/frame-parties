package ru.kotlix.frame.parties.server.handler

import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class RestExceptionHandler {
    @ExceptionHandler(
        MethodArgumentNotValidException::class,
        ConstraintViolationException::class,
    )
    fun handleValidation(ex: Exception): ResponseEntity<Unit> = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
}
