package com.example.ui.screens

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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SneveBrandHeader
import com.example.ui.components.SneveCard
import com.example.ui.components.SneveOutlinedButton
import com.example.ui.components.SnevePrimaryButton
import com.example.ui.theme.SneveBackground
import com.example.ui.theme.SneveBorder
import com.example.ui.theme.SneveBorderSubtle
import com.example.ui.theme.SneveChrome
import com.example.ui.theme.SneveGold
import com.example.ui.theme.SneveRed
import com.example.ui.theme.SneveRedLight
import com.example.ui.theme.SneveRose
import com.example.ui.theme.SneveSilver
import com.example.ui.theme.SneveSurface
import com.example.ui.theme.SneveSurfaceCard
import com.example.ui.theme.SneveSurfaceElevated
import com.example.ui.theme.SneveTextMuted
import com.example.ui.theme.SneveTextPrimary
import com.example.ui.theme.SneveTextSecondary

private val EMAIL_PATTERN = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
private val INDIAN_MOBILE_PATTERN = Regex("^[6-9]\\d{9}$")

private fun isValidEmailAddress(email: String): Boolean {
  val trimmed = email.trim()
  return trimmed.isNotEmpty() && EMAIL_PATTERN.matches(trimmed)
}

private fun extractCleanIndianDigits(phone: String): String {
  var digits = phone.filter { it.isDigit() }
  if (digits.length > 10 && digits.startsWith("91")) {
    digits = digits.drop(2)
  } else if (digits.length > 10 && digits.startsWith("0")) {
    digits = digits.drop(1)
  }
  return digits
}

