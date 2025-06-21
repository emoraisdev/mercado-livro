package com.mercadolivro.validation

import com.mercadolivro.service.CustomerService
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class EmailAvaiableValidator(var customerService: CustomerService) :
    ConstraintValidator<EmailAvaiable, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value.isNullOrEmpty()) {
            return false
        }

        return customerService.isEmailAvaiable(value)
    }

}
