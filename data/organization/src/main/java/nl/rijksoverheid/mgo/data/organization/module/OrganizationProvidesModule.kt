package nl.rijksoverheid.mgo.data.organization.module

import android.content.Context
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import app.cash.sqldelight.db.SqlDriver
import com.eygraber.sqldelight.androidx.driver.AndroidxSqliteDatabaseType
import com.eygraber.sqldelight.androidx.driver.AndroidxSqliteDriver
import com.eygraber.sqldelight.androidx.driver.FileProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.organization.OrganizationsDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object OrganizationProvidesModule {
  @Provides
  @Singleton
  fun provideSqlDriver(
    @ApplicationContext context: Context,
  ): SqlDriver =
    AndroidxSqliteDriver(
      driver = BundledSQLiteDriver(),
      databaseType =
        AndroidxSqliteDatabaseType.Companion.FileProvider(
          context,
          "organizations.db",
        ),
      schema = OrganizationsDatabase.Companion.Schema,
    )
}
