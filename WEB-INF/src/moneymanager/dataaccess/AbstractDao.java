package moneymanager.dataaccess;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import moneymanager.util.MoneyManagerPropertyUtil;

abstract class AbstractDao {

    private Connection connection;
    String datasource_name;

    public AbstractDao() {
        Properties properties = new MoneyManagerPropertyUtil().getProperties();
        this.datasource_name = properties.getProperty("datasource_name");


    }

    private void connect() {
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/" + datasource_name);
            this.connection = ds.getConnection();
        } catch (NamingException e) {
            this.close();
            throw new RuntimeException(e);
        } catch (SQLException e) {
            this.close();
            throw new RuntimeException(e);
        }
    }

    private void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {

        }
    }

    /**
     * 単一行を取得するSELECT文を実行し、結果を取得します。
     * @param query SQLクエリ
     * @param queryparameters プリペアドステートメントのためのクエリパラメーター
     * @param convertFunc SELECTの実行結果をオブジェクト変換する関数
     * @return
     */
    protected <T> T findOne(String query, Object[] queryparameters, Function<ResultSet, T> convertFunc ) {
        this.connect();
        try {
            PreparedStatement statement = (PreparedStatement)this.connection.prepareStatement(query);
            statement = this.setQueryParameters(statement, queryparameters);
            ResultSet result = statement.executeQuery();

            if (result.first()) {
                return convertFunc.apply(result);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.close();
        }
    }

    /**
     * SELECT文を実行し、結果を取得します。
     *
     * @param query SQLクエリ
     * @param queryparameters プリペアドステートメントのためのクエリパラメーター
     * @param convertFunc SELECTの実行結果をオブジェクト変換する関数
     * @return
     */
    protected <T> List<T> findAll(String query, Object[] queryparameters, Function<ResultSet, T> convertFunc ) {
        this.connect();
        try {
            PreparedStatement statement = (PreparedStatement)this.connection.prepareStatement(query);
            statement = this.setQueryParameters(statement, queryparameters);
            ResultSet result = statement.executeQuery();

            List<T> ret = new ArrayList<>();
            while (result.next()) {
                ret.add(convertFunc.apply(result));
            }
            return ret;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.close();
        }
    }

    /**
     * insert文を実行し、結果を取得します。
     * @param query
     * @param queryparameters
     * @return
     */
    protected boolean insert(String query, Object[] queryparameters) {
        this.connect();
        try {
            PreparedStatement statement = (PreparedStatement)this.connection.prepareStatement(query);
            statement = this.setQueryParameters(statement, queryparameters);
            return 0 < statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.close();
        }
    }

    /**
     * プリペアドステートメントをセットします。
     * @param statement
     * @param queryparameters
     * @return PreparedStatement
     * @throws SQLException
     */
    private PreparedStatement setQueryParameters(PreparedStatement statement, Object[] queryparameters) throws SQLException{
        int i = 1;
        for (Object param: queryparameters) {
            if (param instanceof String) {
                statement.setString(i, (String)param);
            } else if(param instanceof Integer) {
                statement.setInt(i, (Integer)param);
            } else if(param instanceof Date) {
                statement.setDate(i, (Date)param);
            } else if(param instanceof Timestamp) {
                statement.setTimestamp(i, (Timestamp)param);
            } else if(param instanceof Boolean) {
                statement.setBoolean(i, (Boolean)param);
            }
            else {
                throw new RuntimeException("SQL実行時に、不正な型がプリペアドステートメントにセットされようとしました。");
            }
            i++;
        }
        return statement;
    }
}