package com.github.tobi812.sprykerplugin.models.resolver;

import pav.sprykerFileCreator.model.renderer.dto.PhpClassInterface;

public interface ClassResolverInterface {

    PhpClassInterface resolveBundleClass(String classType, String bundleName) throws Exception;

    PhpClassInterface resolveClass(String fullQualifiedName, Boolean findInterface, Boolean findClassBelow);

    PhpClassInterface resolveClassBelow(String fullQualifiedName);

}
