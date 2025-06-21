package com.mercadolivro.exception

import com.mercadolivro.controller.response.ErrorResponse
import com.mercadolivro.controller.response.FieldErrorResponse
import com.mercadolivro.enums.Errors
import org.apache.coyote.Response
import org.springframework.aop.BeforeAdvice
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ControllerAdviceException {


    @ExceptionHandler(NotFoundException::class)
    fun hndleNotFoundException(ex: NotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.message,
                ex.errorCode,
                null
            ), HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(BadRequestException::class)
    fun hndleBadRequestException(ex: BadRequestException, request: WebRequest): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.message,
                ex.errorCode,
                null
            ), HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun hndleMethodArgumentNotValidExceptionException(
        ex: MethodArgumentNotValidException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                Errors.ML3001.message,
                Errors.ML3001.code,
                ex.bindingResult.fieldErrors.map { FieldErrorResponse(it.defaultMessage ?: "invalid", it.field) }
            ), HttpStatus.UNPROCESSABLE_ENTITY
        )
    }

}