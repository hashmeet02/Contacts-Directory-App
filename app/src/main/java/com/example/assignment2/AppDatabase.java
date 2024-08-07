package com.example.assignment2;

import androidx.room.Database;
import androidx.room.RoomDatabase;

//******************************************//
//      By: Hashmeet Singh Saini            //
//      August 1st 2024                     //
//******************************************//

@Database(entities = {Contact.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDao ContactDao();
}