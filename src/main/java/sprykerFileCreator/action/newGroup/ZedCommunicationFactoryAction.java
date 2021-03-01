package pav.sprykerFileCreator.action.newGroup;

import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import pav.sprykerFileCreator.SprykerIcons;
import pav.sprykerFileCreator.model.generator.SprykerConstants;

public class ZedCommunicationFactoryAction extends AbstractClassTypeAction {

    public ZedCommunicationFactoryAction() {
        super("Create Zed CommunicationFactory", "Create Zed CommunicationFactory", SprykerIcons.SPRYKER_ICON);
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
        return "Create Zed Communication Factory";
    }

    @Override
    protected String getActionName(PsiDirectory psiDirectory, String s) {
        return "Create Zed Communication Factory";
    }

    protected String getClassType() {
        return SprykerConstants.ZED_COMMUNICATION_FACTORY;
    }

}
