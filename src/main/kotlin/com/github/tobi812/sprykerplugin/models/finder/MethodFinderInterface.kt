package com.github.tobi812.sprykerplugin.models.finder

import com.jetbrains.php.lang.psi.elements.Method
import com.jetbrains.php.lang.psi.elements.PhpClass
import java.util.ArrayList

interface MethodFinderInterface {
    fun findClassFactoryMethod(phpClass: PhpClass): ArrayList<Method>?
}