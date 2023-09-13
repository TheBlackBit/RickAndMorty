package com.theblackbit.rickandmorty.feature.character.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.theblackbit.rickandmorty.core.localstorage.room.entity.CharacterEntity
import com.theblackbit.rickandmorty.core.model.Character
import com.theblackbit.rickandmorty.core.networking.response.CharacterResponse
import com.theblackbit.rickandmorty.core.networking.util.RequestStatus
import com.theblackbit.rickandmorty.feature.character.data.local.CharacterLocalRepository
import com.theblackbit.rickandmorty.feature.character.data.remote.CharacterRemoteRepository
import kotlin.random.Random

class CharactersPagingSource(
    private val localRepository: CharacterLocalRepository,
    private val remoteRepository: CharacterRemoteRepository
) : PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val currentPage = params.key ?: 1
        return when (val charactersResult = remoteRepository.getCharacters(currentPage)) {
            is RequestStatus.Success -> handleSuccessResponse(charactersResult.value, currentPage)
            else -> getDataFromLocalDB(currentPage)
        }
    }

    private suspend fun handleSuccessResponse(
        value: List<CharacterResponse>,
        page: Int
    ): LoadResult<Int, Character> {
        if (page == 1) removeDataFromCache()
        saveDataToLocalDB(value, page)
        return getDataFromLocalDB(page)
    }

    private suspend fun removeDataFromCache() {
        localRepository.deleteCharacter()
    }

    private suspend fun saveDataToLocalDB(value: List<CharacterResponse>, page: Int) {
        val entities = value.map {
            CharacterEntity(
                id = it.id,
                name = it.name ?: "",
                status = it.status ?: "",
                species = it.species ?: "",
                gender = it.gender ?: "",
                image = it.image ?: "",
                page = page
            )
        }
        localRepository.upsertCharacter(entities)
    }

    private suspend fun getDataFromLocalDB(page: Int): LoadResult<Int, Character> {
        val dataCached = localRepository.getCharacters(page).map {
            Character(
                id = it.id,
                name = it.name,
                status = it.status,
                species = it.species,
                gender = it.gender,
                image = it.image,
                height = Random.nextInt(200, 400)
            )
        }
        return LoadResult.Page(
            data = dataCached,
            prevKey = if (page == 1) null else page - 1,
            nextKey = if (dataCached.isEmpty()) null else page + 1
        )
    }
}
