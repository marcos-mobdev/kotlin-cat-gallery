package br.com.appforge.catchallenge


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import br.com.appforge.catchallenge.adapters.GalleryAdapter
import br.com.appforge.catchallenge.databinding.ActivityMainBinding
import br.com.appforge.catchallenge.models.CatImagesResponse
import br.com.appforge.catchallenge.services.ImgurApi
import br.com.appforge.catchallenge.services.RetrofitHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG:String = "info_imgur"
    }

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val retrofit by lazy{
        RetrofitHelper.getApi(ImgurApi::class.java)
    }

    private var images = mutableListOf<String>()
    private lateinit var galleryAdapter: GalleryAdapter
    private var job : Job? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        galleryAdapter = GalleryAdapter()
        binding.rvCats.adapter = galleryAdapter
        binding.rvCats.layoutManager = GridLayoutManager(this, 3)
    }


    override fun onStart() {
        super.onStart()
        getCatImages()
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

    private fun getCatImages() {
        CoroutineScope(Dispatchers.IO).launch {
            var response: Response<CatImagesResponse>?  = null
            try{
                response = retrofit.getImages("cats")
            }catch (e: Exception){
                e.printStackTrace()
                Log.e(TAG, "onStart: ",e )
            }
            if(response != null){
                if(response.isSuccessful){
                    val body = response.body()
                    Log.d(TAG, "Response Body: $body")
                    val data = body?.data
                    Log.d(TAG, "Data: $data")
                    data?.forEach { item->
                        val imageUrl = item.images[0].link
                        if (imageUrl.endsWith(".jpg", ignoreCase = true) ||
                            imageUrl.endsWith(".jpeg", ignoreCase = true) ||
                            imageUrl.endsWith(".png", ignoreCase = true)
                        //|| imageUrl.endsWith(".webp", ignoreCase = true)
                        //||imageUrl.endsWith(".gif", ignoreCase = true)
                        ) {
                            images.add(imageUrl)
                            Log.i(TAG, "Response: $imageUrl")
                        }else{
                            Log.i(TAG, "Not PNG image: ${item.link}")
                        }

                    }
                    Log.d(TAG, "Total Images: ${images.size}") // Adicione aqui

                    withContext(Dispatchers.Main) {
                        galleryAdapter.addList(images)
                    }

                }else{
                    Log.e(
                        TAG,
                        "Response unsuccessfull: ${response.code()} - ${response.errorBody()}",
                    )
                }
            }else{
                Log.e(TAG, "response is null" )
            }

        }
    }


}