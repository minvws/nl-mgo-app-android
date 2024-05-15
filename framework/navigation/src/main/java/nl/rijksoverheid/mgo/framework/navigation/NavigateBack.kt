package nl.rijksoverheid.mgo.framework.navigation

import android.content.Context
import androidx.activity.ComponentActivity

/**
 * TODO Hacky implementation to allow navigating back through multiple nav controllers.
 * Normally you would simply call navController.popBackStack() but since this project uses multiple nav controllers in each feature,
 * this would not work. For example, you navigate from feature1 with a NavController, to feature 2 with a NavController. If you
 * call navController(feature2).popBackStack() from the first composable, this would not go back to feature 1. onBackPressed()
 * has logic build in to automatically handle multiple nav controllers. Probably want to refactor this in future, als because
 * onBackPressed() is a deprecated method, but it's fine for now.
 */
fun Context.navigateBack() {
    (this as ComponentActivity).onBackPressed()
}
