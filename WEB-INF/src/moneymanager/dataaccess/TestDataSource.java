package moneymanager.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class TestDataSource {
    public Connection getConnection() {
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/myds");
            return ds.getConnection();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public String getTestString(Connection con) {
        try {
            String query = "SELECT name FROM items WHERE date = ?;";
            PreparedStatement ps = (PreparedStatement)con.prepareStatement(query);
            ps.setString(1, "2015-08-01");
            ResultSet rs = ps.executeQuery();

            rs.next();

            return rs.getString("name");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
