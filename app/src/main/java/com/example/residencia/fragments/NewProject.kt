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
import com.example.residencia.database.baseDeDatos
import com.example.residencia.proyectos.Proyecto
import kotlinx.coroutines.launch

class NewProject : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_project, container, false)

        val DATABASE_ by lazy { baseDeDatos.getDatabase(context).projectDao() }

        val btnAdd = view.findViewById<Button>(R.id.btnNewProject)
        btnAdd.setOnClickListener {
            lifecycleScope.launch {
                var nombre = view.findViewById<EditText>(R.id.txtNombreProyecto).text.toString()
                var descripcion = view.findViewById<EditText>(R.id.txtDescripcion).text.toString()
                var area = view.findViewById<EditText>(R.id.txtArea).text.toString()
                var alumnosRequeridos = view.findViewById<EditText>(R.id.txtAlumnosRequeridos).text.toString()
                var encargado = view.findViewById<EditText>(R.id.txtEncargado).text.toString()
                var proyecto = Proyecto(0, nombre, descripcion, area, alumnosRequeridos, encargado)

                if (nombre.isEmpty() || descripcion.isEmpty() || area.isEmpty() ||
                        alumnosRequeridos.isEmpty() || encargado.isEmpty()){
                    Toast.makeText(context,"Faltan datos", Toast.LENGTH_SHORT).show();
                }else{
                    DATABASE_.addProject(Proyecto(0, nombre, descripcion, area, alumnosRequeridos, encargado))
                    Toast.makeText(getActivity(),"Proyecto agregado", Toast.LENGTH_SHORT).show()

                    view.findViewById<EditText>(R.id.txtNombreProyecto).setText("")
                    view.findViewById<EditText>(R.id.txtDescripcion).setText("")
                    view.findViewById<EditText>(R.id.txtArea).setText("")
                    view.findViewById<EditText>(R.id.txtAlumnosRequeridos).setText("")
                    view.findViewById<EditText>(R.id.txtEncargado).setText("")
                }
            }
        }

        return view
    }
}