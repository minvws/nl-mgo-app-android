package nl.rijksoverheid.mgo.navigation.dashboard

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.MainActivity
import nl.rijksoverheid.mgo.feature.settings.SettingsScreen
import nl.rijksoverheid.mgo.navigation.mgoComposable
import nl.rijksoverheid.mgo.navigation.onboarding.OnboardingNavigation
import java.io.File

fun NavGraphBuilder.addDashboardSettingsNavGraph(
    mainActivity: MainActivity,
    rootNavController: NavController,
) {
    navigation<DashboardNavigation.Settings.Root>(DashboardNavigation.Settings.Debug) {
        mgoComposable<DashboardNavigation.Settings.Debug>(animate = false) {
            SettingsScreen(
                onNavigateToOnboarding = {
                    rootNavController.navigate(OnboardingNavigation.Root) {
                        popUpTo(rootNavController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                onRestartApp = { clearData ->
                    if (clearData) {
                        deleteRecursively(mainActivity.cacheDir)
                        deleteRecursively(mainActivity.filesDir)
                        deleteRecursively(File(mainActivity.filesDir.parentFile, "shared_prefs"))
                    }
                    val intent = Intent(mainActivity, MainActivity::class.java)
                    mainActivity.finish()
                    mainActivity.startActivity(intent)
                    Runtime.getRuntime().exit(0)
                },
            )
        }
    }
}

private fun deleteRecursively(file: File) {
    if (file.isDirectory) {
        file.listFiles()?.forEach { child ->
            deleteRecursively(child)
        }
    }
    file.delete()
}
