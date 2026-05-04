package com.example.vacationapp.database;

import android.app.Application;

import com.example.vacationapp.dao.ExcursionDAO;
import com.example.vacationapp.dao.VacationDAO;
import com.example.vacationapp.entities.Excursion;
import com.example.vacationapp.entities.Vacation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    // DAO references for database operations
    private final ExcursionDAO mExcursionDAO;
    private final VacationDAO mVacationDAO;

    // Cached query results
    private List<Vacation> mAllVacations;
    private List<Excursion> mAllExcursions;

    // Thread pool used to run database operations off the main thread
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Initialize repository and connect to Room database
    public Repository(Application application) {
        VacationDatabase db = VacationDatabase.getDatabase(application);
        mExcursionDAO = db.excursionDAO();
        mVacationDAO = db.vacationDAO();
    }

    // Retrieve all vacations from the database
    public List<Vacation> getAllVacations() throws InterruptedException {
        databaseExecutor.execute(()->{
            mAllVacations = mVacationDAO.getAllVacations();
        });
        try {
            // Pause briefly to allow background thread to complete query
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllVacations;
    }

    // Delete a vacation from the database
    public void delete(Vacation vacation) {
        databaseExecutor.execute(()->{
            mVacationDAO.delete(vacation);
        });
        try {
            // Pause briefly to allow background thread to complete delete
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Update an existing vacation in the database
    public void update(Vacation vacation) {
        databaseExecutor.execute(()->{
            mVacationDAO.update(vacation);
        });
        try {
            // Pause briefly to allow background thread to complete update
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Insert a new vacation into the database
    public void insert(Vacation vacation) {
        databaseExecutor.execute(()->{
            mVacationDAO.insert(vacation);
        });
        try {
            // Pause briefly to allow background thread to complete insert
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all excursions from the database
    public List<Excursion> getAllExcursions() throws InterruptedException {
        databaseExecutor.execute(()->{
            mAllExcursions = mExcursionDAO.getAllExcursions();
        });
        try {
            // Pause briefly to allow background thread to complete query
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllExcursions;
    }

    // Delete an excursion from the database
    public void delete(Excursion excursion) {
        databaseExecutor.execute(()->{
            mExcursionDAO.delete(excursion);
        });
        try {
            // Pause briefly to allow background thread to complete delete
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Update an existing excursion in the database
    public void update(Excursion excursion) {
        databaseExecutor.execute(()->{
            mExcursionDAO.update(excursion);
        });
        try {
            // Pause briefly to allow background thread to complete update
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Insert a new excursion into the database
    public void insert(Excursion excursion) {
        databaseExecutor.execute(()->{
            mExcursionDAO.insert(excursion);
        });
        try {
            // Pause briefly to allow background thread to complete insert
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all excursions associated with a specific vacation ID
    // Used for validation, such as preventing deletion of a vacation with excursions
    public List<Excursion> getAssociatedExcursions(int vacationId) throws InterruptedException {
        databaseExecutor.execute(()->{
            mAllExcursions = mExcursionDAO.getAssociatedExcursions(vacationId);
        });
        try{
            // Pause briefly to allow background thread to complete query
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllExcursions;
    }
}
