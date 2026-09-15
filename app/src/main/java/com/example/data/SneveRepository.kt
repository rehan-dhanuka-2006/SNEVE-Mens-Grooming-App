package com.example.data

import com.example.model.ActiveBooking
import com.example.model.BookingStatus
import com.example.model.ServiceCategory
import com.example.model.ServiceItem
import com.example.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

object SneveRepository {

  val categories: List<ServiceCategory> = listOf(
    ServiceCategory(
      id = "cat_hair",
      title = "Haircuts & Styling",
      shortName = "Haircuts",
      tagLine = "Master mobile barbers with precision scissors & clippers",
      iconName = "ContentCut",
      proCount = 38,
      startingPrice = "₹499",
      badge = "Most Popular",
      subcategories = listOf("Master Haircut & Style", "Skin Fade & Low Taper", "Classic Scissor Precision Cut", "Buzz & Razor Lineup", "Texturized Crop")
    ),
    ServiceCategory(
      id = "cat_beard",
      title = "Beard & Hot Towel Shave",
      shortName = "Beard & Shave",
      tagLine = "Straight razor sculpting, soothing steam & organic oils",
      iconName = "Face",
      proCount = 42,
      startingPrice = "₹349",
      badge = "Signature",
      subcategories = listOf("Traditional Hot Towel Shave", "Beard Sculpting & Shape-Up", "Razor Sharp Lineup", "Warm Butter Beard Conditioning", "Mustache Wax & Detail")
    ),
    ServiceCategory(
      id = "cat_packages",
      title = "Executive Packages",
      shortName = "Packages",
      tagLine = "Complete top-to-bottom grooming delivered to your door",
      iconName = "AutoAwesome",
      proCount = 29,
      startingPrice = "₹1,199",
      badge = "Best Value",
      subcategories = listOf("The SNEVE Executive Experience", "The Chairman Hair + Beard Combo", "Father & Son Grooming Duo", "Pre-Event Rapid Touchup")
    ),
    ServiceCategory(
      id = "cat_treatment",
      title = "Facials & Scalp Treatments",
      shortName = "Facials & Scalp",
      tagLine = "Rejuvenating charcoal masks, scalp detox & hot stone therapy",
      iconName = "Spa",
      proCount = 21,
      startingPrice = "₹599",
      badge = "Relaxation",
      subcategories = listOf("Black Charcoal Detox Facial", "Invigorating Tea Tree Scalp Scrub", "Under-Eye Revival Treatment", "Anti-Fatigue Cooling Mask")
    ),
    ServiceCategory(
      id = "cat_color",
      title = "Color & Gray Blending",
      shortName = "Coloring",
      tagLine = "Discrete, natural gray coverage and beard tone enhancement",
      iconName = "Palette",
      proCount = 18,
      startingPrice = "₹699",
      badge = "Natural Look",
      subcategories = listOf("Subtle Beard Gray Blending", "Natural Hair Gray Camouflage", "Matte Tone Rebalance", "Full Coverage Coloring")
    ),
    ServiceCategory(
      id = "cat_wedding",
      title = "Wedding & Groomsmen Party",
      shortName = "Wedding & Events",
      tagLine = "Full mobile barbershop squad dispatched to your venue or suite",
      iconName = "Star",
      proCount = 15,
      startingPrice = "₹3,499",
      badge = "Special Event",
      subcategories = listOf("Full Groomsmen Suite Setup", "Groom Day-Of Preparation", "Celebration Grooming Lounge", "Red Carpet Styling Prep")
    )
  )

