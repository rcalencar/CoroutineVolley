package com.rcalencar.coroutinevolley.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.android.volley.RequestQueue
import com.rcalencar.coroutinevolley.R
import com.rcalencar.coroutinevolley.model.GsonRequest
import com.rcalencar.coroutinevolley.model.Todo
import com.rcalencar.coroutinevolley.model.VolleySingleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TodoViewModel(application: Application): AndroidViewModel(application) {
    private val queue = VolleySingleton.getInstance(application.applicationContext).requestQueue

    val liveData = liveData {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = getData(application.applicationContext.getString(R.string.todo_api_url_1), queue, Todo::class.java)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> = Resource(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): Resource<T> = Resource(status = Status.LOADING, data = data, message = null)
    }
}

suspend fun <T> getData(url: String, queue: RequestQueue, clazz: Class<T>) = suspendCoroutine<T> { continuation ->
    val request = GsonRequest(url, clazz, null,
        { response ->
            continuation.resume(response)
        },
        { error ->
            throw error
        }
    )
    queue.add(request)
}