package com.example.vacationapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.vacationapp.entities.Vacation;

import java.util.List;

@Dao
public interface VacationDAO {
    // If a conflict occurs (same primary key), ignore the new data
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Vacation vacation);

    // Update an existing vacation in the database
    @Update
    void update(Vacation vacation);

    // Delete a vacation from the database
    @Delete
    void delete(Vacation vacation);

    // Retrieve all vacations from the database, ordered by ID (ascending)
    @Query("SELECT * FROM VACATIONS ORDER BY vacationId ASC")
    List<Vacation> getAllVacations();

    // Retrieve a specific vacation by its ID
    @Query("SELECT * FROM VACATIONS WHERE vacationId = :id")
    Vacation getVacationById(int id);
}
