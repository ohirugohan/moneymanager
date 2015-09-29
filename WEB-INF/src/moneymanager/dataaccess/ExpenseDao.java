package moneymanager.dataaccess;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;

import moneymanager.base.AbstractDao;
import moneymanager.bean.ExpenseBean;

public class ExpenseDao extends AbstractDao {

    private Function<ResultSet,ExpenseBean> convertFunc = (result) -> {
        int id, user_id, value, category_id, subcategory_id;
        Date date;
        String name;
        Timestamp register_time, update_time;

        try {
            id = result.getInt("id");
            user_id = result.getInt("user_id");
            value = result.getInt("value");
            category_id = result.getInt("category_id");
            subcategory_id = result.getInt("subcategory_id");
            date = result.getDate("date");
            name = result.getString("name");
            register_time = result.getTimestamp("register_time");
            update_time = result.getTimestamp("update_time");
        } catch (SQLException e) {
            throw new RuntimeException("DBから取得した結果のキャストに失敗しました", e);
        }

        return new ExpenseBean(id, user_id, value, category_id, subcategory_id, date, name, register_time, update_time);
    };

    public List<ExpenseBean> get(int user_id, Date date) {
        String query = "SELECT * FROM expenses WHERE user_id = ? AND date = ? ORDER BY category_id, subcategory_id ASC";
        Object[] query_param = new Object[2];
        query_param[0] = new Integer(user_id);
        query_param[1] = date;

        return this.findAll(query, query_param, convertFunc);
    }


    public boolean insert(int user_id, int value, int category_id, int subcategory_id, Date date, String name) {
        String query = "INSERT INTO expenses " +
                "(user_id, value, category_id, subcategory_id, date, name, register_time, update_time) " +
                "values (?, ?, ?, ?, ?, ?, NOW(), NOW())";
        Object[] query_params = new Object[6];
        query_params[0] = new Integer(user_id);
        query_params[1] = new Integer(value);
        query_params[2] = new Integer(category_id);
        query_params[3] = new Integer(subcategory_id);
        query_params[4] = date;
        query_params[5] = name;

        return this.insert(query, query_params);
    }
}
