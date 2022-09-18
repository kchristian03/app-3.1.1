package com.vintech.movielistapplication

import Database.GlobarVar
import Model.Film
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.vintech.movielistapplication.databinding.ActivityFormBinding

class AddForm : AppCompatActivity() {

    private lateinit var viewBinding: ActivityFormBinding
    private lateinit var film: Film
    private var position = -1
    var image: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityFormBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        GetIntent()
        Listener()


    }

    private fun GetIntent() {
        position = intent.getIntExtra("position", -1)
        if (position != -1) {
            val film = GlobarVar.listDataFilm[position]
            viewBinding.toolbarForm.title = "Edit Animal"
            viewBinding.addButton.text = "Save"
            viewBinding.imageView.setImageURI(Uri.parse(GlobarVar.listDataFilm[position].imageUri))
            viewBinding.titleTextInputLayout.editText?.setText(film.title)
            viewBinding.ratingTextInputLayout.editText?.setText(film.rating)
            viewBinding.genreTextInputLayout.editText?.setText(film.genre.toString())
        }

    }

    private val GetResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {   // APLIKASI GALLERY SUKSES MENDAPATKAN IMAGE
                val uri = it.data?.data
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if (uri != null) {
                        baseContext.contentResolver.takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        )
                    }
                }// GET PATH TO IMAGE FROM GALLEY
                viewBinding.imageView.setImageURI(uri)  // MENAMPILKAN DI IMAGE VIEW
                image = uri.toString()
            }
        }


    private fun Listener() {
        viewBinding.imageView.setOnClickListener {
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)
        }

        viewBinding.addButton.setOnClickListener {
            var title = viewBinding.titleTextInputLayout.editText?.text.toString().trim()
            var rating = viewBinding.ratingTextInputLayout.editText?.text.toString().trim()
            var genre = 0

            film = Film(title, rating, genre)
            checker()
        }
        viewBinding.toolbarForm.getChildAt(1).setOnClickListener {
            finish()
        }
    }

    private fun checker() {
        var isCompleted: Boolean = true

        if (film.title!!.isEmpty()) {
            viewBinding.titleTextInputLayout.error = "Nama Hewan Tidak Boleh Kosong"
            isCompleted = false
        } else {
            viewBinding.titleTextInputLayout.error = ""
        }

        if (film.rating!!.isEmpty()) {
            viewBinding.ratingTextInputLayout.error = "Jenis Hewan Tidak Boleh Kosong"
            isCompleted = false
        } else {
            viewBinding.ratingTextInputLayout.error = ""
        }





        film.imageUri = image.toString()
//        if(movie.imageUri!!.isEmpty()){
//            //toast
//            Toast.makeText(this, "Picture cannot be empty", Toast.LENGTH_LONG).show()
//            isCompleted = false
//        }

        if (viewBinding.genreTextInputLayout.editText?.text.toString()
                .isEmpty() || viewBinding.genreTextInputLayout.editText?.text.toString().toInt() < 0
        ) {
            viewBinding.genreTextInputLayout.error = "Usia Hewan Tidak Boleh Kosong/0"
            isCompleted = false
        }

        if (isCompleted) {
            if (position == -1) {
                film.genre = viewBinding.genreTextInputLayout.editText?.text.toString().toInt()
                GlobarVar.listDataFilm.add(film)

            } else {
                film.genre = viewBinding.genreTextInputLayout.editText?.text.toString().toInt()
                GlobarVar.listDataFilm[position] = film
            }
            finish()
        }
    }
}