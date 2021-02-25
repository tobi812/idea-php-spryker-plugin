package com.github.tobi812.sprykerplugin.models.renderer.dto;

import java.util.List;

public interface PhpClassInterface extends PhpFileInterface {

    Boolean isAbstract();

    void setAbstract();

    PhpClassInterface getParentClass();

    void setParentClass(PhpClassInterface parentClass);

    void setParentClass(PhpClassInterface parentClass, String aliasPrefix);

    List<UseBlockItem> getUseBlockItems();

    void addUseBlockItem(UseBlockItem useBlockItem);

    List<DocBlockItem> getDocBlockItems();

    void addDocBlockItem(DocBlockItem docBlockItem);

    String getParentAlias();


}
