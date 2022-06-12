package com.example.residencia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.residencia.databinding.ActivityMainBinding
import com.example.residencia.fragments.Login

class MainActivity : AppCompatActivity() {

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
    }
}