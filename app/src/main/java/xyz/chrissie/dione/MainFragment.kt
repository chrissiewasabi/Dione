package xyz.chrissie.dione

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.model.Image
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.content_main.*
import rx.functions.Action1
import xyz.chrissie.dione.viewmodels.MainViewModel
import java.io.File
import java.util.*


class MainFragment : BaseMvRxFragment() {


    private val viewModel: MainViewModel by fragmentViewModel()
    private var images = ArrayList<Image>()
    private lateinit var selectedImage: File

    companion object {
        private const val TAG = "Main Fragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        fbImageChooser.setOnClickListener {
            viewModel.captureImage(this).forEach(action)
        }
        btnScatter.setOnClickListener {
            btnScatter.isClickable = false
            btnScatter.isEnabled = false
            progressBar.visibility = View.VISIBLE
            //perform scattering and return results
            viewModel.getScatteredImage(selectedImage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { result ->
                    progressBar.visibility = View.GONE
                    btnScatter.isEnabled = true
                    img_pixelated.visibility = View.VISIBLE
                    tvStatus.visibility = View.VISIBLE


                }


        }


    }

    override fun invalidate(): Unit = withState(viewModel) { state ->
        val file = File(state.image)
        selectedImage = file
        val imageUri = Uri.fromFile(file)
        Glide.with(this).load(imageUri).into(img_original)
        btnScatter.visibility = state.showButton


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            images = ImagePicker.getImages(data) as ArrayList<Image>
            printImages(images)
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private var action = Action1<List<Image>> { this.printImages(it) }

    private fun printImages(images: List<Image>?) {
        if (images == null) return

        val stringBuffer = StringBuilder()
        var i = 0
        val l = images.size
        while (i < l) {
            stringBuffer.append(images[0].path)
            i++
        }
        viewModel.setImage(stringBuffer.toString())
        viewModel.showButtonStatus(View.VISIBLE)


    }
}