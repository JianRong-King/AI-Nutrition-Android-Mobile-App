# NutriTrack Pro – AI-Powered Nutrition Tracking App

NutriTrack Pro is a personalized nutrition tracking Android application developed using **Kotlin**, **Jetpack Compose**, **RoomDB**, and **MVVM architecture**. It was created as part of the **FIT2081 – Mobile App Development** unit at Monash University.

This application helps users assess their dietary habits, receive AI-generated food tips, and view personalized nutrition insights through a clean and interactive interface.

---

## 📖 App Specification Overview

This project follows the official **NutriTrack - Student Implementation Specification (v1.0, March 2025)**. It includes:

### ✅ Core Screens & Navigation

1. **Welcome Screen**
   - Displays app name, disclaimer, Monash Nutrition Clinic link
   - Student name and ID (e.g. “Alex Scott (14578373)”)
   - Login button

2. **Login Screen**
   - Dropdown: User ID (from CSV)
   - Text field: Phone number (CSV validated)
   - Validation logic returns errors on mismatch

3. **Food Intake Questionnaire**
   - Checkboxes for food groups (Fruits, Vegetables, etc.)
   - Persona selection
   - Time pickers for meal, wake, and sleep time
   - Data saved to `SharedPreferences`

4. **Home Screen**
   - Shows user greeting and total food score
   - Navigation and Edit buttons

5. **Insights Screen**
   - Progress bars for individual food categories
   - Buttons: “Share with someone”, “Improve my diet” (future use)

---

## 🧠 NutriTrack Pro: Extended Features

In addition to the required spec, NutriTrack Pro introduces several advanced features:

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

