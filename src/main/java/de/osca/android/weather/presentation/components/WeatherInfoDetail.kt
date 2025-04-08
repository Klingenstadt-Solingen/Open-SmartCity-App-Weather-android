package de.osca.android.weather.presentation.components

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import de.osca.android.essentials.R
import de.osca.android.essentials.presentation.component.design.MasterDesignArgs
import de.osca.android.essentials.presentation.component.design.ModuleDesignArgs

@Composable
fun WeatherInfoDetail(
    masterDesignArgs: MasterDesignArgs,
    moduleDesignArgs: ModuleDesignArgs,
    text: String = "",
    value: String = "",
    @DrawableRes icon: Int = -1,
    imageUrl: String? = null,
    iconSize: Dp = 40.dp,
    @ColorRes iconTint: Int = -1,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = if((moduleDesignArgs.mConstrainHeight ?: masterDesignArgs.mConstrainHeight) > 0.dp)
            Modifier
                .height(moduleDesignArgs.mConstrainHeight ?: masterDesignArgs.mConstrainHeight)
                .then(modifier)
        else
            Modifier
                .then(modifier)
    ) {
        Card(
            shape = RoundedCornerShape(moduleDesignArgs.mShapeCard ?: masterDesignArgs.mShapeCard),
            backgroundColor = moduleDesignArgs.mCardBackColor ?: masterDesignArgs.mCardBackColor,
            elevation = moduleDesignArgs.mCardElevation ?: masterDesignArgs.mCardElevation,
            modifier = if((moduleDesignArgs.mConstrainHeight ?: masterDesignArgs.mConstrainHeight) > 0.dp)
                Modifier
                    .fillMaxWidth()
                    .height(moduleDesignArgs.mConstrainHeight ?: masterDesignArgs.mConstrainHeight)
            else
                Modifier
                    .fillMaxWidth()
        ) {
            Box(modifier = if (onClick != null)
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(moduleDesignArgs.mShapeCard ?: masterDesignArgs.mShapeCard))
                    .clickable {
                        onClick()
                    }
                    .padding(moduleDesignArgs.mContentPaddingForMiniCards ?: masterDesignArgs.mContentPaddingForMiniCards)
            else
                Modifier
                    .fillMaxWidth()
                    .padding(moduleDesignArgs.mContentPaddingForMiniCards ?: masterDesignArgs.mContentPaddingForMiniCards)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        painter =
                        if (imageUrl != null)
                            rememberAsyncImagePainter(imageUrl)
                        else if (icon >= 0)
                            painterResource(id = icon)
                        else
                            painterResource(id = R.drawable.ic_circle),
                        tint = if (iconTint >= 0) colorResource(id = iconTint) else (moduleDesignArgs.mCardTextColor ?: masterDesignArgs.mCardTextColor),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(bottom = 20.dp, top = 10.dp)
                            .height(iconSize)
                            .width(iconSize)
                    )

                    Text(
                        text = text,
                        style = masterDesignArgs.subtitleBoldTextStyle,
                        textAlign = TextAlign.Center,
                        color = moduleDesignArgs.mCardTextColor ?: masterDesignArgs.mCardTextColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (value.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = value,
                            style = masterDesignArgs.subtitleTextStyle,
                            color = moduleDesignArgs.mCardTextColor ?: masterDesignArgs.mCardTextColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}