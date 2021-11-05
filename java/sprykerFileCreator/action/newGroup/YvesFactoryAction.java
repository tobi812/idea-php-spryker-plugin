package sprykerFileCreator.action.newGroup;

import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import pav.sprykerFileCreator.SprykerIcons;
import pav.sprykerFileCreator.model.generator.SprykerConstants;

public class YvesFactoryAction extends AbstractClassTypeAction {

    public YvesFactoryAction() {
        super("Create Yves CommunicationFactory", "Create Yves CommunicationFactory", SprykerIcons.SPRYKER_ICON);
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
        return "Create Yves Factory";
    }

    @Override
    protected String getActionName(PsiDirectory psiDirectory, String s) {
        return "Create Yves Factory";
    }

    protected String getClassType() {
        return SprykerConstants.YVES_FACTORY;
    }

}
