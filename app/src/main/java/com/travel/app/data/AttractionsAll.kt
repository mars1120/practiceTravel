package com.travel.app.data


import com.google.gson.annotations.SerializedName

data class AttractionsAll(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("total")
    val total: Int // 486
) {
    data class Data(
        @SerializedName("address")
        val address: String, // 112 臺北市北投區承德路六段329號
        @SerializedName("category")
        val category: List<Category>,
        @SerializedName("distric")
        val distric: String, // 北投區
        @SerializedName("elong")
        val elong: Double, // 121.5112005
        @SerializedName("email")
        val email: String,
        @SerializedName("facebook")
        val facebook: String,
        @SerializedName("fax")
        val fax: String,
        @SerializedName("files")
        val files: List<Any>,
        @SerializedName("friendly")
        val friendly: List<Friendly>,
        @SerializedName("id")
        val id: Int, // 1808
        @SerializedName("images")
        val images: List<Image>,
        @SerializedName("introduction")
        val introduction: String, // 承德路與福國路延伸段10米綠帶交叉處，亦即十字軸綠廊交會之重要位置，是一座兼顧滯洪、休憩與生態的濕地公園—洲美蜆仔港公園面積約30,818平方公尺。公園內原有舊雙溪河道除保留河岸護坡，做為抽水站前池，洪泛期提供園區滯洪空間外，平日則為社區休閒濕地公園。沿河道東側規劃一淺灘為草澤溼生區，留設完整不易受人為干擾，可成為陸域鳥類覓食繁衍之良好棲息地。園區即可一邊欣賞河道之草澤溼地生態，並可遠眺七星山及大屯山系之壯麗山形，公園設施含賞景曲徑、親水平台、人行拱橋、休憩廊架、賞遊散步環道等設施。文字來源/公園走透透.臺北新花漾網站
        @SerializedName("links")
        val links: List<Link>,
        @SerializedName("modified")
        val modified: String, // 2024-09-15 05:00:56 +08:00
        @SerializedName("months")
        val months: String, // 01,07,02,08,03,09,04,10,05,11,06,12
        @SerializedName("name")
        val name: String, // 洲美蜆仔港公園
        @SerializedName("name_zh")
        val nameZh: Any, // null
        @SerializedName("nlat")
        val nlat: Double, // 25.1009992
        @SerializedName("official_site")
        val officialSite: String, // https://parks.taipei/parks/m2/pkl_parks_m2C.php?sid=572
        @SerializedName("open_status")
        val openStatus: Int, // 1
        @SerializedName("open_time")
        val openTime: String,
        @SerializedName("remind")
        val remind: String,
        @SerializedName("service")
        val service: List<Service>,
        @SerializedName("staytime")
        val staytime: String,
        @SerializedName("target")
        val target: List<Target>,
        @SerializedName("tel")
        val tel: String, // +886-2-28832130
        @SerializedName("ticket")
        val ticket: String,
        @SerializedName("url")
        val url: String, // https://www.travel.taipei/zh-tw/attraction/details/1808
        @SerializedName("zipcode")
        val zipcode: String // 112
    ) {
        data class Category(
            @SerializedName("id")
            val id: Int, // 16
            @SerializedName("name")
            val name: String // 戶外踏青
        )

        data class Friendly(
            @SerializedName("id")
            val id: Int, // 387
            @SerializedName("name")
            val name: String // 英語友善
        )

        data class Image(
            @SerializedName("ext")
            val ext: String, // .jpg
            @SerializedName("src")
            val src: String, // https://www.travel.taipei/image/67883
            @SerializedName("subject")
            val subject: String
        )

        data class Link(
            @SerializedName("src")
            val src: String, // https://newtaipei.travel/zh-tw/attractions/detail/112490
            @SerializedName("subject")
            val subject: String // 新北市觀光旅遊網
        )

        data class Service(
            @SerializedName("id")
            val id: Int, // 141
            @SerializedName("name")
            val name: String // 無障礙設施
        )

        data class Target(
            @SerializedName("id")
            val id: Int, // 61
            @SerializedName("name")
            val name: String // 親子共學
        )
    }
}