package com.example.residencia.proyectos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.residencia.R
import com.example.residencia.alumnos.Alumno
import com.example.residencia.fragments.*

class ProjectAdapter (
    private val context: Context?,
    private val dataset: List<Proyecto>,
    private val alumno: Alumno)
    :RecyclerView.Adapter<ProjectAdapter.ItemViewHolder>(){

    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val projectName: TextView = view.findViewById(R.id.tv_project_name)
        val encargado: TextView = view.findViewById(R.id.tv_encargado)
        val area: TextView = view.findViewById(R.id.tv_area)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectAdapter.ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.project_view_design, parent, false)

        return ProjectAdapter.ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ProjectAdapter.ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.projectName.text = "Nombre del proyecto: "+item.nombre
        holder.encargado.text = "Encargado: "+item.encargado
        holder.area.text = "Área: "+item.area

        holder.itemView.setOnClickListener {
            val idProjectoSelected = dataset[position].id

            val mFragmentTransaction = context as AppCompatActivity
            mFragmentTransaction.supportFragmentManager.beginTransaction()
                .replace(R.id.container, ProjectDetail.newInstance(alumno, idProjectoSelected))
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount() = dataset.size
}