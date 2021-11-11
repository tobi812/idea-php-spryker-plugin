package com.github.tobi812.sprykerplugin.linemarkerprovider

import com.github.tobi812.sprykerplugin.SprykerIcons
import com.github.tobi812.sprykerplugin.models.finder.FactoryFinderInterface
import com.github.tobi812.sprykerplugin.models.matcher.ClassTypeMatcherInterface
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.openapi.components.service
import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.psi.elements.PhpClass

class FactoryLineMarkerProvider : RelatedItemLineMarkerProvider() {

    override fun collectNavigationMarkers(
        element: PsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    ) {
        if (element !is PhpClass) {
            return
        }

        val phpClass: PhpClass = element

        if (phpClass.isInterface or phpClass.isAbstract or phpClass.isTrait or (phpClass.namespaceName == "")) {
            return
        }

        val classTypeMatcher = element.project.service<ClassTypeMatcherInterface>()
        if (classTypeMatcher.isSprykerClass(phpClass.fqn)) {
            return
        }

        val factoryFinder = element.project.service<FactoryFinderInterface>()
        val classFactory = factoryFinder.findClassFactory(phpClass) ?: return
        val method = factoryFinder.findClassFactoryMethod(phpClass, classFactory)

        val builder: NavigationGutterIconBuilder<PsiElement> = NavigationGutterIconBuilder
            .create(SprykerIcons.SPRYKER_ICON)

        if (method != null) {
            builder
                .setTarget(method)
                .setTooltipText("Navigate to Factory method")
        } else {
            builder
                .setTarget(classFactory)
                .setTooltipText("Navigate to Factory")
        }

        result.add(builder.createLineMarkerInfo(phpClass))
    }

    override fun getLineMarkerInfo(element: PsiElement): RelatedItemLineMarkerInfo<*>? {
        return null
    }
}
