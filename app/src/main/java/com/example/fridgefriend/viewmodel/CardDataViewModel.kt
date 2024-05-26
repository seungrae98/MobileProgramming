package com.example.fridgefriend.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class CardDataViewModel(): ViewModel() {
    var cardList = mutableStateListOf<CardData>()
        private set

    init {
        // 예시로 몇가지 하드코딩 했지만, DB에서 받아오는 방법도 있음
        val cardDataSample1 = CardData(
            1,
            "NameOfFood1",
            listOf<String>("chicken", "garlic"),
            "1",
            listOf<String>("http://www.example1.com"),
            false
        )
        cardList.add(cardDataSample1)

        val cardDataSample2 = CardData(
            2,
            "NameOfFood2",
            listOf<String>("pork"),
            "2",
            listOf<String>("http://www.example2.com"),
            false
        )
        cardList.add(cardDataSample2)

        val cardDataSample3 = CardData(
            3,
            "NameOfFood3",
            listOf<String>("milk", "egg"),
            "3",
            listOf<String>("http://www.example3.com"),
            false
        )
        cardList.add(cardDataSample3)

        val cardDataSample4 = CardData(
            4,
            "NameOfFood4",
            listOf<String>("milk", "egg"),
            "4",
            listOf<String>("http://www.example3.com")
        )
        cardList.add(cardDataSample4)

        val cardDataSample5 = CardData(
            5,
            "NameOfFood5",
            listOf<String>("milk", "egg"),
            "5",
            listOf<String>("http://www.example3.com")
        )
        cardList.add(cardDataSample5)
    }

    // 메모 저장하면 메모 변경
    fun changeMemo(index:Int) {
        cardList[index] = cardList[index].copy(memo = cardList[index].memo)
    }

    // 카드 누르면 좋아요 상태 변경
    fun changeLike(index:Int) {
        cardList[index] = cardList[index].copy(like = !cardList[index].like)
    }
}