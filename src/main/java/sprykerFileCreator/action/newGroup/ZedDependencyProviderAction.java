package pav.sprykerFileCreator.action.newGroup;

import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import pav.sprykerFileCreator.SprykerIcons;
import pav.sprykerFileCreator.model.generator.SprykerConstants;

public class ZedDependencyProviderAction extends AbstractClassTypeAction {

    public ZedDependencyProviderAction() {
        super("Create ZedDependencyProvider", "Create ZedDependencyProvider", SprykerIcons.SPRYKER_ICON);
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
        return "Create ZedDependencyProvider";
    }

    @Override
    protected String getActionName(PsiDirectory psiDirectory, String s) {
        return "Create ZedDependencyProvider";
    }

    protected String getClassType() {
        return SprykerConstants.ZED_DEPENDENCY_PROVIDER;
    }

}
