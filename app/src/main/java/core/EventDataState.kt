package core

sealed class EventDataState{
    class Success<T>(val data: T) : EventDataState()
    class Error(val error: String) : EventDataState()
    object Loading : EventDataState()

}
