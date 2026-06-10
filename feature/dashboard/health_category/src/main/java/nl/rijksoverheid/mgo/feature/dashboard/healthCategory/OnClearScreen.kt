package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import nl.rijksoverheid.mgo.component.pdf.MgoPdfStore
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceStore
import javax.inject.Inject

internal class OnClearScreen
  @Inject
  constructor(
    private val mgoResourceStore: MgoResourceStore,
    private val mgoPdfStore: MgoPdfStore,
  ) {
    operator fun invoke() {
      mgoResourceStore.clear()
      mgoPdfStore.clear()
    }
  }
