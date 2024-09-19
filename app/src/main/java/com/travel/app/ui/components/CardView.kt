package com.travel.app.ui.components

import android.content.res.Configuration
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.travel.app.R


@Composable
fun NewsElevatedCard(
    title: String,
    desc: String,
    onClickItem: () -> Unit = {},
    replaceLinefeedAndCarriage: Boolean = true,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray, //Card background color
            contentColor = Color.White  //Card content color,e.g.text
        ),
        modifier = modifier
            .fillMaxWidth(), onClick = onClickItem
//            .size(width = 240.dp, height = 100.dp)
    ) {
//        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)

        Text(
            text = if (replaceLinefeedAndCarriage) title.replace("\r", "")
                .replace("\n", "") else title,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
        Text(
            text = if (replaceLinefeedAndCarriage) desc.replace("\r", "")
                .replace("\n", "") else desc,
            modifier = Modifier
                .padding(top = 0.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                .fillMaxWidth(),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ElevatedCardPreview() {
    NewsElevatedCard(
        "Elevated",
        "Elevated\nElevated\nElevated\nElevated\nElevated\nElevated\nElevated\nElevated"
    )
}

@Composable
fun AttractionsElevatedCard(
    title: String,
    desc: String,
    imageUrl: String = "",
    onClickItem: () -> Unit = {},
    replaceLinefeedAndCarriage: Boolean = true,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray, //Card background color
            contentColor = Color.White  //Card content color,e.g.text
        ),
        modifier = modifier
            .fillMaxWidth(), onClick = onClickItem
//            .size(width = 240.dp, height = 100.dp)
    ) {
//        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)

        Row(verticalAlignment = Alignment.CenterVertically) {
            if (imageUrl == "") {
                Image(
                    painter = painterResource(id = R.drawable.ic_broken_image),
                    contentDescription = null,
                    modifier = Modifier
                        .height(120.dp)
                        .width(120.dp)

                )
            } else {
                AsyncImage(
                    modifier = Modifier
                        .height(120.dp)
                        .width(120.dp)
                       ,
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    placeholder = painterResource(R.drawable.loading_img),
                )
            }
            Column (Modifier.weight(1.0f)){
                Text(
                    text = if (replaceLinefeedAndCarriage) title.replace("\r", "")
                        .replace("\n", "") else title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = if (replaceLinefeedAndCarriage) desc.replace("\r", "")
                        .replace("\n", "") else desc,
                    modifier = Modifier
                        .padding(top = 0.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AttractionsCardPreview() {
    AttractionsElevatedCard(
        "Elevated",
        "Elevated\nElevated\nElevated\nElevated\nElevated\nElevated\nElevated\nElevated"
    )
}