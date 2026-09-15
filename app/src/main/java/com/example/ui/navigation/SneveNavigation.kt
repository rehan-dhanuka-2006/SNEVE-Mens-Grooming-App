package com.example.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.data.SneveRepository
import com.example.model.AppDestination
import com.example.model.ServiceCategory
import com.example.model.ServiceItem
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.CategoriesScreen
import com.example.ui.screens.CategoryDetailDialog
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.OtpScreen
import com.example.ui.screens.ServiceDetailDialog
import com.example.ui.screens.SplashScreen
import com.example.ui.theme.SneveBackground
import com.example.ui.theme.SneveBorder
import com.example.ui.theme.SneveBorderSubtle
import com.example.ui.theme.SneveRed
import com.example.ui.theme.SneveRedLight
import com.example.ui.theme.SneveSurface
import com.example.ui.theme.SneveSurfaceElevated
import com.example.ui.theme.SneveTextMuted
import com.example.ui.theme.SneveTextPrimary
import com.example.ui.theme.SneveTextSecondary
import kotlinx.coroutines.launch

@Composable
fun SneveAppNavigation() {
  val rootNavController = rememberNavController()
  var otpDestination by remember { mutableStateOf("+91 98765 43210") }
  var otpUserName by remember { mutableStateOf("SNEVE Client") }

  NavHost(
    navController = rootNavController,
    startDestination = "splash",
    modifier = Modifier.fillMaxSize()
  ) {
    composable("splash") {
      SplashScreen(
        onNavigateToAuth = {
          rootNavController.navigate("auth") {
            popUpTo("splash") { inclusive = true }
          }
        },
        onExploreDirectly = {
          rootNavController.navigate("main") {
            popUpTo("splash") { inclusive = true }
          }
        }
      )
    }

    composable("auth") {
      AuthScreen(
        onLoginSuccess = { name, email ->
          SneveRepository.updateUserName(name, email)
          rootNavController.navigate("main") {
            popUpTo("auth") { inclusive = true }
          }
        },
        onNavigateToOtp = { identifier, name ->
          otpDestination = identifier
          otpUserName = name ?: (if (identifier.contains("@")) identifier.substringBefore("@").replace(".", " ").capitalize() else "SNEVE Client")
          rootNavController.navigate("otp")
        },
        onBypassAsGuest = {
          rootNavController.navigate("main") {
            popUpTo("auth") { inclusive = true }
          }
        }
      )
    }

    composable("otp") {
      OtpScreen(
        destination = otpDestination,
        userName = otpUserName,
        onVerificationSuccess = { verifiedName, verifiedDestination ->
          SneveRepository.updateUserName(verifiedName, verifiedDestination)
          rootNavController.navigate("main") {
            popUpTo("auth") { inclusive = true }
          }
        },
        onNavigateBack = {
          rootNavController.popBackStack()
        }
      )
    }

    composable("main") {
      MainAppShell(
        onSignOut = {
          rootNavController.navigate("auth") {
            popUpTo("main") { inclusive = true }
          }
        }
      )
    }
  }
}

@Composable
fun MainAppShell(
  onSignOut: () -> Unit
) {
  var currentDestination by remember { mutableStateOf(AppDestination.HOME) }
  var selectedCategoryForDetail by remember { mutableStateOf<ServiceCategory?>(null) }
  var selectedServiceForDetail by remember { mutableStateOf<ServiceItem?>(null) }

  val snackbarHostState = remember { SnackbarHostState() }
  val coroutineScope = rememberCoroutineScope()

  Scaffold(
    snackbarHost = { SnackbarHost(snackbarHostState) },
    bottomBar = {
      SneveBottomNavigationBar(
        currentDestination = currentDestination,
        onSelectDestination = { currentDestination = it }
      )
    },
    containerColor = SneveBackground
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .statusBarsPadding()
    ) {
      when (currentDestination) {
        AppDestination.HOME -> {
          HomeScreen(
            onSelectCategory = { cat -> selectedCategoryForDetail = cat },
            onSelectService = { srv -> selectedServiceForDetail = srv },
            onViewAllCategories = { currentDestination = AppDestination.CATEGORIES },
            onSignOut = onSignOut
          )
        }
        AppDestination.CATEGORIES -> {
          CategoriesScreen(
            onSelectCategory = { cat -> selectedCategoryForDetail = cat }
          )
        }
      }
    }

    // Category Detail Modal
    selectedCategoryForDetail?.let { cat ->
      CategoryDetailDialog(
        category = cat,
        onDismiss = { selectedCategoryForDetail = null },
        onSelectService = { srv ->
          selectedCategoryForDetail = null
          selectedServiceForDetail = srv
        }
      )
    }

    // Service Detail & Booking Modal
    selectedServiceForDetail?.let { srv ->
      ServiceDetailDialog(
        service = srv,
        onDismiss = { selectedServiceForDetail = null },
        onConfirmBooking = { bookedService ->
          selectedServiceForDetail = null
          val newBooking = SneveRepository.bookNewService(bookedService)
          coroutineScope.launch {
            snackbarHostState.showSnackbar(
              message = "Booking confirmed! Code: ${newBooking.bookingCode} • SNEVE Master Barber assigned for ${bookedService.title}."
            )
          }
        }
      )
    }
  }
}

@Composable
fun SneveBottomNavigationBar(
  currentDestination: AppDestination,
  onSelectDestination: (AppDestination) -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .background(SneveSurface)
      .border(1.dp, SneveBorderSubtle)
      .navigationBarsPadding()
      .padding(horizontal = 24.dp, vertical = 8.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceEvenly,
      verticalAlignment = Alignment.CenterVertically
    ) {
      AppDestination.values().forEach { destination ->
        val isSelected = currentDestination == destination

        val icon = when (destination) {
          AppDestination.HOME -> if (isSelected) Icons.Filled.Home else Icons.Outlined.Home
          AppDestination.CATEGORIES -> if (isSelected) Icons.Filled.Category else Icons.Outlined.Category
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) SneveRed.copy(alpha = 0.12f) else Color.Transparent)
            .clickable { onSelectDestination(destination) }
            .padding(horizontal = 32.dp, vertical = 8.dp)
            .testTag("nav_tab_${destination.route}"),
          contentAlignment = Alignment.Center
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
          ) {
            Icon(
              imageVector = icon,
              contentDescription = destination.label,
              tint = if (isSelected) SneveRedLight else SneveTextSecondary,
              modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = destination.label,
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                fontSize = 11.sp
              ),
              color = if (isSelected) SneveRedLight else SneveTextSecondary
            )
          }
        }
      }
    }
  }
}
