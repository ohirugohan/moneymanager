package moneymanager.controller.validator;

public class ValidationError {

    private String paramName;
    private ValidationErrorType type;

    public ValidationError(String paramName, ValidationErrorType type) {
        this.paramName = paramName;
        this.type = type;
    }

    public String getParamName(){
        return this.paramName;
    }

    public ValidationErrorType getType(){
        return this.type;
    }
}
