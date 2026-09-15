package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.model.ServiceItem
import com.example.ui.components.SneveBadge
import com.example.ui.components.SneveCard
import com.example.ui.components.SneveEmblem
import com.example.ui.components.SneveSectionHeader
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
fun HomeScreen(
  onSelectCategory: (ServiceCategory) -> Unit,
  onSelectService: (ServiceItem) -> Unit,
  onViewAllCategories: () -> Unit,
  onSignOut: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  val user by SneveRepository.currentUser.collectAsState()

  var searchQuery by remember { mutableStateOf("") }
  var selectedTag by remember { mutableStateOf("All") }
  var showAddressMenu by remember { mutableStateOf(false) }

  val addressOptions = listOf(
    "Flat 802, Pali Hill, Bandra West, Mumbai",
    "Villa 14, Koramangala 4th Block, Bengaluru",
    "DLF Magnolias, Golf Course Road, Gurugram",
    "Road No. 36, Jubilee Hills, Hyderabad",
    "Koregaon Park, Lane 7, Pune"
  )

  val filterTags = listOf("All", "Haircuts", "Beard & Shave", "Combos", "Facials", "Color")

  // Filtered categories based on search
  val displayedCategories = remember(searchQuery, selectedTag) {
    SneveRepository.categories.filter { cat ->
      val matchesSearch = searchQuery.isBlank() ||
        cat.title.contains(searchQuery, ignoreCase = true) ||
        cat.tagLine.contains(searchQuery, ignoreCase = true) ||
        cat.subcategories.any { it.contains(searchQuery, ignoreCase = true) }

      val matchesTag = when (selectedTag) {
        "Haircuts" -> cat.id == "cat_hair"
        "Beard & Shave" -> cat.id == "cat_beard"
        "Combos" -> cat.id in listOf("cat_packages", "cat_wedding")
        "Facials" -> cat.id == "cat_treatment"
        "Color" -> cat.id == "cat_color"
        else -> true
      }
      matchesSearch && matchesTag
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(SneveBackground)
      .verticalScroll(rememberScrollState())
  ) {
    // Top Bar with User Info & Address
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          brush = Brush.verticalGradient(
            colors = listOf(
              SneveSurfaceElevated,
              SneveSurface
            )
          )
        )
        .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            SneveEmblem(size = 40.dp)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = "SNEVE",
                  style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.4.sp
                  ),
                  color = SneveTextPrimary
                )
                Spacer(modifier = Modifier.width(6.dp))
                SneveBadge(
                  text = "Verified Client",
                  color = SneveRed,
                  textColor = Color.White
                )
              }
              Text(
                text = "Welcome, ${user.name}",
                style = MaterialTheme.typography.bodySmall,
                color = SneveTextSecondary
              )
            }
          }

          // Sign Out Action Button
          IconButton(
            onClick = onSignOut,
            modifier = Modifier
              .size(40.dp)
              .clip(CircleShape)
              .background(SneveSurfaceCard)
              .border(1.dp, SneveBorder, CircleShape)
              .testTag("btn_home_sign_out")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.Logout,
              contentDescription = "Sign Out",
              tint = SneveTextSecondary,
              modifier = Modifier.size(18.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Address Selector Dropdown
        Box {
          Row(
            modifier = Modifier
              .clip(RoundedCornerShape(10.dp))
              .background(SneveSurfaceElevated)
              .border(1.dp, SneveBorderSubtle, RoundedCornerShape(10.dp))
              .clickable { showAddressMenu = true }
              .padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.LocationOn,
              contentDescription = null,
              tint = SneveRed,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = user.activeAddress,
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
              color = SneveTextPrimary,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis,
              modifier = Modifier.weight(1f, fill = false)
            )
            Icon(
              imageVector = Icons.Default.ArrowDropDown,
              contentDescription = null,
              tint = SneveTextSecondary,
              modifier = Modifier.size(18.dp)
            )
          }

          DropdownMenu(
            expanded = showAddressMenu,
            onDismissRequest = { showAddressMenu = false },
            modifier = Modifier.background(SneveSurfaceElevated)
          ) {
            addressOptions.forEach { addr ->
              DropdownMenuItem(
                text = {
                  Text(
                    text = addr,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (addr == user.activeAddress) SneveRed else SneveTextPrimary
                  )
                },
                onClick = {
                  SneveRepository.updateAddress(addr)
                  showAddressMenu = false
                }
              )
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Search Bar
    Box(modifier = Modifier.padding(horizontal = 20.dp)) {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = {
          Text(
            text = "Search master haircuts, beard sculpt, hot towel shaves...",
            style = MaterialTheme.typography.bodySmall,
            color = SneveTextMuted
          )
        },
        leadingIcon = {
          Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = SneveRed,
            modifier = Modifier.size(20.dp)
          )
        },
        singleLine = true,
        modifier = Modifier
          .fillMaxWidth()
          .testTag("home_search_bar"),
        shape = RoundedCornerShape(14.dp),
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

    Spacer(modifier = Modifier.height(12.dp))

    // Quick filter chips
    LazyRow(
      contentPadding = PaddingValues(horizontal = 20.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(filterTags) { tag ->
        val isSelected = selectedTag == tag
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .background(if (isSelected) SneveRed else SneveSurfaceCard)
            .border(1.dp, if (isSelected) SneveRed else SneveBorder, RoundedCornerShape(30.dp))
            .clickable { selectedTag = tag }
            .padding(horizontal = 14.dp, vertical = 7.dp)
        ) {
          Text(
            text = tag,
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            ),
            color = if (isSelected) Color.White else SneveTextSecondary
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Hero Promotional Banner
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
    ) {
      SneveCard(
        modifier = Modifier.fillMaxWidth(),
        borderColor = SneveBorderSubtle
      ) {
        Box(modifier = Modifier.fillMaxWidth()) {
          // Banner Image background (Men's Grooming Barber)
          Image(
            painter = painterResource(id = R.drawable.img_sneve_barber_hero),
            contentDescription = "SNEVE Men's Grooming Delivered",
            modifier = Modifier
              .fillMaxWidth()
              .height(180.dp),
            contentScale = ContentScale.Crop
          )

          // Dark luxury overlay gradient
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(180.dp)
              .background(
                brush = Brush.verticalGradient(
                  colors = listOf(
                    Color.Transparent,
                    Color(0xCC0A0B0E),
                    SneveSurfaceElevated
                  )
                )
              )
          )

          // Content
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp)
              .align(Alignment.BottomStart)
          ) {
            SneveBadge(
              text = "MEN'S GROOMING. DELIVERED.",
              color = SneveRed,
              icon = Icons.Default.Star,
              textColor = Color.White
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "Master Barbers At Your Door",
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
              ),
              color = SneveTextPrimary
            )
            Text(
              text = "Precision haircuts, straight razor line-ups, and rejuvenating hot towel treatments delivered to you.",
              style = MaterialTheme.typography.bodySmall,
              color = SneveTextSecondary,
              maxLines = 2
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Service Categories Section
    SneveSectionHeader(
      title = "Grooming Specialties",
      subtitle = "Certified master barbers dispatched on-demand",
      actionText = "View All",
      onActionClick = onViewAllCategories
    )

    // Categories Grid (2 columns or 4x2 responsive layout)
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      displayedCategories.chunked(2).forEach { rowItems ->
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          rowItems.forEach { cat ->
            Box(modifier = Modifier.weight(1f)) {
              CategoryCard(
                category = cat,
                onClick = { onSelectCategory(cat) }
              )
            }
          }
          if (rowItems.size == 1) {
            Spacer(modifier = Modifier.weight(1f))
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(24.dp))

    // Curated Signature Services Carousel
    SneveSectionHeader(
      title = "Signature Services",
      subtitle = "Most requested bookings this week",
      actionText = "Explore",
      onActionClick = onViewAllCategories
    )

    LazyRow(
      contentPadding = PaddingValues(horizontal = 20.dp),
      horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      items(SneveRepository.services) { srv ->
        FeaturedServiceCard(
          service = srv,
          onClick = { onSelectService(srv) }
        )
      }
    }

    Spacer(modifier = Modifier.height(24.dp))

    // SNEVE Quality Guarantee Strip
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
    ) {
      SneveCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = SneveSurface
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Shield,
              contentDescription = null,
              tint = SneveRed,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "THE SNEVE BARBER PROMISE",
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
              ),
              color = SneveRed
            )
          }
          Spacer(modifier = Modifier.height(10.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            GuaranteePill(title = "Master Barbers", desc = "10+ Yrs Licensed")
            GuaranteePill(title = "Hospital Sanitized", desc = "Sterile mobile kit")
            GuaranteePill(title = "Pan-India Service", desc = "Across major metros")
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(28.dp))
  }
}

@Composable
fun CategoryCard(
  category: ServiceCategory,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  SneveCard(
    modifier = modifier.fillMaxWidth(),
    onClick = onClick,
    backgroundColor = SneveSurfaceCard
  ) {
    Column {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(86.dp)
      ) {
        Image(
          painter = painterResource(id = getCategoryDrawable(category.id)),
          contentDescription = category.title,
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop
        )
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(
              brush = Brush.verticalGradient(
                colors = listOf(
                  Color(0x33000000),
                  Color(0x770A0B0E),
                  SneveSurfaceCard
                )
              )
            )
        )
        if (category.badge != null) {
          Box(
            modifier = Modifier
              .align(Alignment.TopEnd)
              .padding(6.dp)
          ) {
            SneveBadge(
              text = category.badge,
              color = SneveRed
            )
          }
        }
      }

      Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)) {
        Text(
          text = category.title,
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
          ),
          color = SneveTextPrimary,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
          text = "From ${category.startingPrice}",
          style = MaterialTheme.typography.bodySmall.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp
          ),
          color = SneveRedLight
        )
      }
    }
  }
}

@Composable
fun FeaturedServiceCard(
  service: ServiceItem,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  SneveCard(
    modifier = modifier.width(260.dp),
    onClick = onClick,
    backgroundColor = SneveSurfaceCard
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        SneveBadge(text = service.categoryName, color = SneveRed)
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = SneveGold,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(3.dp))
          Text(
            text = service.rating.toString(),
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = SneveTextPrimary
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = service.title,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold
        ),
        color = SneveTextPrimary,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = service.description,
        style = MaterialTheme.typography.bodySmall,
        color = SneveTextSecondary,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = service.price,
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.ExtraBold
          ),
          color = SneveRed
        )
        Text(
          text = "Book Now →",
          style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
          color = SneveTextPrimary
        )
      }
    }
  }
}

@Composable
fun GuaranteePill(title: String, desc: String) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Text(
      text = title,
      style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
      color = SneveTextPrimary
    )
    Text(
      text = desc,
      style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
      color = SneveTextMuted
    )
  }
}
