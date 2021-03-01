package pav.sprykerFileCreator.model.resolver;

import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.definition.DefinitionProviderInterface;
import pav.sprykerFileCreator.model.finder.ClassFinderInterface;
import pav.sprykerFileCreator.model.generator.SprykerConstants;
import pav.sprykerFileCreator.model.renderer.dto.PhpClassInterface;

public class ClassResolver implements ClassResolverInterface {

    private String projectName;
    private String[] projectHierarchy;
    private ClassFinderInterface classFinder;
    private DefinitionProviderInterface definitionProvider;

    public ClassResolver(
            String projectName,
            String[] projectHierarchy,
            ClassFinderInterface classFinder,
            DefinitionProviderInterface definitionProvider
    ) {
        this.projectName = projectName;
        this.projectHierarchy = projectHierarchy;
        this.classFinder = classFinder;
        this.definitionProvider = definitionProvider;
    }

    @Override
    public PhpClassInterface resolveBundleClass(String classType, String bundleName) throws Exception {
        ClassDefinitionInterface classDefinition = this.definitionProvider.getDefinitionByType(classType);
        String classPattern = classDefinition.getNamespacePattern() + "\\" + classDefinition.getNamePattern();
        String fullQualifiedName = classPattern
                .replace(SprykerConstants.PROJECT_NAME_PLACEHOLDER, this.projectName)
                .replace(SprykerConstants.BUNDLE_NAME_PLACEHOLDER, bundleName);

        return this.resolveClass(fullQualifiedName, false, true);
    }

    @Override
    public PhpClassInterface resolveClassBelow(String fullQualifiedName) {
        return this.resolveClass(fullQualifiedName, true, false);
    }

    @Override
    public PhpClassInterface resolveClass(String fullQualifiedName, Boolean findClassBelow, Boolean findInterface) {

        for (String projectBelow : this.projectHierarchy) {

            if (this.projectName.equals(projectBelow) && findClassBelow) {
                continue;
            }

            String fullQualifiedCoreName = fullQualifiedName.replaceFirst(this.projectName, projectBelow);

            if (findInterface) {
                PhpClassInterface coreInterface = this.classFinder.findClass(fullQualifiedCoreName + "Interface");
                if (coreInterface != null) {
                    return coreInterface;
                }
            }

            PhpClassInterface coreClass = this.classFinder.findClass(fullQualifiedCoreName);
            if (coreClass != null) {
                return coreClass;
            }
        }

        return null;
    }

}
