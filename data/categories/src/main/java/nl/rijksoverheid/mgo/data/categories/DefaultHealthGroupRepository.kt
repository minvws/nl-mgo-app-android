package nl.rijksoverheid.mgo.data.categories

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import java.io.File
import javax.inject.Inject

internal class DefaultHealthGroupRepository
  @Inject
  constructor(
    @ApplicationContext private val context: Context,
  ) : HealthGroupRepository {
    private val json = Json

    override fun get(): List<HealthGroup> {
      val jsonFile =
        context.assets.open("health-categories.json").bufferedReader().use { reader ->
          reader.readText()
        }

      val healthCategories = json.decodeFromString<List<HealthGroup>>(jsonFile)
      return healthCategories
    }
  }
