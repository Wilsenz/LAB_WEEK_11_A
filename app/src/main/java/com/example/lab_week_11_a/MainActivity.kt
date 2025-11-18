package com.example.lab_week_11_a

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Ambil SettingsStore dari Application
        val settingsStore = (application as SettingsApplication).settingsStore

        // 2. Inisialisasi SettingsViewModel menggunakan Factory
        // Ini mengatasi error 'Cannot infer type' dan 'Unresolved reference'
        val settingsViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            // override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T { // (Untuk API level yang lebih tinggi)
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                // Pastikan settingsStore menjadi parameter constructor
                return SettingsViewModel(settingsStore) as T
            }
        })[SettingsViewModel::class.java]

        // Mendapatkan referensi komponen UI
        val textView = findViewById<TextView>(R.id.activity_main_text_view)
        val editText = findViewById<EditText>(R.id.activity_main_edit_text)
        val button = findViewById<Button>(R.id.activity_main_button)

        // 3. Amati (Observe) LiveData dari ViewModel
        // Ini mengatasi error 'Unresolved reference 'preferenceViewModel'' dan 'Unresolved reference 'it''
        settingsViewModel.textLiveData.observe(this) { text ->
            // Update TextView setiap kali data berubah
            textView.text = text
        }

        // 4. Atur aksi saat tombol diklik
        // Ini mengatasi error 'Unresolved reference 'setOnClickListener'' dan 'Unresolved reference 'saveText''
        button.setOnClickListener {
            // Ambil teks dari EditText
            val input = editText.text.toString()
            // Simpan teks menggunakan ViewModel
            settingsViewModel.saveText(input)
        }
    }
}