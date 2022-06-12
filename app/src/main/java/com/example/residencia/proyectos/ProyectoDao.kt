package com.example.residencia.proyectos

import androidx.room.*

@Dao
interface ProyectoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProject(project: Proyecto)

    @Query("SELECT * FROM proyectos")
    suspend fun getProjects(): List<Proyecto>

    @Query("SELECT * FROM proyectos WHERE id = :id_proyecto")
    suspend fun getById(id_proyecto: Int): Proyecto

    @Update
    suspend fun updateProject(project: Proyecto)

    @Delete
    suspend fun deleteProject(project: Proyecto)
}