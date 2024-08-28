package com.paltech.ontheshelf.presentation.onboarding.components

import com.paltech.ontheshelf_prod.R
import com.paltech.ontheshelf.presentation.Dimens.MediumPadding1
import com.paltech.ontheshelf.presentation.Dimens.MediumPadding2
import com.paltech.ontheshelf.presentation.onboarding.Page
import com.paltech.ontheshelf.ui.theme.OnTheShelfTheme

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: Page,
) {
    Column(modifier = modifier.background(color= colorResource(id = R.color.white))) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.60f),
            painter = painterResource(id = page.image),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(MediumPadding1))
        Text(
            modifier = Modifier.padding(horizontal = MediumPadding2),
            text = page.title,
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.display_small)
        )
        Spacer(modifier = Modifier.height(MediumPadding2))
        Text(
            modifier = Modifier.padding(horizontal = MediumPadding2),
            text = page.description,
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.text_medium)
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun OnBoardingPagePreview() {
    OnTheShelfTheme {
        OnBoardingPage(
            page = Page(
                title = "Lorem Ipsum is simply dummy",
                description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                image = R.drawable.onboarding
            )
        )
    }
}