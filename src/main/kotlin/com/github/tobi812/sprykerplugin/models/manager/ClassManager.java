package com.github.tobi812.sprykerplugin.models.manager;

import com.intellij.psi.PsiElement;
import pav.sprykerFileCreator.model.renderer.PhpClassRendererInterface;
import pav.sprykerFileCreator.model.generator.ClassGeneratorInterface;
import pav.sprykerFileCreator.model.renderer.dto.PhpClassInterface;
import pav.sprykerFileCreator.model.resolver.PathResolverInterface;
import pav.sprykerFileCreator.model.writer.FileWriterInterface;
import java.util.HashMap;

public class ClassManager implements ClassManagerInterface {

    private ClassGeneratorInterface classGenerator;
    private PhpClassRendererInterface classRenderer;
    private PathResolverInterface pathResolver;
    private FileWriterInterface fileWriter;

    public ClassManager(
            ClassGeneratorInterface classGenerator,
            PhpClassRendererInterface classRenderer,
            PathResolverInterface pathResolver,
            FileWriterInterface fileWriter
    ) {
        this.classGenerator = classGenerator;
        this.classRenderer = classRenderer;
        this.pathResolver = pathResolver;
        this.fileWriter = fileWriter;
    }

    @Override
    public PsiElement handleClass(String classType, HashMap<String, String> classConfig) throws Exception {
        PhpClassInterface phpClass = this.classGenerator.generateClass(classType, classConfig);
        String filePath = this.pathResolver.resolvePath(phpClass.getNamespace());
        String phpClassContent = this.classRenderer.renderPhpClass(phpClass);

        return this.writeFile(filePath, phpClass.getName(), phpClassContent);
    }

    private PsiElement writeFile(String filePath, String fileName, String phpClassContent) throws Exception{
        return this.fileWriter.writeFile(filePath, fileName, phpClassContent);
    }

}
