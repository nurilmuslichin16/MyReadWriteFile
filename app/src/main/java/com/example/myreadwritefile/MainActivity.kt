package com.example.myreadwritefile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var path: File

    private lateinit var btnNew: Button
    private lateinit var btnOpen:Button
    private lateinit var btnSave: Button

    private lateinit var edtTitle: EditText
    private lateinit var edtFile: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnNew = findViewById(R.id.btn_new)
        btnOpen = findViewById(R.id.btn_open)
        btnSave = findViewById(R.id.btn_save)

        edtTitle = findViewById(R.id.edt_title)
        edtFile = findViewById(R.id.edt_file)

        btnNew.setOnClickListener(this)
        btnOpen.setOnClickListener(this)
        btnSave.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_new -> newFile()
            R.id.btn_open -> showFile()
            R.id.btn_save -> saveFile()
        }
    }

    private fun newFile() {
        edtTitle.setText("")
        edtFile.setText("")
        Toast.makeText(this, "Clearing File", Toast.LENGTH_SHORT).show()
    }

    private fun showFile() {
        val arrayList = ArrayList<String>()
        val path: File = filesDir
        Collections.addAll(arrayList, *path.list() as Array<String>)
        val items = arrayList.toTypedArray<CharSequence>()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pilih file yang diinginkan")
        builder.setItems(items) { dialog, item -> loadData(items[item].toString())}
        val alert = builder.create()
        alert.show()
    }

    private fun loadData(title: String) {
        val fileModel = FileHelper.readFromFile(this, title)
        edtTitle.setText(fileModel.filename)
        edtFile.setText(fileModel.data)
        Toast.makeText(this, "Loading " + fileModel.filename + " data", Toast.LENGTH_SHORT).show()
    }

    private fun saveFile() {
        when {
            edtTitle.text.toString().isEmpty() -> Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show()
            edtFile.text.toString().isEmpty() -> Toast.makeText(this, "Konten harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show()
            else -> {
                val title = edtTitle.text.toString()
                val text = edtFile.text.toString()
                val fileModel = FileModel()
                fileModel.filename = title
                fileModel.data = text
                FileHelper.writeToFile(fileModel, this)
                Toast.makeText(this, "Saving " + fileModel.filename + " file", Toast.LENGTH_SHORT).show()
            }
        }
    }
}