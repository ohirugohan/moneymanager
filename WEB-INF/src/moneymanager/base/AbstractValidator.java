package moneymanager.base;

import java.util.ArrayList;

import moneymanager.base.validation.ValidationError;
import moneymanager.base.validation.ValidationErrorType;

abstract public class AbstractValidator {

    private String paramName;
    private String value;
    private ArrayList<ValidationError> errors;

    public AbstractValidator(String paramName, String value){
        this.paramName = paramName;
        this.value = value;
    }

    public ArrayList<ValidationError> getErrors(){
        if(errors == null) {
            errors = new ArrayList<ValidationError>();
            this.validate(this.value);
        }
        return this.errors;
    }

    protected void addError(ValidationErrorType type) {
        this.errors.add(new ValidationError(this.paramName, type));
    }

    abstract protected void validate(String value);
}
