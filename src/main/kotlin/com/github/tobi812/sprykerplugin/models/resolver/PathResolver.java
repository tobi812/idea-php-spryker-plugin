package com.github.tobi812.sprykerplugin.models.resolver;

public class PathResolver implements PathResolverInterface {

    private String basePath;

    public PathResolver(String basePath) {
        this.basePath = basePath;
    }

    public String resolvePath(String namespace) {
        String relativePath = namespace.replace("\\", "/");

        return basePath + "/" + relativePath;
    }

}
