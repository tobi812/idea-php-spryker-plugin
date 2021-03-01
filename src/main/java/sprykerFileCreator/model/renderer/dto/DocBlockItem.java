package pav.sprykerFileCreator.model.renderer.dto;

public class DocBlockItem {
    private String tag;
    private String returnType;
    private String value;

    public DocBlockItem(String tag, String returnType, String value) {
        this.tag = tag;
        this.returnType = returnType;
        this.value = value;
    }

    public String getReturnType() {
        return returnType;
    }

    public String getValue() {
        return value;
    }

    public String getTag() {

        return tag;
    }
}
