package sprykerFileCreator.action.newGroup;

import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import pav.sprykerFileCreator.SprykerIcons;
import pav.sprykerFileCreator.model.generator.SprykerConstants;

public class ZedPersistenceFactoryAction extends AbstractClassTypeAction {

    public ZedPersistenceFactoryAction() {
        super("Create Zed PersistenceFactory", "Create Zed PersistenceFactory", SprykerIcons.SPRYKER_ICON);
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
        return "Create Zed Persistence Factory";
    }

    @Override
    protected String getActionName(PsiDirectory psiDirectory, String s) {
        return "Create Zed Persistence Factory";
    }

    protected String getClassType() {
        return SprykerConstants.ZED_PERSISTENCE_FACTORY;
    }

}
