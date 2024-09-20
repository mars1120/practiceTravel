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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
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
    url: String,
    desc: String,
    modifier: Modifier = Modifier
) {
    LazyColumn() {
        item {
            if (imageList.isEmpty())
                Image(
                    painter = painterResource(id = R.drawable.ic_broken_image),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = modifier.fillMaxWidth()
                )
            else
                PagerSample(imageList, modifier = modifier)
        }
        item {
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
        item {
            TextLink(url)
        }
        item {
            Text(
                text = desc,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
        }

    }
}

@Composable
fun TextLink(urllink: String, onClickLink: (String) -> Unit = {}) {
    Text(
        text = AnnotatedString(
            stringResource(R.string.text_url) + ":",
            spanStyle = SpanStyle(
                color = Color.Black
            )
        ) + buildAnnotatedString {
            val link =
                LinkAnnotation.Url(
                    urllink,
                    TextLinkStyles(
                        SpanStyle(
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline
                        )
                    ), linkInteractionListener = {
                        onClickLink(urllink)
                    })
            withLink(link) { append(urllink) }
        }, Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
    )
}


@Preview(heightDp = 160)
@Composable
fun PagerSamplePreview() {
    PagerSample(modifier = Modifier.height(160.dp))
}

@Preview(heightDp = 300)
@Composable
fun AttractionsDetailScreenPreview() {
    AttractionsDetailScreen(
        name = "Title",
        url = "https://test.com",
        desc = "desc",
        modifier = Modifier.height(160.dp)
    )
}

