package moneymanager.bean;

import java.sql.Date;

public class UserCategoryBean {
    public UserCategoryBean(int id, int user_id, int category_id, String name,
            String color_pattern, Date register_time, Date update_time) {
        super();
        this.id = id;
        this.user_id = user_id;
        this.category_id = category_id;
        this.name = name;
        this.color_pattern = color_pattern;
        this.register_time = register_time;
        this.update_time = update_time;
    }
    private int id, user_id, category_id;
    private String name, color_pattern;
    private Date register_time, update_time;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public int getCategory_id() {
        return category_id;
    }
    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getColor_pattern() {
        return color_pattern;
    }
    public void setColor_pattern(String color_pattern) {
        this.color_pattern = color_pattern;
    }
    public Date getRegister_time() {
        return register_time;
    }
    public void setRegister_time(Date register_time) {
        this.register_time = register_time;
    }
    public Date getUpdate_time() {
        return update_time;
    }
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
