package com.example.residencia.alumnos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "alumnos")
data class Alumno(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "numero_de_control")
    val numero_de_control: String,
    @ColumnInfo(name = "contrasena")
    val contrasena: String,
    @ColumnInfo(name = "nombres")
    val nombres: String,
    @ColumnInfo(name = "apellido_paterno")
    val apellido_paterno: String,
    @ColumnInfo(name = "apellido_materno")
    val apellido_materno: String,
    @ColumnInfo(name = "carrera")
    val carrera: String
): Serializable
