package com.example.residencia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import com.example.residencia.alumnos.Alumno
import com.example.residencia.database.baseDeDatos
import com.example.residencia.databinding.ActivityMainBinding
import com.example.residencia.fragments.*
import com.example.residencia.proyectos.Proyecto
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var mDrawerLayout: DrawerLayout
    lateinit var mNavigationView: NavigationView
    lateinit var mToolbar: Toolbar
    lateinit var toggle: ActionBarDrawerToggle

    lateinit var student:Alumno
    lateinit var project: Proyecto
    var approved: Boolean = false

    val URL: String = "https://www.facebook.com/ITChetumal"

    lateinit var binding: ActivityMainBinding
    //private val AlumnoDB by lazy { AlumnoDatabase.getDatabase(this).alumDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Abrir el fragmento "Login"
        val transaccion = supportFragmentManager.beginTransaction()
        val fragmento = Login()
        transaccion.replace(R.id.container, fragmento)
        transaccion.addToBackStack(null)
        transaccion.commit()


        //Navigation Drawer
        //UI
        mDrawerLayout = findViewById(R.id.drawer_layout)
        mNavigationView = findViewById(R.id.nav_view)
        mToolbar = findViewById(R.id.toolbar)
        //Setup toolbar
        setSupportActionBar(mToolbar)

        toggle = ActionBarDrawerToggle(
            this,
            mDrawerLayout,
            mToolbar,
            0,
            0
        )
        mDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        //Deshabilitar el Navigation Drawer
        /*mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        mToolbar.isVisible = false*/

        mNavigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId){
            R.id.nav_home -> {
                //-----------------------------
                Toast.makeText(this, "Inicio", Toast.LENGTH_SHORT).show()
                val transaccion = supportFragmentManager.beginTransaction()
                val fragmento = Login()
                transaccion.replace(R.id.container, StudentInfo.newInstance(student))
                transaccion.addToBackStack(null)
                transaccion.commit()
                true
            }

            //-----------------------------
            R.id.nav_profile -> {
                Toast.makeText(this, "Mi perfil", Toast.LENGTH_SHORT).show()
                val transaccion = supportFragmentManager.beginTransaction()
                val fragmento = Login()
                transaccion.replace(R.id.container, UserProfile.newInstance(student))
                transaccion.addToBackStack(null)
                transaccion.commit()
                true
            }

            //-----------------------------
            R.id.nav_projects -> {
                if (approved == false){
                    Toast.makeText(this, "No cumples los requisitos", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "Proyectos", Toast.LENGTH_SHORT).show()
                    val transaccion = supportFragmentManager.beginTransaction()
                    val fragmento = ProjectList()
                    transaccion.replace(R.id.container, ProjectList.newInstance(student))
                    transaccion.addToBackStack(null)
                    transaccion.commit()
                }
                true
            }

            //-----------------------------
            R.id.nav_status -> {
                val DATABASE_status by lazy { baseDeDatos.getDatabase(this).statusDao() }
                val DATABASE_project by lazy { baseDeDatos.getDatabase(this).projectDao() }
                lifecycleScope.launch {
                    val infoStatusProject = DATABASE_status.getById(student.id)
                    if (infoStatusProject == null) {
                        makeToast()
                    }else{
                        project = DATABASE_project.getById(infoStatusProject.id_proyecto)
                        val transaccion = supportFragmentManager.beginTransaction()
                        transaccion.replace(R.id.container, Status.newInstance(student, project))
                        transaccion.addToBackStack(null)
                        transaccion.commit()
                    }
                }
                true
            }

            //-----------------------------
            R.id.nav_conocenos -> {
                var link = Uri.parse(URL)
                val i = Intent(Intent.ACTION_VIEW, link)
                startActivity(i)
            }

            //-----------------------------
            R.id.nav_LogOut -> {
                Toast.makeText(this, "Has cerrado sesión", Toast.LENGTH_SHORT).show()
                val transaccion = supportFragmentManager.beginTransaction()
                transaccion.replace(R.id.container, Login())
                transaccion.addToBackStack(null)
                transaccion.commit()
                true
            }
        }
        setTitle(menuItem.title)
        mDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressed()
        }
    }

    @Override
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun lockNavitagationDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        mToolbar.isVisible = false
    }

    fun unLockNavitagationDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        mToolbar.isVisible = true
    }

    fun receiveData(alumno: Alumno){
        student = alumno
        findViewById<TextView>(R.id.tv_userData).text = student.nombres +" "+ student.apellido_paterno
    }

    fun receiveProject(proyecto: Proyecto){
        project = proyecto
        findViewById<TextView>(R.id.tv_userData).text = project.nombre
    }

    fun makeToast(){
        Toast.makeText(this, "No has elegido un proyecto", Toast.LENGTH_SHORT).show()
    }

    fun approved(boolean: Boolean){
        approved = boolean
    }
}