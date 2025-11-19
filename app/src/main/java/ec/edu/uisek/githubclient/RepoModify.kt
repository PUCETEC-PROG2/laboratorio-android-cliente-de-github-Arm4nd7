package ec.edu.uisek.githubclient

import android.R.attr.description
import android.R.attr.name
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ec.edu.uisek.githubclient.databinding.ActivityRepoModifyBinding
import ec.edu.uisek.githubclient.models.Repo
import ec.edu.uisek.githubclient.models.RepoEditRequest
import ec.edu.uisek.githubclient.models.RepoRequest
import ec.edu.uisek.githubclient.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoModify : AppCompatActivity() {

    private lateinit var repoFormEditBinding: ActivityRepoModifyBinding
    private lateinit var nameRepoActual: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_repo_modify)
        repoFormEditBinding = ActivityRepoModifyBinding.inflate(layoutInflater)
        setContentView(repoFormEditBinding.root)
        repoFormEditBinding.buttonCancelRepo.setOnClickListener { finish() }
        repoFormEditBinding.buttonSaveRepo.setOnClickListener { editRepo() }
    }

    private fun validateEditFormRepo(): Boolean{
        val repoDescription = repoFormEditBinding.descriptionRepoInputForm.text.toString()
        if(repoDescription.isBlank()){
            repoFormEditBinding.descriptionRepoInputForm.error = "La descripcion no puede estar vacia"
            return false
        }

        return true
    }

    private fun editRepo(){
        if(!validateEditFormRepo()){
            return
        }

        nameRepoActual = intent.getStringExtra("name")!!
        val repoDescription = repoFormEditBinding.descriptionRepoInputForm.text.toString()

        val repoEditRequest: RepoEditRequest = RepoEditRequest(
            name = null,
            description = repoDescription
        )

        //vamos a crar el cliente de GITHUB usando API service
        var apiService = RetrofitClient.gitHubApiService
        val call = apiService.patchEditFormRepo(repoEditRequest)

        call.enqueue(object : Callback<Repo>{
            override fun onResponse(call: Call<Repo?>, response: Response<Repo?>) {
                if(response.isSuccessful){
                    Log.d("RepoModify", "El repositorio ${nameRepoActual} fue editado exitosamente")
                    showMessage("El repositorio ${nameRepoActual} fue editado exitosamente")
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

            override fun onFailure(call: retrofit2.Call<Repo?>, t: Throwable) {
                Log.e("RepoModify", "Error de red: ${t.message}")
                showMessage("Error de red: ${t.message}")
            }
        })
    }

    private fun showMessage(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}