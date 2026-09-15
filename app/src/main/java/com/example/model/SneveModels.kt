package com.example.model

data class ServiceCategory(
  val id: String,
  val title: String,
  val shortName: String,
  val tagLine: String,
  val iconName: String,
  val proCount: Int,
  val startingPrice: String,
  val badge: String? = null,
  val subcategories: List<String>
)

data class ServiceItem(
  val id: String,
  val categoryId: String,
  val title: String,
  val categoryName: String,
  val duration: String,
  val price: String,
  val rating: Double,
  val reviewCount: Int,
  val description: String,
  val highlights: List<String>,
  val isPopular: Boolean = false,
  val isFeatured: Boolean = false
)

enum class BookingStatus(val label: String, val stepIndex: Int) {
  REQUESTED("Order Placed", 0),
  CONFIRMED("Pro Confirmed", 1),
  EN_ROUTE("Pro En Route", 2),
  IN_PROGRESS("Service In Progress", 3),
  COMPLETED("Completed", 4)
}

data class ActiveBooking(
  val id: String,
  val bookingCode: String,
  val serviceTitle: String,
  val categoryTitle: String,
  val providerName: String,
  val providerRating: Double,
  val providerTier: String,
  val status: BookingStatus,
  val scheduledTime: String,
  val address: String,
  val totalPrice: String,
  val etaMinutes: Int
)

data class UserProfile(
  val name: String,
  val email: String,
  val phone: String,
  val memberSince: String,
  val activeAddress: String,
  val completedServices: Int
)

enum class AppDestination(val route: String, val label: String) {
  HOME("home", "Home"),
  CATEGORIES("categories", "Services")
}
