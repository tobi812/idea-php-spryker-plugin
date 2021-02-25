package com.github.tobi812.sprykerplugin.models.generator;

import pav.sprykerFileCreator.model.renderer.dto.DocBlockItem;
import pav.sprykerFileCreator.model.renderer.dto.PhpClassInterface;

import java.util.List;

public interface DocBlockGeneratorInterface {

    List<DocBlockItem> getDocBlockItems(
            String[] classTypes,
            String bundleName
    ) throws Exception;

}
