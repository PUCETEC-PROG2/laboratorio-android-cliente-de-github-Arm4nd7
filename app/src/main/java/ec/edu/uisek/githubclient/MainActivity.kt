package ec.edu.uisek.githubclient

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ec.edu.uisek.githubclient.databinding.ActivityMainBinding
import ec.edu.uisek.githubclient.models.Repo
import ec.edu.uisek.githubclient.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //comienza la magia
    private lateinit var reposAdapter: ReposAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        //lateinit se inicializa despues propio de kotlin
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecylerView()
        fetchRepositories()
    }

    private fun setupRecylerView(){
        reposAdapter = ReposAdapter()
        binding.repoRecyclerView.adapter = reposAdapter
    }
    //comienza otra magia xd
    private fun fetchRepositories(){
        val apiService = RetrofitClient.gitHubApiService //usamos la instancia de githubapiservice
        //hacmos llamada a la API
        val call = apiService.getRepos()

        call.enqueue(object : Callback<List<Repo>>{
            override fun onResponse(call: Call<List<Repo>?>, response: Response<List<Repo>?>) {
                //vamos a verificar la respuesta
                if(response.isSuccessful){
                    val repos = response.body()
                    //verifico si los repos existen
                    if(repos != null && repos.isNotEmpty()){
                        reposAdapter.updateRepositories(repos)
                    }else {
                      //sis repos
                        showMessage("No existe repositorios a mostrar")
                    }
                }else {
                    //no hay respuesta
                    val errorMessage = when(response.code()){
                        401 -> "Error de autenticacion"
                        403 -> "Recurso no permitido"
                        404 -> "Recurso no encontrado"
                        else -> "Error desconociido ${response.code()}"
                    }
                    //voy a lanzar un error
                    Log.e("MainActivity", errorMessage)
                    //muestro el mensaje
                    showMessage(errorMessage)
                }
            }

            override fun onFailure(call: Call<List<Repo>?>, t: Throwable) {
                //no hay conexion a red
                showMessage("Error de conexion")
                Log.e("MainActivity", "Error de conexion ${t.message}")
            }
        })
    }

    private fun showMessage(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_LONG)
    }
}

