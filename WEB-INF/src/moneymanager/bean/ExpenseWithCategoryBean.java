package moneymanager.bean;

import java.sql.Date;

public class ExpenseWithCategoryBean {

    private final int user_id, value, category_id, subcategory_id;
    private final Date date;
    private final String name;

    private final String category_name, subcategory_name, color_pattten;


    public ExpenseWithCategoryBean(ExpenseBean expense, UserCategoryBean category, SubCategoryBean subcategory) {
        this.user_id = expense.getUser_id();
        this.value = expense.getValue();
        this.category_id = expense.getCategory_id();
        this.subcategory_id = expense.getSubcategory_id();
        this.date = expense.getDate();
        this.name = expense.getName();
        if (category != null) {
            this.category_name = category.getName();
            this.color_pattten = category.getColor_pattern();
        } else {
            this.category_name = "未登録";
            this.color_pattten = "#ffffff";
        }
        if (subcategory != null) {
            this.subcategory_name = subcategory.getName();
        } else {
            this.subcategory_name = "未登録";
        }

    }


    public int getUser_id() {
        return user_id;
    }


    public int getValue() {
        return value;
    }


    public int getCategory_id() {
        return category_id;
    }


    public int getSubcategory_id() {
        return subcategory_id;
    }


    public Date getDate() {
        return date;
    }


    public String getName() {
        return name;
    }


    public String getCategory_name() {
        return category_name;
    }


    public String getSubcategory_name() {
        return subcategory_name;
    }


    public String getColor_pattten() {
        return color_pattten;
    }
}
