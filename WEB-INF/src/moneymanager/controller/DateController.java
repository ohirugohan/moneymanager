package moneymanager.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import moneymanager.base.AbstractController;
import moneymanager.base.validation.ValidationError;
import moneymanager.bean.ExpenseBean;
import moneymanager.bean.ExpenseWithCategoryBean;
import moneymanager.bean.SubCategoryBean;
import moneymanager.bean.UserCategoryBean;
import moneymanager.controller.validator.NumberValidator;
import moneymanager.controller.validator.StringValidator;
import moneymanager.service.ExpenseService;
import moneymanager.service.MyCategoryService;
import moneymanager.service.SubCategoryService;


public class DateController extends AbstractController {

    @Override
    protected String getTemplateName() {
        // TODO 自動生成されたメソッド・スタブ
        return "date";
    }

    @Override
    protected void action() {
        int user_id = this.getUserId();

        // 表示する日付を取得
        Date date;
        if(this.getParameter("target") != null) {
            try {
                date = Date.valueOf(this.getParameter("target"));
            } catch (IllegalArgumentException e) {
                date = new Date(System.currentTimeMillis());
            }
        } else {
            date = new Date(System.currentTimeMillis());
        }

        if(this.getParamaterMethod() == ParameterMethod.POST) {
            this.registerNewCategory();
        }

        // 家計簿データを取得
        ExpenseService service = new ExpenseService();
        List<ExpenseBean> expenses = service.get(user_id, date);

        // カテゴリ情報を取得
        MyCategoryService parent_category_service = new MyCategoryService();
        SubCategoryService sub_category_service = new SubCategoryService();
        List<UserCategoryBean> categories = parent_category_service.get(user_id);
        Map<Integer, List<SubCategoryBean>> subcategories = sub_category_service.get(user_id);

        // 家計簿データにカテゴリ情報を付加
        List<ExpenseWithCategoryBean> expenses_full = service.getFullExpenses(expenses, categories, subcategories);

        this.assign("expenses", expenses_full);
        this.assign("date", date.toString());
        this.assign("mycategories", categories);
        this.assign("subcategories", subcategories);
    }

    private void registerNewCategory() {
        int user_id = this.getUserId();
        String name = this.getParameter("name");
        String value_str = this.getParameter("value");
        String parent_category_id_str = this.getParameter("parent_category");
        String sub_category_id_str = this.getParameter("sub_category");
        String date_str = this.getParameter("date");

        if (this.isValidParameter(name, value_str, parent_category_id_str, sub_category_id_str, date_str)) {
            ExpenseService service = new ExpenseService();
            if (service.register(user_id, value_str, parent_category_id_str, sub_category_id_str, date_str, name)) {
                this.debug("登録に成功しました。");
            }
        }
    }

    private boolean isValidParameter(String name, String value_str, String parent_category_id_str, String sub_category_id_str, String date) {
        StringValidator name_validator = new StringValidator("name", name, 1, 64);
        ArrayList<ValidationError> name_errors = name_validator.getErrors();
        NumberValidator value_validator = new NumberValidator("value", value_str, 1);
        ArrayList<ValidationError> value_errors = value_validator.getErrors();
        NumberValidator parent_category_validator = new NumberValidator("category_id", parent_category_id_str, 0);
        ArrayList<ValidationError> parent_category_errors = parent_category_validator.getErrors();
        NumberValidator sub_category_validator = new NumberValidator("subcategory_id", sub_category_id_str, 0);
        ArrayList<ValidationError> sub_category_errors = sub_category_validator.getErrors();
        StringValidator date_validator = new StringValidator("date", date, 10, 10);
        ArrayList<ValidationError> date_errors = date_validator.getErrors();

        if (name_errors.isEmpty() && value_errors.isEmpty() && parent_category_errors.isEmpty() && sub_category_errors.isEmpty() && date_errors.isEmpty()) {
            return true;
        } else {
            this.debug("バリデーションエラーです。（詳細は別途実装）");
        }

        return false;
    }

}
