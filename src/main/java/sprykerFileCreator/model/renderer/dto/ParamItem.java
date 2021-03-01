package pav.sprykerFileCreator.model.renderer.dto;

public class ParamItem {
    private String paramName;
    private String paramType;

    public ParamItem(String paramName, String paramType) {
        this.paramName = paramName;
        this.paramType = paramType;
    }

    public String getParamName() {
        return paramName;
    }

    public String getParamType() {
        return paramType;
    }
}
