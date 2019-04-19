package xyz.chrissie.dione.viewmodels

import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.model.Image
import com.esafirm.rximagepicker.RxImagePicker
import rx.Observable
import xyz.chrissie.dione.MainFragment
import xyz.chrissie.dione.data.MainState
import java.io.File

class MainViewModel(initialState: MainState) : BaseViewModel<MainState>(initialState) {
    fun setImage(selectedImage: String) = setState { copy(image = selectedImage) }
    fun showButtonStatus(status: Int) = setState { copy(showButton = status) }
    fun captureImage(fragment: MainFragment): Observable<List<Image>> =
        RxImagePicker.getInstance().start(fragment.context, ImagePicker.create(fragment))

    fun getScatteredImage(originalImage: File): io.reactivex.Observable<ByteArray> =
        io.reactivex.Observable.fromCallable<ByteArray> {
            scatterImage(originalImage)
        }

    private fun scatterImage(originalImage: File): ByteArray {
        if (originalImage != null) {
            val byteArray = encryptImage(originalImage.readBytes())
            return byteArray

        }


        return byteArrayOf(0x2E)
    }

    fun encryptImage(arr: ByteArray): ByteArray {
        //using an array rotation . Rotating by a pre-determined factor
        var i: Int = 0
        val temp: Byte = arr[0]
        for (i in 0 until arr.size - 1) {
            arr[i] = arr[i + 1]
            arr[i] = temp

        }

        return arr
    }


}