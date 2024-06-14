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
            "소고기",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample1)

        val IngredientDataSample2 = IngredientData(
            2,
            "차돌박이",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample2)

        val IngredientDataSample3 = IngredientData(
            3,
            "우삼겹",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample3)

        val IngredientDataSample4 = IngredientData(
            4,
            "돼지고기 앞다리살",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample4)

        val IngredientDataSample5 = IngredientData(
            5,
            "삼겹살",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample5)

        val IngredientDataSample6 = IngredientData(
            6,
            "대패삼겹살",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample6)

        val IngredientDataSample7 = IngredientData(
            7,
            "쭈꾸미",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample7)

        val IngredientDataSample8 = IngredientData(
            8,
            "닭고기",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample8)

        val IngredientDataSample9 = IngredientData(
            9,
            "닭가슴살",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample9)

        val IngredientDataSample10 = IngredientData(
            10,
            "부추",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample10)

        val IngredientDataSample11 = IngredientData(
            11,
            "콩나물",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample11)

        val IngredientDataSample12 = IngredientData(
            12,
            "대파",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample12)

        val IngredientDataSample13 = IngredientData(
            13,
            "양파",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample13)

        val IngredientDataSample14 = IngredientData(
            14,
            "감자",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample14)

        val IngredientDataSample15 = IngredientData(
            15,
            "청경채",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample15)

        val IngredientDataSample16 = IngredientData(
            16,
            "쪽파",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample16)

        val IngredientDataSample17 = IngredientData(
            17,
            "오이",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample17)

        val IngredientDataSample18 = IngredientData(
            18,
            "브로콜리",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample18)

        val IngredientDataSample19 = IngredientData(
            19,
            "파슬리",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample19)

        val IngredientDataSample20 = IngredientData(
            20,
            "애호박",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample20)

        val IngredientDataSample21 = IngredientData(
            21,
            "청양고추",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample21)

        val IngredientDataSample22 = IngredientData(
            22,
            "무",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample22)

        val IngredientDataSample23 = IngredientData(
            23,
            "당근",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample23)

        val IngredientDataSample24 = IngredientData(
            24,
            "마늘",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample24)

        val IngredientDataSample25 = IngredientData(
            25,
            "토마토",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample25)

        val IngredientDataSample26 = IngredientData(
            26,
            "숙주",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample26)

        val IngredientDataSample27 = IngredientData(
            27,
            "피망",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample27)

        val IngredientDataSample28 = IngredientData(
            28,
            "팽이버섯",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample28)

        val IngredientDataSample29 = IngredientData(
            29,
            "고추",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample29)

        val IngredientDataSample30 = IngredientData(
            30,
            "파프리카",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample30)

        val IngredientDataSample31 = IngredientData(
            31,
            "양배추",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample31)

        val IngredientDataSample32 = IngredientData(
            32,
            "새우",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample32)

        val IngredientDataSample33 = IngredientData(
            33,
            "명란젓",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample33)

        val IngredientDataSample34 = IngredientData(
            34,
            "오징어",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample34)

        val IngredientDataSample35 = IngredientData(
            35,
            "고등어",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample35)

        val IngredientDataSample36 = IngredientData(
            36,
            "연어",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample36)

        val IngredientDataSample37 = IngredientData(
            37,
            "미역",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample37)

        val IngredientDataSample38 = IngredientData(
            38,
            "달걀",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample38)

        val IngredientDataSample39 = IngredientData(
            39,
            "메추리알",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample39)

        val IngredientDataSample40 = IngredientData(
            40,
            "우유",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample40)

        val IngredientDataSample41 = IngredientData(
            41,
            "파마산치즈",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample41)

        val IngredientDataSample42 = IngredientData(
            42,
            "체다치즈",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample42)

        val IngredientDataSample43 = IngredientData(
            43,
            "모짜렐라치즈",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample43)

        val IngredientDataSample44 = IngredientData(
            44,
            "참치캔",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample44)

        val IngredientDataSample45 = IngredientData(
            45,
            "두부",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample45)

        val IngredientDataSample46 = IngredientData(
            46,
            "김",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample46)

        val IngredientDataSample47 = IngredientData(
            47,
            "스팸",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample47)

        val IngredientDataSample48 = IngredientData(
            48,
            "베이컨",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample48)

        val IngredientDataSample49 = IngredientData(
            49,
            "떡볶이 떡",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample49)

        val IngredientDataSample50 = IngredientData(
            50,
            "옥수수통조림",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample50)

        val IngredientDataSample51 = IngredientData(
            51,
            "식빵",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample51)

        val IngredientDataSample52 = IngredientData(
            52,
            "어묵",
            false,
            "00000000"
        )
        ingredientList.add(IngredientDataSample52)

    }

    // 보유 상태 변경
    fun changeContain(index:Int) {
        ingredientList[index] = ingredientList[index].copy(contain = !ingredientList[index].contain)
    }

    // 유통기한 변경
    fun changeExpireDate(index:Int, expireDate:String) {
        ingredientList[index] = ingredientList[index].copy(expireDate = expireDate)
    }
}