package com.test.a5.domain

interface PreferencesRepository {

    val id: String
    fun saveId(id:String)

}