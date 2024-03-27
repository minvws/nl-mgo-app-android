package nl.rijksoverheid.mgo.feature.dashboard

import android.app.Application
import android.content.Intent
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import nl.rijksoverheid.mgo.data.onboarding.SetHasSeenOnboarding
import javax.inject.Inject

@HiltViewModel
internal class DashboardViewModel
    @Inject
    constructor(
        @ApplicationContext private val context: Application,
        private val setHasSeenOnboarding: SetHasSeenOnboarding,
    ) : ViewModel
        () {
        fun reset() {
            setHasSeenOnboarding.invoke(false)

            // Restart app
            val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
