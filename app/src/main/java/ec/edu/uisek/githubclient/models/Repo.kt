package ec.edu.uisek.githubclient.models

import android.accessibilityservice.GestureDescription

data class Repo(
    val id: Long,
    val name: String,
    val description: String?,
    val owner: RepoOwner //deserializamos el owner del el objeto que nos da al hacer la llamada al repo de github
)
