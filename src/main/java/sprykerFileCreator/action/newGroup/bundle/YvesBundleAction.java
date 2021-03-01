package pav.sprykerFileCreator.action.newGroup.bundle;

import com.intellij.ide.IdeView;
import com.intellij.ide.actions.CreateElementActionBase;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import pav.sprykerFileCreator.SprykerIcons;
import pav.sprykerFileCreator.model.ModelFactory;
import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.generator.SprykerConstants;
import pav.sprykerFileCreator.model.manager.ClassManagerInterface;
import pav.sprykerFileCreator.model.matcher.ClassTypeMatcherInterface;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class YvesBundleAction extends AbstractBundleAction {


    protected YvesBundleAction() {
        super("Create Yves Bundle", "Create Yves Bundle", SprykerIcons.SPRYKER_ICON);
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
        return "Create Yves Bundle";
    }

    @Override
    protected String getActionName(PsiDirectory psiDirectory, String s) {
        return "Create Yves Bundle";
    }

    protected String getApplicationName() {
        return "Yves";
    }

    @Override
    ArrayList<String> getClassTypes() {
        ArrayList<String> classTypes = new ArrayList<String>();
        classTypes.add(SprykerConstants.YVES_FACTORY);
        classTypes.add(SprykerConstants.YVES_DEPENDENCY_PROVIDER);

        return classTypes;
    }

}
