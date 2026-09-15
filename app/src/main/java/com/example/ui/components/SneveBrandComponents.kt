package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.SneveBackground
import com.example.ui.theme.SneveBorder
import com.example.ui.theme.SneveBorderSubtle
import com.example.ui.theme.SneveChrome
import com.example.ui.theme.SneveRed
import com.example.ui.theme.SneveRedDark
import com.example.ui.theme.SneveRedGlow
import com.example.ui.theme.SneveRedLight
import com.example.ui.theme.SneveSilver
import com.example.ui.theme.SneveSurface
import com.example.ui.theme.SneveSurfaceCard
import com.example.ui.theme.SneveSurfaceElevated
import com.example.ui.theme.SneveTextMuted
import com.example.ui.theme.SneveTextPrimary
import com.example.ui.theme.SneveTextSecondary

@Composable
fun SneveRedLetterE(
  modifier: Modifier = Modifier,
  width: Dp = 16.dp,
  height: Dp = 22.dp
) {
  Column(
    modifier = modifier
      .width(width)
      .height(height),
    verticalArrangement = Arrangement.SpaceBetween
  ) {
    val barHeight = (height / 6f).coerceAtLeast(3.2.dp)
    repeat(3) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(barHeight)
          .clip(RoundedCornerShape(1.dp))
          .background(
            brush = Brush.horizontalGradient(
              colors = listOf(SneveRedDark, SneveRed, SneveRedLight)
            )
          )
      )
    }
  }
}

@Composable
fun SneveWordmark(
  modifier: Modifier = Modifier,
  fontSize: TextUnit = 32.sp,
  showTagline: Boolean = true
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center
    ) {
      Text(
        text = "S",
        style = MaterialTheme.typography.displayMedium.copy(
          fontWeight = FontWeight.Black,
          fontSize = fontSize,
          letterSpacing = 1.sp
        ),
        color = SneveSilver
      )
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = "N",
        style = MaterialTheme.typography.displayMedium.copy(
          fontWeight = FontWeight.Black,
          fontSize = fontSize,
          letterSpacing = 1.sp
        ),
        color = SneveSilver
      )
      Spacer(modifier = Modifier.width(6.dp))
      SneveRedLetterE(
        width = (fontSize.value * 0.52f).dp,
        height = (fontSize.value * 0.74f).dp
      )
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = "V",
        style = MaterialTheme.typography.displayMedium.copy(
          fontWeight = FontWeight.Black,
          fontSize = fontSize,
          letterSpacing = 1.sp
        ),
        color = SneveSilver
      )
      Spacer(modifier = Modifier.width(6.dp))
      SneveRedLetterE(
        width = (fontSize.value * 0.52f).dp,
        height = (fontSize.value * 0.74f).dp
      )
    }

    if (showTagline) {
      Spacer(modifier = Modifier.height(5.dp))
      Text(
        text = "MEN'S GROOMING. DELIVERED.",
        style = MaterialTheme.typography.labelSmall.copy(
          letterSpacing = 2.5.sp,
          fontWeight = FontWeight.Bold
        ),
        color = SneveSilver.copy(alpha = 0.95f),
        textAlign = TextAlign.Center
      )
    }
  }
}

@Composable
fun SneveEmblem(
  modifier: Modifier = Modifier,
  size: Dp = 72.dp,
  animatedGlow: Boolean = false
) {
  val glowAlpha = if (animatedGlow) {
    val infiniteTransition = rememberInfiniteTransition(label = "EmblemGlow")
    val alpha by infiniteTransition.animateFloat(
      initialValue = 0.35f,
      targetValue = 0.85f,
      animationSpec = infiniteRepeatable(
        animation = tween(1800, easing = FastOutSlowInEasing),
        repeatMode = RepeatMode.Reverse
      ),
      label = "GlowPulse"
    )
    alpha
  } else {
    0.5f
  }

  Box(
    modifier = modifier.size(size),
    contentAlignment = Alignment.Center
  ) {
    // Outer ambient glowing red halo
    Canvas(modifier = Modifier.size(size * 1.3f)) {
      drawCircle(
        brush = Brush.radialGradient(
          colors = listOf(
            SneveRed.copy(alpha = glowAlpha * 0.45f),
            Color.Transparent
          ),
          center = center,
          radius = this.size.minDimension / 2f
        )
      )
    }

    // Emblem container with high-res icon image
    Box(
      modifier = Modifier
        .size(size)
        .clip(RoundedCornerShape(size * 0.28f))
        .border(
          width = 1.5.dp,
          brush = Brush.linearGradient(
            colors = listOf(
              SneveRed,
              SneveRed.copy(alpha = 0.35f),
              SneveBorder
            )
          ),
          shape = RoundedCornerShape(size * 0.28f)
        ),
      contentAlignment = Alignment.Center
    ) {
      Image(
        painter = painterResource(id = R.drawable.img_sneve_icon),
        contentDescription = "SNEVE Men's Grooming Emblem",
        modifier = Modifier.size(size),
        contentScale = ContentScale.Crop
      )
    }
  }
}

@Composable
fun SneveLogoBanner(
  modifier: Modifier = Modifier,
  height: Dp = 80.dp
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .height(height)
      .clip(RoundedCornerShape(14.dp))
      .border(1.dp, SneveBorder, RoundedCornerShape(14.dp))
  ) {
    Image(
      painter = painterResource(id = R.drawable.img_sneve_logo),
      contentDescription = "SNEVE: Men's Grooming. Delivered.",
      modifier = Modifier
        .fillMaxWidth()
        .height(height),
      contentScale = ContentScale.Crop
    )
  }
}