  val services: List<ServiceItem> = listOf(
    ServiceItem(
      id = "srv_1",
      categoryId = "cat_packages",
      title = "The SNEVE Executive Experience",
      categoryName = "Executive Packages",
      duration = "75 min",
      price = "₹1,499",
      rating = 4.99,
      reviewCount = 420,
      description = "Our flagship delivered service: Precision haircut, straight razor neck clean, tailored beard sculpt or shave, eucalyptus hot towel, scalp massage, and finishing charcoal facial mask.",
      highlights = listOf("Master Barber dispatched to your door", "Eucalyptus hot towel treatment", "Organic beard butter & styling wax", "Complimentary neck & shoulder massage"),
      isPopular = true,
      isFeatured = true
    ),
    ServiceItem(
      id = "srv_2",
      categoryId = "cat_hair",
      title = "Master Haircut & Styling",
      categoryName = "Haircuts & Styling",
      duration = "45 min",
      price = "₹699",
      rating = 4.98,
      reviewCount = 680,
      description = "Full custom haircut tailored to your head shape and hair texture. Includes consultation, precision shears or clippers, straight razor neck cleanup, blow dry, and matte finish styling.",
      highlights = listOf("Personalized style consultation", "Straight razor hairline cleanup", "Premium matte paste or clay", "Sanitized mobile station"),
      isPopular = true,
      isFeatured = true
    ),
    ServiceItem(
      id = "srv_3",
      categoryId = "cat_beard",
      title = "Royal Hot Towel Straight Razor Shave",
      categoryName = "Beard & Hot Towel Shave",
      duration = "45 min",
      price = "₹499",
      rating = 4.96,
      reviewCount = 310,
      description = "Old-world barbering brought to your location. Multi-stage hot towel prep, pre-shave botanical oils, rich lather, precision single-blade shave, and soothing cold towel with aftershave balm.",
      highlights = listOf("3 hot towel applications", "Feather-blade single razor", "Soothing alum & aftershave balm", "Zero irritation guarantee"),
      isPopular = true
    ),
    ServiceItem(
      id = "srv_4",
      categoryId = "cat_beard",
      title = "Beard Sculpting & Razor Lineup",
      categoryName = "Beard & Hot Towel Shave",
      duration = "35 min",
      price = "₹399",
      rating = 4.95,
      reviewCount = 285,
      description = "Sculpting the jawline, trimming stray lengths, defining cheek and neck lines with a straight razor, and nourishing with sandalwood beard butter.",
      highlights = listOf("Symmetrical line carving", "High-frequency clipper fade", "Hydrating organic oil application", "Moustache trimming & styling"),
      isPopular = false
    ),
    ServiceItem(
      id = "srv_5",
      categoryId = "cat_treatment",
      title = "Deep Charcoal Detox & Facial",
      categoryName = "Facials & Scalp Treatments",
      duration = "40 min",
      price = "₹599",
      rating = 4.94,
      reviewCount = 145,
      description = "Designed specifically for men's skin. Deep pore cleansing, steam mist, exfoliating scrub, activated charcoal peel-off mask, and intense hydration moisturizer.",
      highlights = listOf("Cleans clogged pores", "Unclogs ingrown hairs", "Hydrating peptide serum", "Refreshing cooling mist"),
      isPopular = false
    ),
    ServiceItem(
      id = "srv_6",
      categoryId = "cat_color",
      title = "Subtle Beard & Hair Gray Camouflage",
      categoryName = "Color & Gray Blending",
      duration = "50 min",
      price = "₹799",
      rating = 4.97,
      reviewCount = 190,
      description = "Naturally blend away salt-and-pepper grays in 10 minutes without looking dyed or unnatural. Uses ammonia-free demi-permanent custom tones.",
      highlights = listOf("Natural undetectable blend", "Lasts 4-6 weeks", "Zero root demarcation", "Ammonia-free formula"),
      isPopular = true
    )
  )

  private val _currentUser = MutableStateFlow(
    UserProfile(
      name = "Arjun Kapoor",
      email = "arjun.kapoor@sneve.in",
      phone = "+91 98201 45678",
      memberSince = "November 2024",
      activeAddress = "Flat 802, Pali Hill, Bandra West, Mumbai, Maharashtra",
      completedServices = 18
    )
  )
  val currentUser: StateFlow<UserProfile> = _currentUser.asStateFlow()

  private val _bookings = MutableStateFlow<List<ActiveBooking>>(
    listOf(
      ActiveBooking(
        id = "bk_101",
        bookingCode = "SNV-9241-IND",
        serviceTitle = "The SNEVE Executive Experience",
        categoryTitle = "Executive Packages",
        providerName = "Marcus Thorne",
        providerRating = 4.99,
        providerTier = "Master Barber & Stylist",
        status = BookingStatus.EN_ROUTE,
        scheduledTime = "Today, 3:30 PM",
        address = "Flat 802, Pali Hill, Bandra West, Mumbai",
        totalPrice = "₹1,499",
        etaMinutes = 12
      ),
      ActiveBooking(
        id = "bk_100",
        bookingCode = "SNV-8102-IND",
        serviceTitle = "Master Haircut & Styling",
        categoryTitle = "Haircuts & Styling",
        providerName = "David Sterling",
        providerRating = 4.98,
        providerTier = "SNEVE Master Barber",
        status = BookingStatus.COMPLETED,
        scheduledTime = "Last Friday, 11:00 AM",
        address = "Flat 802, Pali Hill, Bandra West, Mumbai",
        totalPrice = "₹699",
        etaMinutes = 0
      )
    )
  )
  val bookings: StateFlow<List<ActiveBooking>> = _bookings.asStateFlow()

  fun bookNewService(service: ServiceItem, customNotes: String = ""): ActiveBooking {
    val newBooking = ActiveBooking(
      id = "bk_" + UUID.randomUUID().toString().take(6),
      bookingCode = "SNV-" + (1000..9999).random() + "-IND",
      serviceTitle = service.title,
      categoryTitle = service.categoryName,
      providerName = "SNEVE Master Barber Assigned",
      providerRating = 4.98,
      providerTier = "SNEVE Certified Partner",
      status = BookingStatus.CONFIRMED,
      scheduledTime = "Today in 60 min",
      address = _currentUser.value.activeAddress,
      totalPrice = service.price,
      etaMinutes = 45
    )
    _bookings.value = listOf(newBooking) + _bookings.value
    return newBooking
  }

  fun updateAddress(newAddress: String) {
    _currentUser.value = _currentUser.value.copy(activeAddress = newAddress)
  }

  fun updateUserName(name: String, email: String) {
    _currentUser.value = _currentUser.value.copy(name = name, email = email)
  }
}
