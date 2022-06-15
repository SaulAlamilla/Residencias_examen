package com.example.residencia.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.residencia.R
import com.example.residencia.alumno_proyecto_estado.ProjectStatus
import com.example.residencia.alumnos.Alumno
import com.example.residencia.database.baseDeDatos
import kotlinx.coroutines.launch
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProjectDetail.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProjectDetail : Fragment() {
    // TODO: Rename and change types of parameters
    private var alumno: Alumno? = null
    private var id_proyecto_seleccionado: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            alumno = it.getSerializable(ARG_PARAM1) as Alumno
            id_proyecto_seleccionado = it.getInt(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_project_detail, container, false)

        val DATABASE_ by lazy { baseDeDatos.getDatabase(context).alumDao() }
        val DATABASE_status by lazy { baseDeDatos.getDatabase(context).statusDao() }
        val DATABASE_project by lazy { baseDeDatos.getDatabase(context).projectDao() }


        val id = alumno!!.id
        val numero_de_control = alumno!!.numero_de_control
        val contrasena = alumno!!.contrasena
        val nombres = alumno!!.nombres
        val apellido_paterno = alumno!!.apellido_paterno
        val apellido_materno = alumno!!.apellido_materno
        val carrera = alumno!!.carrera
        val id_proyecto = id_proyecto_seleccionado!!

        val vistaDetalleNombre = view.findViewById<TextView>(R.id.tv_nombre)
        val vistaDetalleArea = view.findViewById<TextView>(R.id.tv_area)
        val vistaDetalleEncargado = view.findViewById<TextView>(R.id.tv_encargado)
        val vistaDetalleDescripcion = view.findViewById<TextView>(R.id.tv_descripcion)
        lifecycleScope.launch {
            val proyectoDetalle = DATABASE_project.getById(id_proyecto)
            vistaDetalleNombre.text = "Proyecto: " + proyectoDetalle.nombre
            vistaDetalleArea.text = "Área: " + proyectoDetalle.area
            vistaDetalleEncargado.text = "Encargado: " + proyectoDetalle.encargado
            vistaDetalleDescripcion.text = "Descripcion: " + proyectoDetalle.descripcion
        }

        val btnSolicitar = view.findViewById<Button>(R.id.btn_solicitar)
        btnSolicitar.setOnClickListener {
            view.findViewById<TextView>(R.id.tv_solicitud).isVisible = true

            lifecycleScope.launch {
                DATABASE_.updateAlumno(Alumno(id, numero_de_control, contrasena, nombres,
                apellido_paterno, apellido_materno, carrera))

                val estado_alumno = DATABASE_status.getStatusAlumno(id)
                if (estado_alumno != null){
                    DATABASE_status.updateProyecto_estado(ProjectStatus(estado_alumno.id,estado_alumno.id_alumno, id_proyecto, false))
                }
                else{
                    DATABASE_status.addProyecto_estado(ProjectStatus(0,id, id_proyecto_seleccionado!!, false))
                }
            }

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
         * @return A new instance of fragment ProjectDetail.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(alumno: Alumno, id_proyecto_seleccionado: Int) =
            ProjectDetail().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, alumno)
                    putInt(ARG_PARAM2, id_proyecto_seleccionado)
                }
            }
    }
}