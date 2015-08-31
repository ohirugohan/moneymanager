package moneymanager.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import moneymanager.bean.ExpenseBean;
import moneymanager.bean.ExpenseWithCategoryBean;
import moneymanager.bean.SubCategoryBean;
import moneymanager.bean.UserCategoryBean;
import moneymanager.dataaccess.ExpenseDao;

public class ExpenseService {

    public boolean register(int user_id, int value, int category_id, int subcategory_id, Date date, String name) {
        ExpenseDao dao = new ExpenseDao();
        return dao.insert(user_id, value, category_id, subcategory_id, date, name);
    }

    public boolean register(int user_id, String value_str, String category_id_str, String sub_category_id_str, String date_str, String name) {
        int value = Integer.parseInt(value_str);
        int category_id = Integer.parseInt(category_id_str);
        int subcategory_id = Integer.parseInt(sub_category_id_str);
        Date date = Date.valueOf(date_str);

        return this.register(user_id, value, category_id, subcategory_id, date, name);
    }

    public List<ExpenseBean> get(int user_id, Date date) {
        ExpenseDao dao = new ExpenseDao();
        return dao.get(user_id, date);
    }

    public List<ExpenseWithCategoryBean> getFullExpenses(List<ExpenseBean> expenses, List<UserCategoryBean> categories, Map<Integer, List<SubCategoryBean>> subcategories_all){
        ArrayList<ExpenseWithCategoryBean> ret = new ArrayList<ExpenseWithCategoryBean>();


        for (ExpenseBean expense: expenses) {
            List<SubCategoryBean> subcategories = subcategories_all.get(expense.getCategory_id());
            UserCategoryBean category = null;
            SubCategoryBean subcategory = null;
            for (UserCategoryBean tmp: categories) {
                if (tmp.getCategory_id() == expense.getCategory_id()) {
                    category = tmp;
                    break;
                }
            }
            if(subcategories != null) {
                for (SubCategoryBean tmp: subcategories) {
                    if (tmp.getSubcategory_id() == expense.getSubcategory_id()) {
                        subcategory = tmp;
                        break;
                    }
                }
            }
            ret.add(new ExpenseWithCategoryBean(expense, category, subcategory));
        }

        return ret;
    }
}
