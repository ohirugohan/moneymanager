package moneymanager.bean;

import java.sql.Timestamp;

public class UserCategoryBean {

    private final int id, user_id, category_id;
    private final String name, color_pattern;
    private final Timestamp register_time, update_time;

    public UserCategoryBean(int id, int user_id, int category_id, String name,
            String color_pattern, Timestamp register_time, Timestamp update_time) {
        super();
        this.id = id;
        this.user_id = user_id;
        this.category_id = category_id;
        this.name = name;
        this.color_pattern = color_pattern;
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
    public String getName() {
        return name;
    }
    public String getColor_pattern() {
        return color_pattern;
    }
    public Timestamp getRegister_time() {
        return register_time;
    }
    public Timestamp getUpdate_time() {
        return update_time;
    }
}
