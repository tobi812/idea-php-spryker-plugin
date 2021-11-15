package com.github.tobi812.sprykerplugin.services.finder

import com.jetbrains.php.lang.psi.elements.Method
import com.jetbrains.php.lang.psi.elements.PhpClass

interface FactoryFinderInterface {
    fun findClassFactory(phpClass: PhpClass): PhpClass?
    fun findClassFactoryMethod(phpClass: PhpClass, factory: PhpClass): Method?
}