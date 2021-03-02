package com.github.tobi812.sprykerplugin.ui.validator

import sprykerFileCreator.ui.SprykerFileCreatorForm
import javax.xml.bind.ValidationException

interface FormValidatorInterface {
    @Throws(ValidationException::class)
    fun validate(form: SprykerFileCreatorForm)
}