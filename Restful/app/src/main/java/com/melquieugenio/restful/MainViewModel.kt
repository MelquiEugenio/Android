package com.melquieugenio.restful

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.melquieugenio.restful.utils.SingleLiveEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    private var composite = CompositeDisposable()

    private val repoList = MutableLiveData<List<Repo>>()
    val state = SingleLiveEvent<MainActivityState>()

    fun getRepoList(user: String): LiveData<List<Repo>> {
        if (repoList.value == null) {
            makeRequest(user)
        }

        return repoList
    }

    private fun makeRequest(user: String) {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val service: RestService = retrofit.create(RestService::class.java)

        val repos: Observable<Response<List<Repo>?>> = service.listRepos(user)

        composite.add(
            repos.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    state.postValue(MainActivityState.OnLoading)
                }
                .doOnComplete {
                    state.postValue(MainActivityState.OnLoaded)
                }
                .subscribe({
                    //onNext
                    if (it.isSuccessful) {
                        repoList.postValue(it.body())
                    } else {
                        state.postValue(MainActivityState.OnError(Throwable("Error ${it.code()} on API")))
                    }
                }, {
                    //onError
                    state.postValue(MainActivityState.OnError(it))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        composite.clear()
    }
}