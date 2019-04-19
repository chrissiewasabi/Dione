package xyz.chrissie.dione.viewmodels

import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.model.Image
import com.esafirm.rximagepicker.RxImagePicker
import rx.Observable
import xyz.chrissie.dione.MainFragment
import xyz.chrissie.dione.data.MainState

class MainViewModel(initialState: MainState) : BaseViewModel<MainState>(initialState) {
    fun setImage(selectedImage: String) = setState { copy(image = selectedImage) }
    fun captureImage(fragment: MainFragment): Observable<List<Image>> =
        RxImagePicker.getInstance().start(fragment.context, ImagePicker.create(fragment))


}