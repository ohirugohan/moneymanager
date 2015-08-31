package moneymanager.bean;

import java.sql.Timestamp;

public class SubCategoryBean {

    private final int id, user_id, category_id, subcategory_id;
    private final String name;
    private final Timestamp register_time, update_time;

    public SubCategoryBean(int id, int user_id, int category_id,
            int subcategory_id, String name, Timestamp register_time,
            Timestamp update_time) {
        super();
        this.id = id;
        this.user_id = user_id;
        this.category_id = category_id;
        this.subcategory_id = subcategory_id;
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

    public int getCategory_id() {
        return category_id;
    }

    public int getSubcategory_id() {
        return subcategory_id;
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
