package com.fitness.fittapp.Data.response

import com.fitness.fittapp.Domain.Model.User

data class UserResponse (
    val uid:String = "",
    val name:String = "",
    val age:String = "",
    val email:String = "",
    val password:String = ""
){
    fun toDomain(): User {
        return User(
            id = uid,
            name = name,
            age = age,
            email = email,
            password = password
        )
    }
}