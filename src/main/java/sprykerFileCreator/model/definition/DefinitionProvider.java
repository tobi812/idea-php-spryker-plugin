package pav.sprykerFileCreator.model.definition;

import pav.sprykerFileCreator.model.definition.spryker.*;

import java.util.HashMap;

public class DefinitionProvider implements DefinitionProviderInterface {

    public static HashMap<String, ClassDefinitionInterface> classDefinitions = new HashMap<>();

    public DefinitionProvider() {
        this.addClassDefinition(new ClientDefinition());
        this.addClassDefinition(new ClientFactoryDefinition());
        this.addClassDefinition(new ClientDependencyProviderDefinition());
        this.addClassDefinition(new ClientPluginDefinition());
        this.addClassDefinition(new YvesControllerDefinition());
        this.addClassDefinition(new YvesFactoryDefinition());
        this.addClassDefinition(new YvesDependencyProviderDefinition());
        this.addClassDefinition(new YvesPluginDefinition());
        this.addClassDefinition(new ZedControllerDefinition());
        this.addClassDefinition(new ZedQueryContainerDefinition());
        this.addClassDefinition(new ZedFacadeDefinition());
        this.addClassDefinition(new ZedBusinessFactoryDefinition());
        this.addClassDefinition(new ZedPersistenceFactoryDefinition());
        this.addClassDefinition(new ZedCommunicationFactoryDefinition());
        this.addClassDefinition(new ZedConfigDefinition());
        this.addClassDefinition(new ZedPluginDefinition());
        this.addClassDefinition(new ZedDependencyProviderDefinition());
    }

    public ClassDefinitionInterface getDefinitionByType(String classType) throws Exception {

        ClassDefinitionInterface classDefinition = classDefinitions.get(classType);

        if (classDefinition == null) {
            throw new Exception("Class definition was not found: " + classType);
        }

        return classDefinition;
    }

    public HashMap<String, ClassDefinitionInterface> getAllClassDefinitions() {
        return classDefinitions;
    }

    private void addClassDefinition(ClassDefinitionInterface classDefinition) {
        classDefinitions.put(classDefinition.getClassType(), classDefinition);
    }
}
