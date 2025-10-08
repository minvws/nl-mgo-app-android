package nl.rijksoverheid.mgo.data.hcimParser.mgoResource

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MgoResourceStore
  @Inject
  constructor() {
    private var mgoResources: MutableList<MgoResource> = mutableListOf()

    fun store(mgoResource: MgoResource) {
      mgoResources.add(mgoResource)
    }

    fun clear() {
      mgoResources.clear()
    }
  }
