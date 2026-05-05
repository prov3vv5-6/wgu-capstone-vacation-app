package com.example.vacationapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.vacationapp.dao.ExcursionDAO;
import com.example.vacationapp.dao.VacationDAO;
import com.example.vacationapp.entities.Excursion;
import com.example.vacationapp.entities.Vacation;

// Defines the Room database configuration
// Lists all entities (tables) included in the database
@Database(entities = {Vacation.class, Excursion.class}, version = 22, exportSchema = false)
public abstract class VacationDatabase extends RoomDatabase {
    // Abstract methods that provide access to DAO interfaces
    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();

    // Singleton instance of the database (only one instance allowed)
    private static volatile VacationDatabase INSTANCE;

    // Method to get the database instance
    static VacationDatabase getDatabase(final Context context){

        // Check if instance is null (not yet created)
        if(INSTANCE == null){

            // Synchronize to prevent multiple threads creating multiple instances
            synchronized (VacationDatabase.class){

                // Double-check instance inside synchronized block
                if(INSTANCE == null){

                    // Build the database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), VacationDatabase.class, "MyVacationDatabase.db")

                            // If schema changes and no migration is provided, delete the database and recreate it
                            .fallbackToDestructiveMigration()

                            // Create the database
                            .build();
                }
            }
        }
        // Return the existing database instance
        return INSTANCE;
    }
}
