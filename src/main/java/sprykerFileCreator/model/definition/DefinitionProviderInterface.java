package sprykerFileCreator.model.definition;

import java.util.HashMap;

public interface DefinitionProviderInterface {

    ClassDefinitionInterface getDefinitionByType(String classType) throws Exception;

    HashMap<String, ClassDefinitionInterface> getAllClassDefinitions();
}
