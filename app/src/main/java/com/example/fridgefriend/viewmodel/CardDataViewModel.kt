package com.example.fridgefriend.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.fridgefriend.R

class CardDataViewModel(): ViewModel() {
    var cardList = mutableStateListOf<CardData>()
        private set

    init {
        val cardDataSample1 = CardData(
            1,
            "참치두부조림",
            listOf("참치캔", "양파", "대파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/4579916"),
            imageResId = R.drawable.img1
        )
        cardList.add(cardDataSample1)

        val cardDataSample2 = CardData(
            2,
            "콩나물무침",
            listOf("콩나물", "대파", "당근"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6867256"),
            imageResId = R.drawable.img2
        )
        cardList.add(cardDataSample2)

        val cardDataSample3 = CardData(
            3,
            "베이컨감자채볶음",
            listOf("감자", "대파", "베이컨", "양파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/7007585"),
            imageResId = R.drawable.img3
        )
        cardList.add(cardDataSample3)

        val cardDataSample4 = CardData(
            4,
            "청경채 된장 무침",
            listOf("청경채"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6901939"),
            imageResId = R.drawable.img4
        )
        cardList.add(cardDataSample4)

        val cardDataSample5 = CardData(
            5,
            "파채",
            listOf("대파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6844934"),
            imageResId = R.drawable.img5
        )
        cardList.add(cardDataSample5)

        val cardDataSample6 = CardData(
            6,
            "오이무침",
            listOf("오이", "양파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6897261"),
            imageResId = R.drawable.img6
        )
        cardList.add(cardDataSample6)

        val cardDataSample7 = CardData(
            7,
            "브로콜리 두부무침",
            listOf("브로콜리", "두부"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6854034"),
            imageResId = R.drawable.img7
        )
        cardList.add(cardDataSample7)

        val cardDataSample8 = CardData(
            8,
            "칠리새우",
            listOf("새우"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6881815"),
            imageResId = R.drawable.img8
        )
        cardList.add(cardDataSample8)

        val cardDataSample9 = CardData(
            9,
            "버터갈릭새우",
            listOf("새우", "파슬리"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6918977#google_vignette"),
            imageResId = R.drawable.img9
        )
        cardList.add(cardDataSample9)

        val cardDataSample10 = CardData(
            10,
            "명란버터우동",
            listOf("쪽파", "달걀", "명란젓", "김"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/7002301"),
            imageResId = R.drawable.img10
        )
        cardList.add(cardDataSample10)

        val cardDataSample11 = CardData(
            11,
            "오징어무국",
            listOf("오징어", "대파", "무"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/1787453"),
            imageResId = R.drawable.img11
        )
        cardList.add(cardDataSample11)

        val cardDataSample12 = CardData(
            12,
            "오삼불고기",
            listOf("오징어", "삼겹살", "양파", "당근", "대파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6854567"),
            imageResId = R.drawable.img12
        )
        cardList.add(cardDataSample12)

        val cardDataSample13 = CardData(
            13,
            "오징어덮밥",
            listOf("오징어", "양파", "대파", "양배추", "당근", "달걀"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6833475"),
            imageResId = R.drawable.img13
        )
        cardList.add(cardDataSample13)

        val cardDataSample14 = CardData(
            14,
            "쭈꾸미덮밥",
            listOf("쭈꾸미", "청양고추", "대파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6923529"),
            imageResId = R.drawable.img14
        )
        cardList.add(cardDataSample14)

        val cardDataSample15 = CardData(
            15,
            "고등어 양념구이",
            listOf("고등어", "청양고추", "대파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6936499"),
            imageResId = R.drawable.img15
        )
        cardList.add(cardDataSample15)

        val cardDataSample16 = CardData(
            16,
            "오징어짬뽕",
            listOf("오징어", "대파", "청양고추", "양파", "양배추", "당근"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6865551"),
            imageResId = R.drawable.img16
        )
        cardList.add(cardDataSample16)

        val cardDataSample17 = CardData(
            17,
            "오징어볶음",
            listOf("오징어", "양파", "대파", "고추", "양배추", "당근"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6903507"),
            imageResId = R.drawable.img17
        )
        cardList.add(cardDataSample17)

        val cardDataSample18 = CardData(
            18,
            "고등어조림",
            listOf("고등어", "무", "대파", "양파", "청양고추"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6857726"),
            imageResId = R.drawable.img18
        )
        cardList.add(cardDataSample18)

        val cardDataSample19 = CardData(
            19,
            "연어 데리야키구이",
            listOf("연어", "대파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/1964300"),
            imageResId = R.drawable.img19
        )
        cardList.add(cardDataSample19)

        val cardDataSample20 = CardData(
            20,
            "새우 볶음밥",
            listOf("달걀", "새우", "대파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6896028"),
            imageResId = R.drawable.img20
        )
        cardList.add(cardDataSample20)

        val cardDataSample21 = CardData(
            21,
            "계란말이",
            listOf("달걀", "대파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6838011"),
            imageResId = R.drawable.img21
        )
        cardList.add(cardDataSample21)

        val cardDataSample22 = CardData(
            22,
            "메추리알조림",
            listOf("메추리알", "스팸", "청양고추"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6916408"),
            imageResId = R.drawable.img22
        )
        cardList.add(cardDataSample22)

        val cardDataSample23 = CardData(
            23,
            "까르보나라",
            listOf("달걀", "베이컨", "파슬리", "양파", "우유", "파마산치즈"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/3845351"),
            imageResId = R.drawable.img23
        )
        cardList.add(cardDataSample23)

        val cardDataSample24 = CardData(
            24,
            "베이컨 달걀 볶음밥",
            listOf("베이컨", "달걀", "대파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6942745"),
            imageResId = R.drawable.img24
        )
        cardList.add(cardDataSample24)

        val cardDataSample25 = CardData(
            25,
            "계란찜",
            listOf("달걀", "대파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6872350"),
            imageResId = R.drawable.img25
        )
        cardList.add(cardDataSample25)

        val cardDataSample26 = CardData(
            26,
            "크림 떡볶이",
            listOf("떡볶이 떡", "베이컨", "체다치즈", "우유", "마늘", "파마산치즈", "양파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6952393"),
            imageResId = R.drawable.img26
        )
        cardList.add(cardDataSample26)

        val cardDataSample27 = CardData(
            27,
            "콘치즈",
            listOf("옥수수통조림", "파슬리", "모짜렐라치즈"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6845227"),
            imageResId = R.drawable.img27
        )
        cardList.add(cardDataSample27)

        val cardDataSample28 = CardData(
            28,
            "에그치즈토스트",
            listOf("식빵", "체다치즈", "달걀"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6939543"),
            imageResId = R.drawable.img28
        )
        cardList.add(cardDataSample28)

        val cardDataSample29 = CardData(
            29,
            "치즈감자전",
            listOf("감자", "모짜렐라치즈"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6863890"),
            imageResId = R.drawable.img29
        )
        cardList.add(cardDataSample29)

        val cardDataSample30 = CardData(
            30,
            "라볶이",
            listOf("떡볶이 떡", "어묵", "양배추", "양파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6665634"),
            imageResId = R.drawable.img30
        )
        cardList.add(cardDataSample30)

        val cardDataSample31 = CardData(
            31,
            "치즈오믈렛",
            listOf("달걀", "체다치즈", "우유", "토마토", "양파", "파슬리"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6842435"),
            imageResId = R.drawable.img31
        )
        cardList.add(cardDataSample31)

        val cardDataSample32 = CardData(
            32,
            "소고기숙주볶음",
            listOf("소고기", "숙주", "대파", "마늘"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6842164"),
            imageResId = R.drawable.img32
        )
        cardList.add(cardDataSample32)

        val cardDataSample33 = CardData(
            33,
            "규동",
            listOf("우삼겹", "양파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6966065"),
            imageResId = R.drawable.img33
        )
        cardList.add(cardDataSample33)

        val cardDataSample34 = CardData(
            34,
            "찜닭",
            listOf("닭고기", "감자", "당근", "대파", "양파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6636695"),
            imageResId = R.drawable.img34
        )
        cardList.add(cardDataSample34)

        val cardDataSample35 = CardData(
            35,
            "차돌박이 부추무침",
            listOf("양파", "차돌박이", "부추"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6876298"),
            imageResId = R.drawable.img35
        )
        cardList.add(cardDataSample35)

        val cardDataSample36 = CardData(
            36,
            "얼큰소고기무국",
            listOf("소고기", "숙주", "대파", "무", "청양고추"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6880161"),
            imageResId = R.drawable.img36
        )
        cardList.add(cardDataSample36)

        val cardDataSample37 = CardData(
            37,
            "소고기미역국",
            listOf("소고기", "미역"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6873683"),
            imageResId = R.drawable.img37
        )
        cardList.add(cardDataSample37)

        val cardDataSample38 = CardData(
            38,
            "스테이크덮밥",
            listOf("소고기", "양파", "쪽파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6868118"),
            imageResId = R.drawable.img38
        )
        cardList.add(cardDataSample38)

        val cardDataSample39 = CardData(
            39,
            "차돌박이 숙주볶음",
            listOf("차돌박이", "숙주", "쪽파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6860481"),
            imageResId = R.drawable.img39
        )
        cardList.add(cardDataSample39)

        val cardDataSample40 = CardData(
            40,
            "찹스테이크",
            listOf("소고기", "피망", "양파", "마늘"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6907592"),
            imageResId = R.drawable.img40
        )
        cardList.add(cardDataSample40)

        val cardDataSample41 = CardData(
            41,
            "차돌박이 된장찌개",
            listOf("차돌박이", "두부", "애호박", "양파", "대파", "청양고추", "팽이버섯"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6922749"),
            imageResId = R.drawable.img41
        )
        cardList.add(cardDataSample41)

        val cardDataSample42 = CardData(
            42,
            "우삼겹 배추볶음",
            listOf("우삼겹", "양배추", "당근", "고추"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6922571"),
            imageResId = R.drawable.img42
        )
        cardList.add(cardDataSample42)

        val cardDataSample43 = CardData(
            43,
            "쇠고기 달걀 덮밥",
            listOf("소고기", "양파", "대파", "달걀", "팽이버섯"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/4195367"),
            imageResId = R.drawable.img43
        )
        cardList.add(cardDataSample43)

        val cardDataSample44 = CardData(
            44,
            "베이컨 떡말이",
            listOf("떡볶이 떡", "베이컨"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6904044"),
            imageResId = R.drawable.img44
        )
        cardList.add(cardDataSample44)

        val cardDataSample45 = CardData(
            45,
            "돼지고기감자조림",
            listOf("감자", "돼지고기 앞다리살", "양파", "청양고추", "대파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/7002427"),
            imageResId = R.drawable.img45
        )
        cardList.add(cardDataSample45)

        val cardDataSample46 = CardData(
            46,
            "고추장삼겹살",
            listOf("양파", "삼겹살"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6838588"),
            imageResId = R.drawable.img46
        )
        cardList.add(cardDataSample46)

        val cardDataSample47 = CardData(
            47,
            "제육볶음",
            listOf("돼지고기 앞다리살", "양파", "당근", "마늘", "대파", "고추"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6845428"),
            imageResId = R.drawable.img47
        )
        cardList.add(cardDataSample47)

        val cardDataSample48 = CardData(
            48,
            "돼지고기마늘조림",
            listOf("돼지고기 앞다리살", "마늘"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6843348"),
            imageResId = R.drawable.img48
        )
        cardList.add(cardDataSample48)

        val cardDataSample49 = CardData(
            49,
            "콩나물불고기",
            listOf("대패삼겹살", "콩나물", "대파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6883016"),
            imageResId = R.drawable.img49
        )
        cardList.add(cardDataSample49)

        val cardDataSample50 = CardData(
            50,
            "부타동",
            listOf("대패삼겹살", "양파", "달걀"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6902207"),
            imageResId = R.drawable.img50
        )
        cardList.add(cardDataSample50)

        val cardDataSample51 = CardData(
            51,
            "동파육",
            listOf("삼겹살", "양파", "대파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6867023"),
            imageResId = R.drawable.img51
        )
        cardList.add(cardDataSample51)

        val cardDataSample52 = CardData(
            52,
            "삼겹살김치말이찜",
            listOf("삼겹살", "대파", "청양고추"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6862218"),
            imageResId = R.drawable.img52
        )
        cardList.add(cardDataSample52)

        val cardDataSample53 = CardData(
            53,
            "치즈불닭",
            listOf("닭가슴살", "모짜렐라치즈", "마늘", "양파", "청양고추"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/4810471"),
            imageResId = R.drawable.img53
        )
        cardList.add(cardDataSample53)

        val cardDataSample54 = CardData(
            54,
            "닭가슴살 꼬치",
            listOf("닭가슴살", "양파", "파프리카"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6956954"),
            imageResId = R.drawable.img54
        )
        cardList.add(cardDataSample54)

        val cardDataSample55 = CardData(
            55,
            "브로콜리 당근 닭가슴살 스크램블",
            listOf("닭가슴살", "당근", "브로콜리", "달걀"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6952367"),
            imageResId = R.drawable.img55
        )
        cardList.add(cardDataSample55)

        val cardDataSample56 = CardData(
            56,
            "초계국수",
            listOf("닭가슴살", "대파", "마늘", "오이", "무"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6855165"),
            imageResId = R.drawable.img56
        )
        cardList.add(cardDataSample56)

        val cardDataSample57 = CardData(
            57,
            "오야꼬동",
            listOf("닭고기", "양파", "달걀", "대파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6870013"),
            imageResId = R.drawable.img57
        )
        cardList.add(cardDataSample57)

        val cardDataSample58 = CardData(
            58,
            "닭장국",
            listOf("닭고기", "당근", "마늘", "양파", "대파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6862524"),
            imageResId = R.drawable.img58
        )
        cardList.add(cardDataSample58)

        val cardDataSample59 = CardData(
            59,
            "닭볶음탕",
            listOf("닭고기", "당근", "대파", "감자", "양파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6857999"),
            imageResId = R.drawable.img59
        )
        cardList.add(cardDataSample59)

        val cardDataSample60 = CardData(
            60,
            "삼계탕",
            listOf("닭고기", "마늘", "대파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6854423"),
            imageResId = R.drawable.img60
        )
        cardList.add(cardDataSample60)

        val cardDataSample61 = CardData(
            61,
            "떡볶이",
            listOf("떡볶이 떡", "달걀", "대파", "어묵"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6846848"),
            imageResId = R.drawable.img61
        )
        cardList.add(cardDataSample61)

        val cardDataSample62 = CardData(
            62,
            "참치 감자 조림",
            listOf("감자", "참치캔", "양파", "대파", "청양고추"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6880341"),
            imageResId = R.drawable.img62
        )
        cardList.add(cardDataSample62)

        val cardDataSample63 = CardData(
            63,
            "참치마요덮밥",
            listOf("참치캔", "양파", "김", "달걀", "쪽파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6879335"),
            imageResId = R.drawable.img63
        )
        cardList.add(cardDataSample63)

        val cardDataSample64 = CardData(
            64,
            "청경채 달걀볶음밥",
            listOf("청경채", "달걀", "대파"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6862612"),
            imageResId = R.drawable.img64
        )
        cardList.add(cardDataSample64)

        val cardDataSample65 = CardData(
            65,
            "두부 청경채 볶음",
            listOf("두부", "청경채", "대파", "청양고추"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6877808"),
            imageResId = R.drawable.img65
        )
        cardList.add(cardDataSample65)

        val cardDataSample66 = CardData(
            66,
            "안동찜닭",
            listOf("닭고기", "감자", "양파", "당근", "오이", "대파", "고추"),
            "empty memo",
            listOf("https://www.10000recipe.com/recipe/6880798"),
            imageResId = R.drawable.img66
        )
        cardList.add(cardDataSample66)
    }

    // 메모 저장하면 메모 변경
    fun changeMemo(index:Int, memo:String) {
        cardList[index] = cardList[index].copy(memo = memo)
    }

    // 카드 누르면 좋아요 상태 변경
    fun changeLike(index:Int) {
        cardList[index] = cardList[index].copy(like = !cardList[index].like)
    }
}
