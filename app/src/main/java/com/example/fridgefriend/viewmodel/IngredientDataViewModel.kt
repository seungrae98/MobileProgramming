package com.example.fridgefriend.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class IngredientDataViewModel(): ViewModel() {
    var ingredientList = mutableStateListOf<IngredientData>()
        private set

    init {
        // 예시로 몇가지 하드코딩 했지만, DB에서 받아오는 방법도 있음
        val IngredientDataSample1 = IngredientData(
            1,
            "NameOfIngredient1",
            true,
            "20240524"
        )
        ingredientList.add(IngredientDataSample1)

        val IngredientDataSample2 = IngredientData(
            2,
            "NameOfIngredient2",
            false,
            "20240525"
        )
        ingredientList.add(IngredientDataSample2)

        val IngredientDataSample3 = IngredientData(
            3,
            "NameOfIngredient3",
            false,
            "20240526"
        )
        ingredientList.add(IngredientDataSample3)
    }

    // 보유 상태 변경
    fun changeContain(index:Int) {
        ingredientList[index] = ingredientList[index].copy(contain = !ingredientList[index].contain)
    }

    // 유통기한 변경
    fun changeExpireDate(index:Int) {
        ingredientList[index] = ingredientList[index].copy(expireDate = ingredientList[index].expireDate)
    }
}