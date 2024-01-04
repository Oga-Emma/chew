package app.seven.chew.auth.utils

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.time.LocalDate


@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [DateValidator::class])
@MustBeDocumented
annotation class IsAfter(val message: String = "{message.key}", val current: String)

class DateValidator : ConstraintValidator<IsAfter?, LocalDate?> {
    private var validDate: String? = null

    fun initialize(constraintAnnotation: IsAfter) {
        validDate = constraintAnnotation.current
    }

    override fun isValid(date: LocalDate?, constraintValidatorContext: ConstraintValidatorContext?): Boolean {
        val splitDate = validDate!!.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return date != null && date.isAfter(LocalDate.of(splitDate[2].toInt(), splitDate[1].toInt(), splitDate[0].toInt()))
    }

}