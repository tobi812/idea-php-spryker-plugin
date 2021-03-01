package sprykerFileCreator.model.definition.spryker;

import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.generator.SprykerConstants;

public class ZedPersistenceFactoryDefinition implements ClassDefinitionInterface {

    @Override
    public String getClassType() {
        return SprykerConstants.ZED_PERSISTENCE_FACTORY;
    }

    @Override
    public String getNamePattern() {
        return "{{ bundleName }}PersistenceFactory";
    }

    @Override
    public String getNamespacePattern() {
        return "{{ projectName }}\\Zed\\{{ bundleName }}\\Persistence";
    }

    @Override
    public String getMethodForReturnType() {
        return "getFactory()";
    }

    @Override
    public String[] getDocBlockClasses() {
        String [] docBlockClasses = {
                SprykerConstants.ZED_CONFIG
        };

        return docBlockClasses;
    }

    @Override
    public String getDefaultParentClass() {
        return "Spryker\\Zed\\Kernel\\Persistence\\AbstractPersistenceFactory";
    }

}
