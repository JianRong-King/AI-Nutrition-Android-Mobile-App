# NutriTrack Pro – AI-Powered Nutrition Tracking App

NutriTrack Pro is a personalized nutrition tracking Android application developed as an academic project. It integrates AI-driven dietary tips, user authentication, and detailed health insights through a clean MVVM architecture.

## 📱 Features

- 🔐 **Multi-user authentication**
  - Account claiming on first login via UserID + Phone Number
  - Secure password setup and persistent login
  - Logout and session management via Settings screen

- 🗃️ **Room Database Integration**
  - Initial CSV load on first launch only
  - Patient and FoodIntake tables with foreign key relationships
  - GenAI tips stored per user in `NutriCoachTips` table

- 🧠 **NutriCoach AI Screen**
  - Conditional logic: shown only for users with suboptimal fruit scores
  - 🍎 **FruityVice API** integration for real-time fruit facts
  - 🤖 **Gemini GenAI** integration for motivational food tips
  - Previous tips displayed via modal history view

- ⚙️ **Settings Screen**
  - Displays user info (name, phone number)
  - Logout functionality
  - Access to Admin View via clinician key

- 📊 **Clinician/Admin View**
  - Gender-based HEIFA score analytics
  - AI-generated data patterns (e.g. "High veg score → high fruit score")

## 🛠 Tech Stack

- **Language**: Kotlin  
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room (SQLite)  
- **Networking**: Retrofit + Coroutines  
- **UI**: Jetpack Components + XML  
- **State Management**: LiveData  

## 🧩 Architecture

All data interactions flow through:
