package com.kromer.openweather.features.weather.data.datasources.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kromer.openweather.features.weather.domain.entities.City

/**
 * Data Access Object for the cities table.
 */
@Dao
interface WeatherDao {
    /**
     * Select all cities from the cities table.
     *
     * @return all cities.
     */
    @Query("SELECT * FROM cities")
    fun getAll(): LiveData<List<City>>

    /**
     * Select all cities from the cities table that matches this date.
     *
     * @return all cities.
     */
    @Query("SELECT * FROM cities WHERE name = :name")
    suspend fun getByName(name: String): City?

    /**
     * Select a city by id.
     *
     * @param id the city id.
     * @return the city with id.
     */
    @Query("SELECT * FROM cities WHERE id = :id")
    suspend fun getById(id: Long): City?

    /**
     * Insert cities in the database. If the city already exists, replace it.
     *
     * @param cities the cities to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cities: List<City>)

    /**
     * Insert city in the database. If the city already exists, replace it.
     *
     * @param city the city to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(city: City)

    @Delete
    fun delete(city: City)
}