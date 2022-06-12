package com.example.residencia.relationships

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.example.residencia.alumno_proyecto_estado.ProjectStatus
import com.example.residencia.alumnos.Alumno
import java.io.Serializable

@Entity
data class StudentAndStatus(
    @Embedded val student: Alumno,
    @Relation(
        parentColumn = "id",
        entityColumn = "id_estado"
    )
    val status: ProjectStatus
):Serializable
