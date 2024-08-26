package gameData

fun getAppDataDirectory(): String {
    val userHome = System.getProperty("user.home")
    val appName = "Birne"

    return when (val os = System.getProperty("os.name").lowercase()) {
        "windows" -> "$userHome\\AppData\\Local\\$appName"
        "mac os x" -> "$userHome/Library/Application Support/$appName"
        else -> "$userHome/.local/share/$appName" // Assume Linux or Unix-like
    }
}
