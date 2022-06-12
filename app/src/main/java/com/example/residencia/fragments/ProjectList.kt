package com.example.residencia.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.residencia.R
import com.example.residencia.alumnos.Alumno
import com.example.residencia.database.baseDeDatos
import com.example.residencia.proyectos.ProjectAdapter
import com.example.residencia.proyectos.Proyecto
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"

class ProjectList : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Alumno? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Alumno
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_project_list, container, false)

        val DATABASE_ by lazy { baseDeDatos.getDatabase(context).projectDao() }
        lifecycleScope.launch {
            var listProjects: List<Proyecto> = DATABASE_.getProjects()

            val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerId)
            recyclerView.adapter = ProjectAdapter(context, listProjects, param1!!)
            recyclerView.setHasFixedSize(true)
        }
        return view
    }

    companion object {
        fun newInstance(param1: Alumno) =
            ProjectList().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}