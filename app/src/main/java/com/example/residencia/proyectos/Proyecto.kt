package com.example.residencia.proyectos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "proyectos")
data class Proyecto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Int,
    @ColumnInfo(name = "nombre")
    val nombre: String,
    @ColumnInfo(name = "descripcion")
    val descripcion: String,
    @ColumnInfo(name = "area")
    val area: String,
    @ColumnInfo(name = "alumnosRequeridos")
    val alumnosRequeridos: String,
    @ColumnInfo(name = "encargado")
    val encargado: String
)