package nl.rijksoverheid.mgo.feature.dashboard.healthCategory.listItemGroup

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.hcimParser.UiSchemaParserFactory
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.TEST_IHEMHDMINIMAL_DOCUMENT_REFERENCE
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.TEST_R4BBS_DOCUMENT_REFERENCE
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.createMgoResource
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.UiSchemaParser
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.Profiles
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup.HealthCategory
import nl.rijksoverheid.mgo.data.organization.OrganizationRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [34])
@RunWith(RobolectricTestRunner::class)
internal class ListItemsGroupedBySubcategoryMapperTest {
  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val organizationRepository = mockk<OrganizationRepository>(relaxed = true)

  private lateinit var uiSchemaParser: UiSchemaParser
  private lateinit var mapper: ListItemsGroupedBySubcategoryMapper

  @Before
  fun setup() =
    runTest {
      uiSchemaParser = UiSchemaParserFactory.createForJvm()
      mapper = ListItemsGroupedBySubcategoryMapper(context = context, uiSchemaParser = uiSchemaParser, organizationRepository = organizationRepository)

      // Given: Organization is saved
      every { organizationRepository.getSaved(any()) } answers { flowOf(listOf(TEST_MGO_ORGANIZATION)) }
    }

  @Test
  fun testInvoke() =
    runTest {
      // Given: Mgo resources
      val mgoResource1 =
        createMgoResource(
          organizationId = TEST_MGO_ORGANIZATION.id,
          profile = Profiles.iHEMHDMinimalDocumentReference,
          decodedObject = TEST_IHEMHDMINIMAL_DOCUMENT_REFERENCE(),
        )
      val mgoResource2 =
        createMgoResource(
          organizationId = TEST_MGO_ORGANIZATION.id,
          profile = Profiles.bbsDocumentReference,
          decodedObject = TEST_R4BBS_DOCUMENT_REFERENCE(),
        )
      val mgoResource3 =
        createMgoResource(
          organizationId = TEST_MGO_ORGANIZATION.id,
          profile = Profiles.iHEMHDMinimalDocumentReference,
          decodedObject = TEST_IHEMHDMINIMAL_DOCUMENT_REFERENCE(id = "2", date = null),
        )
      val mgoResource4 =
        createMgoResource(
          organizationId = TEST_MGO_ORGANIZATION.id,
          profile = Profiles.bbsDocumentReference,
          decodedObject = TEST_R4BBS_DOCUMENT_REFERENCE(id = "2", date = null),
        )
      val mgoResources = listOf(mgoResource1, mgoResource2, mgoResource3, mgoResource4)

      // Given: Health category
      val category =
        HealthCategory(
          id = "1",
          icon = "1",
          heading = "Heading",
          subheading = "Subheading",
          subcategories =
            listOf(
              HealthCategory.Subcategory(
                id = "1",
                heading = "app_name_tst",
                profiles = listOf(Profiles.iHEMHDMinimalDocumentReference),
              ),
              HealthCategory.Subcategory(
                id = "2",
                heading = "app_name_acc",
                profiles = listOf(Profiles.bbsDocumentReference),
              ),
            ),
        )

      // When: Calling invoke
      val listItems = mapper.invoke(category = category, mgoResources = mgoResources)

      // Then: List items are returned
      assertEquals(2, listItems.size)
      assertEquals(2, listItems[0].items.size)
      assertEquals("MGO (Test)", listItems[0].heading)
      assertEquals(2, listItems[1].items.size)
      assertEquals("MGO (Acc)", listItems[1].heading)
    }
}
