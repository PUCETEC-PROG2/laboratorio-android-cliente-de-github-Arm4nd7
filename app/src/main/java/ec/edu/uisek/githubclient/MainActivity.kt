package ec.edu.uisek.githubclient

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ec.edu.uisek.githubclient.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //lateinit se inicializa despues propio de kotlin
        private lateinit var binding: ActivityMainBinding
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
    }
}