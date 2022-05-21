package com.example.database.models

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.github.javafaker.Faker
import kotlin.random.Random.Default.nextInt

//класс базы данных. бд создается один раз, когда устанавливается приложение (это может спросить)
class DataBase(context: Context, name : String,version : Int ) : SQLiteOpenHelper(context, name,null,version) {
    private var id:Int = 0 // id студента

    //втавка студента в таблицу в бд
    private fun studentsInsert(database: SQLiteDatabase, students: Students) {
        val studentValues = ContentValues() // коллекция, которую положим в бд
        studentValues.put("id", students.Id) // кладем id
        studentValues.put("name", students.Name) // кладем имя
        studentValues.put("weight", students.Weight) //кладем вес
        studentValues.put("height", students.Height) // кладем рост
        studentValues.put("age", students.Age) // возраст
        database.insert("STUDENTS", null, studentValues) // вставляем все значения укажанные выше
    }
    //генерация рандомного студента
    private fun randomStudent(): Students {
        val faker = Faker.instance() // генератор, библиотека faker
        val name = faker.name().name() // случайное имя
        val weight = nextInt(40, 120) // вес в указанном диапозоне
        val height = nextInt(150, 210) // рост в указанном диапощоне
        val age = nextInt(17, 40) // возраст в укащанном диапощоне
        id++
        return Students(id, name, weight, height, age) // создаем и возвращаем случайного студента
    }
    // создание бд
    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE STUDENTS (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " + // поле id
                    "name TEXT, " +
                    "weight INTEGER, " +
                    "height INTEGER, " +
                    "age INTEGER," +
                    "photo TEXT);" // фотка не нужна
        )
        for(i in 0..150){
            studentsInsert(database,randomStudent()) // вставляем 150 студентов в бд
        }
    }

    fun fill(database: SQLiteDatabase){
        for(i in 0..150){
            studentsInsert(database,randomStudent()) // вставляем 150 студентов в бд
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}