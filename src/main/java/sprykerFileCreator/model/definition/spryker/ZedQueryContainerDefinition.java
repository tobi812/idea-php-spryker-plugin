package pav.sprykerFileCreator.model.definition.spryker;

import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.generator.SprykerConstants;

public class ZedQueryContainerDefinition implements ClassDefinitionInterface {

    @Override
    public String getClassType() {
        return SprykerConstants.ZED_QUERY_CONTAINER;
    }

    @Override
    public String getNamePattern() {
        return "{{ bundleName }}QueryContainer";
    }

    @Override
    public String getNamespacePattern() {
        return "{{ projectName }}\\Zed\\{{ bundleName }}\\Persistence";
    }

    @Override
    public String getMethodForReturnType() {
        return "getQueryContainer()";
    }

    @Override
    public String[] getDocBlockClasses() {
        String [] docBlockClasses = {
                SprykerConstants.ZED_PERSISTENCE_FACTORY
        };

        return docBlockClasses;
    }

    @Override
    public String getDefaultParentClass() {
        return "Spryker\\Zed\\Kernel\\Persistence\\AbstractQueryContainer";
    }
}
