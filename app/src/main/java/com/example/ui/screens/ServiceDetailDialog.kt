package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.ServiceItem
import com.example.ui.components.SneveBadge
import com.example.ui.components.SnevePrimaryButton
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
fun ServiceDetailDialog(
  service: ServiceItem,
  onDismiss: () -> Unit,
  onConfirmBooking: (ServiceItem) -> Unit
) {
  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.94f)
        .clip(RoundedCornerShape(24.dp))
        .border(1.dp, SneveBorder, RoundedCornerShape(24.dp)),
      color = SneveSurfaceElevated
    ) {
      Column(
        modifier = Modifier
          .padding(22.dp)
          .verticalScroll(rememberScrollState())
      ) {
        // Header with close button
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          SneveBadge(
            text = service.categoryName,
            color = SneveRed
          )
          IconButton(
            onClick = onDismiss,
            modifier = Modifier.size(36.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Close",
              tint = SneveTextSecondary
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Title
        Text(
          text = service.title,
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
          ),
          color = SneveTextPrimary
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Quick meta row (Rating, duration, price)
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(SneveSurfaceCard)
            .padding(horizontal = 14.dp, vertical = 10.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Star,
              contentDescription = null,
              tint = SneveGold,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "${service.rating} (${service.reviewCount})",
              style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
              color = SneveTextPrimary
            )
          }

          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.AccessTime,
              contentDescription = null,
              tint = SneveTextSecondary,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = service.duration,
              style = MaterialTheme.typography.bodySmall,
              color = SneveTextSecondary
            )
          }

          Text(
            text = service.price,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.ExtraBold
            ),
            color = SneveRed
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Text(
          text = "SERVICE SPECIFICATIONS",
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          ),
          color = SneveTextMuted
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = service.description,
          style = MaterialTheme.typography.bodyMedium,
          color = SneveTextSecondary,
          lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Highlights checklist
        Text(
          text = "SNEVE BARBER ASSURANCE & HIGHLIGHTS",
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          ),
          color = SneveTextMuted
        )
        Spacer(modifier = Modifier.height(8.dp))
        service.highlights.forEach { highlight ->
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .background(SneveRed.copy(alpha = 0.18f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = SneveRed,
                modifier = Modifier.size(12.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = highlight,
              style = MaterialTheme.typography.bodySmall,
              color = SneveTextPrimary
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = SneveBorder)
        Spacer(modifier = Modifier.height(16.dp))

        // Trust badge
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.VerifiedUser,
            contentDescription = null,
            tint = SneveRed,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Guaranteed with SNEVE Master Barber Quality Seal",
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
            color = SneveTextMuted
          )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Booking Action
        SnevePrimaryButton(
          text = "Confirm Booking • ${service.price}",
          onClick = { onConfirmBooking(service) }
        )
      }
    }
  }
}
