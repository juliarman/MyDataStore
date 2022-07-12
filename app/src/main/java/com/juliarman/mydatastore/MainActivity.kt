package com.juliarman.mydatastore

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {


    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnSwitch = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(dataStore)

        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(

            MainViewModel::class.java

        )


        mainViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->


            if (isDarkModeActive) {


                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                btnSwitch.isChecked = true

            } else {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                btnSwitch.isChecked = false

            }


        }


        btnSwitch.setOnCheckedChangeListener { _, isChecked: Boolean ->


            mainViewModel.saveThemeSetting(isChecked)


       }

    }
}