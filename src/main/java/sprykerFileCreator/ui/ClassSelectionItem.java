package sprykerFileCreator.ui;

public class ClassSelectionItem {
    private Boolean isClassNameEditable;
    private Boolean isSelected;
    private String classTypeLabel;
    private String className;
    private String classType;

    public ClassSelectionItem(
            Boolean isSelected,
            String classTypeLabel,
            Boolean hasClassName,
            String classType) {
        this(isSelected, classTypeLabel, hasClassName, classType, "");
    }

    public ClassSelectionItem(
            Boolean isSelected,
            String classTypeLabel,
            Boolean isClassNameEditable,
            String classType,
            String className
    ) {
        this.isSelected = isSelected;
        this.classType = classType;
        this.classTypeLabel = classTypeLabel;
        this.className = className;
        this.isClassNameEditable = isClassNameEditable;
    }

    public Boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(Boolean selected) {
        this.isSelected = selected;
    }

    public String getClassTypeLabel() {
        return this.classTypeLabel;
    }

    public Boolean isClassNameEditable() {
        return this.isClassNameEditable;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassType() {
        return this.classType;
    }

}
