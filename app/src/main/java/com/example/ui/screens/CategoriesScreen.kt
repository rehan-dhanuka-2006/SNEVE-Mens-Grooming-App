package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.SneveRepository
import com.example.model.ServiceCategory
import com.example.ui.components.SneveBadge
import com.example.ui.components.SneveCard
import com.example.ui.components.getCategoryDrawable
import com.example.ui.components.getServiceIcon
import com.example.ui.theme.SneveBackground
import com.example.ui.theme.SneveBorder
import com.example.ui.theme.SneveBorderSubtle
import com.example.ui.theme.SneveChrome
import com.example.ui.theme.SneveGold
import com.example.ui.theme.SneveRed
import com.example.ui.theme.SneveRedLight
import com.example.ui.theme.SneveSilver
import com.example.ui.theme.SneveSurface
import com.example.ui.theme.SneveSurfaceCard
import com.example.ui.theme.SneveSurfaceElevated
import com.example.ui.theme.SneveTextMuted
import com.example.ui.theme.SneveTextPrimary
import com.example.ui.theme.SneveTextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
  onSelectCategory: (ServiceCategory) -> Unit,
  modifier: Modifier = Modifier
) {
  var searchQuery by remember { mutableStateOf("") }
  var selectedFilter by remember { mutableStateOf("All Grooming") }

  val filters = listOf("All Grooming", "Haircuts", "Beard & Shave", "Combos", "Treatments", "Color")

  val filteredCategories = remember(searchQuery, selectedFilter) {
    SneveRepository.categories.filter { cat ->
      val matchesSearch = searchQuery.isBlank() ||
        cat.title.contains(searchQuery, ignoreCase = true) ||
        cat.tagLine.contains(searchQuery, ignoreCase = true) ||
        cat.subcategories.any { it.contains(searchQuery, ignoreCase = true) }

      val matchesFilter = when (selectedFilter) {
        "Haircuts" -> cat.id == "cat_hair"
        "Beard & Shave" -> cat.id == "cat_beard"
        "Combos" -> cat.id in listOf("cat_packages", "cat_wedding")
        "Treatments" -> cat.id == "cat_treatment"
        "Color" -> cat.id == "cat_color"
        else -> true
      }
      matchesSearch && matchesFilter
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(SneveBackground)
  ) {
    // Header
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(SneveSurface)
        .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
      Text(
        text = "Service Categories",
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
        color = SneveTextPrimary
      )
      Text(
        text = "6 Grooming Verticals • 160+ Certified SNEVE Master Barbers",
        style = MaterialTheme.typography.bodySmall,
        color = SneveTextSecondary
      )

      Spacer(modifier = Modifier.height(14.dp))

      // Search field
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = {
          Text("Search cuts, beard trims, shaves, combos...", color = SneveTextMuted, style = MaterialTheme.typography.bodySmall)
        },
        leadingIcon = {
          Icon(Icons.Default.Search, contentDescription = null, tint = SneveRed, modifier = Modifier.size(20.dp))
        },
        singleLine = true,
        modifier = Modifier
          .fillMaxWidth()
          .testTag("categories_search_input"),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = SneveRed,
          unfocusedBorderColor = SneveBorder,
          focusedTextColor = SneveTextPrimary,
          unfocusedTextColor = SneveTextPrimary,
          focusedContainerColor = SneveSurfaceCard,
          unfocusedContainerColor = SneveSurfaceCard,
          cursorColor = SneveRed
        )
      )
    }

    // Filter Chips
    LazyRow(
      contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(filters) { filter ->
        val isSelected = selectedFilter == filter
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .background(if (isSelected) SneveRed else SneveSurfaceCard)
            .border(1.dp, if (isSelected) SneveRed else SneveBorder, RoundedCornerShape(30.dp))
            .clickable { selectedFilter = filter }
            .padding(horizontal = 14.dp, vertical = 7.dp)
        ) {
          Text(
            text = filter,
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            ),
            color = if (isSelected) Color.White else SneveTextSecondary
          )
        }
      }
    }

    // Full Categories List
    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      items(filteredCategories) { category ->
        SneveCard(
          modifier = Modifier
            .fillMaxWidth()
            .testTag("category_card_${category.id}"),
          onClick = { onSelectCategory(category) }
        ) {
          Column {
            // Visual Category Image Banner
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
            ) {
              Image(
                painter = painterResource(id = getCategoryDrawable(category.id)),
                contentDescription = category.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
              )

              // Dark luxury gradient overlay
              Box(
                modifier = Modifier
                  .fillMaxSize()
                  .background(
                    brush = Brush.verticalGradient(
                      colors = listOf(
                        Color(0x40000000),
                        Color(0x880A0B0E),
                        SneveSurfaceCard
                      )
                    )
                  )
              )

              // Top Badges Row
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                if (category.badge != null) {
                  SneveBadge(text = category.badge, color = SneveRed)
                } else {
                  Spacer(modifier = Modifier.width(1.dp))
                }

                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xDD0A0B0E))
                    .border(1.dp, SneveBorderSubtle, RoundedCornerShape(20.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                  Text(
                    text = "${category.proCount} Specialists",
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontWeight = FontWeight.SemiBold,
                      fontSize = 11.sp
                    ),
                    color = SneveSilver
                  )
                }
              }

              // Bottom Title Row inside image
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .align(Alignment.BottomStart)
                  .padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Box(
                    modifier = Modifier
                      .size(34.dp)
                      .clip(RoundedCornerShape(8.dp))
                      .background(SneveRed.copy(alpha = 0.92f)),
                    contentAlignment = Alignment.Center
                  ) {
                    Icon(
                      imageVector = getServiceIcon(category.iconName),
                      contentDescription = null,
                      tint = Color.White,
                      modifier = Modifier.size(18.dp)
                    )
                  }
                  Spacer(modifier = Modifier.width(10.dp))
                  Text(
                    text = category.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                      fontWeight = FontWeight.Bold,
                      fontSize = 17.sp
                    ),
                    color = SneveTextPrimary
                  )
                }

                Text(
                  text = "From ${category.startingPrice}",
                  style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraBold),
                  color = SneveRedLight
                )
              }
            }

            // Card body with description and subcategories
            Column(modifier = Modifier.padding(14.dp)) {
              Text(
                text = category.tagLine,
                style = MaterialTheme.typography.bodySmall,
                color = SneveTextSecondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
              )

              Spacer(modifier = Modifier.height(10.dp))

              // Subcategories preview chips in horizontal scroll to prevent vertical stretching
              LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                items(category.subcategories) { sub ->
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(6.dp))
                      .background(SneveSurfaceElevated)
                      .border(1.dp, SneveBorderSubtle, RoundedCornerShape(6.dp))
                      .padding(horizontal = 8.dp, vertical = 5.dp)
                  ) {
                    Text(
                      text = sub,
                      style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                      color = SneveTextMuted,
                      maxLines = 1,
                      softWrap = false,
                      overflow = TextOverflow.Ellipsis
                    )
                  }
                }
              }

              Spacer(modifier = Modifier.height(12.dp))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "Certified Master Barber Service",
                  style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                  color = SneveTextMuted
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "View Services",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = SneveRed
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = SneveRed,
                    modifier = Modifier.size(16.dp)
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}
