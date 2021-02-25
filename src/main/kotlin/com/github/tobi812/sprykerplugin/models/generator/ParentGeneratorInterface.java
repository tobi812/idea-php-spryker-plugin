package com.github.tobi812.sprykerplugin.models.generator;

import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.renderer.dto.PhpClassInterface;

public interface ParentGeneratorInterface {

    PhpClassInterface getParentClass(PhpClassInterface phpClass, ClassDefinitionInterface classDefinition);

}
