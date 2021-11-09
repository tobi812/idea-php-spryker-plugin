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
        if (element !is PhpClass) {
            return
        }

        val phpClass: PhpClass = element

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
        val factoryFinder = this.modelFactory.createMethodFinder(phpClass.project, projectName)
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
