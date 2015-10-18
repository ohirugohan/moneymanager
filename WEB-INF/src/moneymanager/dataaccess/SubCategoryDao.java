package moneymanager.dataaccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;

import moneymanager.base.AbstractDao;
import moneymanager.bean.SubCategoryBean;

public class SubCategoryDao extends AbstractDao {

    private Function<ResultSet,SubCategoryBean> convertFunc = (result) -> {
        int id, user_id, category_id, subcategory_id;
        String name;
        Timestamp register_time, update_time;
        try {
            id = result.getInt("id");
            user_id = result.getInt("user_id");
            category_id = result.getInt("category_id");
            subcategory_id = result.getInt("subcategory_id");
            name = result.getString("name");
            register_time = result.getTimestamp("register_time");
            update_time = result.getTimestamp("update_time");
        } catch (SQLException e) {
            throw new RuntimeException("DBから取得した結果のキャストに失敗しました", e);
        }

        return new SubCategoryBean(id, user_id, category_id, subcategory_id, name, register_time, update_time);
    };

    private Function<ResultSet, Integer> convertMaxFunc = (result) -> {
        try {
            return new Integer(result.getInt("max"));
        } catch (SQLException e) {
            throw new RuntimeException("DBから取得した結果のキャストに失敗しました", e);
        }
    };


    public List<SubCategoryBean> get(int user_id){
        String query = "SELECT * FROM user_subcategories WHERE user_id = ? ORDER BY category_id, subcategory_id ASC";
        Object[] query_param = new Object[1];
        query_param[0] = new Integer(user_id);

        return this.findAll(query, query_param, this.convertFunc);
    }


    public Integer getMaxSubCategoryId (int user_id, int parent_category_id) {
        String query = "SELECT MAX(subcategory_id) as max FROM user_subcategories WHERE user_id = ? AND category_id = ?";
        Object[] query_param = new Object[2];
        query_param[0] = new Integer(user_id);
        query_param[1] = new Integer(parent_category_id);

        Integer result = this.findOne(query, query_param, this.convertMaxFunc);
        if (result == null) {
            return new Integer(0);
        }
        return result;
    }

    public boolean insert(int user_id, int parent_category_id, int sub_category_id, String name) {
        String query = "INSERT INTO user_subcategories " +
                "(user_id, category_id, subcategory_id, name, register_time, update_time) " +
                "values (?, ?, ?, ?, NOW(), NOW())";
        Object[] query_params = new Object[4];
        query_params[0] = new Integer(user_id);
        query_params[1] = new Integer(parent_category_id);
        query_params[2] = new Integer(sub_category_id);
        query_params[3] = name;

        return this.insert(query, query_params);
    }
}
