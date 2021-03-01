package pav.sprykerFileCreator.model.command;

import com.intellij.openapi.project.Project;
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment;
import com.jetbrains.php.lang.psi.PhpPsiElementFactory;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.generator.DocBlockGeneratorInterface;
import pav.sprykerFileCreator.model.matcher.ClassTypeMatcherInterface;
import pav.sprykerFileCreator.model.renderer.PhpClassRendererInterface;
import pav.sprykerFileCreator.model.renderer.dto.DocBlockItem;

import java.util.List;

public class UpdateDocBlockCommand {

    private ClassTypeMatcherInterface classTypeMatcher;
    private final DocBlockGeneratorInterface docBlockGenerator;
    private final PhpClassRendererInterface phpRenderer;

    public UpdateDocBlockCommand(
            ClassTypeMatcherInterface classTypeMatcher,
            DocBlockGeneratorInterface docBlockGenerator,
            PhpClassRendererInterface phpRenderer
    ) {
        this.classTypeMatcher = classTypeMatcher;
        this.docBlockGenerator = docBlockGenerator;
        this.phpRenderer = phpRenderer;
    }

    public void updateDocBlock(PhpClass phpClass, Project project) throws Exception {
        String fqClassName = phpClass.getFQN();

        ClassDefinitionInterface classDefinition = this.classTypeMatcher.matchClassType(fqClassName);
        String bundleName = this.classTypeMatcher.matchBundleNameFromFQName(classDefinition, fqClassName);

        PhpDocComment oldComment = phpClass.getDocComment();


        List<DocBlockItem> docBlockItems = docBlockGenerator.getDocBlockItems(classDefinition.getDocBlockClasses(), bundleName);

        PhpDocComment comment = PhpPsiElementFactory.createFromText(
                project,
                PhpDocComment.class,
                this.phpRenderer.renderDocBlock(docBlockItems)
        );

        if (comment == null) {
            return;
        }

        if (oldComment == null) {
            phpClass.addBefore(comment, phpClass.getFirstChild());
        } else {
            oldComment.replace(comment);
        }

    }
}
