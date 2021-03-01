package pav.sprykerFileCreator.model.generator;

import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.definition.DefinitionProviderInterface;
import pav.sprykerFileCreator.model.renderer.dto.DocBlockItem;
import pav.sprykerFileCreator.model.renderer.dto.PhpClassInterface;
import pav.sprykerFileCreator.model.resolver.ClassResolverInterface;

import java.util.ArrayList;
import java.util.List;

public class DocBlockGenerator implements DocBlockGeneratorInterface {

    private ClassResolverInterface classResolver;
    private DefinitionProviderInterface definitionProvider;

    public DocBlockGenerator(
            ClassResolverInterface classResolver,
            DefinitionProviderInterface definitionProvider
    ) {
        this.classResolver = classResolver;
        this.definitionProvider = definitionProvider;
    }

    public List<DocBlockItem> getDocBlockItems(
            String[] classTypes,
            String bundleName
    ) throws Exception {
        List<DocBlockItem> docBlockItemList = new ArrayList<>();

        for (String classType : classTypes) {
            PhpClassInterface returnClass = this.classResolver.resolveBundleClass(classType, bundleName);

            if (returnClass != null) {
                String returnClassName = returnClass.getFullQualifiedName();
                String docBlockMethod = this.getMethodForType(classType);

                DocBlockItem docBlockItem = new DocBlockItem(
                        "method",
                        returnClassName,
                        docBlockMethod
                );

                docBlockItemList.add(docBlockItem);
            }
        }

        return docBlockItemList;
    }

    private String getMethodForType(String classType) throws Exception {
        ClassDefinitionInterface classDefinition = this.definitionProvider.getDefinitionByType(classType);

        return classDefinition.getMethodForReturnType();
    }

}
