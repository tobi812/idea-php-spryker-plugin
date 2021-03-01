package pav.sprykerFileCreator.ui.validator;

import pav.sprykerFileCreator.ui.SprykerFileCreatorForm;
import javax.xml.bind.ValidationException;

public interface FormValidatorInterface {

    public void validate(SprykerFileCreatorForm form) throws ValidationException;

}
