package com.example.vacationapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.vacationapp.entities.Excursion;

import java.util.List;

// DAO (Data Access Object(Design Pattern)
@Dao
public interface ExcursionDAO {

    // If a conflict occurs (same primary key), ignore the new data
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Excursion excursion);

    // Update an existing excursion in the database
    @Update
    void update(Excursion excursion);

    // Delete an excursion from the database
    @Delete
    void delete(Excursion excursion);

    // Retrieve all excursions from the database, ordered by ID (ascending)
    @Query("SELECT * FROM EXCURSIONS ORDER BY excursionID ASC")
    List<Excursion> getAllExcursions();

    // Retrieve all excursions associated with a specific vacation ID
    @Query("SELECT * FROM EXCURSIONS WHERE vacationId = :vacId ORDER BY excursionId ASC")
    List<Excursion> getAssociatedExcursions(int vacId);
}
