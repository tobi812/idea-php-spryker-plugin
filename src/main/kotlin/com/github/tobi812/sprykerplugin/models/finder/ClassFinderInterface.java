package com.github.tobi812.sprykerplugin.models.finder;

import pav.sprykerFileCreator.model.renderer.dto.PhpClassInterface;

public interface ClassFinderInterface {

    Boolean doesClassExist(String fullQualifiedName);

    PhpClassInterface findClass(String fullQualifiedCoreName);

}
