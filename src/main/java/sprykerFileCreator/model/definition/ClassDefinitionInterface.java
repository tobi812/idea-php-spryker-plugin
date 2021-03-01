package pav.sprykerFileCreator.model.definition;

public interface ClassDefinitionInterface {

    String getClassType();

    String getNamePattern();

    String getNamespacePattern();

    String getMethodForReturnType();

    String[] getDocBlockClasses();

    String getDefaultParentClass();

}
