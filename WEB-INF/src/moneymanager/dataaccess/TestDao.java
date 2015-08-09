package moneymanager.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import moneymanager.entity.ItemEntity;
import moneymanager.util.MoneyManagerPropertyUtil;

public class TestDao {

    public List<ItemEntity> getData()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            Properties properties = new MoneyManagerPropertyUtil().getProperties();
            String dbhost = properties.getProperty("dbhost");
            String dbname = properties.getProperty("dbname");
            String dbuser = properties.getProperty("dbuser");
            String dbpw = properties.getProperty("dbpw");

            // MySQLに接続
            Connection con = DriverManager.getConnection("jdbc:mysql://" + dbhost + "/" + dbname, dbuser, dbpw);
            // ステートメント生成
            Statement stmt = con.createStatement();

            // SQLを実行
            String sqlStr = "SELECT * FROM items WHERE user_id = 0 ORDER BY date ASC;";
            ResultSet rs = stmt.executeQuery(sqlStr);

            List<ItemEntity> list = new ArrayList<>();

            // 結果行をループ
            while(rs.next()){
                // レコードの値
                list.add(new ItemEntity(rs.getInt("id"), rs.getInt("user_id"), rs.getDate("date"), rs.getString("name"), rs.getInt("value")));
            }

            // 接続を閉じる
            rs.close();
            stmt.close();
            con.close();

            return list;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public boolean insert(String date, String name, String value) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            Properties properties = new MoneyManagerPropertyUtil().getProperties();
            String dbhost = properties.getProperty("dbhost");
            String dbname = properties.getProperty("dbname");
            String dbuser = properties.getProperty("dbuser");
            String dbpw = properties.getProperty("dbpw");

            // MySQLに接続
            Connection con = DriverManager.getConnection("jdbc:mysql://" + dbhost + "/" + dbname, dbuser, dbpw);
            // ステートメント生成
            Statement stmt = con.createStatement();

            // SQLを実行
            String sqlStr = "INSERT INTO items (user_id, date, type, name, value, register_time, update_time) "
                         + "VALUES ('0', '" + date + "', 'expense', '" + name + "', '" + value + "', NOW(), NOW());";
            int count = stmt.executeUpdate(sqlStr);

            // 接続を閉じる
            stmt.close();
            con.close();

            return count > 0;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}