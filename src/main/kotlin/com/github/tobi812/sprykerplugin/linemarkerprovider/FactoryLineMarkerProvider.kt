package com.github.tobi812.sprykerplugin.linemarkerprovider

import com.github.tobi812.sprykerplugin.SprykerIcons
import com.github.tobi812.sprykerplugin.models.ModelFactory
import com.github.tobi812.sprykerplugin.models.matcher.ClassTypeMatcherInterface
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.psi.elements.PhpClass


class FactoryLineMarkerProvider : RelatedItemLineMarkerProvider() {
    private val modelFactory: ModelFactory = ModelFactory()

    override fun collectNavigationMarkers(
        element: PsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    ) {
        val phpClass: PsiElement = element.context ?: return

        if (phpClass !is PhpClass) {
            return
        }

        if (phpClass.isInterface or phpClass.isAbstract or phpClass.isTrait) {
            return
        }

        val classTypeMatcher: ClassTypeMatcherInterface = this.modelFactory.classTypeMatcher
        if (classTypeMatcher.isSprykerClass(phpClass.fqn)) {
            return
        }

        val fqClassName = phpClass.fqn
        val classSegments = fqClassName.split("\\\\".toRegex()).toTypedArray()
        val projectName = classSegments[1]
        val methodFinder = this.modelFactory.createMethodFinder(phpClass.project, projectName)

        val methodCollection = methodFinder.findClassFactoryMethod(phpClass)

        if (methodCollection != null && methodCollection.size > 0) {
            val builder: NavigationGutterIconBuilder<PsiElement> = NavigationGutterIconBuilder.create(SprykerIcons.SPRYKER_ICON)
                .setTargets(methodCollection)
                .setTooltipText("Navigate to Factory method")
            result.add(builder.createLineMarkerInfo(element))
        }
    }

    override fun getLineMarkerInfo(element: PsiElement): RelatedItemLineMarkerInfo<*>? {
        return null
    }
}
