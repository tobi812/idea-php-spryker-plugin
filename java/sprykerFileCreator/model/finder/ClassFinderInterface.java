package sprykerFileCreator.model.finder;

import pav.sprykerFileCreator.model.renderer.dto.PhpClassInterface;

public interface ClassFinderInterface {

    Boolean doesClassExist(String fullQualifiedName);

    PhpClassInterface findClass(String fullQualifiedCoreName);

}
