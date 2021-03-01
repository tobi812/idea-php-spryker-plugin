package sprykerFileCreator.action;

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import com.jetbrains.php.lang.psi.PhpPsiUtil;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.jetbrains.annotations.NotNull;
import pav.sprykerFileCreator.config.SprykerPluginConfig;
import pav.sprykerFileCreator.model.ModelFactory;
import pav.sprykerFileCreator.model.command.UpdateDocBlockCommand;
import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.matcher.ClassTypeMatcherInterface;

public class SprykerUpdateClassDocBlockIntention extends PsiElementBaseIntentionAction {

    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) throws IncorrectOperationException {
        PhpClass phpClass = PhpPsiUtil.getParentByCondition(psiElement, PhpClass.INSTANCEOF);

        if (phpClass == null) {
            return;
        }

        CommandProcessor.getInstance().executeCommand(project, () -> {
            ApplicationManager.getApplication().runWriteAction(() -> {
                ModelFactory modelFactory = new ModelFactory();
                ClassTypeMatcherInterface classTypeMatcher = modelFactory.createClassTypeMatcher();
                String projectName = classTypeMatcher.matchProjectName(phpClass.getFQN());
                SprykerPluginConfig.getInstance(project, projectName);
                UpdateDocBlockCommand command = modelFactory.createUpdateDocBlockCommand(project, projectName);
                try {
                    command.updateDocBlock(phpClass, project);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
        }, "Add Spryker DocBlock", null);

    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) {

        PhpClass phpClass = PsiTreeUtil.getParentOfType(psiElement, PhpClass.class);
        if(phpClass == null) {
            return false;
        }

        ModelFactory modelFactory = new ModelFactory();
        ClassTypeMatcherInterface classTypeMatcher = modelFactory.createClassTypeMatcher();
        ClassDefinitionInterface classDefinition = classTypeMatcher.matchClassType(phpClass.getFQN());

        return classDefinition != null;
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return "Spryker";
    }

    @NotNull
    @Override
    public String getText() {
        return "Update Spryker DocBlock";
    }

}
