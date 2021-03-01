package pav.sprykerFileCreator.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;

import java.util.ArrayList;

//@State(
//        name="SprykerPluginConfig",
//        storages = {
//                @Storage("SprykerPluginConfig.xml")}
//)

public class SprykerPluginConfig //implements PersistentStateComponent<SprykerPluginConfig>
{

    private String projectName = "Pyz";
    private static String[] coreNames = { "Pyz", "Pav", "Spryker" };
    private String basePath = "src";

    @Nullable
//    @Override
    public SprykerPluginConfig getState() {
        return this;
    }

//    @Override
    public void loadState(SprykerPluginConfig config) {
        XmlSerializerUtil.copyBean(config, this);
    }

    @Nullable
    public static SprykerPluginConfig getInstance(Project project, String projectName) {
        ArrayList<String> slicedCoreNames = new ArrayList<>();
        Boolean matched = false;

        for (String coreName : coreNames) {
            if (coreName.equals(projectName)) {
                matched = true;
            }

            if (matched) {
                slicedCoreNames.add(coreName);
            }
        }

        coreNames = slicedCoreNames.toArray(new String[0]);

        return ServiceManager.getService(project, SprykerPluginConfig.class);
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String[] getCoreNames() {
        return coreNames;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
