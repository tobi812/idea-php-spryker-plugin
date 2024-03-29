package com.github.tobi812.sprykerplugin.services.renderer

import com.github.tobi812.sprykerplugin.services.renderer.dto.DocBlockItem
import com.github.tobi812.sprykerplugin.services.renderer.dto.PhpClassInterface
import com.github.tobi812.sprykerplugin.services.renderer.dto.UseBlockItem
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
            method += this.renderConstructorCall(constructor, phpClass)
        }

        method += "\n     );\n}"

        return method
    }

    private fun renderConstructorCall(
        constructor: Method,
        phpClass: PhpClass
    ): String {
        val methodCalls = ArrayList<String>()
        val parameters = constructor.parameters
        for (parameter in parameters) {
            if (parameter.declaredType.isEmpty) {
                methodCalls.add(this.createMethodCallString(parameter.name.capitalize()))

                continue
            }

            val classNamespace = this.extractModuleNamespace(phpClass.namespaceName)
            val parameterNamespace = this.extractModuleNamespace(parameter.declaredType.toString())

            val parameterType = parameter.localType.toString().split("\\").last()

            var methodPrefix = "create"
            if (classNamespace != parameterNamespace) {
                methodPrefix = "get"
            }

            methodCalls.add(this.createMethodCallString(parameterType, methodPrefix))
        }

        return StringUtil.join(methodCalls, ",")
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

    private fun extractModuleNamespace(namespace: String): String {
        val preparedNamespace = namespace.removePrefix("\\")
        val namespaceSegments = preparedNamespace.split("\\")
        val appName = namespaceSegments[1]
        var occurence = 3

        if (appName == "Zed") {
            occurence = 4
        }

        val index = this.indexOf(preparedNamespace, "\\", occurence)

        return preparedNamespace.substring(0, index).removeSuffix("\\")
    }

    private fun indexOf(string: String, subString: String, occurence: Int): Int {
        var i = 0
        var match = 0
        while (i < occurence) {
            match = string.indexOf(subString, match) + 1
            i++
        }

        return match
    }

    private fun createMethodCallString(className: String, methodPrefix: String = "create"): String {
        val preparedClassName = className
            .removeSuffix("Interface")
            .removePrefix("Abstract")

        return "\n\$this->${methodPrefix}${preparedClassName}()"
    }
}
