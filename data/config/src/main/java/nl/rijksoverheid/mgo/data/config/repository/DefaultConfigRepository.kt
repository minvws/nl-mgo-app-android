package nl.rijksoverheid.mgo.data.config.repository

import nl.rijksoverheid.mgo.data.config.Config
import nl.rijksoverheid.mgo.data.config.datasource.ConfigResponseLocalDataSource
import nl.rijksoverheid.mgo.data.config.datasource.ConfigResponseRemoteDataSource
import nl.rijksoverheid.mgo.data.config.toConfig
import javax.inject.Inject

internal class DefaultConfigRepository
    @Inject
    constructor(private val localDataSource: ConfigResponseLocalDataSource, private val remoteDataSource: ConfigResponseRemoteDataSource) :
    ConfigRepository {
        override suspend fun getConfig(): Result<Config> =
            localDataSource.get()
                .recoverCatching {
                    // If failed to get local config, get config from remote
                    remoteDataSource
                        .get()
                        .onSuccess { response -> localDataSource.store(response) }
                        .getOrThrow()
                }
                .mapCatching { it.toConfig() }
    }
