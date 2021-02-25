package com.github.tobi812.sprykerplugin.models.renderer;

import pav.sprykerFileCreator.model.renderer.dto.DocBlockItem;
import pav.sprykerFileCreator.model.renderer.dto.PhpClassInterface;

import java.util.List;

public interface PhpClassRendererInterface {

    String renderPhpClass(PhpClassInterface phpClass);

    String renderDocBlock(List<DocBlockItem> docBlockItems);

}
