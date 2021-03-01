package sprykerFileCreator.model.renderer.dto;

import com.intellij.openapi.util.text.StringUtil;
import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PhpClassItem implements PhpClassInterface {

    private String namespace;
    private String name;
    private Boolean isAbstract;
    private PhpClassInterface parentClass;
    private String parentAlias = "";
    private List<UseBlockItem> useBlockItems;
    private List<DocBlockItem> docBlockItems;

    public PhpClassItem(String namespace, String className) {
        this.namespace = namespace;
        this.name = className;
        this.useBlockItems = new ArrayList<UseBlockItem>();
        this.docBlockItems = new ArrayList<DocBlockItem>();
    }

    @Override
    public String getNamespace() {
        return this.namespace;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getFullQualifiedName() {

        return StringUtil.trimEnd(this.namespace, "\\") + "\\" + this.name;
    }

    @Override
    public Boolean isAbstract() {
        return this.isAbstract;
    }

    @Override
    public void setAbstract() {
        this.isAbstract = true;
    }

    @Override
    public PhpClassInterface getParentClass() {
        return this.parentClass;
    }

    @Override
    public void setParentClass(PhpClassInterface parentClass) {
        this.setParentClass(parentClass, "Core");
    }

    @Override
    public void setParentClass(PhpClassInterface parentClass, String aliasPrefix) {
        String parentClassName = parentClass.getName();
        String parentNamespace = parentClass.getNamespace();
        parentNamespace = StringUtil.trimStart(parentNamespace, "\\");
        UseBlockItem parentUseItem = new UseBlockItem(parentClassName, parentNamespace);

        if (parentClassName.equals(this.name)) {
//            if (parentNamespace.equals(this.namespace)) {
//                throw new Exception();
//            }
            this.parentAlias = aliasPrefix + parentClassName;
            parentUseItem.setAlias(this.parentAlias);
        }

        this.addUseBlockItem(parentUseItem);
        this.parentClass = parentClass;
    }

    @Override
    public List<UseBlockItem> getUseBlockItems() {
        return this.useBlockItems;
    }

    @Override
    public void addUseBlockItem(UseBlockItem useBlockItem) {
        this.useBlockItems.add(useBlockItem);
    }

    @Override
    public List<DocBlockItem> getDocBlockItems() {
        return this.docBlockItems;
    }

    @Override
    public void addDocBlockItem(DocBlockItem docBlockItem) {
        this.docBlockItems.add(docBlockItem);
    }

    @Override
    public String getParentAlias() {
        return this.parentAlias;
    }

}
