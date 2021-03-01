package sprykerFileCreator.ui.validator;

import pav.sprykerFileCreator.ui.ClassSelectionItem;
import pav.sprykerFileCreator.ui.SprykerFileCreatorForm;

import javax.xml.bind.ValidationException;

public class FormValidator implements FormValidatorInterface {

    public void validate(SprykerFileCreatorForm form) throws ValidationException {
        this.validateFileSelectionItems(form);
        this.validateBundleName(form);
    }

    private void validateFileSelectionItems(SprykerFileCreatorForm form) throws ValidationException {
        for (ClassSelectionItem fileSelectionItem : form.getClassSelectionItems()) {
            if (fileSelectionItem.isSelected()) {
                return;
            }
        }

        throw new ValidationException("blub");
    }

    private void validateBundleName(SprykerFileCreatorForm form) throws ValidationException {
        if (form.getBundleName().isEmpty()) {
            throw new ValidationException("blub");
        }
    }

}
