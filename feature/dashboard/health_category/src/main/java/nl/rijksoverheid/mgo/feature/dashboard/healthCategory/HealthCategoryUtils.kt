package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import android.content.Context
import androidx.annotation.StringRes
import getStringResourceByName
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareCategory
import nl.rijksoverheid.mgo.framework.copy.R

@StringRes
fun HealthCareCategory.getTitle(context: Context): Int {
  val stringResource = context.getStringResourceByName("hc_$id.heading")
  if (stringResource == 0) {
    return R.string.common_unknown
  }
  return stringResource
}
