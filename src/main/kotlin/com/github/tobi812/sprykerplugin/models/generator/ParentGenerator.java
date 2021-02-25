package com.github.tobi812.sprykerplugin.models.generator;

import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.finder.ClassFinderInterface;
import pav.sprykerFileCreator.model.renderer.dto.PhpClassInterface;
import pav.sprykerFileCreator.model.resolver.ClassResolverInterface;

public class ParentGenerator implements ParentGeneratorInterface {

    private ClassResolverInterface classResolver;
    private ClassFinderInterface classFinder;

    public ParentGenerator(
            ClassResolverInterface classResolver,
            ClassFinderInterface classFinder
    ) {
        this.classResolver = classResolver;
        this.classFinder = classFinder;
    }

    @Override
    public PhpClassInterface getParentClass(PhpClassInterface phpClass, ClassDefinitionInterface classDefinition) {

        PhpClassInterface coreClass = this.classResolver.resolveClassBelow(phpClass.getFullQualifiedName());

        if (coreClass == null) {
            coreClass = this.classFinder.findClass(classDefinition.getDefaultParentClass());
        }

        return coreClass;
    }

}
