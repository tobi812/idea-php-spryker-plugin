package com.github.tobi812.sprykerplugin.models.renderer.dto;

import pav.sprykerFileCreator.model.renderer.dto.DocBlockItem;
import pav.sprykerFileCreator.model.renderer.dto.ParamItem;

import java.util.List;

public class PhpMethod {

    private Boolean isPublic;
    private Boolean isPrivate;
    private Boolean isProtected;
    private String methodName;
    private List<DocBlockItem> docBlockItems;
    private List<ParamItem> paramItems;

}
