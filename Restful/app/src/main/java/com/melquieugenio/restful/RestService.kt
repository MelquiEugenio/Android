package com.melquieugenio.restful

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RestService {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String?): Observable<Response<List<Repo>?>>
}