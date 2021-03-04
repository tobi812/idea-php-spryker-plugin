package com.github.tobi812.sprykerplugin.models.matcher

import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.models.definitions.ClassDefinitionInterface
import com.github.tobi812.sprykerplugin.models.definitions.DefinitionProviderInterface
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiDirectory
import org.apache.commons.lang.ArrayUtils
import java.lang.Exception
import java.util.ArrayList
import java.util.regex.Pattern

class ClassTypeMatcher(private val definitionProvider: DefinitionProviderInterface) :
    ClassTypeMatcherInterface {

    companion object {
        private const val WILDCARD = ".*"
    }

    override fun matchClassType(fqClassName: String): ClassDefinitionInterface? {
        val classDefinitions: Collection<ClassDefinitionInterface> = this.definitionProvider.allClassDefinitions.values
        for (classDefinition in classDefinitions) {
            val classTypePattern = this.getClassTypePattern(classDefinition)
            val matched = Pattern.matches(classTypePattern, fqClassName)
            if (matched) {
                return classDefinition
            }
        }

        return null
    }

    @Throws(Exception::class)
    override fun classTypeMatchesDir(classType: String, directory: PsiDirectory): Boolean {
        val classDefinition: ClassDefinitionInterface = this.definitionProvider.getDefinitionByType(classType)
        var dir = directory.toString()
        dir = dir.replace("/", "\\")
        val bundleName = this.matchBundleName(classType, directory)
        var namespacePattern = this.getNamespacePattern(classDefinition, bundleName, true)
        namespacePattern = "^.*$namespacePattern$"

        return Pattern.matches(namespacePattern, dir)
    }

    @Throws(Exception::class)
    override fun matchProjectName(classType: String, psiDirectory: PsiDirectory): String {
        val namespacePattern: String = this.definitionProvider.getDefinitionByType(classType).namespacePattern
        val projectPosition = this.getPlaceholderPosition(namespacePattern, SprykerConstants.PROJECT_NAME_PLACEHOLDER)
        var currentDirectory: PsiDirectory? = psiDirectory
        for (i in 1 until projectPosition) {
            currentDirectory = currentDirectory!!.parent
        }

        return currentDirectory?.name ?: ""
    }

    override fun matchProjectName(fqClassName: String): String {
        val classSegments = fqClassName.split("\\\\".toRegex()).toTypedArray()
        return classSegments[1]
    }

    private fun getClassTypePattern(classDefinition: ClassDefinitionInterface): String {
        val namePattern = this.getNamePattern(classDefinition, false)
        val namespacePattern = this.getNamespacePattern(classDefinition, WILDCARD, false)

        return namespacePattern + "\\\\" + namePattern
    }

    @Throws(Exception::class)
    fun matchClassTypesByDir(directory: PsiDirectory): ArrayList<String> {
        val classDefinitions: Collection<ClassDefinitionInterface> = definitionProvider
            .allClassDefinitions
            .values
        val matchedTypes = ArrayList<String>()
        var dir = directory.toString()
        dir = dir.replace("/", "\\")
        for (classDefinition in classDefinitions) {
            val bundleName = matchBundleName(classDefinition.classType, directory)
            val namespacePattern = getNamespacePattern(classDefinition, bundleName, true)
            val matched = Pattern.matches("^.*" + namespacePattern + WILDCARD, dir)
            if (matched) {
                matchedTypes.add(classDefinition.classType)
            }
        }
        return matchedTypes
    }

    @Throws(Exception::class)
    override fun matchBundleName(classType: String, psiDirectory: PsiDirectory): String {
        val namespacePattern: String = this.definitionProvider.getDefinitionByType(classType).namespacePattern
        val bundlePosition = this.getPlaceholderPosition(namespacePattern, SprykerConstants.MODULE_NAME_PLACEHOLDER)
        var currentDirectory: PsiDirectory = psiDirectory
        for (i in 1 until bundlePosition) {
            if (currentDirectory.parent is PsiDirectory) {
                currentDirectory = currentDirectory.parent!!
            } else {
                return ""
            }
        }

        return currentDirectory.name
    }

    override fun matchBundleNameFromFQName(classDefinition: ClassDefinitionInterface, fqName: String): String {
        val namespacePattern: String = classDefinition.namespacePattern
        val bundlePosition = this.getPlaceholderPosition(namespacePattern, SprykerConstants.MODULE_NAME_PLACEHOLDER)
        val nameSegments = fqName.split("\\\\".toRegex()).toTypedArray()
        ArrayUtils.reverse(nameSegments)
        var bundleName = ""
        var currentPosition = 0
        for (nameSegment in nameSegments) {
            if (bundlePosition == currentPosition) {
                bundleName = nameSegment
                break
            }

            currentPosition++
        }

        return bundleName
    }

    private fun getPlaceholderPosition(namespacePattern: String, bundleNamePlaceholder: String): Int {
        val segments = namespacePattern.split("\\\\".toRegex()).toTypedArray()
        ArrayUtils.reverse(segments)

        var bundlePosition = 0
        var segmentCount = 0
        for (segment in segments) {
            segmentCount++
            if (segment == bundleNamePlaceholder) {
                bundlePosition = segmentCount
            }
        }

        return bundlePosition
    }

    private fun getNamePattern(classDefinition: ClassDefinitionInterface, trim: Boolean): String {
        var namePattern: String = classDefinition.namePattern
        namePattern = namePattern.replace(SprykerConstants.MODULE_NAME_PLACEHOLDER, WILDCARD)
        namePattern = namePattern.replace(SprykerConstants.CLASS_NAME_PLACEHOLDER, WILDCARD)
        namePattern = namePattern.replace("\\", "\\\\")

        if (trim) {
            namePattern = StringUtil.trimEnd(namePattern, WILDCARD)
            namePattern = StringUtil.trimStart(namePattern, WILDCARD)
        }

        return namePattern
    }

    private fun getNamespacePattern(
        classDefinition: ClassDefinitionInterface,
        bundleName: String,
        trim: Boolean
    ): String {
        var namespacePattern: String = classDefinition.namespacePattern
        namespacePattern = namespacePattern.replace(SprykerConstants.MODULE_NAME_PLACEHOLDER, bundleName)
        namespacePattern = namespacePattern.replace(SprykerConstants.PROJECT_NAME_PLACEHOLDER, WILDCARD)
        namespacePattern = namespacePattern.replace("\\", "\\\\")

        if (trim) {
            namespacePattern = StringUtil.trimStart(namespacePattern, WILDCARD)
            namespacePattern = StringUtil.trimEnd(namespacePattern, WILDCARD)
        }

        return namespacePattern
    }
}