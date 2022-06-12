package com.example.residencia.relationships

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.example.residencia.alumno_proyecto_estado.ProjectStatus
import com.example.residencia.proyectos.Proyecto
import java.io.Serializable

@Entity
data class ProjectAndStatus(
    @Embedded val project: Proyecto,
    @Relation(
        parentColumn = "id",
        entityColumn = "id_estado"
    )
    val status: ProjectStatus
):Serializable
