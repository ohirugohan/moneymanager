package moneymanager.controller.validator;

public class StringValidator extends AbstractValidator {

    private int maxLength = 0;
    private int minLength = 0;

    public StringValidator(String paramName, String value) {
        super(paramName, value);
    }

    /**
     *
     */
    public StringValidator(String paramName, String value, int maxLength) {
        this(paramName, value);
        this.maxLength = maxLength;
    }

    public StringValidator(String paramName, String value, int minLength, int maxLength) {
        this(paramName, value, maxLength);
        this.minLength = minLength;
    }

    protected void validate(String value){
        // 空文字判定
        if (value == null) {
            this.addError(ValidationErrorType.Empty);
            return;
        }

        // 文字列長判定
        if (value.length() < this.minLength) {
            this.addError(ValidationErrorType.TooShort);
            return;
        }

        if (0 < this.maxLength && this.maxLength < value.length()) {
            this.addError(ValidationErrorType.TooLong);
            return;
        }
    }
}
