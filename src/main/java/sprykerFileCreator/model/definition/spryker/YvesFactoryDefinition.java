package sprykerFileCreator.model.definition.spryker;

import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.generator.SprykerConstants;

public class YvesFactoryDefinition implements ClassDefinitionInterface {

    @Override
    public String getClassType() {
        return SprykerConstants.YVES_FACTORY;
    }

    @Override
    public String getNamePattern() {
        return "{{ bundleName }}Factory";
    }

    @Override
    public String getNamespacePattern() {
        return "{{ projectName }}\\Yves\\{{ bundleName }}";
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
        return "Spryker\\Yves\\Kernel\\AbstractFactory";
    }

}
