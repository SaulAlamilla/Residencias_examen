package com.example.residencia.fragments

import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.residencia.MainActivity
import com.example.residencia.R
import com.example.residencia.alumnos.Alumno
import com.example.residencia.database.baseDeDatos
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"

class UserProfile : Fragment() {
    // TODO: Rename and change types of parameters
    private var student: Alumno? = null

    private lateinit var etName: TextView
    private lateinit var etSurname: TextView
    private lateinit var etSecondSurname: TextView
    private lateinit var etCareer: TextView
    private lateinit var etControlNumber: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            student = it.getSerializable(ARG_PARAM1) as Alumno
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)

        etName = view.findViewById<EditText>(R.id.et_nombre)
        etSurname = view.findViewById<EditText>(R.id.et_apellido_paterno)
        etSecondSurname = view.findViewById<EditText>(R.id.et_apellido_materno)
        etCareer = view.findViewById<EditText>(R.id.et_carrera)
        etControlNumber = view.findViewById<EditText>(R.id.et_numero_de_control)

        etName.setText(student!!.nombres)
        etSurname.setText(student!!.apellido_paterno)
        etSecondSurname.setText(student!!.apellido_materno)
        etCareer.setText(student!!.carrera)
        etControlNumber.setText(student!!.numero_de_control)



        val btn_editar = view.findViewById<Button>(R.id.btn_editar)
        val btn_guardar = view.findViewById<Button>(R.id.btn_guardar)
        btn_editar.setOnClickListener {
            btn_guardar.isVisible = true
            enabled(true)
        }

        btn_guardar.setOnClickListener {
            val DATABASE_alumno by lazy { baseDeDatos.getDatabase(context).alumDao() }

            var nombre = etName.text.toString()
            var apellidoP = etSurname.text.toString()
            var apellidoM = etSecondSurname.text.toString()
            var numero_de_control = etControlNumber.text.toString()
            var carrera = etCareer.text.toString()

            var alumno = Alumno(student!!.id, numero_de_control, student!!.contrasena, nombre, apellidoP, apellidoM, carrera)
            (activity as MainActivity?)?.receiveData(alumno)

            lifecycleScope.launch {
                DATABASE_alumno.updateAlumno(alumno)
            }

            btn_guardar.isVisible = false
            btn_guardar.isVisible = false
            enabled(false)
        }

        return view
    }

    fun enabled(boolean: Boolean){
        etName.isEnabled = boolean
        etSurname.isEnabled = boolean
        etSecondSurname.isEnabled = boolean
    }

    companion object {
        fun newInstance(student: Alumno) =
            UserProfile().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, student)
                }
            }
    }
}