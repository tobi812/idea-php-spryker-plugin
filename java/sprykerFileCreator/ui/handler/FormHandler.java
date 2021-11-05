package sprykerFileCreator.ui.handler;

import pav.sprykerFileCreator.model.generator.SprykerConstants;
import pav.sprykerFileCreator.model.manager.ClassManagerInterface;
import pav.sprykerFileCreator.ui.ClassSelectionItem;
import pav.sprykerFileCreator.ui.SprykerFileCreatorForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FormHandler implements FormHandlerInterface {

    private ClassManagerInterface classManager;

    public FormHandler(ClassManagerInterface classManager) {
        this.classManager = classManager;
    }

    public Boolean handle(SprykerFileCreatorForm form) {

        String bundleName = form.getBundleName();
        List<ClassSelectionItem> classSelectionItems = form.getClassSelectionItems();
        List<ClassSelectionItem> selectedItems = this.extractSelectedItems(classSelectionItems);
//        selectedItems = this.sortSelectedItems();

        for (ClassSelectionItem selectedItem : selectedItems) {
            HashMap<String, String> fileConfig = this.getClassConfig(bundleName, selectedItem);

//            Boolean result = this.classManager.handleClass(selectedItem.getClassType(), fileConfig);
//            System.out.print(result);
        }

        return true;
    }

    private List<ClassSelectionItem> extractSelectedItems(List<ClassSelectionItem> classSelectionItems) {
        List<ClassSelectionItem> selectedItems = new ArrayList<ClassSelectionItem>();

        for (ClassSelectionItem classSelectionItem : classSelectionItems) {
            if (classSelectionItem.isSelected()) {
                selectedItems.add(classSelectionItem);
            }
        }

        return selectedItems;
    }

    private HashMap<String,String> getClassConfig(String bundleName, ClassSelectionItem classSelectionItem) {
        HashMap<String,String> classConfig = new HashMap<>();

        classConfig.put(SprykerConstants.PROJECT_NAME, "Pyz");
        classConfig.put(SprykerConstants.BUNDLE_NAME, bundleName);

        if (classSelectionItem.isClassNameEditable()) {
            String className = classSelectionItem.getClassName();
            classConfig.put(SprykerConstants.CLASS_NAME, className);
        }

        return classConfig;
    }

}
