package com.example.residencia.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.residencia.R
import com.example.residencia.alumno_proyecto_estado.ProjectStatus
import com.example.residencia.alumnos.Alumno
import com.example.residencia.database.baseDeDatos
import com.example.residencia.proyectos.Proyecto
import com.example.residencia.relationships.ProjectAndStatus
import com.example.residencia.relationships.StudentAndStatus
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Status.newInstance] factory method to
 * create an instance of this fragment.
 */
class Status : Fragment() {
    // TODO: Rename and change types of parameters
    private var estudiante: Alumno? = null
    private var proyecto: Proyecto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            estudiante = it.getSerializable(ARG_PARAM1) as Alumno
            proyecto = it.getSerializable(ARG_PARAM2) as Proyecto
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_status, container, false)

        val DATABASE_status by lazy { baseDeDatos.getDatabase(context).statusDao() }

        lifecycleScope.launch {
            val infoStatus = DATABASE_status.getStudentAndStatus(estudiante!!.id)

            val nombre = estudiante!!.nombres
            val apellido_paterno = estudiante!!.apellido_paterno
            val proyecto = proyecto!!.nombre
            val estado = infoStatus?.status?.estado
            var estado_validado = ""

            if (estado == false) estado_validado = "Pendiente" else estado_validado = "Aceptado"

            val status_ = view.findViewById<TextView>(R.id.tv_status)
            status_.setText(nombre + " " + apellido_paterno +
                    "\nProyecto: " + proyecto +
                    "\nEstado: " + estado_validado)
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Status.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(estudiante: Alumno, proyecto: Proyecto?) =
            Status().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, estudiante)
                    putSerializable(ARG_PARAM2, proyecto)
                }
            }
    }
}