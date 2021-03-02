package com.github.tobi812.sprykerplugin.ui.handler

import pav.sprykerFileCreator.ui.SprykerFileCreatorForm

interface FormHandlerInterface {
    fun handle(form: SprykerFileCreatorForm?): Boolean?
}