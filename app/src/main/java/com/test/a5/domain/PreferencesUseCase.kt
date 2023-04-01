package com.test.a5.domain


class PreferencesUseCase(
    private val preferencesRepository: PreferencesRepository
) {

    val uniqID = preferencesRepository.id

    fun saveId(id:String){
        preferencesRepository.saveId(id)
    }
}
