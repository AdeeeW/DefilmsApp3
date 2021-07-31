package com.adewijayanto.defilmsapp3.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.adewijayanto.defilmsapp3.data.remote.ApiResponse
import com.adewijayanto.defilmsapp3.utils.AppExecutors
import com.adewijayanto.defilmsapp3.vo.Resources
import com.adewijayanto.defilmsapp3.vo.StatusMessage


abstract class NetworkBoundResource<ResultType, RequestType>(private val mExecutor: AppExecutors) {

    private val result = MediatorLiveData<Resources<ResultType>>()

    init {
        result.value = Resources.loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadFromDb()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    result.value = Resources.success(newData)
                }
            }
        }
    }

    private fun onFetchFailed() {}

    protected abstract fun loadFromDb(): LiveData<ResultType>
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
    protected abstract fun saveCallResult(data: RequestType)

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {

        val apiResponse = createCall()

        result.addSource(dbSource) { newData ->
            result.value = Resources.loading(newData)
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response.status) {
                StatusMessage.SUCCESS ->
                    mExecutor.diskIO().execute {
                        saveCallResult(response.body)
                        mExecutor.mainThread().execute {
                            result.addSource(loadFromDb()) { newData ->
                                result.value = Resources.success(newData)
                            }
                        }
                    }
                StatusMessage.EMPTY -> mExecutor.mainThread().execute {
                    result.addSource(loadFromDb()) { newData ->
                        result.value = Resources.success(newData)
                    }
                }
                StatusMessage.ERROR -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        result.value = Resources.error(response.message, newData)
                    }
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resources<ResultType>> = result

}