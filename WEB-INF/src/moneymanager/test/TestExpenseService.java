package moneymanager.test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import moneymanager.base.AbstractTest;
import moneymanager.bean.ExpenseBean;
import moneymanager.service.ExpenseService;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestExpenseService extends AbstractTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        TestExpenseService.setCsvDataset("./test_fixtures/TestExpenseService");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        TestExpenseService.clean("./test_fixtures/TestExpenseService");
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGet() throws ParseException {
        ExpenseService service = new ExpenseService();
        DateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        java.util.Date util_date = format.parse("2015/09/01 00:00:00");
        Date date = new Date(util_date.getTime());

        List<ExpenseBean> actual = service.get(1, date);

        assertSame(1, actual.size());

        Date date_expected = new Date(util_date.getTime());
        java.util.Date util_time = format.parse("2015/09/01 12:34:56");
        Timestamp time = new Timestamp(util_time.getTime());

        ExpenseBean actualbean = actual.get(0);
        assertEquals(1, actualbean.getId());
        assertEquals(1, actualbean.getUser_id());
        assertEquals(200, actualbean.getValue());
        assertEquals(0, actualbean.getCategory_id());
        assertEquals(0, actualbean.getSubcategory_id());
        assertEquals(date_expected, actualbean.getDate());
        assertEquals("test", actualbean.getName());
        assertEquals(time, actualbean.getRegister_time());
        assertEquals(time, actualbean.getUpdate_time());
    }

}
