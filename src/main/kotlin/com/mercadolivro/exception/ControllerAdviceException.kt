package com.mercadolivro.exception

import com.mercadolivro.controller.response.ErrorResponse
import org.apache.coyote.Response
import org.springframework.aop.BeforeAdvice
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ControllerAdviceException {


    @ExceptionHandler(NotFoundException::class)
    fun hndleException(ex: NotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.message,
                ex.errorCode,
                null
            ), HttpStatus.NOT_FOUND
        )
    }

}