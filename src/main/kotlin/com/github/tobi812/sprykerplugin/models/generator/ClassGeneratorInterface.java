package com.github.tobi812.sprykerplugin.models.generator;

import pav.sprykerFileCreator.model.renderer.dto.PhpClassInterface;

import java.util.HashMap;

public interface ClassGeneratorInterface {
    PhpClassInterface generateClass(String classType, HashMap<String, String> config) throws Exception;
}
