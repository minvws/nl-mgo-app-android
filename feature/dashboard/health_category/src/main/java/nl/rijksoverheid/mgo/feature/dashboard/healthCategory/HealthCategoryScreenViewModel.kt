package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import android.content.Context
import androidx.annotation.StringRes
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import getStringResourceByName
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.data.fhirParser.uiSchema.UiSchemaMapper
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataState
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.HealthCareDataStatesRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.MgoResourceRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.getProfiles
import nl.rijksoverheid.mgo.data.healthcare.models.toSections
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.framework.pdf.Pdf
import nl.rijksoverheid.mgo.framework.pdf.PdfGenerator
import nl.rijksoverheid.mgo.framework.pdf.PdfGroupedTables
import nl.rijksoverheid.mgo.framework.pdf.PdfStyle
import nl.rijksoverheid.mgo.framework.pdf.PdfSubTable
import nl.rijksoverheid.mgo.framework.pdf.PdfTable
import java.io.File
import java.time.Clock
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import javax.inject.Named
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * The [ViewModel] for [HealthCategoryScreen].
 *
 * @param category The [HealthCareCategory] to determine which health care data falls into this category.
 * @param filterOrganization If not null, will observe health care data for this organization. If null will observe for all added
 * organizations.
 * @param context Application context.
 * @param organizationRepository The [OrganizationRepository] to fetch the added organizations.
 * @param healthCareDataStatesRepository The [HealthCareDataStatesRepository] that is responsible for fetching the health care data.
 * @param mgoResourceRepository The [MgoResourceRepository] that is used to filter out resources so that only the resources are shown
 * that we want to show.
 * @param uiSchemaMapper The [UiSchemaMapper] to get models for displaying the health care data.
 * @param clock The clock used to be used in the pdf generation.
 */
