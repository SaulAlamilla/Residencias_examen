package com.example.residencia.alumno_proyecto_estado

import androidx.room.*
import com.example.residencia.alumnos.Alumno
import com.example.residencia.relationships.ProjectAndStatus
import com.example.residencia.relationships.StudentAndStatus

@Dao
interface ProjectStatus_dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProyecto_estado(proyecto_estado: ProjectStatus?)

    @Update
    suspend fun updateProyecto_estado(proyecto_estado: ProjectStatus)

    @Transaction
    @Query("SELECT * FROM alumnos WHERE id = :id_alumno")
    suspend fun getStudentAndStatus(id_alumno: Int): StudentAndStatus?

    @Transaction
    @Query("SELECT * FROM proyectos WHERE id = :id_proyecto")
    suspend fun getProjectAndStatus(id_proyecto: Int): ProjectAndStatus?

    @Query("SELECT * FROM estado_proyectos WHERE id_estado = :id_alumno")
    suspend fun getById(id_alumno: Int): ProjectStatus?


    @Query("SELECT * FROM estado_proyectos WHERE id_alumno LIKE :id_alumno")
    suspend fun getStatusAlumno(id_alumno: Int) : ProjectStatus?

}