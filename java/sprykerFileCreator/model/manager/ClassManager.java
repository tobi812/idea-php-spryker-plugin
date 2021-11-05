package sprykerFileCreator.model.manager;

import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import pav.sprykerFileCreator.model.renderer.PhpClassRendererInterface;
import pav.sprykerFileCreator.model.generator.ClassGeneratorInterface;
import pav.sprykerFileCreator.model.renderer.dto.PhpClassInterface;
import pav.sprykerFileCreator.model.writer.FileWriterInterface;
import java.util.HashMap;

public class ClassManager implements ClassManagerInterface {

    private ClassGeneratorInterface classGenerator;
    private PhpClassRendererInterface classRenderer;
    private FileWriterInterface fileWriter;

    public ClassManager(
            ClassGeneratorInterface classGenerator,
            PhpClassRendererInterface classRenderer,
            FileWriterInterface fileWriter
    ) {
        this.classGenerator = classGenerator;
        this.classRenderer = classRenderer;
        this.fileWriter = fileWriter;
    }

    @Override
    public PsiElement handleClass(PsiDirectory fileDirectory, String classType, HashMap<String, String> classConfig) throws Exception {
        PhpClassInterface phpClass = this.classGenerator.generateClass(classType, classConfig);
        String phpClassContent = this.classRenderer.renderPhpClass(phpClass);

        return this.writeFile(fileDirectory, phpClass.getName(), phpClassContent);
    }

    private PsiElement writeFile(PsiDirectory fileDirectory, String fileName, String phpClassContent) throws Exception{
        return this.fileWriter.writeFile(fileDirectory, fileName, phpClassContent);
    }

}
