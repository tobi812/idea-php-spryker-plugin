package com.github.tobi812.sprykerplugin.ui.handler

import pav.sprykerFileCreator.model.generator.SprykerConstants
import pav.sprykerFileCreator.model.manager.ClassManagerInterface
import pav.sprykerFileCreator.ui.ClassSelectionItem
import pav.sprykerFileCreator.ui.SprykerFileCreatorForm
import java.util.ArrayList
import java.util.HashMap

class FormHandler(classManager: ClassManagerInterface) : FormHandlerInterface {
    private val classManager: ClassManagerInterface
    override fun handle(form: SprykerFileCreatorForm): Boolean {
        val bundleName: String = form.getBundleName()
        val classSelectionItems: List<ClassSelectionItem> = form.getClassSelectionItems()
        val selectedItems: List<ClassSelectionItem> = extractSelectedItems(classSelectionItems)
        //        selectedItems = this.sortSelectedItems();
        for (selectedItem in selectedItems) {
            val fileConfig = getClassConfig(bundleName, selectedItem)

//            Boolean result = this.classManager.handleClass(selectedItem.getClassType(), fileConfig);
//            System.out.print(result);
        }
        return true
    }

    private fun extractSelectedItems(classSelectionItems: List<ClassSelectionItem>): List<ClassSelectionItem> {
        val selectedItems: MutableList<ClassSelectionItem> = ArrayList<ClassSelectionItem>()
        for (classSelectionItem in classSelectionItems) {
            if (classSelectionItem.isSelected()) {
                selectedItems.add(classSelectionItem)
            }
        }
        return selectedItems
    }

    private fun getClassConfig(bundleName: String, classSelectionItem: ClassSelectionItem): HashMap<String, String> {
        val classConfig = HashMap<String, String>()
        classConfig[SprykerConstants.PROJECT_NAME] = "Pyz"
        classConfig[SprykerConstants.BUNDLE_NAME] = bundleName
        if (classSelectionItem.isClassNameEditable()) {
            val className: String = classSelectionItem.getClassName()
            classConfig[SprykerConstants.CLASS_NAME] = className
        }
        return classConfig
    }

    init {
        this.classManager = classManager
    }
}