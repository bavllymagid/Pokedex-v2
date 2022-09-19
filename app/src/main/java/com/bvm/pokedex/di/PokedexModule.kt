package com.bvm.pokedex.di

import android.content.Context
import com.bvm.pokedex.data.remote.PokedexApi
import com.bvm.pokedex.data.remote.RemoteDataSource
import com.bvm.pokedex.data.remote.RemoteDataSourceImpl
import com.bvm.pokedex.data.repository.PokedexRepositoryImpl
import com.bvm.pokedex.domain.repository.PokedexRepository
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PokedexModule {

    @Singleton
    @Provides
    fun provideRetrofitApi(@ApplicationContext context: Context) : PokedexApi = Retrofit.Builder()
        .client(chuckProvider(context))
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://pokeapi.co/api/v2/")
        .build().create(PokedexApi::class.java)

    private fun chuckProvider(@ApplicationContext context: Context) : OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .collector(ChuckerCollector(context))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            .build()

    @Singleton
    @Provides
    fun provideRemoteDataSource(api:PokedexApi):RemoteDataSource =
        RemoteDataSourceImpl(api)

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: RemoteDataSource):PokedexRepository =
        PokedexRepositoryImpl(remoteDataSource)

}