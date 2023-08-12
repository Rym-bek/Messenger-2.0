package com.example.messenger.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.messenger.models.Country
import com.example.messenger.models.Slide
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream


class DatabaseHelper(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 52
        const val DATABASE_NAME = "messenger.db"
        var DATABASE_PATH = ""
        private var mDataBase: SQLiteDatabase? = null
        const val TABLE_SLIDES = "slider"
        const val TABLE_COUNTRY = "country"
        const val COLUMN_CODE = "code"
    }

    init {
        DATABASE_PATH = context.applicationInfo.dataDir + "/databases/";
    }

    fun createDataBase() {
        if (!checkDataBase()) {
            this.readableDatabase
            close()
            copyDataBase()
        }
    }

    private fun checkDataBase(): Boolean {
        val dbFile = File(DATABASE_PATH + DATABASE_NAME)
        return dbFile.exists()
    }

    private fun copyDataBase() {
        val mInput: InputStream = context.assets.open(DATABASE_NAME)
        val mOutput: OutputStream = FileOutputStream(DATABASE_PATH + DATABASE_NAME)
        val mBuffer = ByteArray(1024)
        var mLength: Int
        while (mInput.read(mBuffer).also { mLength = it } > 0) mOutput.write(mBuffer, 0, mLength)
        mOutput.flush()
        mOutput.close()
        mInput.close()
    }

    fun openDataBase(){
        mDataBase = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY)
    }

    override fun close() {
        if (mDataBase != null)
        {
            mDataBase!!.close()
        }
        super.close()
    }


    override fun onCreate(p0: SQLiteDatabase?) {
        copyDataBase()
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        onCreate(p0)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onCreate(db)
    }

    //специальные методы

    //получить информацию о слайдах

    fun getSliderInfo(): ArrayList<Slide> {
        val listSlider: ArrayList<Slide> = ArrayList()
        val query = "SELECT * FROM $TABLE_SLIDES"
        val cursor = mDataBase!!.rawQuery(query, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val id = cursor.getInt(0)
            val image = cursor.getString(1)
            val title = cursor.getString(2)
            val text = cursor.getString(3)
            listSlider.add(Slide(id, image, title, text))
            cursor.moveToNext()
        }
        cursor.close()
        return listSlider
    }

    //получить информацию о странах
    fun getCountriesInfo(): ArrayList<Country> {
        val countryArrayList: ArrayList<Country> = ArrayList()
        val query = "SELECT * FROM $TABLE_COUNTRY"
        val cursor = mDataBase!!.rawQuery(query, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val id = cursor.getInt(0)
            val flag = cursor.getString(1)
            val name = cursor.getString(2)
            val code = cursor.getString(3)
            countryArrayList.add(Country(id, flag, name, code))
            cursor.moveToNext()
        }
        cursor.close()
        return countryArrayList
    }

    //найти страну по коду

    fun getCountriesCode(code: String): ArrayList<Country> {
        val countriesList: ArrayList<Country> = ArrayList()
        val query = "SELECT * FROM $TABLE_COUNTRY WHERE $COLUMN_CODE = $code"
        val cursor = mDataBase!!.rawQuery(query, null)
        if (cursor != null) {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val id = cursor.getInt(0)
                val flag = cursor.getString(1)
                val name = cursor.getString(2)
                val code = cursor.getString(3)
                countriesList.add(Country(id, flag, name, code))
                cursor.moveToNext()
            }
        }
        cursor!!.close()
        return countriesList
    }

}