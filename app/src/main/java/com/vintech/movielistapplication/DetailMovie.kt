package com.vintech.movielistapplication

import Database.GlobarVar
import Model.Film
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vintech.movielistapplication.databinding.ActivityDetailMovieBinding

class DetailMovie : AppCompatActivity() {
    private lateinit var viewBinding: ActivityDetailMovieBinding
    private var position = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityDetailMovieBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        getintent()
        listener()
    }

    override fun onResume() {
        val movie = GlobarVar.listDataFilm[position]
        super.onResume()

        open(movie)
    }

    private fun getintent() {
        position = intent.getIntExtra("position", -1)
        val movie = GlobarVar.listDataFilm[position]
        if (!movie.imageUri.isEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                baseContext.contentResolver.takePersistableUriPermission(
                    Uri.parse(GlobarVar.listDataFilm[position].imageUri),
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
            }
        }
        open(movie)
    }

    private fun open(movie: Film) {
        viewBinding.imageView.setImageURI(Uri.parse(GlobarVar.listDataFilm[position].imageUri))
        viewBinding.genreTextFill.text = movie.genre.toString()
        viewBinding.titleTextFill.text = movie.title
        viewBinding.ratingTextFill.text = movie.rating
    }

    private fun listener() {

//        viewBinding.deleteButton.setOnClickListener {
//            val builder = AlertDialog.Builder(this)
//            builder.setTitle("Delete movie")
//            builder.setMessage("Are you sure you want to delete this movie?")
//            //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))
//
//            builder.setPositiveButton(android.R.string.yes) { function, which ->
//                val snackbar = Snackbar.make(viewBinding.deleteButton, "Movie Deleted", Snackbar.LENGTH_INDEFINITE)
//                snackbar.setAction("Dismiss") { snackbar.dismiss() }
//                snackbar.setActionTextColor(Color.WHITE)
//                snackbar.setBackgroundTint(Color.GRAY)
//                snackbar.show()
//
//                //remove
//                GlobarVar.listDataFilm.removeAt(position)
//                finish()
//            }
//
//            builder.setNegativeButton(android.R.string.no) { dialog, which ->
//                Toast.makeText(applicationContext,
//                    android.R.string.no, Toast.LENGTH_SHORT).show()
//            }
//            builder.show()
//        }

        viewBinding.toolbarDetail.getChildAt(1).setOnClickListener {
            finish()
        }

//        viewBinding.editButton.setOnClickListener {
//            val myintent = Intent(this, AddForm::class.java).apply {
//                putExtra("position", position)
//            }
//            startActivity(myintent)
//        }
    }
}