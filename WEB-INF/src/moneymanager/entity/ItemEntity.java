package moneymanager.entity;

import java.sql.*;

public class ItemEntity {

    public int id;
    public int user_id;
    public String date;
    public String name;
    public int value;
    
    public ItemEntity(int id, int user_id, Date date, String name, int value) {
        this.id = id;
        this.user_id = user_id;
        this.date = date.toString();
        this.name = name;
        this.value = value;
    }
    
}