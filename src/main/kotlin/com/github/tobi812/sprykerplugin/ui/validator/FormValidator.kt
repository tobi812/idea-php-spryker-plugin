package com.github.tobi812.sprykerplugin.ui.validator

import sprykerFileCreator.ui.SprykerFileCreatorForm
import javax.xml.bind.ValidationException

class FormValidator : FormValidatorInterface {

    @Throws(ValidationException::class)
    override fun validate(form: SprykerFileCreatorForm) {
        this.validateFileSelectionItems(form)
        this.validateBundleName(form)
    }

    @Throws(ValidationException::class)
    private fun validateFileSelectionItems(form: SprykerFileCreatorForm) {
        for (fileSelectionItem in form.getClassSelectionItems()) {
            if (fileSelectionItem.isSelected()) {
                return
            }
        }

        throw ValidationException("blub")
    }

    @Throws(ValidationException::class)
    private fun validateBundleName(form: SprykerFileCreatorForm) {
        if (form.getBundleName().isEmpty()) {
            throw ValidationException("blub")
        }
    }
}