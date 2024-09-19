package com.travel.app.ui.attractionsdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.travel.app.R


@Composable
fun PagerSample(imageList: List<String> = emptyList(), modifier: Modifier = Modifier) {

    val pagerState = rememberPagerState(pageCount = { imageList.size.coerceAtLeast(1) })

    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            beyondViewportPageCount = 1,
            modifier = modifier,

            ) { page ->


            if (imageList.size == 0) {
                Image(
                    painter = painterResource(id = R.drawable.ic_broken_image),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = modifier
                        .fillMaxWidth()

                )
            } else {
                AsyncImage(
                    modifier = modifier
                        .fillMaxWidth(),
                    model = imageList[page],
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    placeholder = painterResource(R.drawable.loading_img),
                )
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }
    }

}

@Composable
fun AttractionsDetailScreen(
    imageList: List<String> = emptyList(),
    name: String,
    modifier: Modifier = Modifier
) {
    Column {
        if (imageList.isEmpty())
            Image(
                painter = painterResource(id = R.drawable.ic_broken_image),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = modifier.fillMaxWidth()
            )
        else
            PagerSample(imageList, modifier = modifier)

        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
    }
}


@Preview(heightDp = 160)
@Composable
fun PagerSamplePreview() {
    PagerSample(modifier = Modifier.height(160.dp))
}

@Preview(heightDp = 160)
@Composable
fun AttractionsDetailScreenPreview() {
    AttractionsDetailScreen(name = "Title", modifier = Modifier.height(160.dp))
}

