package com.example.residencia.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.residencia.R
import com.example.residencia.alumnos.Alumno
import com.example.residencia.database.baseDeDatos
import com.example.residencia.proyectos.Proyecto
import kotlinx.coroutines.launch
import org.w3c.dom.Text

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Status : Fragment() {
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
            val proyectoName = proyecto!!.nombre
            val estado = infoStatus?.status?.estado
            var estado_validado = ""

            if (estado == false) estado_validado = "Pendiente" else estado_validado = "Aceptado"

            val nombre_ = view.findViewById<TextView>(R.id.tv_nombre_p)
            val area_ = view.findViewById<TextView>(R.id.tv_area)
            val descripcion = view.findViewById<TextView>(R.id.tv_descripcion)
            val encargado = view.findViewById<TextView>(R.id.tv_encargado)
            val status_ = view.findViewById<TextView>(R.id.tv_status_p)

            nombre_.text = proyectoName
            area_.text = proyecto!!.area
            descripcion.text = proyecto!!.descripcion
            encargado.text = proyecto!!.encargado
            status_.text = estado_validado
        }
        return view
    }

    companion object {
        fun newInstance(estudiante: Alumno, proyecto: Proyecto?) =
            Status().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, estudiante)
                    putSerializable(ARG_PARAM2, proyecto)
                }
            }
    }
}