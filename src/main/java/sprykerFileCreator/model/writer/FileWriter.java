package pav.sprykerFileCreator.model.writer;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.jetbrains.php.lang.PhpFileType;
import org.jetbrains.annotations.NotNull;

public class FileWriter implements FileWriterInterface {

    private  Project project;

    public FileWriter(Project project) {
        this.project = project;
    }

    @Override
    public PsiElement writeFile(PsiDirectory fileDirectory, String fileName, String phpClassContent) throws Exception {
        PsiFile existingFile = fileDirectory.findFile(fileName + ".php");

        if (existingFile != null) {
            throw new Exception(fileName + " does already exist.");
        }

        PsiFile file = PsiFileFactory
                .getInstance(project)
                .createFileFromText(fileName + ".php", PhpFileType.INSTANCE, phpClassContent);


        new WriteCommandAction(this.project) {

            @Override
            protected void run(@NotNull Result result) throws Throwable {
                fileDirectory.add(file);

                PsiFile virtualFile = fileDirectory.findFile(fileName + ".php");
                if (virtualFile != null) {
                    new OpenFileDescriptor(project, virtualFile.getVirtualFile()).navigate(true);
                }
            }

            @Override
            public String getGroupID() {
                return "Create Command";
            }

        }.execute();

        return file;
    }

}
