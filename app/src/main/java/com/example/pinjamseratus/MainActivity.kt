package com.example.pinjamseratus

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private val menuTeks = arrayOf("Transaksi", "Hutang", "Kantong")
    private val menuIcon = arrayOf(R.drawable.ic_transaksi, R.drawable.ic_hutang, R.drawable.ic_dompet)

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        val adapter = ViewPagerAdapter(this)
        view_pager.adapter = adapter

        // Set up TabLayout with icons and text
        TabLayoutMediator(tab_layout, view_pager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = menuTeks[position]
                tab.icon = ResourcesCompat.getDrawable(resources, menuIcon[position], null)
            }).attach()
    }

    // Example method to save data to SharedPreferences
    private fun saveDataToPreferences(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    // Example method to retrieve data from SharedPreferences
    private fun getDataFromPreferences(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }
}
