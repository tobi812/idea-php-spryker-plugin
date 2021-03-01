package pav.sprykerFileCreator.model.generator;

import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.definition.DefinitionProviderInterface;
import pav.sprykerFileCreator.model.renderer.dto.DocBlockItem;
import pav.sprykerFileCreator.model.renderer.dto.PhpClassInterface;
import pav.sprykerFileCreator.model.renderer.dto.PhpClassItem;

import java.util.HashMap;
import java.util.List;

public class ClassGenerator implements ClassGeneratorInterface {

    private ParentGeneratorInterface parentGenerator;
    private DocBlockGeneratorInterface docBlockGenerator;
    private DefinitionProviderInterface definitionProvider;

    public ClassGenerator(
            ParentGeneratorInterface parentGenerator,
            DocBlockGeneratorInterface docBlockGenerator,
            DefinitionProviderInterface definitionProvider
    ) {
        this.parentGenerator = parentGenerator;
        this.docBlockGenerator = docBlockGenerator;
        this.definitionProvider = definitionProvider;
    }

    @Override
    public PhpClassInterface generateClass(String classType, HashMap<String, String> config) throws Exception {

        ClassDefinitionInterface classDefinition = this.definitionProvider.getDefinitionByType(classType);

        String namespace = this.createNamespace(classDefinition, config);
        String className = this.createClassName(classDefinition, config);

        PhpClassInterface phpClass = new PhpClassItem(namespace, className);

        this.addParentClass(phpClass, classDefinition);
        this.addDocBlock(phpClass, classDefinition, config);

        return phpClass;
    }

    private String createNamespace(ClassDefinitionInterface classDefinition, HashMap<String, String> config) {

        String projectName = config.get(SprykerConstants.PROJECT_NAME);
        String bundleName = config.get(SprykerConstants.BUNDLE_NAME);

        return classDefinition.getNamespacePattern()
                .replace(SprykerConstants.PROJECT_NAME_PLACEHOLDER, projectName)
                .replace(SprykerConstants.BUNDLE_NAME_PLACEHOLDER, bundleName);
    }

    private String createClassName(ClassDefinitionInterface classDefinition, HashMap<String, String> config) {
        String className = "Index";
        String bundleName = config.get(SprykerConstants.BUNDLE_NAME);

        if (config.containsKey(SprykerConstants.CLASS_NAME)) {
            className = config.get(SprykerConstants.CLASS_NAME);
        }

        return classDefinition.getNamePattern()
                .replace(SprykerConstants.CLASS_NAME_PLACEHOLDER, className)
                .replace(SprykerConstants.BUNDLE_NAME_PLACEHOLDER, bundleName);
    }

    private void addParentClass(PhpClassInterface phpClass, ClassDefinitionInterface classDefinition) {
        PhpClassInterface parentClass = this.parentGenerator.getParentClass(phpClass, classDefinition);

        if (parentClass != null) {
            phpClass.setParentClass(parentClass);
        }
    }

    private void addDocBlock(
            PhpClassInterface phpClass,
            ClassDefinitionInterface classDefinition,
            HashMap<String, String> config
    ) throws Exception {
        String bundleName = config.get(SprykerConstants.BUNDLE_NAME);

        String[] docBlockClassTypes = classDefinition.getDocBlockClasses();
        List<DocBlockItem> docBlockItems =  this.docBlockGenerator.getDocBlockItems(docBlockClassTypes, bundleName);

        for (DocBlockItem docBlockItem: docBlockItems) {
            phpClass.addDocBlockItem(docBlockItem);
        }
    }
}
