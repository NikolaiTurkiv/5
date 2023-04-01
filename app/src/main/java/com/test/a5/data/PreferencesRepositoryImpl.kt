package com.test.a5.data

import android.content.Context
import com.test.a5.domain.PreferencesRepository
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    context: Context
): PreferencesRepository {

    companion object{
        private const val DAGGER_SHARED_PREF = "DAGGER_SHARED_PREF"
        private const val ID = "ID"
     }


    private var sharedPreferences =
        context.getSharedPreferences(DAGGER_SHARED_PREF, Context.MODE_PRIVATE)

    override val id: String
        get() = sharedPreferences.getString(ID,"") ?: ""

    override fun saveId(id: String) {
        sharedPreferences.edit().putString(ID,id).apply()
    }

}