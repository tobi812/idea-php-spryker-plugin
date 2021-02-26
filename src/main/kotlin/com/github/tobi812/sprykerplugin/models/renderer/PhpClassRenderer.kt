package com.github.tobi812.sprykerplugin.models.renderer

import com.github.tobi812.sprykerplugin.models.renderer.dto.DocBlockItem
import com.github.tobi812.sprykerplugin.models.renderer.dto.PhpClassInterface
import com.intellij.openapi.util.io.StreamUtil
import com.intellij.openapi.util.text.StringUtil
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
        private const val TEMPLATE_PATH = "template/PhpClassTemplate.php"
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
            val docBlockElements = arrayOf<String>(
                docBlockItem.getTag(),
                docBlockItem.getReturnType(),
                docBlockItem.getValue()
            )
            docBlock += " * @${StringUtil.join(docBlockElements, " ")}\n"
        }

        docBlock += " */\n"

        return docBlock
    }

    private fun renderUseBlock(phpClass: PhpClassInterface): String {
        val useBlockItems: List<UseBlockItem> = phpClass.getUseBlockItems()
        if (useBlockItems.size == 0) {
            return ""
        }
        var useBlock = ""
        for (useItem in phpClass.getUseBlockItems()) {
            useBlock += "use " + useItem.getFullQualifiedClassName()
            if (useItem.getAlias() != null) {
                useBlock += " as " + useItem.getAlias()
            }
            useBlock += ";\n"
        }
        return """
            $useBlock
            
            """.trimIndent()
    }

    private fun renderParent(phpClass: PhpClassInterface): String {
        val parentClass: PhpClassInterface = phpClass.parentClass ?: return ""
        var parentClassName: String = parentClass.getName()

        if (!phpClass.getParentAlias().equals("")) {
            parentClassName = phpClass.parentAlias
        }

        return " extends $parentClassName"
    }

    private fun generateMethodBlock(phpClass: PhpClassInterface): String {
        return ""
    }

    private fun generateInterfaceBlock(phpClass: PhpClassInterface): String {
        return ""
    }

    /**
     *
     * @param path String
     * @return String
     */
    private fun readFile(path: String): String {
        var content = ""

        try {
            content = StreamUtil.readText(
                PhpClassRenderer::class.java.getResourceAsStream(path),
                "UTF-8"
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return content
    }
}