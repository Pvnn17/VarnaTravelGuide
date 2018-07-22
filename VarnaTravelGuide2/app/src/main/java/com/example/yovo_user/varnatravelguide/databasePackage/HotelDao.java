package com.example.yovo_user.varnatravelguide.databasePackage;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
interface HotelDao {
    @Query("SELECT * FROM HOTELS")
    List<Hotel> getAllHotels();

    @Insert
    void insertAll(Hotel... hotels);
}
