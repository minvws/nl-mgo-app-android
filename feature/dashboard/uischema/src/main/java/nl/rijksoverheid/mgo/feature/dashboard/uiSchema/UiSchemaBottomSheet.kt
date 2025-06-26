package nl.rijksoverheid.mgo.feature.dashboard.uiSchema

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import nl.rijksoverheid.mgo.component.mgo.navigation.mgoComposable
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import kotlin.reflect.typeOf

@Serializable
data object Root

@Serializable
data class UiSchema(
  val organization: MgoOrganization,
  val mgoResource: MgoResource,
)

object UiSchemaBottomSheetTestTag {
  const val SHEET = "UiSchemaBottomSheet"
}

@Composable
fun UiSchemaBottomSheet(
  organization: MgoOrganization,
  mgoResource: MgoResource,
  onDismissRequest: () -> Unit,
) {
  val navController = rememberNavController()
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    modifier = Modifier.testTag(UiSchemaBottomSheetTestTag.SHEET),
    contentWindowInsets = { WindowInsets(0) },
    onDismissRequest = onDismissRequest,
    sheetState = sheetState,
    dragHandle = { BottomSheetDefaults.DragHandle() },
  ) {
    NavHost(
      navController = navController,
      startDestination = Root,
      enterTransition = { EnterTransition.None },
      exitTransition = { ExitTransition.None },
    ) {
      mgoComposable<Root>(
        animate = false,
        typeMap =
          mapOf(
            typeOf<MgoOrganization?>() to JsonNavType(MgoOrganization::class.java, MgoOrganization.serializer()),
            typeOf<MgoOrganization>() to JsonNavType(MgoOrganization::class.java, MgoOrganization.serializer()),
            typeOf<MgoResource>() to JsonNavType(MgoResource::class.java, MgoResource.serializer()),
          ),
      ) {
        UiSchemaScreen(
          organization = organization,
          mgoResource = mgoResource,
          isSummary = false,
          isBottomSheet = true,
          onNavigateToDetail = { organization, mgoResource ->
            navController.navigate(UiSchema(organization, mgoResource))
          },
          onNavigateBack = null,
        )
      }

      mgoComposable<UiSchema>(
        typeMap =
          mapOf(
            typeOf<MgoOrganization?>() to JsonNavType(MgoOrganization::class.java, MgoOrganization.serializer()),
            typeOf<MgoOrganization>() to JsonNavType(MgoOrganization::class.java, MgoOrganization.serializer()),
            typeOf<MgoResource>() to JsonNavType(MgoResource::class.java, MgoResource.serializer()),
          ),
      ) { backStackEntry ->
        val route = backStackEntry.toRoute<UiSchema>()
        UiSchemaScreen(
          organization = route.organization,
          mgoResource = route.mgoResource,
          isSummary = false,
          isBottomSheet = true,
          onNavigateToDetail = { organization, mgoResource ->
            navController.navigate(UiSchema(organization, mgoResource))
          },
          onNavigateBack = { navController.popBackStack() },
        )
      }
    }
  }
}
