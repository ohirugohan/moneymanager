package moneymanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import moneymanager.bean.SubCategoryBean;
import moneymanager.dataaccess.SubCategoryDao;

public class SubCategoryService {

    public Map<Integer, List<SubCategoryBean>> get(int user_id) {
        SubCategoryDao sub_category_dao = new SubCategoryDao();
        List<SubCategoryBean> sub_categories = sub_category_dao.get(user_id);

        Map<Integer, List<SubCategoryBean>> map = new TreeMap<>();

        for (SubCategoryBean subcategory: sub_categories) {
            int category_id = subcategory.getCategory_id();
            if (map.containsKey(category_id)) {
                map.get(category_id).add(subcategory);
            } else {
                List<SubCategoryBean> list_per_category_id = new ArrayList<>();
                list_per_category_id.add(subcategory);
                map.put(category_id, list_per_category_id);
            }
        }

        return map;
    }

    public boolean register(int user_id, int parent_category_id, String name) {
        // TODO 親カテゴリの存在確認をしたい

        SubCategoryDao sub_category_dao = new SubCategoryDao();
        int max_sub_category_id = sub_category_dao.getMaxSubCategoryId(user_id, parent_category_id);
        int new_sub_category_id = max_sub_category_id + 1;

        return sub_category_dao.insert(user_id, parent_category_id, new_sub_category_id, name);
    }
}
