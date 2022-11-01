package com.example.directoryofcountries

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.lifecycleScope
import com.example.directoryofcountries.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.searchButton.setOnClickListener {
            onGetCountry()
        }

        binding.countryNameEditText.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                return@setOnEditorActionListener onGetCountry()
            }
            return@setOnEditorActionListener false
        }
    }

    fun onGetCountry(): Boolean {
        //получаем введенную строку
        val countryName = binding.countryNameEditText.text.toString()

        //корутины - легковесный поток
        lifecycleScope.launch {
            try {
                //запрос к api
                val countries = restCountriesApi.getCountryByName(countryName)
                val country = countries[0]

                binding.countryNameTextView.text = country.name
                binding.capitalTextView.text = country.capital
                binding.populationTextView.text = formatNumber(country.population)
                binding.areaTextView.text = formatNumber(country.area)
                binding.langTextView.text = languagesToString(country.languages)

                loadSvg(binding.imageView, country.flag)

                binding.resultLayout.visibility = View.VISIBLE
                binding.statusLayout.visibility = View.INVISIBLE


            } catch (e: Exception) {
                binding.statusTextView.text = "Страна не найдена"
                binding.statusImageView.setImageResource(R.drawable.ic_baseline_error_24)

                binding.resultLayout.visibility = View.INVISIBLE
                binding.statusLayout.visibility = View.VISIBLE

            }
        }
        return false
    }
}

