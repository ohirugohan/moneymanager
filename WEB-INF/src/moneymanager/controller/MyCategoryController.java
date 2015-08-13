package moneymanager.controller;

import java.util.ArrayList;
import java.util.List;

import moneymanager.bean.UserCategoryBean;
import moneymanager.controller.validator.StringValidator;
import moneymanager.controller.validator.ValidationError;
import moneymanager.dataaccess.UserCategoryDao;

public class MyCategoryController extends AbstractController {

    @Override
    protected String getTemplateName() {
        return "mycategory";
    }

    @Override
    protected void action() {
        // TODO 自動生成されたメソッド・スタブ

        int user_id = this.getUserId();
        UserCategoryDao user_category_dao = new UserCategoryDao();

        if(this.getParamaterMethod() == ParameterMethod.POST) {
            this.registerNewCategory();
        }

        List<UserCategoryBean> mycategories;
        mycategories = user_category_dao.findUserCategories(user_id);
        this.assign("mycategories", mycategories);

    }

    private void registerNewCategory() {
        int user_id = this.getUserId();
        String category_name = this.getParameter("name");
        String color = this.getParameter("color");

        // 渡ってきたパラメータのバリデーション
        StringValidator name_validator = new StringValidator("name", category_name, 1, 16);
        ArrayList<ValidationError> name_errors = name_validator.getErrors();
        StringValidator color_validator = new StringValidator("color", color, 7, 7);
        ArrayList<ValidationError> color_errors = color_validator.getErrors();


        if (name_errors.isEmpty() && color_errors.isEmpty()) {
            UserCategoryDao user_category_dao = new UserCategoryDao();
            int max_category_id = user_category_dao.getMaxCategoryId(user_id);
            int new_category_id = max_category_id + 1;
            if(user_category_dao.insertNewCategory(user_id, new_category_id, category_name, color)) {
                this.debug("新規カテゴリの登録に成功しました。");
            }

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
            if (!color_errors.isEmpty()) {
                // 後で処理を追加
            }
        }
    }

}
