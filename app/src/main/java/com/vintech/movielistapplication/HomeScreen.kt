package com.vintech.movielistapplication

import Adapter.ListDataRVAdapter
import Database.GlobarVar
import Interface.cardListener
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vintech.movielistapplication.databinding.ActivityHomeScreenBinding

class HomeScreen : AppCompatActivity(), cardListener {

    private lateinit var viewBinding: ActivityHomeScreenBinding
    private val adapter = ListDataRVAdapter(GlobarVar.listDataFilm, this)
    private var jml: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        checkPermissions()
        listener()
        setupRecyclerView()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        jml = GlobarVar.listDataFilm.size
        if (jml == 0) {
            viewBinding.textView.alpha = 1f
        } else {
            viewBinding.textView.alpha = 0f
        }
        adapter.notifyDataSetChanged()
    }


    private fun listener() {
        viewBinding.addDataFAB.setOnClickListener {
            val myIntent = Intent(this, AddForm::class.java)
            startActivity(myIntent)
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(baseContext)
        viewBinding.listDataRV.layoutManager = layoutManager
        viewBinding.listDataRV.adapter = adapter
    }

    override fun onCardClick(position: Int) {
        val myIntent = Intent(this, DetailMovie::class.java).apply {
            putExtra("position", position)
        }
        startActivity(myIntent)
    }

    override fun onCardClick1(isEdit: Boolean, position: Int) {
        if (!isEdit) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Delete movie")
            builder.setMessage("Are you sure you want to delete this movie?")


            builder.setPositiveButton(android.R.string.yes) { function, which ->
                val snackbar = Snackbar.make(
                    viewBinding.listDataRV,
                    "Movie Deleted",
                    Snackbar.LENGTH_INDEFINITE
                )
                snackbar.setAction("Dismiss") { snackbar.dismiss() }
                snackbar.setActionTextColor(Color.WHITE)
                snackbar.setBackgroundTint(Color.GRAY)
                snackbar.show()
                GlobarVar.listDataFilm.removeAt(position)
                adapter.notifyDataSetChanged()
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(
                    applicationContext,
                    android.R.string.no, Toast.LENGTH_SHORT
                ).show()
            }
            builder.show()


        } else {
            val intent = Intent(this, AddForm::class.java).putExtra("position", position)
            startActivity(intent)
        }
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // Requesting the permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                GlobarVar.STORAGEWrite_PERMISSION_CODE
            )
        } else {
            Toast.makeText(this, "Storage Write Permission already granted", Toast.LENGTH_SHORT)
                .show()
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // Requesting the permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                GlobarVar.STORAGERead_PERMISSION_CODE
            )
        } else {
            Toast.makeText(this, "Storage Read Permission already granted", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == GlobarVar.STORAGERead_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read Storage Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Read Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == GlobarVar.STORAGEWrite_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Write Storage Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Write Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}