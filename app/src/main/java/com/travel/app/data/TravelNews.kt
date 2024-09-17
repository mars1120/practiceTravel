package com.travel.app.data


import com.google.gson.annotations.SerializedName

data class TravelNews(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("total")
    val total: Int // 35
) {
    data class Data(
        @SerializedName("begin")
        val begin: Any, // null
        @SerializedName("description")
        val description: String, // 「水門何時關閉？」「紅黃線是否開放臨停？」「沙包去哪領取？」「明天會不會放假？」臺北市政府觀傳局指出，每年5月1日至11月30日為防汛期，極端氣候導致瞬間強降雨的情況也越趨頻繁。北市水利處LINE官方帳號「戀戀水綠 臺北水利」（@taipeiheo），在汛期期間提供「水情資訊查找服務」，民眾點選「警報器訂閱」並選擇住家行政里，只要住家周邊水情超過警戒值，可即時收到警報資訊，也為民眾提供自主應變相關資訊。觀傳局進一步說，若是臺北市政府LINE官方帳號（@taipei）的好友，在汛期前也會收到防汛準備相關資訊，如：低窪地區民眾一定要注意疏散路線及位置，清溝渠、安裝防水閘門或沙包備用。而豪大雨或颱風期間，也會發布臺北市河岸邊疏散門啟閉狀況、市區內紅黃線臨停範圍、停班停課等市府應變作為等即時資訊，讓民眾防汛資訊不漏接，並做好因應準備。▲颱風期間，自主防災才能保護生命及財產安全。(圖片提供：臺北市政府觀光傳播局)觀傳局說明，使用臺北市政府LINE官方帳號的民眾，可點選圖文選單中「訂閱市政訊息」功能，其中「防汛資訊」包含水情、災情、淹水警示及紅黃線停車等，而其他災情資訊也能以行政區查詢最新資訊，讓民眾在汛期期間，輕鬆掌握各項資訊。觀傳局也提醒，車輛停放於堤外停車場的車主，務必提高警覺，在豪大雨發生時，可先將車輛駛離，或主動註冊北市停管處官網「停車查詢一站式服務」通知服務，在堤外停車場發生緊急事故或疏散必要時，系統將通知有登錄的車主儘速駛離、免於財損。登錄方式可上官網或掃描堤外停車場QR CODE。觀傳局補充，除了市府兩個LINE官方帳號外，民眾可以掌握汛期資訊的管道還有臺北市防災資訊網，或者下載臺北市行動防災APP，同樣可更加即時掌握紅黃線停車和水閘門開關閉資訊，保障自身生命及財產安全。
        @SerializedName("end")
        val end: Any, // null
        @SerializedName("files")
        val files: List<File>,
        @SerializedName("id")
        val id: Int, // 45417
        @SerializedName("links")
        val links: List<Link>,
        @SerializedName("modified")
        val modified: String, // 2023-04-10 10:21:08 +08:00
        @SerializedName("posted")
        val posted: String, // 2023-04-04 09:06:00 +08:00
        @SerializedName("title")
        val title: String, // 水門何時關？北市府設多管道　民眾防汛資訊不漏接
        @SerializedName("url")
        val url: String // https://www.travel.taipei/zh-tw/news/details/45417
    ) {
        data class File(
            @SerializedName("ext")
            val ext: String, // .pdf
            @SerializedName("src")
            val src: String, // https://www.travel.taipei/file/2977
            @SerializedName("subject")
            val subject: String
        )

        data class Link(
            @SerializedName("src")
            val src: String, // https://parkingfee.pma.gov.taipei/Notice
            @SerializedName("subject")
            val subject: String // 臺北市停車查詢官方網站
        )
    }
}