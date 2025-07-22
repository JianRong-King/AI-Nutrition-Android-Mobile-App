# NutriTrack Pro – AI-Powered Nutrition Tracking App

NutriTrack Pro is a personalized nutrition tracking Android application developed using **Kotlin**, **Jetpack Compose**, **RoomDB**, and **MVVM architecture**. It was created as part of the an academic project.

This application helps users assess their dietary habits, receive AI-generated food tips, and view personalized nutrition insights through a clean and interactive interface.

## 📖 Features Overview

### 🔐 Multi-user Authentication
- Account claiming on first login using User ID + Phone Number
- Password setup and persistent sessions
- Secure logout via Settings screen

### 🗃️ Room Database Integration
- One-time CSV import into Room
- Tables: `Patient`, `FoodIntake`, `NutriCoachTips`
- Data accessed through Repository → ViewModel → DAO pattern

### 🤖 NutriCoach AI Integration
- **FruityVice API** for real-time fruit nutrition stats
- **Gemini GenAI API** for personalized motivational food tips
- Tips stored in Room and shown in modal history

### ⚙️ Settings & Admin View
- Displays user info and logout option
- Admin mode unlocked with key: `dollar-entry-apples`
- Clinician dashboard with:
  - Average HEIFA scores by gender
  - GenAI-generated nutrition trends

---

## 📖 App Specification Overview

## 📱 App Screens

Here’s a breakdown of all major screens included in the application (based on your updated mockup):

### 🟢 **Welcome Screen**
- App name, logo, disclaimer
- Link to Monash Nutrition Clinic
- Student ID/name displayed
- "Login" button to continue

### 🔐 **Login Screen**
- Dropdown for selecting User ID (from CSV)
- Phone number input and validation
- Account claiming flow with password creation on first login
- Login state persisted unless user logs out

### 📋 **Questionnaire Screen**
- Checkboxes for food groups (fruits, veg, etc.)
- Persona selection
- Time pickers for meal/sleep/wake time
- "Save" button stores data in `SharedPreferences`

### 🏠 **Home Screen**
- Displays personalized greeting and total food quality score
- Includes edit button to return to questionnaire
- Navigation to Insights or NutriCoach screen

### 📊 **Insights Screen**
- Visual progress bars for each food group (e.g., Fruits, Dairy)
- Total HEIFA score
- Buttons:
  - “Share with someone”
  - “Improve my diet” → links to NutriCoach

### 🧠 **NutriCoach Screen**
- Conditional display based on fruit score
- **FruityVice API**: fruit search and nutrition facts
- **Gemini GenAI**: AI-generated food tips
- History modal to view previously generated tips

### ⚙️ **Settings Screen**
- Displays logged-in user’s name and phone number
- Logout button (resets session)
- “Admin View” access with clinician key

### 👩‍⚕️ **Clinician/Admin View**
- Accessible via code: `dollar-entry-apples`
- Analytics:
  - Average HEIFA by gender
  - GenAI-generated insights from all patient data

### 🏆 **Leaderboard Modal **
- Real-time comparison of users based on HEIFA scores
- Further breakdown of each score for the user to understand where they stand on each scores. 



---

## 🛠️ Tech Stack

| Layer         | Tech Used                          |
|---------------|------------------------------------|
| Language      | Kotlin                             |
| UI            | Jetpack Compose, Material Design   |
| Architecture  | MVVM, Repository pattern           |
| DB            | Room (SQLite)                      |
| Network       | Retrofit + Coroutines              |
| State Mgmt    | LiveData, ViewModel                |
| Storage       | SharedPreferences (questionnaire)  |
| APIs          | FruityVice, Gemini GenAI           |

---

## 🗂️ Data Flow & Architecture

All data follows a clean architecture pattern:





---

## 🚀 Getting Started

### Prerequisites
- Android Studio 
- Emulator or device with API level 35

### Setup Steps
1. Clone the repository
2. Open the project in Android Studio
3. Add your Gemini GenAI API key to `secrets.properties` or via `BuildConfig`
4. Run the app (first launch will import the CSV into Room)

⚠️ The CSV is imported **only on the first launch**. Reinstall the app to reset.

---

## 📎 APIs Used

- [FruityVice API](https://www.fruityvice.com/) – fruit nutrition data  
- [Gemini GenAI](https://aistudio.google.com/) – food tips & motivation  
- [Picsum Photos](https://picsum.photos/) – fallback image display  

---


## 👤 Author

**Jian Rong King**  
[LinkedIn](https://www.linkedin.com/in/jianrong-king) | [GitHub](https://github.com/JianRong-King) | [Portfolio](https://kingjianrong.vercel.app)


