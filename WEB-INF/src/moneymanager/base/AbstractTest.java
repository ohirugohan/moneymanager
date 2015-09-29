package moneymanager.base;

import java.io.File;
import java.sql.SQLException;

import moneymanager.base.test.TestMode;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;

enum Operation {
    CLEAN_INSERT,
    DELETE_ALL;
}

/**
 * テスト用クラスの基底です
 *
 */
public class AbstractTest {
    static {
        TestMode.setTestMode();
    }

    protected static void setCsvDataset(String path) throws Exception {
        AbstractTest.operationDatabase(path, Operation.CLEAN_INSERT);
    }

    protected static void clean(String path) throws Exception {
        AbstractTest.operationDatabase(path, Operation.DELETE_ALL);
    }

    private static void operationDatabase(String path, Operation operation) throws Exception {
        IDataSet dataset = new CsvDataSet(new File(path));
        TestDao dao = new TestDao();
        dao.operationDatabase(dataset, operation);
    }
}

/**
 * テスト用DBのデータをセットします
 *
 */
class TestDao extends AbstractDao {

    TestDao() throws Exception {
        super();
        if (!TestMode.isTest()) {
            throw new RuntimeException("UT外でDBテストを行おうとしました。危険な処理です。");
        }
    }

    protected void operationDatabase(IDataSet dataset, Operation operation) {
        IDatabaseConnection dbconn = null;
        try {
            this.connect();
            dbconn = new DatabaseConnection(this.connection);
            DatabaseConfig config = dbconn.getConfig();
            config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
            switch (operation) {
                case CLEAN_INSERT:
                    DatabaseOperation.CLEAN_INSERT.execute(dbconn, dataset);
                    break;
                case DELETE_ALL:
                    DatabaseOperation.DELETE_ALL.execute(dbconn, dataset);
                    break;
            }
        } catch (SQLException | DatabaseUnitException e) {
            e.printStackTrace();
        } finally {
            if (dbconn != null) {
                try {
                    dbconn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
