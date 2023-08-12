package com.example.cookou;

import com.example.cookou.database.DatabaseHelper;

public interface MainListener {
    void switchTo(String tag);
    DatabaseHelper getDatabasehelper();
    void goTo(String activity);
}
