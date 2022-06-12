package com.example.residencia.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.example.residencia.R
import com.example.residencia.alumnos.Alumno
import com.example.residencia.database.baseDeDatos
import kotlinx.coroutines.launch

class SingUp : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sing_up, container, false)


        val DATABASE_ by lazy { baseDeDatos.getDatabase(context).alumDao() }
        val btnLogin = view.findViewById<Button>(R.id.btnBackToLogin)
        val btnSignUp = view.findViewById<Button>(R.id.btnRegistrar)

        btnSignUp.setOnClickListener {
            lifecycleScope.launch{
                val numero_de_control: String = view.findViewById<EditText>(R.id.et_numero_de_control).text.toString()
                val contrasena: String = view.findViewById<EditText>(R.id.et_contrasena).text.toString()
                val nombres: String = view.findViewById<EditText>(R.id.et_nombre).text.toString()
                val apellido_paterno: String = view.findViewById<EditText>(R.id.et_apellido_paterno).text.toString()
                val apellido_materno: String = view.findViewById<EditText>(R.id.et_apellido_materno).text.toString()
                val carrera: String = view.findViewById<EditText>(R.id.et_carrera).text.toString()
                var alumno = Alumno(0,numero_de_control,contrasena,nombres,apellido_paterno,apellido_materno,carrera,0)

                if (numero_de_control.isEmpty() || contrasena.isEmpty() || nombres.isEmpty() ||
                    nombres.isEmpty() || apellido_paterno.isEmpty() || apellido_materno.isEmpty() ||
                    carrera.isEmpty()){
                    Toast.makeText(context,"Faltan datos", Toast.LENGTH_SHORT).show();
                }else{
                    DATABASE_.addAlumno(alumno)

                    var mFragmentTransaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                    var fragList = Login()
                    mFragmentTransaction.replace(R.id.container, fragList)
                    mFragmentTransaction.addToBackStack(null)
                    mFragmentTransaction.commit()
                }
            }
        }

        btnLogin.setOnClickListener {

            var mFragmentTransaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            var fragList = Login()
            mFragmentTransaction.replace(R.id.container, fragList)
            mFragmentTransaction.addToBackStack(null)
            mFragmentTransaction.commit()
        }
        return view
    }
}