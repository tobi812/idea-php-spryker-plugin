package com.github.tobi812.sprykerplugin.models.renderer.dto;

import com.intellij.openapi.util.text.StringUtil;

public class UseBlockItem {
    private String className;
    private String namespace;
    private String alias;

    public UseBlockItem(String className, String namespace) {
        this.className = className;
        this.namespace = namespace;
    }

    public String getFullQualifiedClassName() {
        return StringUtil.trimEnd(this.namespace, "\\") + "\\" + this.className;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

}
