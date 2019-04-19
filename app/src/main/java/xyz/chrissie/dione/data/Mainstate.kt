package xyz.chrissie.dione.data

import android.view.View
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.PersistState
import com.airbnb.mvrx.Uninitialized

/* This is a data class of states/ data i need to persist in the app. Makes use of mavericks*/
data class MainState(
    val title: String = "Hello World",
    @PersistState val count: Int = 0,
    @PersistState val image: String = "",
    @PersistState val showButton: Int = View.GONE,
    val scattered: Async<ByteArray> = Uninitialized,
    val temperature: Async<Int> = Uninitialized
) : MvRxState {
}