package com.example.residencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.example.residencia.MainActivity
import com.example.residencia.R
import com.example.residencia.alumnos.Alumno
import com.example.residencia.database.baseDeDatos
import kotlinx.coroutines.launch


private const val ALUMNO = "alumno"
class Login : Fragment() {
    private val ADMIN = "admin"
    private val ROOT = "root"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        //Deshabilitar el Navigation Drawer
        (activity as MainActivity?)?.lockNavitagationDrawer()

        val DATABASE_ by lazy { baseDeDatos.getDatabase(context).alumDao() }
        val anclaRegistro = view.findViewById<TextView>(R.id.Registrarse)
        anclaRegistro.setOnClickListener {
            var mFragmentTransaction: FragmentTransaction = parentFragmentManager.beginTransaction()

            mFragmentTransaction.replace(R.id.container, SingUp())
            mFragmentTransaction.addToBackStack(null)
            mFragmentTransaction.commit()
        }

        val btnAcceder = view.findViewById<TextView>(R.id.Acceder)
        btnAcceder.setOnClickListener {
            val numero_de_control = view.findViewById<EditText>(R.id.txt_usuario).text.toString()
            val contrasena = view.findViewById<EditText>(R.id.txt_contrasena).text.toString()

            //Entrar como administrador
            if (numero_de_control == ADMIN && contrasena == ROOT){
                val mFragmentTransaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                mFragmentTransaction.replace(R.id.container, NewProject())
                mFragmentTransaction.addToBackStack(null)
                mFragmentTransaction.commit()
            } else {
                lifecycleScope.launch{
                    var alumno: Alumno? = DATABASE_.logInAlumno(numero_de_control,contrasena)
                    if(alumno == null){
                        Toast.makeText(getActivity(),"Datos Incorrectos", Toast.LENGTH_SHORT).show()
                    }else{
                        //Toast.makeText(getActivity(),alumno?.nombres, Toast.LENGTH_SHORT).show()
                        val mFragmentTransaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                        mFragmentTransaction.replace(R.id.container, StudentInfo.newInstance(alumno))
                        mFragmentTransaction.addToBackStack(null)
                        mFragmentTransaction.commit()
                    }
                }

            }
        }

        return view
    }
}