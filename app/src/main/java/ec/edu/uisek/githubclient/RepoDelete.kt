package ec.edu.uisek.githubclient

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ec.edu.uisek.githubclient.databinding.ActivityRepoDeleteBinding
import ec.edu.uisek.githubclient.databinding.ActivityRepoModifyBinding
import ec.edu.uisek.githubclient.models.Repo
import ec.edu.uisek.githubclient.models.RepoEditRequest
import ec.edu.uisek.githubclient.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoDelete : AppCompatActivity() {
    private lateinit var repoConfirmDeleteBinding: ActivityRepoDeleteBinding
    private lateinit var owner: String
    private lateinit var nameRepo: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        repoConfirmDeleteBinding = ActivityRepoDeleteBinding.inflate(layoutInflater)
        setContentView(repoConfirmDeleteBinding.root)
        repoConfirmDeleteBinding.buttonCancelRepo.setOnClickListener {
            finish()
        }

        repoConfirmDeleteBinding.confirmButton.setOnClickListener {
            deleteRepoActual()
        }

        owner = intent.getStringExtra("owner")!!
        nameRepo = intent.getStringExtra("name")!!

        repoConfirmDeleteBinding.repoNameText.setText(
            "Va a eliminar el repositorio ${nameRepo}"
        )
    }

    private fun deleteRepoActual(){

        //vamos a eliminar el cliente de GITHUB usando API service
        val apiService = RetrofitClient.gitHubApiService
        val call = apiService.deleteRepoSelect(owner, nameRepo)


        call.enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    Log.d("RepoDelete", "El repositorio ${nameRepo} fue eliminado exitosamente")
                    showMessage("El repositorio ${nameRepo} fue eliminado exitosamente")
                    finish()
                }else {
                    //no hay respuesta
                    val errorMessage = when(response.code()){
                        401 -> "Error de autenticacion"
                        403 -> "Recurso no permitido"
                        404 -> "Recurso no encontrado"
                        else -> "Error desconociido ${response.code()}: ${response.message()}"
                    }
                    //voy a lanzar un error
                    Log.e("RepoModify", errorMessage)
                    //muestro el mensaje
                    showMessage(errorMessage)
                }
            }

            override fun onFailure(call: retrofit2.Call<Void>, t: Throwable) {
                Log.e("RepoModify", "Error de red: ${t.message}")
                showMessage("Error de red: ${t.message}")
            }
        })
    }

    private fun showMessage(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}