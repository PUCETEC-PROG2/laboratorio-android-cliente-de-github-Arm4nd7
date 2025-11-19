package ec.edu.uisek.githubclient

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ec.edu.uisek.githubclient.databinding.ActivityRepoDeleteBinding
import ec.edu.uisek.githubclient.databinding.ActivityRepoModifyBinding

class RepoDelete : AppCompatActivity() {
    private lateinit var repoConfirmDeleteBinding: ActivityRepoDeleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_repo_delete)
        repoConfirmDeleteBinding = ActivityRepoDeleteBinding.inflate(layoutInflater)
        setContentView(repoConfirmDeleteBinding.root)
        repoConfirmDeleteBinding.buttonCancelRepo.setOnClickListener {
            finish()
        }

        repoConfirmDeleteBinding..setText(
            intent.getStringExtra("description") ?: ""
        )
    }

    private fun deleteRepoActual(){

    }
}