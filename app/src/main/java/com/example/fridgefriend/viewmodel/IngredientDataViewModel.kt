package com.example.fridgefriend.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class IngredientDataViewModel(): ViewModel() {
    var ingredientList = mutableStateListOf<IngredientData>()
        private set

    init {
        // 예시로 몇가지 하드코딩 했지만, DB에서 받아오는 방법도 있음
        val IngredientDataSample1 = IngredientData(
            201,
            "소고기",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample1)

        val IngredientDataSample2 = IngredientData(
            202,
            "차돌박이",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample2)

        val IngredientDataSample3 = IngredientData(
            203,
            "우삼겹",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample3)

        val IngredientDataSample4 = IngredientData(
            204,
            "돼지고기 앞다리살",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample4)

        val IngredientDataSample5 = IngredientData(
            205,
            "삼겹살",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample5)

        val IngredientDataSample6 = IngredientData(
            206,
            "대패삼겹살",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample6)

        val IngredientDataSample7 = IngredientData(
            207,
            "쭈꾸미",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample7)

        val IngredientDataSample8 = IngredientData(
            208,
            "닭고기",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample8)

        val IngredientDataSample9 = IngredientData(
            209,
            "닭가슴살",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample9)

        val IngredientDataSample10 = IngredientData(
            210,
            "부추",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample10)

        val IngredientDataSample11 = IngredientData(
            211,
            "콩나물",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample11)

        val IngredientDataSample12 = IngredientData(
            212,
            "대파",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample12)

        val IngredientDataSample13 = IngredientData(
            213,
            "양파",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample13)

        val IngredientDataSample14 = IngredientData(
            214,
            "감자",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample14)

        val IngredientDataSample15 = IngredientData(
            215,
            "청경채",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample15)

        val IngredientDataSample16 = IngredientData(
            216,
            "쪽파",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample16)

        val IngredientDataSample17 = IngredientData(
            217,
            "오이",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample17)

        val IngredientDataSample18 = IngredientData(
            218,
            "브로콜리",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample18)

        val IngredientDataSample19 = IngredientData(
            219,
            "파슬리",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample19)

        val IngredientDataSample20 = IngredientData(
            220,
            "애호박",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample20)

        val IngredientDataSample21 = IngredientData(
            221,
            "청양고추",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample21)

        val IngredientDataSample22 = IngredientData(
            222,
            "무",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample22)

        val IngredientDataSample23 = IngredientData(
            223,
            "당근",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample23)

        val IngredientDataSample24 = IngredientData(
            224,
            "마늘",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample24)

        val IngredientDataSample25 = IngredientData(
            225,
            "토마토",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample25)

        val IngredientDataSample26 = IngredientData(
            226,
            "숙주",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample26)

        val IngredientDataSample27 = IngredientData(
            227,
            "피망",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample27)

        val IngredientDataSample28 = IngredientData(
            228,
            "팽이버섯",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample28)

        val IngredientDataSample29 = IngredientData(
            229,
            "고추",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample29)

        val IngredientDataSample30 = IngredientData(
            230,
            "파프리카",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample30)

        val IngredientDataSample31 = IngredientData(
            231,
            "양배추",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample31)

        val IngredientDataSample32 = IngredientData(
            232,
            "새우",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample32)

        val IngredientDataSample33 = IngredientData(
            233,
            "명란젓",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample33)

        val IngredientDataSample34 = IngredientData(
            234,
            "오징어",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample34)

        val IngredientDataSample35 = IngredientData(
            235,
            "고등어",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample35)

        val IngredientDataSample36 = IngredientData(
            236,
            "연어",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample36)

        val IngredientDataSample37 = IngredientData(
            237,
            "미역",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample37)

        val IngredientDataSample38 = IngredientData(
            238,
            "달걀",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample38)

        val IngredientDataSample39 = IngredientData(
            239,
            "메추리알",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample39)

        val IngredientDataSample40 = IngredientData(
            240,
            "우유",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample40)

        val IngredientDataSample41 = IngredientData(
            241,
            "파마산치즈",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample41)

        val IngredientDataSample42 = IngredientData(
            242,
            "체다치즈",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample42)

        val IngredientDataSample43 = IngredientData(
            243,
            "모짜렐라치즈",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample43)

        val IngredientDataSample44 = IngredientData(
            244,
            "참치캔",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample44)

        val IngredientDataSample45 = IngredientData(
            245,
            "두부",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample45)

        val IngredientDataSample46 = IngredientData(
            246,
            "김",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample46)

        val IngredientDataSample47 = IngredientData(
            247,
            "스팸",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample47)

        val IngredientDataSample48 = IngredientData(
            248,
            "베이컨",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample48)

        val IngredientDataSample49 = IngredientData(
            249,
            "떡볶이 떡",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample49)

        val IngredientDataSample50 = IngredientData(
            250,
            "옥수수통조림",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample50)

        val IngredientDataSample51 = IngredientData(
            251,
            "식빵",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample51)

        val IngredientDataSample52 = IngredientData(
            252,
            "어묵",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample52)

    }

    // 유통기한 변경
    fun changeExpireDate(index:Int, expireDate:String) {
        ingredientList[index] = ingredientList[index].copy(expireDate = expireDate)
    }
}