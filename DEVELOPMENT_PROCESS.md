
---

# ✅ `DEVELOPMENT_SUMMARY.md`

```md
# Development Summary – TripEase

---

## Project Objective  
The objective of the TripEase project was to design and implement a mobile application that assists users in planning trips and managing travel-related expenses. The app was developed in response to Scenario 7 from the CP3406 assessment brief, which focuses on reducing the complexity of travel planning through a centralised digital solution.

---

## Development Process  

### Initial Planning  
The project began with analysing the problem scenario and identifying core user needs. Key requirements included:
- Centralised storage of trip information
- Expense tracking per trip
- Simple and intuitive navigation
- A clean and minimal user interface

These requirements guided early design decisions and feature prioritisation.

---

### Architecture and Structure  
TripEase was built using the **MVVM architecture**, which allowed for clear separation between:
- **Model**: Data entities such as Trip and Expense  
- **ViewModel**: Business logic and state management  
- **View (UI)**: Jetpack Compose composable screens  

The project was structured into logical packages, including `data`, `model`, `viewmodel`, and `ui`, improving readability and maintainability.

---

### Implementation  
The core functionality was implemented incrementally:
- Navigation using a bottom navigation bar and NavHost
- Trip creation and listing
- Expense creation linked to selected trips
- Local data persistence using Room
- State handling using ViewModel and Kotlin flows

Jetpack Compose was used for all UI elements, enabling a declarative approach to building reusable and modular screens.

---

### Challenges Encountered  
Several challenges arose during development, including:
- Navigation configuration and route management
- Package and file organisation issues
- Gradle and manifest configuration errors

These issues required careful debugging and reinforced the importance of correct project structure and incremental testing.

---

### Version Control and Documentation  
GitHub was used for version control throughout development. Commits were made incrementally to reflect meaningful milestones. Documentation, including the README and this development summary, was written to clearly communicate the project’s purpose, features, and implementation approach.

---

## Outcome  
The final result is a functional prototype that demonstrates efficient trip organisation and expense tracking. The app meets the core requirements of the assessment and reflects the application of modern Android development practices.

---

## Future Improvements  
If further development time were available, the following enhancements would be considered:
- Editing and deleting trips and expenses
- Improved expense summaries and analytics
- User customisation options and reminders

---

## Conclusion  
TripEase provided valuable hands-on experience in Android application development. The project strengthened understanding of Jetpack Compose, Room, and MVVM architecture, and contributed to improved problem-solving and software design skills.
