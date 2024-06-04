package com.fitness.fittapp.Data.response

import com.fitness.fittapp.Domain.Imc

data class ImcResponse(
    val userId:String = "",
    val imcData:String = ""
){
    fun toDomain():Imc{
        return Imc(imcData = imcData)
    }
}