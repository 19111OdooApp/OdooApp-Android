package odoo.miem.android.common.uiKitComponents.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import odoo.miem.android.common.uiKitComponents.R
import odoo.miem.android.core.uiKitTheme.mainVerticalPadding

@OptIn(ExperimentalAnimationApi::class)
@Suppress("MagicNumber")
@Composable
fun OdooSplashScreen() = Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxSize()
        .imePadding()
) {
    var isLogoVisible by rememberSaveable { mutableStateOf(false) }
    var isLoaderVisible by rememberSaveable { mutableStateOf(false) }

    val animationDuration = 1000
    val floatAnimationSpec = tween<Float>(animationDuration)
    val intAnimationSpec = tween<IntSize>(animationDuration)

    LaunchedEffect(Unit) {
        isLogoVisible = true
        delay(1000)
        isLoaderVisible = true
    }

    AnimatedVisibility(
        visible = isLogoVisible,
        enter = expandVertically(
            animationSpec = intAnimationSpec,
            expandFrom = Alignment.Top
        ) + fadeIn(floatAnimationSpec) + scaleIn(floatAnimationSpec),
        exit = shrinkVertically(
            animationSpec = intAnimationSpec,
            shrinkTowards = Alignment.Bottom
        ) + fadeOut(floatAnimationSpec) + scaleOut(floatAnimationSpec)
    ) {
        Image(
            painter = painterResource(R.drawable.logo_odoo),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(192.dp)
        )
    }

    Spacer(modifier = Modifier.height(mainVerticalPadding * 10))

    AnimatedVisibility(
        visible = isLogoVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(48.dp)
        )
    }
}
