package sprykerFileCreator.action.newGroup;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.NonEmptyInputValidator;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import pav.sprykerFileCreator.SprykerIcons;
import pav.sprykerFileCreator.model.generator.SprykerConstants;

import java.util.HashMap;

public class ZedControllerAction extends AbstractClassTypeAction {

    public ZedControllerAction() {
        super("Create Zed Controller", "Create Zed Controller", SprykerIcons.SPRYKER_ICON);
    }

    @NotNull
    @Override
    protected PsiElement[] invokeDialog(Project project, PsiDirectory psiDirectory) {
        HashMap<String,String> classConfig = new HashMap<>();

        String controllerName = Messages.showInputDialog(
                "Set Controller Name",
                "Input Controller Name",
                Messages.getQuestionIcon(),
                "",
                new NonEmptyInputValidator()
        );

        if (StringUtils.isBlank(controllerName)) {
            PsiElement[] psiElements = new PsiElement[1];

            return psiElements;
        }

        classConfig.put(SprykerConstants.CLASS_NAME, controllerName);

        return this.createClassType(project, psiDirectory, classConfig);
    }

    @NotNull
    @Override
    protected PsiElement[] create(String s, PsiDirectory psiDirectory) throws Exception {
        return new PsiElement[0];
    }

    @Override
    protected String getErrorTitle() {
        return null;
    }

    @Override
    protected String getCommandName() {
        return "Create Zed Controller";
    }

    @Override
    protected String getActionName(PsiDirectory psiDirectory, String s) {
        return "Create Zed Controller";
    }

    protected String getClassType() {
        return SprykerConstants.ZED_CONTROLLER;
    }

}
