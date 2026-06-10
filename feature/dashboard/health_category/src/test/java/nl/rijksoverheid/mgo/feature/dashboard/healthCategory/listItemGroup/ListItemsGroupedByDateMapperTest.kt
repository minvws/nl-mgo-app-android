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
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.createMgoResource
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.UiSchemaParser
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.Profiles
import nl.rijksoverheid.mgo.data.organization.OrganizationRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneOffset
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Config(sdk = [34])
@RunWith(RobolectricTestRunner::class)
internal class ListItemsGroupedByDateMapperTest {
  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val organizationRepository = mockk<OrganizationRepository>(relaxed = true)
  private lateinit var uiSchemaParser: UiSchemaParser

  private val today = LocalDateTime.of(2026, 1, 2, 12, 0)
  private val clock = Clock.fixed(today.toInstant(ZoneOffset.UTC), ZoneOffset.UTC)
  private lateinit var mapper: ListItemsGroupedByDateMapper

  @Before
  fun setup() =
    runTest {
      uiSchemaParser = UiSchemaParserFactory.createForJvm()
      mapper = ListItemsGroupedByDateMapper(context = context, organizationRepository = organizationRepository, uiSchemaParser = uiSchemaParser, clock = clock)

      // Given: Organization is saved
      every { organizationRepository.getSaved(any()) } answers { flowOf(listOf(TEST_MGO_ORGANIZATION)) }
    }

  @Test
  fun testInvoke() =
    runTest {
      // Given: Mgo resource with todays date
      val mgoResource1 =
        createMgoResource(
          organizationId = TEST_MGO_ORGANIZATION.id,
          profile = Profiles.iHEMHDMinimalDocumentReference,
          decodedObject = TEST_IHEMHDMINIMAL_DOCUMENT_REFERENCE(date = today),
        )

      // Given: Mgo resource with yesterdays date
      val mgoResource2 =
        createMgoResource(
          organizationId = TEST_MGO_ORGANIZATION.id,
          profile = Profiles.iHEMHDMinimalDocumentReference,
          decodedObject = TEST_IHEMHDMINIMAL_DOCUMENT_REFERENCE(date = today.minusDays(1)),
        )

      // Given: Mgo resource with last weeks date
      val mgoResource3 =
        createMgoResource(
          organizationId = TEST_MGO_ORGANIZATION.id,
          profile = Profiles.iHEMHDMinimalDocumentReference,
          decodedObject = TEST_IHEMHDMINIMAL_DOCUMENT_REFERENCE(date = today.minusWeeks(1)),
        )

      // Given: Mgo resource with no date
      val mgoResource4 =
        createMgoResource(
          organizationId = TEST_MGO_ORGANIZATION.id,
          profile = Profiles.iHEMHDMinimalDocumentReference,
          decodedObject = TEST_IHEMHDMINIMAL_DOCUMENT_REFERENCE(date = null),
        )

      // When: Calling invoke
      val mgoResources = listOf(mgoResource1, mgoResource2, mgoResource3, mgoResource4)
      val listItems = mapper.invoke(mgoResources)

      // Then: List items are returned
      assertEquals(4, listItems.size)
      assertEquals(1, listItems[0].items.size)
      assertEquals(context.getString(CopyR.string.common_today), listItems[0].heading)
      assertEquals(context.getString(CopyR.string.common_yesterday), listItems[1].heading)
      assertEquals("December 26, 2025", listItems[2].heading)
      assertNull(listItems[3].heading)
    }
}
