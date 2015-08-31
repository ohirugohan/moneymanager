package moneymanager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import moneymanager.bean.SubCategoryBean;
import moneymanager.bean.UserCategoryBean;
import moneymanager.controller.validator.NumberValidator;
import moneymanager.controller.validator.StringValidator;
import moneymanager.controller.validator.ValidationError;
import moneymanager.service.MyCategoryService;
import moneymanager.service.SubCategoryService;

public class SubCategoryController extends AbstractController {

    @Override
    protected String getTemplateName() {
        return "subcategory";
    }

    @Override
    protected void action() {
        if(this.getParamaterMethod() == ParameterMethod.POST) {
            this.registerNewCategory();
        }


        int user_id = this.getUserId();
        MyCategoryService parent_service = new MyCategoryService();
        SubCategoryService sub_service = new SubCategoryService();

        List<UserCategoryBean> mycategories = parent_service.get(user_id);
        this.assign("mycategories", mycategories);

        Map<Integer, List<SubCategoryBean>> subcategories = sub_service.get(user_id);
        this.assign("subcategories", subcategories);
    }

    private void registerNewCategory() {
        int user_id = this.getUserId();
        String category_name = this.getParameter("name");
        String parent_category_id_str = this.getParameter("category_id");

        if (this.isValidParameter(category_name, parent_category_id_str)) {
            int parent_category_id = Integer.parseInt(parent_category_id_str);
            SubCategoryService service = new SubCategoryService();

            if(service.register(user_id, parent_category_id, category_name)) {
                this.debug("新規カテゴリの登録に成功しました。");
            }
        }
    }

    private boolean isValidParameter(String category_name, String parent_category_id_str) {

        StringValidator name_validator = new StringValidator("name", category_name, 1, 16);
        ArrayList<ValidationError> name_errors = name_validator.getErrors();
        NumberValidator category_validator = new NumberValidator("category_id", parent_category_id_str, 1);
        ArrayList<ValidationError> category_errors = category_validator.getErrors();

        if (name_errors.isEmpty() && category_errors.isEmpty()) {
            return true;
        } else {
            if (!name_errors.isEmpty()) {
                for (ValidationError error: name_errors) {
                    switch (error.getType().toString()) {
                    case "TooLong":
                        this.debug("カテゴリー名は16文字以内で入力してください。");
                        break;
                    case "TooShort":
                        this.debug("カテゴリー名を入力してください。");
                        break;
                    default:
                        break;
                    }
                }
            }

            if (!category_errors.isEmpty()) {
                // 後で処理を追加
            }
        }
        return false;
    }
}
