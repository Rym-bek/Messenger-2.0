package com.example.messenger.methods

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import com.example.messenger.R
import com.example.messenger.database.DatabaseHelper
import com.example.messenger.models.Country
import com.google.android.material.textfield.TextInputEditText

class CountryCodeTextWatcher(
    private val context: Context,
    private val textInputEditTextCountry: TextInputEditText,
    private val textInputEditTextNumber: TextInputEditText,
) : TextWatcher {

    var countriesArrayList: ArrayList<Country> = ArrayList()

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
    private lateinit var databaseHelper: DatabaseHelper

    init {
        createDatabase()
    }

    override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
        if(text.isNotEmpty())
        {
            findCountryByCode(text.toString())
            if(countriesArrayList.isNotEmpty())
            {
                setCountryName()
            }
            else{
                textInputEditTextCountry.setText(context.getString(R.string.incorrect_code))
            }
        }
        else
        {
            textInputEditTextCountry.setText(context.getString(R.string.select_country))
        }
    }
    override fun afterTextChanged(s: Editable?) {
    }

    private fun findCountryByCode(text:String)
    {
        countriesArrayList.clear()
        countriesArrayList.addAll(databaseHelper.getCountriesCode(text))
    }

    private fun createDatabase()
    {
        databaseHelper = DatabaseHelper(context)
        databaseHelper.createDataBase()
        databaseHelper.openDataBase()
    }

    private fun setCountryName()
    {
        textInputEditTextCountry.setText(countriesArrayList[0].flag + " " + countriesArrayList[0].name)
        textInputEditTextNumber.requestFocus();
    }


}