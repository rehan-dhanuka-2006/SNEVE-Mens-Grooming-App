package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SneveBrandHeader
import com.example.ui.components.SneveEmblem
import com.example.ui.components.SnevePrimaryButton
import com.example.ui.components.SneveWordmark
import com.example.ui.theme.SneveBackground
import com.example.ui.theme.SneveChrome
import com.example.ui.theme.SneveGold
import com.example.ui.theme.SneveRed
import com.example.ui.theme.SneveRedLight
import com.example.ui.theme.SneveSilver
import com.example.ui.theme.SneveSurface
import com.example.ui.theme.SneveTextMuted
import com.example.ui.theme.SneveTextPrimary
import com.example.ui.theme.SneveTextSecondary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
  onNavigateToAuth: () -> Unit,
  onExploreDirectly: () -> Unit,
  modifier: Modifier = Modifier
) {
  val scaleAnim = remember { Animatable(0.75f) }
  val alphaAnim = remember { Animatable(0f) }
  var showContent by remember { mutableStateOf(false) }

  LaunchedEffect(Unit) {
    alphaAnim.animateTo(1f, animationSpec = tween(700, easing = FastOutSlowInEasing))
    scaleAnim.animateTo(1f, animationSpec = tween(800, easing = FastOutSlowInEasing))
    delay(200)
    showContent = true
  }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(
        brush = Brush.verticalGradient(
          colors = listOf(
            Color(0xFF0D1627),
            SneveBackground,
            Color(0xFF060B12)
          )
        )
      )
      .statusBarsPadding()
      .navigationBarsPadding()
      .padding(24.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(
      modifier = Modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      // Top luxury tag
      Row(
        modifier = Modifier
          .padding(top = 16.dp)
          .alpha(alphaAnim.value),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Icon(
          imageVector = Icons.Default.Shield,
          contentDescription = null,
          tint = SneveGold,
          modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "OFFICIAL SNEVE ECOSYSTEM",
          style = MaterialTheme.typography.labelSmall.copy(
            letterSpacing = 1.8.sp,
            fontWeight = FontWeight.Bold
          ),
          color = SneveGold
        )
      }

      // Center Brand Identity
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .scale(scaleAnim.value)
          .alpha(alphaAnim.value)
      ) {
        SneveEmblem(
          size = 120.dp,
          animatedGlow = true
        )
        Spacer(modifier = Modifier.height(26.dp))
        SneveWordmark(
          fontSize = 38.sp,
          showTagline = true
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
          text = "Master barbers & precision luxury grooming delivered directly at your doorstep.",
          style = MaterialTheme.typography.bodyMedium,
          color = SneveTextSecondary,
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(horizontal = 20.dp)
        )
      }

      // Bottom interactive action controls
      AnimatedVisibility(
        visible = showContent,
        enter = fadeIn(tween(400)) + slideInVertically(tween(400)) { it / 2 }
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          SnevePrimaryButton(
            text = "Sign In or Register",
            icon = Icons.AutoMirrored.Filled.ArrowForward,
            onClick = onNavigateToAuth,
            modifier = Modifier.testTag("splash_get_started_button")
          )
          Spacer(modifier = Modifier.height(12.dp))
          Text(
            text = "Explore App as Guest",
            style = MaterialTheme.typography.labelLarge.copy(
              fontWeight = FontWeight.SemiBold,
              letterSpacing = 0.5.sp
            ),
            color = SneveRedLight,
            modifier = Modifier
              .testTag("splash_guest_explore_button")
              .padding(8.dp)
              .clickable(onClick = onExploreDirectly)
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Mumbai • Bengaluru • Delhi NCR • Hyderabad • Pune",
            style = MaterialTheme.typography.labelSmall,
            color = SneveTextMuted,
            textAlign = TextAlign.Center
          )
        }
      }
    }
  }
}
