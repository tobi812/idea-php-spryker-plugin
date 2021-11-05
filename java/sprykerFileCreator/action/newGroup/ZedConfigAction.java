package sprykerFileCreator.action.newGroup;

import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import pav.sprykerFileCreator.SprykerIcons;
import pav.sprykerFileCreator.model.generator.SprykerConstants;

public class ZedConfigAction extends AbstractClassTypeAction {

    public ZedConfigAction() {
        super("Create Zed Config", "Create Zed Config", SprykerIcons.SPRYKER_ICON);
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
        return "Create Zed Config";
    }

    @Override
    protected String getActionName(PsiDirectory psiDirectory, String s) {
        return "Create Zed Config";
    }

    protected String getClassType() {
        return SprykerConstants.ZED_CONFIG;
    }

}
