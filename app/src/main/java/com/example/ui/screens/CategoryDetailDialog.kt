package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.SneveRepository
import com.example.model.ServiceCategory
import com.example.model.ServiceItem
import com.example.ui.components.SneveBadge
import com.example.ui.components.SneveCard
import com.example.ui.components.getCategoryDrawable
import com.example.ui.components.getServiceIcon
import com.example.ui.theme.SneveBorder
import com.example.ui.theme.SneveChrome
import com.example.ui.theme.SneveGold
import com.example.ui.theme.SneveRed
import com.example.ui.theme.SneveRedLight
import com.example.ui.theme.SneveSilver
import com.example.ui.theme.SneveSurfaceCard
import com.example.ui.theme.SneveSurfaceElevated
import com.example.ui.theme.SneveTextMuted
import com.example.ui.theme.SneveTextPrimary
import com.example.ui.theme.SneveTextSecondary

@Composable
fun CategoryDetailDialog(
  category: ServiceCategory,
  onDismiss: () -> Unit,
  onSelectService: (ServiceItem) -> Unit
) {
  val relatedServices = SneveRepository.services.filter { it.categoryId == category.id }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.95f)
        .clip(RoundedCornerShape(24.dp))
        .border(1.dp, SneveBorder, RoundedCornerShape(24.dp)),
      color = SneveSurfaceElevated
    ) {
      Column(
        modifier = Modifier
          .verticalScroll(rememberScrollState())
      ) {
        // Hero Image Header
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
        ) {
          Image(
            painter = painterResource(id = getCategoryDrawable(category.id)),
            contentDescription = category.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
          )

          // Dark Gradient Vignette
          Box(
            modifier = Modifier
              .fillMaxSize()
              .background(
                brush = Brush.verticalGradient(
                  colors = listOf(
                    Color(0x55000000),
                    Color(0x880A0B0E),
                    SneveSurfaceElevated
                  )
                )
              )
          )

          // Close button
          IconButton(
            onClick = onDismiss,
            modifier = Modifier
              .align(Alignment.TopEnd)
              .padding(12.dp)
              .size(36.dp)
              .clip(CircleShape)
              .background(Color(0x88000000))
          ) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
          }

          // Bottom Bar inside header
          Row(
            modifier = Modifier
              .align(Alignment.BottomStart)
              .fillMaxWidth()
              .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(SneveRed),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = getServiceIcon(category.iconName),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(22.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = category.title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = SneveTextPrimary
              )
              Text(
                text = "${category.proCount} Master Barbers Available",
                style = MaterialTheme.typography.bodySmall,
                color = SneveRedLight
              )
            }
          }
        }

        Column(modifier = Modifier.padding(20.dp)) {
          Text(
            text = category.tagLine,
            style = MaterialTheme.typography.bodyMedium,
            color = SneveTextSecondary
          )

        Spacer(modifier = Modifier.height(16.dp))

        // Subcategories chips
        Text(
          text = "SPECIALTY SUBDIVISIONS",
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          ),
          color = SneveTextMuted
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          category.subcategories.forEach { sub ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(SneveSurfaceCard)
                .padding(horizontal = 12.dp, vertical = 8.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = sub,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                color = SneveTextPrimary
              )
              SneveBadge(text = "Available", color = SneveRed)
            }
          }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Divider(color = SneveBorder)
        Spacer(modifier = Modifier.height(16.dp))

        // Featured services in this category
        Text(
          text = "CURATED SIGNATURE SERVICES",
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          ),
          color = SneveTextMuted
        )
        Spacer(modifier = Modifier.height(10.dp))

        if (relatedServices.isNotEmpty()) {
          relatedServices.forEach { srv ->
            SneveCard(
              modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
              onClick = {
                onDismiss()
                onSelectService(srv)
              }
            ) {
              Row(
                modifier = Modifier
                  .padding(14.dp)
                  .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = srv.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = SneveTextPrimary
                  )
                  Spacer(modifier = Modifier.height(4.dp))
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                      Icons.Default.Star,
                      contentDescription = null,
                      tint = SneveGold,
                      modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                      text = "${srv.rating} • ${srv.duration}",
                      style = MaterialTheme.typography.bodySmall,
                      color = SneveTextSecondary
                    )
                  }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(horizontalAlignment = Alignment.End) {
                  Text(
                    text = srv.price,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = SneveRed
                  )
                  Text(
                    text = "Tap to book",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = SneveTextMuted
                  )
                }
              }
            }
          }
        } else {
          // Dynamic category instant dispatch option
          SneveCard(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
              val instantService = ServiceItem(
                id = "instant_" + category.id,
                categoryId = category.id,
                title = "Standard Priority ${category.title}",
                categoryName = category.title,
                duration = "Direct Barber Dispatch",
                price = category.startingPrice,
                rating = 4.95,
                reviewCount = 88,
                description = "Direct booking for ${category.title}. Certified master barber dispatches to your registered address with complete luxury grooming station.",
                highlights = listOf("Vetted SNEVE Master Barber", "Express 60-min arrival", "All sanitised tools & cape included"),
                isPopular = true
              )
              onDismiss()
              onSelectService(instantService)
            }
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "Express ${category.shortName} Dispatch",
                  style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                  color = SneveTextPrimary
                )
                Text(
                  text = category.startingPrice,
                  style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                  color = SneveRed
                )
              }
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "Tap to request on-demand master barber dispatch for this service.",
                style = MaterialTheme.typography.bodySmall,
                color = SneveTextSecondary
              )
            }
          }
        }
      }
    }
  }
}
}
