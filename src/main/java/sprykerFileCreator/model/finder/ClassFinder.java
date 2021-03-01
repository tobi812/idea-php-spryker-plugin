package sprykerFileCreator.model.finder;

import com.intellij.openapi.project.Project;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.jetbrains.annotations.NotNull;
import pav.sprykerFileCreator.model.renderer.dto.PhpClassInterface;
import pav.sprykerFileCreator.model.renderer.dto.PhpClassItem;

import java.util.ArrayList;
import java.util.Collection;

public class ClassFinder implements ClassFinderInterface {
    private Project project;

    public ClassFinder(@NotNull Project project) {
        this.project = project;
    }

    @Override
    public Boolean doesClassExist(String fullQualifiedName) {
        Collection<PhpClass> phpClassCollection = this.getPhpClassCollection(fullQualifiedName);

        return phpClassCollection.size() > 0;
    }

    @Override
    public PhpClassInterface findClass(String fullQualifiedName) {
        Collection<PhpClass> phpClassCollection = this.getPhpClassCollection(fullQualifiedName);

        if (phpClassCollection.size() > 0) {
            PhpClass phpClass = phpClassCollection.iterator().next();
            String parentName = phpClass.getName();
            String namespace = phpClass.getNamespaceName();

            return new PhpClassItem(namespace, parentName);
        }

        return null;
    }

    @NotNull
    private Collection<PhpClass> getPhpClassCollection(String fullQualifiedName) {
        try {
            PhpIndex phpIndex = PhpIndex.getInstance(this.project);
            return phpIndex.getAnyByFQN(fullQualifiedName);
        } catch (IllegalStateException exception) {
            return new ArrayList<>();
        }
    }

}
