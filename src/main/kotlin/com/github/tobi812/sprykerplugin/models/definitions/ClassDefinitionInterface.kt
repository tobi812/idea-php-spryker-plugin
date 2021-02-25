package com.github.tobi812.sprykerplugin.models.definitions

interface ClassDefinitionInterface {
    val classType: String
    val namePattern: String
    val namespacePattern: String
    val methodForReturnType: String
    val docBlockClasses: Array<String>
    val defaultParentClass: String?
}