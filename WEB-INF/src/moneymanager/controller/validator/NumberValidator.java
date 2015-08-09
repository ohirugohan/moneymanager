package moneymanager.controller.validator;

/**
 * 整数値のバリデーター
 *
 */
public class NumberValidator extends AbstractValidator{

    private Integer max;
    private Integer min;

    public NumberValidator(String paramName, String value){
        super(paramName, value);
    }

    public NumberValidator(String paramName, String value, int min){
        this(paramName, value);
        this.min = new Integer(min);
    }

    public NumberValidator(String paramName, String value, int min, int max){
        this(paramName, value);
        this.min = new Integer(min);
        this.max = new Integer(max);
    }

    protected void validate(String value){
        // 空文字判定
        if (value == null) {
            this.addError(ValidationErrorType.Empty);
            return;
        }

        // 数字であるかどうか判定
        int numberValue;
        try {
            numberValue = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            this.addError(ValidationErrorType.WrongFormat);
            return;
        }

        // 最小値確認
        if(this.min != null) {
            min = this.min.intValue();
            if(numberValue < min) {
                this.addError(ValidationErrorType.TooSmall);
                return;
            }
        }

        // 最大値確認
        if(this.max != null) {
            max = this.max.intValue();
            if(max < numberValue) {
                this.addError(ValidationErrorType.TooLarge);
                return;
            }
        }
    }
}
