RestaurantDemo is a sample project demonstrating mvvm approach with using lifecycle, livedata, room, viewmodel, repository, databinding

# Requirements
Android SDK

Latest version of stable Android Studio

# To get project working:
Import project -> Build -> Run

Alternative way is: 
  
  run **_./gradlew :app:assembleDebug_**
  
  run **_adb install app/build/outputs/apk/debug/app-debug.apk_**
  
  run **_adb shell am start -n "com.bodoss.restaurantdemo/com.bodoss.restaurantdemo.MainActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER_**
  
[How to run unit tests in Android studio](https://developer.android.com/training/testing/unit-testing/local-unit-tests#run)

