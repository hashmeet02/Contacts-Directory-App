package com.example.assignment2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//******************************************//
//      By: Hashmeet Singh Saini            //
//      August 1st 2024                     //
//******************************************//

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contact")
    List<Contact> getAllContact();

    @Update
    void update(Contact contact);

    @Insert
    void insert(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("SELECT * FROM contact WHERE uid = :userId")
    Contact findById(int userId);

    @Query("SELECT * FROM contact WHERE full_name LIKE :name LIMIT 1")
    Contact findByName(String name);

    @Query("SELECT * FROM contact WHERE full_name = :name AND email = :email AND phone = :phone LIMIT 1")
    Contact getContactByNameEmailPhone(String name, String email, String phone);
}
