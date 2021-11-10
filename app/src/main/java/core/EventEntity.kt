package core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventEntity(
    var tag: String = String(),
    var body: String = String(),
    var timestamp: Long = System.currentTimeMillis(),
    var appID: String = String()
) : Parcelable{

    @Parcelize
    data class Result(val accepted : Boolean) : Parcelable
}
