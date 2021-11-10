package core

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventViewModel(val apiService: ApiService) {


    val mainScope =  CoroutineScope(Dispatchers.Main)
    val remoteScope = CoroutineScope(Dispatchers.IO)
    val eventMediatorLivedata = MediatorLiveData<EventDataState>()

        fun registerEvent(event : EventEntity) : LiveData<EventDataState>{
            mainScope.launch {
                eventMediatorLivedata.postValue(EventDataState.Loading)
                try {
                    val response = withContext(remoteScope.coroutineContext){
                        apiService.registerEvent(event)
                    }

                    if(response.isSuccessful){
                        eventMediatorLivedata.postValue(EventDataState.Success(response.message()))
                    }else{
                        eventMediatorLivedata.postValue(EventDataState.Error("Error"))
                    }
                }catch (e : Exception){
                    Log.e("EventViewModel", "registerEvent: $e")
                    eventMediatorLivedata.postValue(EventDataState.Error("Event successfully sent with warning!"))
                }
            }
           return  eventMediatorLivedata
        }

}