package moneymanager.bean;

import java.sql.Date;
import java.sql.Timestamp;

public class ExpenseBean {
    private final int id, user_id, value, category_id, subcategory_id;
    private final Date date;
    private final String name;
    private final Timestamp register_time, update_time;
    public ExpenseBean(int id, int user_id, int value, int category_id,
            int subcategory_id, Date date, String name,
            Timestamp register_time, Timestamp update_time) {
        super();
        this.id = id;
        this.user_id = user_id;
        this.value = value;
        this.category_id = category_id;
        this.subcategory_id = subcategory_id;
        this.date = date;
        this.name = name;
        this.register_time = register_time;
        this.update_time = update_time;
    }
    public int getId() {
        return id;
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
    public Timestamp getRegister_time() {
        return register_time;
    }
    public Timestamp getUpdate_time() {
        return update_time;
    }
}
