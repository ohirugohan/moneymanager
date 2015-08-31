package moneymanager.service;

import java.util.List;

import moneymanager.bean.UserCategoryBean;
import moneymanager.dataaccess.UserCategoryDao;

public class MyCategoryService {

    /**
     * 親カテゴリを取得
     * @param user_id
     * @return
     */
    public List<UserCategoryBean> get(int user_id)
    {
        UserCategoryDao user_category_dao = new UserCategoryDao();
        return user_category_dao.findUserCategories(user_id);
    }

    /**
     * 新しい親カテゴリを登録
     * @param user_id
     * @param category_name
     * @param color
     * @return
     */
    public boolean register(int user_id, String category_name, String color) {
        // 新しいカテゴリIDを算出
        UserCategoryDao user_category_dao = new UserCategoryDao();
        int max_category_id = user_category_dao.getMaxCategoryId(user_id);
        int new_category_id = max_category_id + 1;

        return user_category_dao.insertNewCategory(user_id, new_category_id, category_name, color);
    }
}
