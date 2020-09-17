package com.melquieugenio.restful

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RestService {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String?): Call<List<Repo>?>
}