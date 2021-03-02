package com.github.tobi812.sprykerplugin.ui

class ClassSelectionItem(
    var isSelected: Boolean?,
    val classTypeLabel: String?,
    val isClassNameEditable: Boolean?,
    val classType: String?,
    var className: String
) {

    constructor(
        isSelected: Boolean?,
        classTypeLabel: String?,
        hasClassName: Boolean?,
        classType: String?
    ) : this(isSelected, classTypeLabel, hasClassName, classType, "") {
    }
}