@Composable
fun SneveBrandHeader(
  modifier: Modifier = Modifier,
  showTagline: Boolean = true,
  emblemSize: Dp = 68.dp,
  animatedGlow: Boolean = false
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    SneveEmblem(size = emblemSize, animatedGlow = animatedGlow)
    Spacer(modifier = Modifier.height(14.dp))
    SneveWordmark(
      fontSize = 30.sp,
      showTagline = showTagline
    )
  }
}

@Composable
fun SnevePrimaryButton(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  icon: ImageVector? = null,
  isLoading: Boolean = false
) {
  Button(
    onClick = onClick,
    modifier = modifier
      .fillMaxWidth()
      .defaultMinSize(minHeight = 52.dp),
    enabled = enabled && !isLoading,
    shape = RoundedCornerShape(14.dp),
    colors = ButtonDefaults.buttonColors(
      containerColor = SneveRed,
      contentColor = Color.White,
      disabledContainerColor = SneveSurfaceElevated,
      disabledContentColor = SneveTextMuted
    ),
    elevation = ButtonDefaults.buttonElevation(
      defaultElevation = 4.dp,
      pressedElevation = 1.dp
    )
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center
    ) {
      if (icon != null) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          modifier = Modifier.size(20.dp),
          tint = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
      }
      Text(
        text = if (isLoading) "Please wait..." else text,
        style = MaterialTheme.typography.labelLarge.copy(
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.5.sp
        )
      )
    }
  }
}

@Composable
fun SneveOutlinedButton(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  icon: ImageVector? = null
) {
  OutlinedButton(
    onClick = onClick,
    modifier = modifier
      .fillMaxWidth()
      .defaultMinSize(minHeight = 50.dp),
    shape = RoundedCornerShape(14.dp),
    colors = ButtonDefaults.outlinedButtonColors(
      contentColor = SneveTextPrimary
    ),
    border = androidx.compose.foundation.BorderStroke(1.dp, SneveBorder)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center
    ) {
      if (icon != null) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          modifier = Modifier.size(18.dp),
          tint = SneveTextSecondary
        )
        Spacer(modifier = Modifier.width(8.dp))
      }
      Text(
        text = text,
        style = MaterialTheme.typography.labelLarge.copy(
          fontWeight = FontWeight.SemiBold
        )
      )
    }
  }
}

@Composable
fun SneveCard(
  modifier: Modifier = Modifier,
  onClick: (() -> Unit)? = null,
  borderColor: Color = SneveBorder,
  backgroundColor: Color = SneveSurfaceCard,
  content: @Composable () -> Unit
) {
  val shape = RoundedCornerShape(18.dp)
  val cardModifier = if (onClick != null) {
    modifier
      .clip(shape)
      .background(backgroundColor)
      .border(1.dp, borderColor, shape)
      .clickable(onClick = onClick)
  } else {
    modifier
      .clip(shape)
      .background(backgroundColor)
      .border(1.dp, borderColor, shape)
  }

  Box(modifier = cardModifier) {
    content()
  }
}

@Composable
fun SneveBadge(
  text: String,
  modifier: Modifier = Modifier,
  color: Color = SneveRed,
  textColor: Color = SneveTextPrimary,
  icon: ImageVector? = null
) {
  Row(
    modifier = modifier
      .clip(RoundedCornerShape(30.dp))
      .background(color.copy(alpha = 0.16f))
      .border(1.dp, color.copy(alpha = 0.4f), RoundedCornerShape(30.dp))
      .padding(horizontal = 8.dp, vertical = 3.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center
  ) {
    if (icon != null) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = color,
        modifier = Modifier.size(12.dp)
      )
      Spacer(modifier = Modifier.width(4.dp))
    }
    Text(
      text = text,
      style = MaterialTheme.typography.labelSmall.copy(
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.3.sp
      ),
      color = textColor
    )
  }
}

@Composable
fun SneveSectionHeader(
  title: String,
  modifier: Modifier = Modifier,
  subtitle: String? = null,
  actionText: String? = null,
  onActionClick: (() -> Unit)? = null
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 8.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column {
      Text(
        text = title,
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.Bold
        ),
        color = SneveTextPrimary
      )
      if (subtitle != null) {
        Text(
          text = subtitle,
          style = MaterialTheme.typography.bodySmall,
          color = SneveTextSecondary
        )
      }
    }
    if (actionText != null && onActionClick != null) {
      Text(
        text = actionText,
        style = MaterialTheme.typography.labelMedium.copy(
          fontWeight = FontWeight.SemiBold
        ),
        color = SneveRed,
        modifier = Modifier
          .clickable(onClick = onActionClick)
          .padding(vertical = 4.dp, horizontal = 6.dp)
      )
    }
  }
}

fun getServiceIcon(iconName: String): ImageVector {
  return when (iconName) {
    "ContentCut" -> Icons.Default.ContentCut
    "Face" -> Icons.Default.Face
    "AutoAwesome" -> Icons.Default.AutoAwesome
    "Spa" -> Icons.Default.Spa
    "Palette" -> Icons.Default.Palette
    "Star" -> Icons.Default.Star
    else -> Icons.Default.ContentCut
  }
}

fun getCategoryDrawable(categoryId: String): Int {
  return when (categoryId) {
    "cat_hair" -> R.drawable.img_cat_haircut
    "cat_beard" -> R.drawable.img_cat_beard
    "cat_packages" -> R.drawable.img_cat_packages
    "cat_treatment" -> R.drawable.img_cat_treatment
    "cat_color" -> R.drawable.img_cat_color
    "cat_wedding" -> R.drawable.img_cat_wedding
    else -> R.drawable.img_sneve_barber_hero
  }
}
