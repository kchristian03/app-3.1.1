package com.vintech.movielistapplication

import Adapter.ListDataRVAdapter
import Database.GlobarVar
import Interface.cardListener
import Model.Film
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.vintech.movielistapplication.databinding.ActivityHomeScreenBinding

class HomeScreen : AppCompatActivity(), cardListener {

    private lateinit var viewBinding: ActivityHomeScreenBinding
    private val listDataFilm = ArrayList<Film>()
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