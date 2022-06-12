package com.example.residencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.example.residencia.R
import com.example.residencia.alumno_proyecto_estado.ProjectStatus
import com.example.residencia.alumnos.Alumno
import com.example.residencia.database.baseDeDatos
import com.example.residencia.proyectos.Proyecto
import com.example.residencia.relationships.StudentAndStatus
import kotlinx.coroutines.launch
import org.w3c.dom.Text

private const val ALUMNO = "alumno"
class StudentInfo : Fragment() {
    var alumno: Alumno? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            alumno = it.getSerializable(ALUMNO) as Alumno
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_student_info, container, false)
        updateUI(view)

        return view
    }

    override fun onResume() {
        super.onResume()
        val DATABASE_ by lazy { baseDeDatos.getDatabase(context).alumDao() }

        lifecycleScope.launch {

            //Obtener de nuevo el alumno pero ya actualizado y mostrarlo en pantalla
            var alumnoActualizado: Alumno = DATABASE_.getById(alumno!!.id)
            val proyecto_id = view?.findViewById<TextView>(R.id.tv_id_project)
            proyecto_id?.text = "Proyecto elegido: " + alumnoActualizado.project_id.toString()

            //Dehabilitar boton "ver estado" si no se ha elegido un proyecto
            if (alumnoActualizado.project_id == 0) {
                view?.findViewById<Button>(R.id.btn_estado)?.isEnabled = false
            }

            //Boton para ver el estado del proyecto
            val btn_estado = view?.findViewById<Button>(R.id.btn_estado)
            btn_estado?.setOnClickListener {

                val mFragmentTransaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                mFragmentTransaction.replace(R.id.container, Status.newInstance(alumnoActualizado))
                mFragmentTransaction.addToBackStack(null)
                mFragmentTransaction.commit()
            }

            //Boton para ver los proyectos disponibles
            val btn_continuar = view?.findViewById<Button>(R.id.btn_continuar)
            btn_continuar?.setOnClickListener {

                val mFragmentTransaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                mFragmentTransaction.replace(R.id.container, ProjectList.newInstance(alumnoActualizado))
                mFragmentTransaction.addToBackStack(null)
                mFragmentTransaction.commit()
            }
        }
    }

    fun updateUI(view: View){

        val nombre = view.findViewById<TextView>(R.id.tv_Nombre)
        val numeroDeControl = view.findViewById<TextView>(R.id.tv_NumeroDeControl)
        val carrera = view.findViewById<TextView>(R.id.tv_Carrera)
        val proyecto_id = view.findViewById<TextView>(R.id.tv_id_project)
        val creditosComplementarios = view.findViewById<CheckBox>(R.id.cb_CreditosComplementarios)
        val servicioSocial = view.findViewById<CheckBox>(R.id.cb_servicioSocial)
        val ochentaPorCiento = view.findViewById<CheckBox>(R.id.cb_ochentaPorCiento)

        nombre.text = getString(R.string.student_full_name,
            alumno?.nombres,alumno?.apellido_paterno,alumno?.apellido_materno)
        numeroDeControl.text = alumno?.numero_de_control
        carrera.text = alumno?.carrera
        proyecto_id.text = proyecto_id.text.toString() + alumno?.project_id.toString()

        creditosComplementarios.isChecked = true
        servicioSocial.isChecked = true
        ochentaPorCiento.isChecked = true

        if (creditosComplementarios.isChecked && servicioSocial.isChecked && ochentaPorCiento.isChecked){
            view.findViewById<TextView>(R.id.tv_not_ok).isVisible = false
        }else{
            view.findViewById<TextView>(R.id.tv_ok).isVisible = false
        }
    }

    companion object {
        fun newInstance(alumno: Alumno?) =
            StudentInfo().apply {
                arguments = Bundle().apply {
                    putSerializable(ALUMNO,alumno)
                }
            }
    }
}