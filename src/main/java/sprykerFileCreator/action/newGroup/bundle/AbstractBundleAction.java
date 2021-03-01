package sprykerFileCreator.action.newGroup.bundle;

import com.intellij.ide.IdeView;
import com.intellij.ide.actions.CreateElementActionBase;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.NonEmptyInputValidator;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.FileContentUtil;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.PhpFile;
import com.jetbrains.php.lang.psi.PhpPsiUtil;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import pav.sprykerFileCreator.config.SprykerPluginConfig;
import pav.sprykerFileCreator.model.ModelFactory;
import pav.sprykerFileCreator.model.command.UpdateDocBlockCommand;
import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.definition.DefinitionProviderInterface;
import pav.sprykerFileCreator.model.generator.SprykerConstants;
import pav.sprykerFileCreator.model.manager.ClassManagerInterface;
import pav.sprykerFileCreator.model.matcher.ClassTypeMatcherInterface;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

abstract public class AbstractBundleAction extends CreateElementActionBase {

    protected AbstractBundleAction(String text, String description, Icon icon) {
        super(text, description, icon);
    }

    private ModelFactory modelFactory;

    private Project project;

    abstract String getApplicationName();

    abstract ArrayList<String> getClassTypes();


    @NotNull
    protected ModelFactory getModelFactory() {
        if (this.modelFactory == null) {
            this.modelFactory = new ModelFactory();
        }

        return this.modelFactory;
    }

    @NotNull
    @Override
    protected PsiElement[] invokeDialog(Project project, PsiDirectory psiDirectory) {
        HashMap<String,String> classConfig = new HashMap<>();
        PsiElement[] psiElements = new PsiElement[1];

        String bundleName = Messages.showInputDialog(
                "Set Bundle Name",
                "Input Bundle Name",
                Messages.getQuestionIcon(),
                "",
                new NonEmptyInputValidator()
        );

        if (StringUtils.isBlank(bundleName)) {
            return psiElements;
        }

        this.project = project;

        if (psiDirectory.findSubdirectory(bundleName) != null) {
            Messages.showErrorDialog(project, "Bundle " + bundleName + " already exists!", "Error");
            return psiElements;
        }

        try {
            PsiDirectory bundleDirectory = this.createSubdirectory(psiDirectory, bundleName);
            String projectName = this.matchProjectName(psiDirectory);
            classConfig.put(SprykerConstants.BUNDLE_NAME, bundleName);
            classConfig.put(SprykerConstants.PROJECT_NAME, projectName);

            psiElements = this.createBundleClasses(project, bundleDirectory, classConfig, psiElements);
        } catch (Exception exception) {
            exception.printStackTrace();
            Messages.showErrorDialog(project, "Error:" + exception.getMessage(), "Error");
        }

        return psiElements;
    }

    private String matchProjectName(PsiDirectory psiDirectory) throws Exception {
        PsiDirectory projectDir = psiDirectory.getParent();

        if (projectDir == null) {
            throw new Exception();
        }

        return projectDir.getName();
    }

    @NotNull
    protected PsiElement[] createBundleClasses(
            Project project,
            PsiDirectory bundleDirectory,
            HashMap<String, String> classConfig,
            PsiElement[] psiElements
    ) throws Exception {
        String projectName = classConfig.get(SprykerConstants.PROJECT_NAME);
        ClassManagerInterface classManager = this.getModelFactory().createClassManager(project, projectName);
        DefinitionProviderInterface definitionProvider = this.getModelFactory().createDefinitionProvider();

        for (String classType: this.getClassTypes()) {
            ClassDefinitionInterface classDefinition = definitionProvider.getDefinitionByType(classType);
            PsiDirectory classDirectory = this.resolveClassDirectory(classDefinition, bundleDirectory);

            classManager.handleClass(classDirectory, classType, classConfig);
        }

        return psiElements;
    }

    protected PsiDirectory resolveClassDirectory(ClassDefinitionInterface classDirectory, PsiDirectory bundleDirectory) throws Exception {
        String namespacePattern = classDirectory.getNamespacePattern();
        namespacePattern = namespacePattern.replace(SprykerConstants.BUNDLE_NAME_PLACEHOLDER, "bundleName");
        String[] namespaceSplit = namespacePattern.split("\\\\bundleName\\\\");

        if (namespaceSplit.length <= 1) {
            return bundleDirectory;
        }

        String[] subDirectoryNames = namespaceSplit[namespaceSplit.length - 1].split("\\\\");

        PsiDirectory currentDirectory = bundleDirectory;
        for (String subDirectoryName : subDirectoryNames) {
            PsiDirectory subDirectory = currentDirectory.findSubdirectory(subDirectoryName);

            if (subDirectory == null) {
                subDirectory = this.createSubdirectory(currentDirectory, subDirectoryName);
            }

            currentDirectory = subDirectory;
        }

        return currentDirectory;
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

        if (!dir.getName().equals(this.getApplicationName())) {
            return false;
        }

        PsiDirectory projectDir = dir.getParent();

        if (projectDir == null) {
            return false;
        }

        PsiDirectory srcDir = projectDir.getParent();

        return srcDir != null && srcDir.getName().equals("src");
    }

    protected PsiDirectory createSubdirectory(PsiDirectory fileDirectory, String subDirectoryName) throws Exception {
        new WriteCommandAction(this.project) {

            @Override
            protected void run(@NotNull Result result) throws Throwable {
                fileDirectory.createSubdirectory(subDirectoryName);
            }

            @Override
            public String getGroupID() {
                return "Create Command";
            }

        }.execute();

        return fileDirectory.findSubdirectory(subDirectoryName);
    }

}
