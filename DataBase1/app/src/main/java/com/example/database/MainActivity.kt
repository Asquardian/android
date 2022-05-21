package com.example.database

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.database.models.DataBase

class MainActivity : AppCompatActivity() {
    @SuppressLint("Recycle", "Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tableLayout: TableLayout = findViewById(R.id.table) // создаем таблицу
        val database = DataBase(this, "Students", 1) // создаем бд
        val db = database.readableDatabase // получаем бд
        // курсор нужен, чтобы при помощи его ходить по данным, которые нам пришли
        val cursor = db.query( // делаем запрост
            "STUDENTS", // таблица откуда будем получать данные
            arrayOf("name", "weight", "height", "age"), // какие данные получаем
            null,
            null,
            null,
            null,
            "age" // сортировка по возрасту (может спросить)
        )
        while (cursor.moveToNext()) { // запись в таблицу на экране
            val row = TableRow(this) // строка в таблице

            val name = TextView(this) // ячека для имени
            name.text = cursor.getString(cursor.getColumnIndex("name")) // записываем имя в ячейку

            val weight = TextView(this) // ячейка для веса
            weight.text = cursor.getInt(cursor.getColumnIndex("weight")).toString() // записываем в ячейку

            val height = TextView(this) // ячейка рост
            height.text = cursor.getInt(cursor.getColumnIndex("height")).toString() // запись

            val age = TextView(this) // ячейка возраст
            age.text = cursor.getInt(cursor.getColumnIndex("age")).toString() // запись

            row.addView(name) // добавляем в строку колонки или ячейки хз
            row.addView(weight)
            row.addView(height)
            row.addView(age)

            tableLayout.addView(row) // добавлем в таблицу строку с ячеками

        }
        cursor.close() // закрываем курсор
        db.close();
    }
}