
package pav.sprykerFileCreator.model.renderer;

import com.intellij.openapi.util.io.StreamUtil;
import com.intellij.openapi.util.text.StringUtil;
import pav.sprykerFileCreator.model.renderer.dto.DocBlockItem;
import pav.sprykerFileCreator.model.renderer.dto.PhpClassInterface;
import pav.sprykerFileCreator.model.renderer.dto.UseBlockItem;

import java.io.IOException;
import java.util.List;

public class PhpClassRenderer implements PhpClassRendererInterface {

    private static final String NAMESPACE = "{{ namespace }}";
    private static final String USE_BLOCK = "{{ useBlock }}\n";
    private static final String DOC_BLOCK = "{{ docBlock }}\n";
    private static final String CLASS_NAME = "{{ className }}";
    private static final String PARENT_CLASS = "{{ parentClass }}";
    private static final String INTERFACE_BLOCK = "{{ interfaceBlock }}";
    private static final String METHOD_BLOCK = "{{ methodBlock }}";
    private static final String TEMPLATE_PATH = "template/PhpClassTemplate.php";


    @Override
    public String renderPhpClass(PhpClassInterface phpClass) {
        String content = this.readFile(TEMPLATE_PATH);

        if (content != null && !content.equals("")) {
            content = content.replace(NAMESPACE, phpClass.getNamespace());
            content = content.replace(USE_BLOCK, this.renderUseBlock(phpClass));
            content = content.replace(DOC_BLOCK, this.renderDocBlock(phpClass.getDocBlockItems()));
            content = content.replace(CLASS_NAME, phpClass.getName());
            content = content.replace(PARENT_CLASS, this.renderParent(phpClass));
            content = content.replace(INTERFACE_BLOCK, this.generateInterfaceBlock(phpClass));
            content = content.replace(METHOD_BLOCK, this.generateMethodBlock(phpClass));
        }

        return content;
    }

    public String renderDocBlock(List<DocBlockItem> docBlockItems) {

        if (docBlockItems.size() == 0) {
            return "";
        }

        String docBlock = "/**\n";

        for (DocBlockItem docBlockItem: docBlockItems) {
            String[] docBlockElements = {
                    docBlockItem.getTag(),
                    docBlockItem.getReturnType(),
                    docBlockItem.getValue(),
            };
            docBlock += " * @" + StringUtil.join(docBlockElements, " ") + "\n";
        }

        docBlock += " */\n";

        return docBlock;
    }


    private String renderUseBlock(PhpClassInterface phpClass) {

        List<UseBlockItem> useBlockItems = phpClass.getUseBlockItems();

        if (useBlockItems.size() == 0) {
            return "";
        }

        String useBlock = "";

        for (UseBlockItem useItem : phpClass.getUseBlockItems()) {
            useBlock += "use " + useItem.getFullQualifiedClassName();

            if (useItem.getAlias() != null) {
                useBlock += " as " + useItem.getAlias();
            }

            useBlock += ";\n";
        }

        return useBlock + "\n";
    }

    private String renderParent(PhpClassInterface phpClass) {
        PhpClassInterface parentClass = phpClass.getParentClass();

        if (parentClass == null) {
            return "";
        }

        String parentClassName = parentClass.getName();

        if (!phpClass.getParentAlias().equals("")) {
            parentClassName = phpClass.getParentAlias();
        }

        return " extends " + parentClassName;
    }

    private String generateMethodBlock(PhpClassInterface phpClass) {
        return "";
    }

    private String generateInterfaceBlock(PhpClassInterface phpClass) {
        return "";
    }

    /**
     *
     * @param path String
     * @return String
     */
    private String readFile(String path)
    {
        String content = "";

        try {
            content = StreamUtil.readText(
                    PhpClassRenderer.class.getResourceAsStream(path),
                    "UTF-8"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

}

