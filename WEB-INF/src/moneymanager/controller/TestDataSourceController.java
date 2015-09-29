package moneymanager.controller;

import java.sql.Connection;

import moneymanager.base.AbstractController;
import moneymanager.dataaccess.TestDataSource;

public class TestDataSourceController extends AbstractController {

    @Override
    protected String getTemplateName() {
        // TODO 自動生成されたメソッド・スタブ
        return "testdatasource";
    }

    @Override
    protected void action() {
        TestDataSource dao = new TestDataSource();
        Connection con = dao.getConnection();
        //String str = con.toString();
        String str = dao.getTestString(con);
        this.assign("test", str);
    }
}
