package com.travel.app.ui.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.travel.app.data.TravelNews

@Composable
fun <T> OverviewScreen(
    data: List<T>,
    onClickSeeAllAccounts: () -> Unit = {},
    onClickSeeAllBills: () -> Unit = {},
    onAccountClick: (String) -> Unit = {},
    contentBuilder: @Composable (T) -> Unit
) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier.padding(8.dp)
    ) {
        items(data.size) { index ->
            contentBuilder(data[index])
        }
        item {
            Text(
                "最新消息",
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        items(data.size) { index ->
            contentBuilder(data[index])
        }
    }
}

//for fragment used
@Composable
fun OverviewScreen(
    data: List<TravelNews.Data>,
    onClickSeeAllAccounts: () -> Unit = {},
    onClickSeeAllBills: () -> Unit = {},
    onAccountClick: (String) -> Unit = {}
) {
    OverviewScreen(
        data = data,
        onClickSeeAllAccounts = onClickSeeAllAccounts,
        onClickSeeAllBills = onClickSeeAllBills,
        onAccountClick = onAccountClick
    ) { newsItem ->
        BodyContent(newsItem.title)
    }
}

// for preview
@Composable
fun PreviewOverviewScreen(
    previewData: List<String>,
    onClickSeeAllAccounts: () -> Unit = {},
    onClickSeeAllBills: () -> Unit = {},
    onAccountClick: (String) -> Unit = {}
) {
    OverviewScreen(
        data = previewData,
        onClickSeeAllAccounts = onClickSeeAllAccounts,
        onClickSeeAllBills = onClickSeeAllBills,
        onAccountClick = onAccountClick
    ) { title ->
        BodyContent(title)
    }
}

@Composable
fun BodyContent(itemInfo: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text("Item #$itemInfo")
    }
}


@Preview(showBackground = true, heightDp = 160)
@Composable
fun OverviewScreenPreview() {
    val travelDestinations: List<String> = listOf(
        "台北101",
        "故宮博物院",
        "中正紀念堂",
        "龍山寺",
        "士林夜市",
        "國立故宮博物院",
        "九份老街",
        "陽明山國家公園",
        "西門町",
        "淡水老街",
        "十分瀑布",
        "台北動物園",
        "松山文創園區",
        "台北市立美術館",
        "國立台灣大學"
    )
    PreviewOverviewScreen(previewData = travelDestinations)
}