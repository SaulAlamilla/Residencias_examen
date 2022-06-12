package com.example.residencia.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.residencia.alumno_proyecto_estado.ProjectStatus
import com.example.residencia.alumno_proyecto_estado.ProjectStatus_dao
import com.example.residencia.alumnos.Alumno
import com.example.residencia.alumnos.AlumnoDao
import com.example.residencia.proyectos.Proyecto
import com.example.residencia.proyectos.ProyectoDao

@Database(
    entities = [Alumno::class, Proyecto::class, ProjectStatus::class],
    version = 1,
    exportSchema = true
)
abstract class baseDeDatos: RoomDatabase() {
    abstract fun alumDao(): AlumnoDao
    abstract fun projectDao(): ProyectoDao
    abstract fun statusDao(): ProjectStatus_dao

    companion object {

        @Volatile
        private var INSTANCE: baseDeDatos? = null

        fun getDatabase(context: Context?): baseDeDatos {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(this) {
                    // Pass the database to the INSTANCE
                    INSTANCE = buildDatabase(context!!)
                }
            }
            // Return database.
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): baseDeDatos {
            return Room.databaseBuilder(
                context.applicationContext,
                baseDeDatos::class.java,
                "database_database"
            )
                .build()
        }
    }
}