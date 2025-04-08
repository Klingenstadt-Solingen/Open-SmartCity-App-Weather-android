package de.osca.android.weather.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.osca.android.essentials.presentation.component.design.BaseCardContainer
import de.osca.android.essentials.presentation.component.design.MasterDesignArgs
import de.osca.android.essentials.utils.extensions.toFormattedString
import de.osca.android.weather.domain.entity.WeatherObserved
import de.osca.android.weather.presentation.args.WeatherDesignArgs

@Composable
fun SectionCurrentTemperature(
    myWeather: WeatherObserved?,
    design: WeatherDesignArgs,
    masterDesignArgs: MasterDesignArgs
) {
    val showLocationInCard = true
    val dateTime = myWeather?.lastObservedDate

    BaseCardContainer(
        text = if(showLocationInCard) "" else myWeather?.shortName.toString(),
        isTitleCentered = !showLocationInCard,
        isTitleBig = !showLocationInCard,
        useBigHeaderSpace = showLocationInCard,
        moduleDesignArgs = design,
        masterDesignArgs = masterDesignArgs
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if(showLocationInCard) {
                Text(
                    text = myWeather?.shortName.toString(),
                    style = masterDesignArgs.captionTextStyle,
                    color = design.mCardTextColor ?: masterDesignArgs.mCardTextColor,
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                )
            }

            Spacer(modifier = Modifier
                .height(16.dp)
            )

            if(dateTime != null) {
                Text(
                    text = String.format(
                        "%02d.%02d.%04d  %02d:%02d Uhr",
                        dateTime.dayOfMonth,
                        dateTime.monthValue,
                        dateTime.year,
                        dateTime.hour,
                        dateTime.minute
                    ),
                    style = masterDesignArgs.normalTextStyle,
                    color = design.mCardTextColor ?: masterDesignArgs.mCardTextColor
                )
            }

            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(vertical = 32.dp)
            ) {
                val unit = myWeather?.values?.temperature?.unit ?: "°C"
                val temp = myWeather?.values?.temperature?.value?.toFormattedString(design.weatherDigits)
                Text(
                    text = "${temp}${unit}",
                    style = masterDesignArgs.reallyBigTextStyle,
                    color = design.mCardTextColor ?: masterDesignArgs.mCardTextColor
                )
            }
        }
    }
}