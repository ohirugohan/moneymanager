package moneymanager.dataaccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;

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
}
