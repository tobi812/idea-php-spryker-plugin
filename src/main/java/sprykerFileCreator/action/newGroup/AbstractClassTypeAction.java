package pav.sprykerFileCreator.action.newGroup;

import com.intellij.ide.IdeView;
import com.intellij.ide.actions.CreateElementActionBase;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import pav.sprykerFileCreator.model.ModelFactory;
import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.generator.SprykerConstants;
import pav.sprykerFileCreator.model.manager.ClassManagerInterface;
import pav.sprykerFileCreator.model.matcher.ClassTypeMatcherInterface;

import javax.swing.*;
import java.lang.instrument.ClassDefinition;
import java.util.HashMap;

abstract public class AbstractClassTypeAction extends CreateElementActionBase {

    private ModelFactory modelFactory;

    protected AbstractClassTypeAction(String text, String description, Icon icon) {
        super(text, description, icon);
    }

    @NotNull
    protected ModelFactory getModelFactory() {
        if (this.modelFactory == null) {
            this.modelFactory = new ModelFactory();
        }

        return this.modelFactory;
    }

    abstract protected String getClassType();

    @NotNull
    @Override
    protected PsiElement[] invokeDialog(Project project, PsiDirectory psiDirectory) {
        HashMap<String,String> classConfig = new HashMap<>();

        return this.createClassType(project, psiDirectory, classConfig);
    }

    @NotNull
    protected PsiElement[] createClassType(
            Project project,
            PsiDirectory psiDirectory,
            HashMap<String, String> classConfig
    ) {
        String classType = this.getClassType();

        PsiElement[] psiElements = new PsiElement[1];

        if (project == null) {
            return psiElements;
        }

        try {
            ClassTypeMatcherInterface classTypeMatcher = this.getModelFactory().createClassTypeMatcher();
            String projectName = classTypeMatcher.matchProjectName(classType, psiDirectory);
            ClassManagerInterface classManager = this.getModelFactory().createClassManager(project, projectName);
            classConfig.put(SprykerConstants.PROJECT_NAME, projectName);
            classConfig.put(SprykerConstants.BUNDLE_NAME, classTypeMatcher.matchBundleName(classType, psiDirectory));
            PsiElement psiElement = classManager.handleClass(psiDirectory, classType, classConfig);

            psiElements[0] = psiElement;

        } catch (Exception exception) {
            exception.printStackTrace();
            Messages.showErrorDialog(project, "Error:" + exception.getMessage(), "Error");
        }

        return psiElements;
    }

    @Override
    protected boolean isAvailable(DataContext dataContext) {
        IdeView view = (IdeView) LangDataKeys.IDE_VIEW.getData(dataContext);

        if (view == null) {
            return false;
        }

        PsiDirectory dir = view.getOrChooseDirectory();

        if (dir == null) {
            return false;
        }

        return this.classTypeMatchesDir(dir);
    }

    protected Boolean classTypeMatchesDir(PsiDirectory directory) {
        try {
            ModelFactory modelFactory = this.getModelFactory();
            String classType = this.getClassType();
            ClassTypeMatcherInterface classTypeMatcher = modelFactory.createClassTypeMatcher();

            if (!classTypeMatcher.classTypeMatchesDir(classType, directory)) {
                return false;
            }

            String bundleName = classTypeMatcher.matchBundleName(classType, directory);

            ClassDefinitionInterface classDefinition = this.getModelFactory()
                    .createDefinitionProvider()
                    .getDefinitionByType(classType);

            String className = classDefinition.
                    getNamePattern()
                    .replace(SprykerConstants.BUNDLE_NAME_PLACEHOLDER, bundleName);

            return directory.findFile(className + ".php") == null;

        } catch (Exception exception) {
            return false;
        }
    }
}
