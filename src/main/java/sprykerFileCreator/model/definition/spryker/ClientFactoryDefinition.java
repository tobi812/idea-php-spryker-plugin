package pav.sprykerFileCreator.model.definition.spryker;

import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.generator.SprykerConstants;

public class ClientFactoryDefinition implements ClassDefinitionInterface {

    @Override
    public String getClassType() {
        return SprykerConstants.CLIENT_FACTORY;
    }

    @Override
    public String getNamePattern() {
        return "{{ bundleName }}Factory";
    }

    @Override
    public String getNamespacePattern() {
        return "{{ projectName }}\\Client\\{{ bundleName }}";
    }

    @Override
    public String getMethodForReturnType() {
        return "getFactory()";
    }

    @Override
    public String[] getDocBlockClasses() {
        String [] docBlockClasses = {};

        return docBlockClasses;
    }

    @Override
    public String getDefaultParentClass() {
        return "Spryker\\Client\\Kernel\\AbstractFactory";
    }

}
