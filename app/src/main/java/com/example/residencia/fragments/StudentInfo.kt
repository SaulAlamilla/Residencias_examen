package com.example.residencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.example.residencia.MainActivity
import com.example.residencia.R
import com.example.residencia.alumno_proyecto_estado.ProjectStatus
import com.example.residencia.alumnos.Alumno
import com.example.residencia.database.baseDeDatos
import com.example.residencia.proyectos.Proyecto
import com.example.residencia.relationships.ProjectAndStatus
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

        //Para habilitar el NavigationDrawer
        (activity as MainActivity?)?.unLockNavitagationDrawer()

        //Para actualizar el alumno si se ha modificado
        (activity as MainActivity?)?.receiveData(alumno!!)
        updateUI(view)
        return view
    }

    override fun onResume() {
        super.onResume()
        val DATABASE_proyectos by lazy { baseDeDatos.getDatabase(context).projectDao() }
        val DATABASE_status by lazy { baseDeDatos.getDatabase(context).statusDao() }

        lifecycleScope.launch {

            //Mostrar en la informacion del proyecto elegido
            val infoStatusProject = DATABASE_status.getById(alumno!!.id)
            val infoSelectedProject = view?.findViewById<TextView>(R.id.tv_id_project)

            var selectedProject: Proyecto? = null

            if (infoStatusProject == null) {
                infoSelectedProject?.text = "Proyecto elegido: Sin elegir"
            } else {
                selectedProject = DATABASE_proyectos.getById(infoStatusProject.id_proyecto)
                infoSelectedProject?.text = "Proyecto elegido: " + selectedProject?.nombre
            }
        }
    }

    fun updateUI(view: View){

        val nombre = view.findViewById<TextView>(R.id.tv_Nombre)
        val creditosComplementarios = view.findViewById<CheckBox>(R.id.cb_CreditosComplementarios)
        val servicioSocial = view.findViewById<CheckBox>(R.id.cb_servicioSocial)
        val ochentaPorCiento = view.findViewById<CheckBox>(R.id.cb_ochentaPorCiento)

        nombre.text = getString(R.string.student_full_name,
            alumno?.nombres,alumno?.apellido_paterno,alumno?.apellido_materno)

        creditosComplementarios.isChecked = true
        servicioSocial.isChecked = true
        ochentaPorCiento.isChecked = true

        if (creditosComplementarios.isChecked && servicioSocial.isChecked && ochentaPorCiento.isChecked){
            view.findViewById<TextView>(R.id.tv_not_ok).isVisible = false
            (activity as MainActivity?)?.approved(true)
        }else{
            view.findViewById<TextView>(R.id.tv_ok).isVisible = false
            (activity as MainActivity?)?.approved(false)
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