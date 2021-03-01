package sprykerFileCreator.model.definition.spryker;

import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.generator.SprykerConstants;

public class ClientDefinition implements ClassDefinitionInterface {

    @Override
    public String getClassType() {
        return SprykerConstants.CLIENT;
    }

    @Override
    public String getNamePattern() {
        return "{{ bundleName }}Client";
    }

    @Override
    public String getNamespacePattern() {
        return "{{ projectName }}\\Client\\{{ bundleName }}";
    }

    @Override
    public String getMethodForReturnType() {
        return "getClient()";
    }

    @Override
    public String[] getDocBlockClasses() {
        String [] docBlockClasses = {};

        return docBlockClasses;
    }

    @Override
    public String getDefaultParentClass() {
        return null;
    }

}
