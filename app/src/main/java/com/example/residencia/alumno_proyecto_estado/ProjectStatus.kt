package com.example.residencia.alumno_proyecto_estado

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "estado_proyectos")
data class ProjectStatus(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_estado")
    val id: Int,

    @ColumnInfo(name = "id_alumno")
    val id_alumno: Int,

    @ColumnInfo(name = "id_proyecto")
    val id_proyecto: Int,

    @ColumnInfo(name = "estado")
    val estado: Boolean,
): Serializable
