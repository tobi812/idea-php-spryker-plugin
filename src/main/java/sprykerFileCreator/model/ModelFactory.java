package pav.sprykerFileCreator.model;

import com.intellij.openapi.project.Project;
import pav.sprykerFileCreator.config.SprykerPluginConfig;
import pav.sprykerFileCreator.model.command.UpdateDocBlockCommand;
import pav.sprykerFileCreator.model.definition.DefinitionProvider;
import pav.sprykerFileCreator.model.definition.DefinitionProviderInterface;
import pav.sprykerFileCreator.model.finder.ClassFinder;
import pav.sprykerFileCreator.model.finder.ClassFinderInterface;
import pav.sprykerFileCreator.model.generator.*;
import pav.sprykerFileCreator.model.manager.ClassManager;
import pav.sprykerFileCreator.model.manager.ClassManagerInterface;
import pav.sprykerFileCreator.model.matcher.ClassTypeMatcher;
import pav.sprykerFileCreator.model.matcher.ClassTypeMatcherInterface;
import pav.sprykerFileCreator.model.renderer.PhpClassRenderer;
import pav.sprykerFileCreator.model.renderer.PhpClassRendererInterface;
import pav.sprykerFileCreator.model.resolver.ClassResolver;
import pav.sprykerFileCreator.model.resolver.ClassResolverInterface;
import pav.sprykerFileCreator.model.writer.FileWriter;
import pav.sprykerFileCreator.model.writer.FileWriterInterface;

public class ModelFactory {

    private final DefinitionProviderInterface definitionProvider;
    private ClassResolverInterface classResolver;

    public ModelFactory() {
        this.definitionProvider = this.createDefinitionProvider();
    }

    public ClassManagerInterface createClassManager(Project project, String projectName) {
        return new ClassManager(
                this.createClassGenerator(project, projectName),
                this.createClassRenderer(),
                this.createFileWriter(project)
        );
    }

    public ClassTypeMatcherInterface createClassTypeMatcher() {
        return new ClassTypeMatcher(this.definitionProvider);
    }

    public UpdateDocBlockCommand createUpdateDocBlockCommand(Project project, String projectName) {
        return new UpdateDocBlockCommand(
                this.createClassTypeMatcher(),
                this.createDocBlockGenerator(project, projectName),
                this.createClassRenderer()
        );
    }

    public DefinitionProviderInterface createDefinitionProvider() {
        return new DefinitionProvider();
    }

    private ClassResolverInterface createClassResolver(Project project, String projectName) {
        if (this.classResolver == null) {
            this.classResolver = new ClassResolver(
                    this.getConfig(project, projectName).getProjectName(),
                    this.getConfig(project, projectName).getCoreNames(),
                    this.createClassFinder(project),
                    this.definitionProvider
            );
        }

        return this.classResolver;
    }

    private ClassFinderInterface createClassFinder(Project project) {
        return new ClassFinder(project);
    }

    private ClassGeneratorInterface createClassGenerator(Project project, String projectName) {
        return new ClassGenerator(
                this.createParentGenerator(project, projectName),
                this.createDocBlockGenerator(project, projectName),
                this.createDefinitionProvider()
        );
    }

    private PhpClassRendererInterface createClassRenderer() {
        return new PhpClassRenderer();
    }

    private FileWriterInterface createFileWriter(Project project) {
        return new FileWriter(project);
    }

    private ParentGeneratorInterface createParentGenerator(Project project, String projectName) {
        return new ParentGenerator(
                this.createClassResolver(project, projectName),
                this.createClassFinder(project)
        );
    }

    private DocBlockGeneratorInterface createDocBlockGenerator(Project project, String projectName) {
        return new DocBlockGenerator(
                this.createClassResolver(project, projectName),
                this.definitionProvider
        );
    }

    private SprykerPluginConfig getConfig(Project project, String projectName) {
        return SprykerPluginConfig.getInstance(project, projectName);
    }

}
