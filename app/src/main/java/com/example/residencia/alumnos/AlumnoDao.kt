package com.example.residencia.alumnos

import androidx.room.*
import com.example.residencia.alumnos.Alumno
import com.example.residencia.relationships.StudentAndStatus

@Dao
interface AlumnoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAlumno(alumno: Alumno)

    @Query("SELECT * FROM alumnos")
    suspend fun getAlumnos(): List<Alumno>

    @Query("SELECT * FROM alumnos WHERE id = :id_alumno")
    suspend fun getById(id_alumno: Int): Alumno

    @Update
    suspend fun updateAlumno(alumno: Alumno)

    /*
    @Transaction
    @Query("SELECT * FROM alumnos WHERE id = :id_alumno")
    suspend fun getStudentAndStatus(id_alumno: Int): List<StudentAndStatus>*/

    @Query("SELECT * FROM alumnos WHERE numero_de_control LIKE :numero_de_control AND contrasena LIKE :contrasena")
    suspend fun logInAlumno(numero_de_control:String,contrasena:String) : Alumno?
}