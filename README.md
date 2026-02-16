# Best Calculator (Android)

A simple Android calculator app built with Kotlin + Jetpack Compose.

## What this app can do
- Basic operations: `+`, `-`, `*`, `/`
- Expression evaluation with operator precedence
- Clear (`C`) and evaluate (`=`) actions

## How to download the project files

### Option 1: Download ZIP from GitHub
1. Open the repository page in GitHub.
2. Click **Code**.
3. Click **Download ZIP**.
4. Extract the ZIP to your computer.

### Option 2: Clone with Git
```bash
git clone <your-repository-url>
cd Android-best-claculator
```

## How to open and run in Android Studio
1. Install **Android Studio** (latest stable version).
2. Open Android Studio.
3. Select **Open** and choose this project folder (`Android-best-claculator`).
4. Let Gradle sync finish.
5. Connect an Android phone (USB debugging enabled) or start an emulator.
6. Click **Run** ▶ to install and launch the app.

## How to build APK

### Build debug APK in Android Studio
1. Open project in Android Studio.
2. Go to **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
3. After build completes, click **locate** in the notification.
4. APK path is usually:
   - `app/build/outputs/apk/debug/app-debug.apk`

### Build debug APK from terminal
From project root:
```bash
./gradlew assembleDebug
```
Then use:
- `app/build/outputs/apk/debug/app-debug.apk`

## How to share/install APK
- Send `app-debug.apk` file to another device.
- On the phone, allow install from unknown sources (if prompted).
- Open the APK and install.

## Notes
- This project currently targets Android API 24+.
- If Gradle build fails, ensure Android SDK + JDK 17 are configured properly in Android Studio.