private fun isValidIndianMobile(phone: String): Boolean {
  val clean = extractCleanIndianDigits(phone)
  return clean.length == 10 && INDIAN_MOBILE_PATTERN.matches(clean)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
  onLoginSuccess: (name: String, email: String) -> Unit,
  onNavigateToOtp: (identifier: String, userName: String?) -> Unit = { _, _ -> },
  onBypassAsGuest: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableIntStateOf(0) } // 0 = Sign In, 1 = Sign Up

  // Sign In state
  var loginEmail by remember { mutableStateOf("") }
  var loginPassword by remember { mutableStateOf("") }
  var isPasswordVisible by remember { mutableStateOf(false) }
  var rememberMe by remember { mutableStateOf(false) }

  // Sign Up state
  var registerFullName by remember { mutableStateOf("") }
  var registerEmail by remember { mutableStateOf("") }
  var registerPhone by remember { mutableStateOf("") }
  var registerPassword by remember { mutableStateOf("") }
  var registerPasswordConfirm by remember { mutableStateOf("") }

  // Feedback states
  var errorMessage by remember { mutableStateOf<String?>(null) }
  var showForgotPasswordDialog by remember { mutableStateOf(false) }
  var resetEmailSent by remember { mutableStateOf(false) }
  var forgotEmailInput by remember { mutableStateOf("") }
  var forgotPasswordError by remember { mutableStateOf<String?>(null) }

  val scrollState = rememberScrollState()

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(SneveBackground)
      .statusBarsPadding()
      .navigationBarsPadding()
      .imePadding()
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(horizontal = 24.dp, vertical = 20.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Spacer(modifier = Modifier.height(8.dp))

      // SNEVE Brand Header
      SneveBrandHeader(
        emblemSize = 56.dp,
        showTagline = true,
        animatedGlow = false
      )

      Spacer(modifier = Modifier.height(28.dp))

      // Mode Switcher Tabs
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(14.dp))
          .background(SneveSurfaceElevated)
          .border(1.dp, SneveBorder, RoundedCornerShape(14.dp))
          .padding(4.dp)
      ) {
        Row(modifier = Modifier.fillMaxWidth()) {
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(10.dp))
              .background(if (selectedTab == 0) SneveRed else Color.Transparent)
              .clickable {
                selectedTab = 0
                errorMessage = null
              }
              .padding(vertical = 12.dp)
              .testTag("tab_sign_in"),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "Sign In",
              style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Medium
              ),
              color = if (selectedTab == 0) Color.White else SneveTextSecondary
            )
          }

          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(10.dp))
              .background(if (selectedTab == 1) SneveRed else Color.Transparent)
              .clickable {
                selectedTab = 1
                errorMessage = null
              }
              .padding(vertical = 12.dp)
              .testTag("tab_sign_up"),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "Create Account",
              style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Medium
              ),
              color = if (selectedTab == 1) Color.White else SneveTextSecondary
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // Error message if any
      AnimatedVisibility(visible = errorMessage != null) {
        if (errorMessage != null) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(SneveRose.copy(alpha = 0.15f))
              .border(1.dp, SneveRose.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
              .padding(12.dp)
          ) {
            Text(
              text = errorMessage ?: "",
              style = MaterialTheme.typography.bodySmall,
              color = SneveRose
            )
          }
          Spacer(modifier = Modifier.height(16.dp))
        }
      }

      if (selectedTab == 0) {
        // Sign In Form
        SneveCard(
          modifier = Modifier.fillMaxWidth(),
          backgroundColor = SneveSurfaceCard
        ) {
          Column(modifier = Modifier.padding(20.dp)) {
            Text(
              text = "Welcome to SNEVE",
              style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
              color = SneveTextPrimary
            )
            Text(
              text = "Access your on-demand master barber bookings",
              style = MaterialTheme.typography.bodySmall,
              color = SneveTextSecondary
            )
            Spacer(modifier = Modifier.height(18.dp))

            // Email or 10-digit mobile input
            OutlinedTextField(
              value = loginEmail,
              onValueChange = { 
                loginEmail = it 
                errorMessage = null
              },
              label = { Text("Email or 10-Digit Mobile") },
              placeholder = { Text("name@example.com or 9876543210") },
              leadingIcon = {
                Icon(
                  imageVector = if (loginEmail.any { it.isDigit() } && !loginEmail.contains("@")) Icons.Default.PhoneAndroid else Icons.Default.Email,
                  contentDescription = null,
                  tint = SneveRed
                )
              },
              singleLine = true,
              keyboardOptions = KeyboardOptions(
                keyboardType = if (loginEmail.any { it.isDigit() } && !loginEmail.contains("@")) KeyboardType.Phone else KeyboardType.Email,
                imeAction = ImeAction.Next
              ),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("input_login_email"),
              shape = RoundedCornerShape(12.dp),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SneveRed,
                unfocusedBorderColor = SneveBorder,
                focusedTextColor = SneveTextPrimary,
                unfocusedTextColor = SneveTextPrimary,
                focusedContainerColor = SneveSurfaceElevated,
                unfocusedContainerColor = SneveSurfaceElevated,
                focusedLabelColor = SneveRed,
                unfocusedLabelColor = SneveTextSecondary,
                cursorColor = SneveRed
              )
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Password input
            OutlinedTextField(
              value = loginPassword,
              onValueChange = { 
                loginPassword = it 
                errorMessage = null
              },
              label = { Text("Password") },
              leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = null, tint = SneveRed)
              },
              trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                  Icon(
                    imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = "Toggle password",
                    tint = SneveTextSecondary
                  )
                }
              },
              visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
              singleLine = true,
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("input_login_password"),
              shape = RoundedCornerShape(12.dp),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SneveRed,
                unfocusedBorderColor = SneveBorder,
                focusedTextColor = SneveTextPrimary,
                unfocusedTextColor = SneveTextPrimary,
                focusedContainerColor = SneveSurfaceElevated,
                unfocusedContainerColor = SneveSurfaceElevated,
                focusedLabelColor = SneveRed,
                unfocusedLabelColor = SneveTextSecondary,
                cursorColor = SneveRed
              )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Remember me & Forgot Password
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                  checked = rememberMe,
                  onCheckedChange = { rememberMe = it },
                  colors = CheckboxDefaults.colors(
                    checkedColor = SneveRed,
                    checkmarkColor = Color.White,
                    uncheckedColor = SneveTextSecondary
                  )
                )
                Text(
                  text = "Remember me",
                  style = MaterialTheme.typography.bodySmall,
                  color = SneveTextSecondary
                )
              }
              Text(
                text = "Forgot password?",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                color = SneveRed,
                modifier = Modifier
                  .clickable { showForgotPasswordDialog = true }
                  .padding(4.dp)
              )
            }

            Spacer(modifier = Modifier.height(18.dp))

            SnevePrimaryButton(
              text = "Sign In",
              icon = Icons.AutoMirrored.Filled.ArrowForward,
              onClick = {
                val input = loginEmail.trim()
                if (input.isBlank()) {
                  errorMessage = "Please enter your email address or 10-digit mobile number"
                } else if (input.contains("@")) {
                  if (!isValidEmailAddress(input)) {
                    errorMessage = "Please enter a valid email address (e.g. name@example.com)"
                  } else if (loginPassword.isBlank()) {
                    errorMessage = "Please enter your password"
                  } else if (loginPassword.length < 6) {
                    errorMessage = "Password must be at least 6 characters"
                  } else {
                    val resolvedName = input.substringBefore("@").replace(".", " ").capitalize()
                    onLoginSuccess(resolvedName, input)
                  }
                } else {
                  val cleanPhone = extractCleanIndianDigits(input)
                  if (cleanPhone.length != 10) {
                    errorMessage = "Mobile number must be exactly 10 digits"
                  } else if (!INDIAN_MOBILE_PATTERN.matches(cleanPhone)) {
                    errorMessage = "Please enter a valid 10-digit Indian mobile number (starts with 6-9)"
                  } else if (loginPassword.isBlank()) {
                    errorMessage = "Please enter your password"
                  } else if (loginPassword.length < 6) {
                    errorMessage = "Password must be at least 6 characters"
                  } else {
                    onLoginSuccess("SNEVE Client", "+91 $cleanPhone")
                  }
                }
              },
              modifier = Modifier.testTag("btn_submit_sign_in")
            )
          }
        }
      } else {
        // Sign Up Form
        SneveCard(
          modifier = Modifier.fillMaxWidth(),
          backgroundColor = SneveSurfaceCard
        ) {
          Column(modifier = Modifier.padding(20.dp)) {
            Text(
              text = "Join SNEVE Grooming Club",
              style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
              color = SneveTextPrimary
            )
            Text(
              text = "Create your account for on-demand master barber delivery",
              style = MaterialTheme.typography.bodySmall,
              color = SneveTextSecondary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Full name
            OutlinedTextField(
              value = registerFullName,
              onValueChange = { 
                registerFullName = it 
                errorMessage = null
              },
              label = { Text("Full Legal Name") },
              placeholder = { Text("e.g. Arjun Kapoor") },
              leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = null, tint = SneveRed)
              },
              singleLine = true,
              keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("input_register_name"),
              shape = RoundedCornerShape(12.dp),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SneveRed,
                unfocusedBorderColor = SneveBorder,
                focusedTextColor = SneveTextPrimary,
                unfocusedTextColor = SneveTextPrimary,
                focusedContainerColor = SneveSurfaceElevated,
                unfocusedContainerColor = SneveSurfaceElevated,
                focusedLabelColor = SneveRed,
                unfocusedLabelColor = SneveTextSecondary,
                cursorColor = SneveRed
              )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Email
            OutlinedTextField(
              value = registerEmail,
              onValueChange = { 
                registerEmail = it 
                errorMessage = null
              },
              label = { Text("Email Address") },
              placeholder = { Text("name@example.com") },
              leadingIcon = {
                Icon(Icons.Default.Email, contentDescription = null, tint = SneveRed)
              },
              singleLine = true,
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("input_register_email"),
              shape = RoundedCornerShape(12.dp),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SneveRed,
                unfocusedBorderColor = SneveBorder,
                focusedTextColor = SneveTextPrimary,
                unfocusedTextColor = SneveTextPrimary,
                focusedContainerColor = SneveSurfaceElevated,
                unfocusedContainerColor = SneveSurfaceElevated,
                focusedLabelColor = SneveRed,
                unfocusedLabelColor = SneveTextSecondary,
                cursorColor = SneveRed
              )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Phone (10 digits India)
            OutlinedTextField(
              value = registerPhone,
              onValueChange = { input ->
                val clean = extractCleanIndianDigits(input)
                if (clean.length <= 10) {
                  registerPhone = clean
                  errorMessage = null
                }
              },
              label = { Text("Mobile Number") },
              placeholder = { Text("9876543210") },
              prefix = {
                Text(
                  text = "+91 ",
                  style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                  color = SneveGold
                )
              },
              leadingIcon = {
                Icon(Icons.Default.Phone, contentDescription = null, tint = SneveRed)
              },
              supportingText = {
                Text(
                  text = "10 digits required",
                  style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                  color = SneveTextMuted
                )
              },
              singleLine = true,
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("input_register_phone"),
              shape = RoundedCornerShape(12.dp),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SneveRed,
                unfocusedBorderColor = SneveBorder,
                focusedTextColor = SneveTextPrimary,
                unfocusedTextColor = SneveTextPrimary,
                focusedContainerColor = SneveSurfaceElevated,
                unfocusedContainerColor = SneveSurfaceElevated,
                focusedLabelColor = SneveRed,
                unfocusedLabelColor = SneveTextSecondary,
                cursorColor = SneveRed
              )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Password
            OutlinedTextField(
              value = registerPassword,
              onValueChange = { 
                registerPassword = it 
                errorMessage = null
              },
              label = { Text("Create Password") },
              leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = null, tint = SneveRed)
              },
              visualTransformation = PasswordVisualTransformation(),
              singleLine = true,
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("input_register_password"),
              shape = RoundedCornerShape(12.dp),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SneveRed,
                unfocusedBorderColor = SneveBorder,
                focusedTextColor = SneveTextPrimary,
                unfocusedTextColor = SneveTextPrimary,
                focusedContainerColor = SneveSurfaceElevated,
                unfocusedContainerColor = SneveSurfaceElevated,
                focusedLabelColor = SneveRed,
                unfocusedLabelColor = SneveTextSecondary,
                cursorColor = SneveRed
              )
            )

            Spacer(modifier = Modifier.height(20.dp))

            SnevePrimaryButton(
              text = "Create Account",
              icon = Icons.Default.CheckCircle,
              onClick = {
                val trimmedName = registerFullName.trim()
                val trimmedEmail = registerEmail.trim()
                val cleanPhone = extractCleanIndianDigits(registerPhone)

                if (trimmedName.isBlank()) {
                  errorMessage = "Please enter your full legal name"
                } else if (trimmedName.length < 2) {
                  errorMessage = "Name must be at least 2 characters"
                } else if (trimmedEmail.isBlank()) {
                  errorMessage = "Please enter your email address"
                } else if (!isValidEmailAddress(trimmedEmail)) {
                  errorMessage = "Please enter a valid email address (e.g. name@example.com)"
                } else if (cleanPhone.isBlank()) {
                  errorMessage = "Please enter your 10-digit Indian mobile number"
                } else if (cleanPhone.length != 10) {
                  errorMessage = "Phone number must be exactly 10 digits (India)"
                } else if (!INDIAN_MOBILE_PATTERN.matches(cleanPhone)) {
                  errorMessage = "Please enter a valid 10-digit Indian mobile number (starts with 6, 7, 8, or 9)"
                } else if (registerPassword.isBlank()) {
                  errorMessage = "Please create a password"
                } else if (registerPassword.length < 6) {
                  errorMessage = "Password must be at least 6 characters"
                } else {
                  onNavigateToOtp("+91 $cleanPhone", trimmedName)
                }
              },
              modifier = Modifier.testTag("btn_submit_sign_up")
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // Social Authentication Divider
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Divider(modifier = Modifier.weight(1f), color = SneveBorder)
        Text(
          text = "  OR  ",
          style = MaterialTheme.typography.labelSmall,
          color = SneveTextMuted
        )
        Divider(modifier = Modifier.weight(1f), color = SneveBorder)
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Guest explore button
      SneveOutlinedButton(
        text = "Continue as Guest",
        onClick = onBypassAsGuest,
        modifier = Modifier
          .fillMaxWidth()
          .testTag("btn_auth_guest_pass")
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Text(
      //   text = "Protected by SNEVE 256-bit Security Protocol",
      //   style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
      //   color = SneveTextMuted,
      //   textAlign = TextAlign.Center
      // )
    }

    // Forgot Password Dialog
    if (showForgotPasswordDialog) {
      AlertDialog(
        onDismissRequest = {
          showForgotPasswordDialog = false
          resetEmailSent = false
        },
        containerColor = SneveSurfaceElevated,
        title = {
          Text(
            text = "Reset Password",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = SneveTextPrimary
          )
        },
        text = {
          Column {
            if (!resetEmailSent) {
              Text(
                text = "Enter your registered SNEVE email. We will dispatch a secure instant login passkey.",
                style = MaterialTheme.typography.bodyMedium,
                color = SneveTextSecondary
              )
              Spacer(modifier = Modifier.height(16.dp))
              OutlinedTextField(
                value = forgotEmailInput,
                onValueChange = { 
                  forgotEmailInput = it
                  forgotPasswordError = null
                },
                label = { Text("Email Address") },
                placeholder = { Text("name@example.com") },
                singleLine = true,
                isError = forgotPasswordError != null,
                supportingText = if (forgotPasswordError != null) {
                  { Text(text = forgotPasswordError ?: "", color = SneveRose) }
                } else null,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = SneveRed,
                  unfocusedBorderColor = SneveBorder,
                  errorBorderColor = SneveRose,
                  focusedTextColor = SneveTextPrimary,
                  unfocusedTextColor = SneveTextPrimary,
                  cursorColor = SneveRed
                )
              )
            } else {
              Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = null,
                  tint = SneveRed,
                  modifier = Modifier.size(44.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                  text = "Reset Link Dispatched!",
                  style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                  color = SneveRedLight
                )
                Text(
                  text = "Please check your inbox to restore SNEVE barber access.",
                  style = MaterialTheme.typography.bodySmall,
                  color = SneveTextSecondary,
                  textAlign = TextAlign.Center
                )
              }
            }
          }
        },
        confirmButton = {
          if (!resetEmailSent) {
            TextButton(
              onClick = { 
                val email = forgotEmailInput.trim()
                if (email.isBlank()) {
                  forgotPasswordError = "Please enter your email address"
                } else if (!isValidEmailAddress(email)) {
                  forgotPasswordError = "Please enter a valid email address (e.g. name@example.com)"
                } else {
                  forgotPasswordError = null
                  resetEmailSent = true
                }
              }
            ) {
              Text("Send Reset Link", color = SneveRed)
            }
          } else {
            TextButton(
              onClick = {
                showForgotPasswordDialog = false
                resetEmailSent = false
              }
            ) {
              Text("Close", color = SneveRed)
            }
          }
        },
        dismissButton = {
          if (!resetEmailSent) {
            TextButton(onClick = { showForgotPasswordDialog = false }) {
              Text("Cancel", color = SneveTextMuted)
            }
          }
        }
      )
    }
  }
}
