package com.example.test4.Model

class Repository { // part of model

    private val apiService = RetrofitServiceNetwork.apiService

    suspend fun getPersons(): List<Person> {
        return apiService.getPersons()
    }

}