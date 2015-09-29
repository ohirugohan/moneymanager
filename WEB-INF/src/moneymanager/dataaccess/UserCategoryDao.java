package moneymanager.dataaccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;

import moneymanager.base.AbstractDao;
import moneymanager.bean.UserCategoryBean;

public class UserCategoryDao extends AbstractDao {

    private Function<ResultSet,UserCategoryBean> convertFunc = (result) -> {
        int id, user_id, category_id;
        String name, color_pattern;
        Timestamp register_time, update_time;
        try {
            id = result.getInt("id");
            user_id = result.getInt("user_id");
            category_id = result.getInt("category_id");
            name = result.getString("name");
            color_pattern = result.getString("color_pattern");
            register_time = result.getTimestamp("register_time");
            update_time = result.getTimestamp("update_time");
        } catch (SQLException e) {
            throw new RuntimeException("DBから取得した結果のキャストに失敗しました", e);
        }

        return new UserCategoryBean(id, user_id, category_id, name, color_pattern, register_time, update_time);
    };

    private Function<ResultSet, Integer> convertMaxFunc = (result) -> {
        try {
            return new Integer(result.getInt("max"));
        } catch (SQLException e) {
            throw new RuntimeException("DBから取得した結果のキャストに失敗しました", e);
        }
    };


    /**
     * 指定したユーザーのカテゴリーを取得します。
     * @param user_id
     * @return List<UserCategoryBean>
     */
    public List<UserCategoryBean> findUserCategories(int user_id) {
        String query = "SELECT * FROM user_categories WHERE user_id = ? ORDER BY category_id ASC";
        Object[] query_param = new Object[1];
        query_param[0] = new Integer(user_id);

        return this.findAll(query, query_param, this.convertFunc);
    }

    /**
     * 最大カテゴリIDを取得します。
     * @param user_id
     * @return
     */
    public Integer getMaxCategoryId (int user_id) {
        String query = "SELECT MAX(category_id) as max FROM user_categories WHERE user_id = ?";
        Object[] query_param = new Object[1];
        query_param[0] = new Integer(user_id);

        Integer result = this.findOne(query, query_param, this.convertMaxFunc);
        if (result == null) {
            return new Integer(0);
        }
        return result;
    }

    /**
     * 新しいカテゴリを登録します。
     * @param user_id
     * @param new_category_id
     * @param name
     * @param color_pattern
     * @return
     */
    public boolean insertNewCategory (int user_id, int new_category_id, String name, String color_pattern) {
        String query = "INSERT INTO user_categories " +
                       "(user_id, category_id, name, color_pattern, register_time, update_time) " +
                       "values (?, ?, ?, ?, NOW(), NOW())";
        Object[] query_params = new Object[4];
        query_params[0] = new Integer(user_id);
        query_params[1] = new Integer(new_category_id);
        query_params[2] = name;
        query_params[3] = color_pattern;

        return this.insert(query, query_params);
    }
}
