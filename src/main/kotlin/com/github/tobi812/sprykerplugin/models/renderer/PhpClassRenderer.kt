package com.github.tobi812.sprykerplugin.models.renderer

import com.github.tobi812.sprykerplugin.models.renderer.dto.DocBlockItem
import com.github.tobi812.sprykerplugin.models.renderer.dto.PhpClassInterface
import com.github.tobi812.sprykerplugin.models.renderer.dto.UseBlockItem
import com.intellij.openapi.util.text.StringUtil
import com.jetbrains.php.lang.psi.elements.Method
import com.jetbrains.php.lang.psi.elements.PhpClass
import java.io.IOException

class PhpClassRenderer : PhpClassRendererInterface {
    companion object {
        private const val NAMESPACE = "{{ namespace }}"
        private const val USE_BLOCK = "{{ useBlock }}\n"
        private const val DOC_BLOCK = "{{ docBlock }}\n"
        private const val CLASS_NAME = "{{ className }}"
        private const val PARENT_CLASS = "{{ parentClass }}"
        private const val INTERFACE_BLOCK = "{{ interfaceBlock }}"
        private const val METHOD_BLOCK = "{{ methodBlock }}"
        private const val TEMPLATE_PATH = "/template/PhpClassTemplate.php.template"
    }

    override fun renderPhpClass(phpClass: PhpClassInterface): String {
        var content = this.readFile(TEMPLATE_PATH)

        if (content != "") {
            content = content.replace(NAMESPACE, phpClass.namespace)
            content = content.replace(USE_BLOCK, this.renderUseBlock(phpClass))
            content = content.replace(DOC_BLOCK, this.renderDocBlock(phpClass.docBlockItems))
            content = content.replace(CLASS_NAME, phpClass.name)
            content = content.replace(PARENT_CLASS, renderParent(phpClass))
            content = content.replace(INTERFACE_BLOCK, generateInterfaceBlock(phpClass))
            content = content.replace(METHOD_BLOCK, generateMethodBlock(phpClass))
        }

        return content
    }

    override fun renderDocBlock(docBlockItems: List<DocBlockItem>): String {
        if (docBlockItems.isEmpty()) {
            return ""
        }

        var docBlock = "/**\n"
        for (docBlockItem in docBlockItems) {
            val docBlockElements = ArrayList<String>()
            docBlockElements.add(docBlockItem.tag)
            docBlockElements.add(docBlockItem.returnType)

            if (docBlockItem.value != null) {
                docBlockElements.add(docBlockItem.value)
            }

            docBlock += " * @${StringUtil.join(docBlockElements, " ")}\n"
        }

        docBlock += " */\n"

        return docBlock
    }

    override fun renderFactoryMethod(phpClass: PhpClass): String {
        var method: String = "public function create"
        method += phpClass.name
        method += "()\n{\n"
        method += "    return new ${phpClass.name}("

        val constructor = phpClass.constructor
        if (constructor is Method) {
            val parameters = constructor.parameters
            for (parameter in parameters) {
                System.out.print(parameter.declaredType)
            }
        }

        method += ");\n}"

        return method
    }

    private fun renderUseBlock(phpClass: PhpClassInterface): String {
        val useBlockItems: List<UseBlockItem> = phpClass.useBlockItems
        if (useBlockItems.isEmpty()) {
            return ""
        }

        var useBlock = ""
        for (useItem in phpClass.useBlockItems) {
            useBlock += "use " + useItem.fullQualifiedClassName
            if (useItem.alias != null) {
                useBlock += " as " + useItem.alias
            }
            useBlock += ";\n"
        }

        return """
            $useBlock
            
            """.trimIndent()
    }

    private fun renderParent(phpClass: PhpClassInterface): String {
        val parentClass: PhpClassInterface = phpClass.parentClass ?: return ""
        var parentClassName: String = parentClass.name

        val parentAlias: String = phpClass.parentAlias as String
        if (parentAlias.isNotBlank()) parentClassName = parentAlias

        return " extends $parentClassName"
    }

    private fun generateMethodBlock(phpClass: PhpClassInterface): String {
        return ""
    }

    private fun generateInterfaceBlock(phpClass: PhpClassInterface): String {
        return ""
    }

    private fun readFile(path: String): String {
        try {
            val resource = javaClass.getResource(path)

            if (resource != null) {
                return resource.readText()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return ""
    }
}
