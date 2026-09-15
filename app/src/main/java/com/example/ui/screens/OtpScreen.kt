package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SneveBadge
import com.example.ui.components.SneveCard
import com.example.ui.components.SnevePrimaryButton
import com.example.ui.theme.SneveBackground
import com.example.ui.theme.SneveBorder
import com.example.ui.theme.SneveBorderSubtle
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
import kotlinx.coroutines.delay

@Composable
fun OtpScreen(
  destination: String,
  userName: String = "Client",
  onVerificationSuccess: (name: String, destination: String) -> Unit,
  onNavigateBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  var otpCode by remember { mutableStateOf("") }
  var secondsLeft by remember { mutableIntStateOf(45) }
  var isResendAvailable by remember { mutableStateOf(false) }
  var isVerifying by remember { mutableStateOf(false) }
  var errorMessage by remember { mutableStateOf<String?>(null) }
  var resendNotice by remember { mutableStateOf<String?>(null) }

  val focusRequester = remember { FocusRequester() }
  val interactionSource = remember { MutableInteractionSource() }

  // Countdown timer for resending OTP
  LaunchedEffect(secondsLeft) {
    if (secondsLeft > 0) {
      delay(1000L)
      secondsLeft--
    } else {
      isResendAvailable = true
    }
  }

  // Request focus on keyboard on initial launch
  LaunchedEffect(Unit) {
    delay(250L)
    try {
      focusRequester.requestFocus()
    } catch (_: Exception) {}
  }

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
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 24.dp, vertical = 16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Top Navigation Bar
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        IconButton(
          onClick = onNavigateBack,
          modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(SneveSurfaceElevated)
            .border(1.dp, SneveBorderSubtle, CircleShape)
            .testTag("btn_otp_back")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = SneveTextPrimary
          )
        }

        SneveBadge(
          text = "Secure Verification",
          color = SneveRed
        )

        Spacer(modifier = Modifier.size(42.dp))
      }

      Spacer(modifier = Modifier.height(28.dp))

      // Lock / Security Emblem
      Box(
        modifier = Modifier
          .size(76.dp)
          .clip(CircleShape)
          .background(SneveRed.copy(alpha = 0.12f))
          .border(1.dp, SneveRed.copy(alpha = 0.35f), CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Shield,
          contentDescription = null,
          tint = SneveRed,
          modifier = Modifier.size(38.dp)
        )
      }

      Spacer(modifier = Modifier.height(22.dp))

      Text(
        text = "Enter Verification Code",
        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
        color = SneveTextPrimary,
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "We have dispatched a 6-digit one-time password to your registered mobile number:",
        style = MaterialTheme.typography.bodyMedium,
        color = SneveTextSecondary,
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Recipient Phone / Email Chip
      Row(
        modifier = Modifier
          .clip(RoundedCornerShape(30.dp))
          .background(SneveSurfaceElevated)
          .border(1.dp, SneveBorder, RoundedCornerShape(30.dp))
          .clickable { onNavigateBack() }
          .padding(horizontal = 14.dp, vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.PhoneAndroid,
          contentDescription = null,
          tint = SneveRed,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = destination.ifBlank { "+91 98765 43210" },
          style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
          color = SneveTextPrimary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
          imageVector = Icons.Default.Edit,
          contentDescription = "Edit Number",
          tint = SneveTextMuted,
          modifier = Modifier.size(14.dp)
        )
      }

      Spacer(modifier = Modifier.height(30.dp))

      // Notification / Error alerts
      if (resendNotice != null) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SneveRed.copy(alpha = 0.12f))
            .border(1.dp, SneveRed.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
            .padding(12.dp)
        ) {
          Text(
            text = resendNotice ?: "",
            style = MaterialTheme.typography.bodySmall,
            color = SneveRedLight,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
          )
        }
        Spacer(modifier = Modifier.height(16.dp))
      }

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
            color = SneveRose,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
          )
        }
        Spacer(modifier = Modifier.height(16.dp))
      }

      // OTP Card Container
      SneveCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = SneveSurfaceCard
      ) {
        Column(
          modifier = Modifier.padding(20.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          // Hidden single BasicTextField for robust IME & paste input
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clickable(
                interactionSource = interactionSource,
                indication = null
              ) {
                focusRequester.requestFocus()
              }
          ) {
            BasicTextField(
              value = otpCode,
              onValueChange = { newText ->
                val digitsOnly = newText.filter { it.isDigit() }
                if (digitsOnly.length <= 6) {
                  otpCode = digitsOnly
                  errorMessage = null
                  resendNotice = null
                }
              },
              keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
              ),
              keyboardActions = KeyboardActions(
                onDone = {
                  if (otpCode.length == 6) {
                    isVerifying = true
                    onVerificationSuccess(userName, destination)
                  } else {
                    errorMessage = "Please enter complete 6-digit OTP"
                  }
                }
              ),
              modifier = Modifier
                .focusRequester(focusRequester)
                .alpha(0.01f)
                .size(1.dp)
                .testTag("input_otp_code")
            )

            // 6 Visual Digit Cells
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceEvenly,
              verticalAlignment = Alignment.CenterVertically
            ) {
              for (i in 0 until 6) {
                val digit = otpCode.getOrNull(i)?.toString() ?: ""
                val isCurrentCell = otpCode.length == i
                val isFilled = digit.isNotEmpty()

                Box(
                  modifier = Modifier
                    .size(46.dp, 56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isFilled) SneveSurfaceElevated else SneveSurface)
                    .border(
                      width = if (isCurrentCell) 2.dp else 1.dp,
                      color = when {
                        isCurrentCell -> SneveRed
                        isFilled -> SneveRed.copy(alpha = 0.6f)
                        else -> SneveBorder
                      },
                      shape = RoundedCornerShape(12.dp)
                    ),
                  contentAlignment = Alignment.Center
                ) {
                  Text(
                    text = digit,
                    style = MaterialTheme.typography.headlineMedium.copy(
                      fontWeight = FontWeight.Bold,
                      fontSize = 22.sp
                    ),
                    color = if (isFilled) SneveTextPrimary else SneveTextMuted
                  )
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(20.dp))

          // Quick Auto-fill for easy testing
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(20.dp))
              .background(SneveRed.copy(alpha = 0.12f))
              .border(1.dp, SneveRed.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
              .clickable {
                otpCode = "749216"
                errorMessage = null
              }
              .padding(horizontal = 14.dp, vertical = 6.dp)
              .testTag("btn_otp_autofill_demo")
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Key,
                contentDescription = null,
                tint = SneveRed,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Tap to auto-fill test code (749216)",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                color = SneveRedLight
              )
            }
          }

          Spacer(modifier = Modifier.height(24.dp))

          // Resend Timer Row
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
          ) {
            if (!isResendAvailable) {
              Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                tint = SneveTextMuted,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Resend OTP in 00:${secondsLeft.toString().padStart(2, '0')}",
                style = MaterialTheme.typography.bodySmall,
                color = SneveTextMuted
              )
            } else {
              Text(
                text = "Didn't receive code? ",
                style = MaterialTheme.typography.bodySmall,
                color = SneveTextSecondary
              )
              Text(
                text = "Resend OTP",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color = SneveRed,
                modifier = Modifier
                  .clickable {
                    secondsLeft = 45
                    isResendAvailable = false
                    otpCode = ""
                    resendNotice = "New 6-digit OTP sent to $destination"
                  }
                  .padding(4.dp)
                  .testTag("btn_resend_otp")
              )
            }
          }

          Spacer(modifier = Modifier.height(24.dp))

          // Verify Button
          SnevePrimaryButton(
            text = if (isVerifying) "Verifying..." else "Verify & Continue",
            icon = Icons.Default.CheckCircle,
            enabled = otpCode.length == 6 && !isVerifying,
            onClick = {
              if (otpCode.length == 6) {
                isVerifying = true
                onVerificationSuccess(userName, destination)
              } else {
                errorMessage = "Please enter the complete 6-digit verification code"
              }
            },
            modifier = Modifier.testTag("btn_submit_otp")
          )
        }
      }

      Spacer(modifier = Modifier.height(28.dp))

      // Security Note
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.Lock,
          contentDescription = null,
          tint = SneveTextMuted,
          modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "Protected by 256-bit Telecom Verification Protocol",
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
          color = SneveTextMuted
        )
      }
    }
  }
}
