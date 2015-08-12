package moneymanager.controller;

import java.sql.Connection;

import moneymanager.dataaccess.TestDataSource;

public class TestDataSourceController extends AbstractController {

    @Override
    String getTemplateName() {
        // TODO 自動生成されたメソッド・スタブ
        return "testdatasource";
    }

    @Override
    void action() {
        TestDataSource dao = new TestDataSource();
        Connection con = dao.getConnection();
        //String str = con.toString();
        String str = dao.getTestString(con);
        this.assign("test", str);
    }
}
