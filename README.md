# Basic Layouts in Jetpack Compose – MySoothe App

This project is based on the **Basic Layouts in Jetpack Compose** codelab from the [Android Developers – Jetpack Compose Essentials](https://developer.android.com/courses/pathways/jetpack-compose-for-android-developers-1) course.

It implements a real-world wellness UI called **MySoothe**, covering how to build clean, structured layouts using Jetpack Compose.

---

## 📱 About the App

MySoothe is a wellness app UI that demonstrates:
- Horizontal and vertical scrollable rows using `LazyRow` and `LazyColumn`
- Reusable composables for cards and list items
- `Scaffold` with a bottom navigation bar
- Proper use of `padding`, `Arrangement`, and `Alignment`
- Material 3 theming and typography

---

## 🛠️ Setup & Run Instructions

### Prerequisites
Make sure you have the following installed:
- [Android Studio Hedgehog or later](https://developer.android.com/studio)
- JDK 11 or higher
- Android SDK with **API level 21+**

---

### Step 1: Clone the Repository
```bash
git clone https://github.com/your-username/your-repo-name.git
```
> Replace `your-username` and `your-repo-name` with your actual GitHub details.

---

### Step 2: Open in Android Studio
1. Open **Android Studio**
2. Click **"Open"** and select the cloned project folder
3. Wait for **Gradle sync** to complete (this may take a minute)

---

### Step 3: Run the App
1. Connect an **Android device** via USB, or launch an **emulator** (API 21+)
2. Click the **▶ Run** button or press `Shift + F10`
3. The MySoothe app UI will launch on your device/emulator

---

## 📂 Project Structure

```
app/
 └── src/
      └── main/
           ├── java/.../MainActivity.kt       # Entry point
           ├── java/.../ui/theme/             # Material theme setup
           └── java/.../                      # Composable functions
```

---

## 🔗 References
- [Codelab – Basic Layouts in Jetpack Compose](https://developer.android.com/codelabs/jetpack-compose-layouts)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)

---

## 📄 License
This project is built for learning purposes as part of the Android Developers Jetpack Compose Essentials course.