@HiltViewModel(assistedFactory = HealthCategoryScreenViewModel.Factory::class)
internal class HealthCategoryScreenViewModel
  @AssistedInject
  constructor(
    @Assisted("category") private val category: HealthCareCategory,
    @Assisted("filterOrganization") private val filterOrganization: MgoOrganization? = null,
    @ApplicationContext private val context: Context,
    private val organizationRepository: OrganizationRepository,
    private val healthCareDataStatesRepository: HealthCareDataStatesRepository,
    private val mgoResourceRepository: MgoResourceRepository,
    private val uiSchemaMapper: UiSchemaMapper,
    @Named("systemDefaultZone") private val clock: Clock,
    private val pdfGenerator: PdfGenerator,
  ) : ViewModel() {
    @AssistedFactory
    interface Factory {
      fun create(
        @Assisted("category") category: HealthCareCategory,
        @Assisted("filterOrganization") filterOrganization: MgoOrganization? = null,
      ): HealthCategoryScreenViewModel
    }

    private val initialState = HealthCategoryScreenViewState.initialState(category)
    private val _viewState: MutableStateFlow<HealthCategoryScreenViewState> = MutableStateFlow(initialState)
    val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialState)

    private val _openPdfViewer = MutableSharedFlow<File>(extraBufferCapacity = 1)
    val openPdfViewer = _openPdfViewer.asSharedFlow()

    init {
      viewModelScope.launch {
        healthCareDataStatesRepository
          .observe(
            category = category,
            filterOrganization = filterOrganization,
          ).distinctUntilChanged()
          .collectLatest { states ->
            val loading = states.any { state -> state is HealthCareDataState.Loading }
            val empty = states.all { state -> state is HealthCareDataState.Empty }
            val listItems =
              states
                .map { state ->
                  state.toListItems(
                    organization = state.organization,
                    category = state.category,
                  )
                }.flatten()
            val error =
              states
                .filterIsInstance<HealthCareDataState.Loaded>()
                .any { state -> state.results.any { result -> result.isFailure } }
            _viewState.update {
              val listItemState =
                when {
                  loading -> HealthCategoryScreenViewState.ListItemsState.Loading
                  empty -> HealthCategoryScreenViewState.ListItemsState.NoData
                  else -> HealthCategoryScreenViewState.ListItemsState.Loaded(listItems)
                }
              HealthCategoryScreenViewState(
                category = category,
                showErrorBanner = error,
                listItemsState = listItemState,
              )
            }
          }
      }
    }

    /**
     * Get health care data.
     */
    fun retry() {
      viewModelScope.launch {
        if (filterOrganization == null) {
          val organizations = organizationRepository.get()
          for (organization in organizations) {
            healthCareDataStatesRepository.refresh(category = category, organization = organization)
          }
        } else {
          healthCareDataStatesRepository.refresh(category = category, organization = filterOrganization)
        }
      }
    }

    fun generatePdf() {
      viewModelScope.launch {
        val now = LocalDateTime.now(clock)
        val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale("nl", "NL"))
        val dateString = now.format(dateFormatter)
        val timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale("nl", "NL"))
        val timeString = now.format(timeFormatter)
        val groupedPdfTables =
          (_viewState.value.listItemsState as? HealthCategoryScreenViewState.ListItemsState.Loaded)?.listItemsGroup?.toPdfTables() ?: listOf()
        val pdf =
          Pdf(
            heading = context.getString(category.getTitle(context)),
            subHeading = context.getString(CopyR.string.export_pdf_subheading, dateString, timeString),
            groupedTables = groupedPdfTables,
            footer = context.getString(CopyR.string.export_pdf_footer),
          )
        val pdfStyle =
          PdfStyle(
            tableHeadingsBackgroundColor = "#F4F4F4".toColorInt(),
            tableCellBorderColor = "#E1E1E1".toColorInt(),
            footerTextColor = "#6D6D6D".toColorInt(),
          )
        val file =
          pdfGenerator.invoke(
            pdf = pdf,
            style = pdfStyle,
            fileName = "export.pdf",
          )
        _openPdfViewer.tryEmit(file)
      }
    }

    private suspend fun HealthCareDataState.toListItems(
      organization: MgoOrganization,
      category: HealthCareCategory,
    ): List<HealthCategoryScreenListItemsGroup> {
      return if (this is HealthCareDataState.Loaded) {
        // Get all the mgo resources as one big list
        val mgoResources =
          this.results
            .mapNotNull { result -> result.getOrNull() }
            .flatten()

        // Filter them to only display the onces we want to show
        val filteredMgoResources = mgoResourceRepository.filter(resources = mgoResources, profiles = category.getProfiles())

        // Group them by category
        val groupedMgoResources =
          filteredMgoResources
            .groupBy { mgoResource -> mgoResource.getGroupHeading() }

        // Map it to own list items group class
        return groupedMgoResources.toListItemsGroup(uiSchemaMapper = uiSchemaMapper, organization = organization)
      } else {
        listOf()
      }
    }

    private suspend fun List<HealthCategoryScreenListItemsGroup>.toPdfTables(): List<PdfGroupedTables> =
      map { itemsGroup ->
        val pdfTables =
          itemsGroup.items.map { listItem ->
            uiSchemaMapper
              .getSummary(listItem.mgoResource)
              .toSections()
              .map { section ->
                PdfSubTable(
                  heading = section.heading,
                  data = section.rows.mapNotNull { row -> (row.heading ?: return@mapNotNull null) to row.value },
                )
              }.filter { it.data.isNotEmpty() }
              .let { subTables ->
                PdfTable(
                  heading = listItem.title,
                  subTables = subTables,
                )
              }
          }
        PdfGroupedTables(
          heading = context.getString(itemsGroup.heading),
          tables = pdfTables,
        )
      }
  }

@StringRes
internal fun HealthCareCategory.getTitle(context: Context): Int {
  val stringResource = context.getStringResourceByName("hc_$id.heading")
  if (stringResource == 0) {
    return CopyR.string.common_unknown
  }
  return stringResource
}
