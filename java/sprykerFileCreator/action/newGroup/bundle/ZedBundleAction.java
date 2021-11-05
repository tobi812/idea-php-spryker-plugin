package sprykerFileCreator.action.newGroup.bundle;

import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import pav.sprykerFileCreator.SprykerIcons;
import pav.sprykerFileCreator.model.generator.SprykerConstants;

import java.util.ArrayList;
import java.util.HashMap;

public class ZedBundleAction extends AbstractBundleAction {

    protected ZedBundleAction() {
        super("Create Zed Bundle", "Create Zed Bundle", SprykerIcons.SPRYKER_ICON);
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
        return "Create Zed Bundle";
    }

    @Override
    protected String getActionName(PsiDirectory psiDirectory, String s) {
        return "Create Zed Bundle";
    }

    protected String getApplicationName() {
        return "Zed";
    }

    @Override
    ArrayList<String> getClassTypes() {
        ArrayList<String> classTypes = new ArrayList<String>();
        classTypes.add(SprykerConstants.ZED_DEPENDENCY_PROVIDER);
        classTypes.add(SprykerConstants.ZED_CONFIG);
        classTypes.add(SprykerConstants.ZED_PERSISTENCE_FACTORY);
        classTypes.add(SprykerConstants.ZED_QUERY_CONTAINER);
        classTypes.add(SprykerConstants.ZED_BUSINESS_FACTORY);
        classTypes.add(SprykerConstants.ZED_FACADE);
        classTypes.add(SprykerConstants.ZED_COMMUNICATION_FACTORY);

        return classTypes;
    }

}
