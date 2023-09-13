package theblackbit.rickandmorty.feature.character.data.pagingsource

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.testing.TestPager
import com.theblackbit.rickandmorty.core.localstorage.room.entity.CharacterEntity
import com.theblackbit.rickandmorty.core.networking.response.CharacterResponse
import com.theblackbit.rickandmorty.core.networking.util.RequestStatus
import com.theblackbit.rickandmorty.feature.character.data.local.CharacterLocalRepository
import com.theblackbit.rickandmorty.feature.character.data.pagingsource.CharactersPagingSource
import com.theblackbit.rickandmorty.feature.character.data.remote.CharacterRemoteRepository
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CharactersPagingSourceTest {

    private lateinit var localRepository: CharacterLocalRepository

    private lateinit var remoteRepository: CharacterRemoteRepository

    private lateinit var charactersPagingSource: CharactersPagingSource

    @Before
    fun setUp() {
        localRepository = mockk()
    }

    @Test
    fun testRefreshKey() = runTest {
        remoteRepository = mockk()
        charactersPagingSource = CharactersPagingSource(
            localRepository = localRepository,
            remoteRepository = remoteRepository
        )

        val result = charactersPagingSource.getRefreshKey(
            PagingState(
                pages = emptyList(),
                anchorPosition = 1,
                config = PagingConfig(
                    pageSize = 20
                ),
                leadingPlaceholderCount = 0
            )
        )

        Assert.assertEquals(result, 1)
    }

    @Test
    fun loadReturnsPageWhenOnSuccessfulLoadOfItemKeyedData() = runTest {
        val fakeData = getFakeCharactersResponse()
        val dataFromCache = getDataFromCache(1, fakeData)

        remoteRepository = object : CharacterRemoteRepository {
            override suspend fun getCharacters(page: Int): RequestStatus<List<CharacterResponse>> {
                return RequestStatus.Success(
                    fakeData
                )
            }
            override suspend fun getCharacter(id: Int): RequestStatus<CharacterResponse?> {
                return RequestStatus.Success(null)
            }
        }

        coJustRun { localRepository.deleteCharacter() }

        coJustRun { localRepository.upsertCharacter(dataFromCache) }

        charactersPagingSource = CharactersPagingSource(
            localRepository = localRepository,
            remoteRepository = remoteRepository
        )

        coEvery {
            localRepository.getCharacters(1)
        } returns dataFromCache

        val pager = TestPager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSource = charactersPagingSource
        )

        val page = with(pager) {
            refresh()
        } as PagingSource.LoadResult.Page

        coVerify {
            localRepository.deleteCharacter()
        }

        coVerify {
            localRepository.upsertCharacter(dataFromCache)
        }

        assert(page.data.size == 20)
    }

    @Test
    fun`when load success page 2 does not clear cache`() = runTest {
        val fakeData = getFakeCharactersResponse()
        val dataFromCache = getDataFromCache(2, fakeData)

        remoteRepository = object : CharacterRemoteRepository {
            override suspend fun getCharacters(page: Int): RequestStatus<List<CharacterResponse>> {
                return RequestStatus.Success(
                    fakeData
                )
            }
            override suspend fun getCharacter(id: Int): RequestStatus<CharacterResponse?> {
                return RequestStatus.Success(null)
            }
        }

        coJustRun { localRepository.deleteCharacter() }

        coJustRun { localRepository.upsertCharacter(dataFromCache) }

        charactersPagingSource = CharactersPagingSource(
            localRepository = localRepository,
            remoteRepository = remoteRepository
        )

        coEvery {
            localRepository.getCharacters(2)
        } returns dataFromCache

        val pager = TestPager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSource = charactersPagingSource
        )

        val page = with(pager) {
            refresh(initialKey = 2)
        } as PagingSource.LoadResult.Page

        coVerify(exactly = 0) {
            localRepository.deleteCharacter()
        }

        coVerify {
            localRepository.upsertCharacter(dataFromCache)
        }

        assert(page.data.size == 20)
    }

    @Test
    fun `when api load error returns data from cache`() = runTest {
        val fakeData = getFakeCharactersResponse()
        val dataFromCache = getDataFromCache(1, fakeData)

        remoteRepository = object : CharacterRemoteRepository {
            override suspend fun getCharacters(page: Int): RequestStatus<List<CharacterResponse>> {
                return RequestStatus.NetworkError
            }
            override suspend fun getCharacter(id: Int): RequestStatus<CharacterResponse?> {
                return RequestStatus.Success(null)
            }
        }

        coJustRun { localRepository.deleteCharacter() }

        coJustRun { localRepository.upsertCharacter(dataFromCache) }

        charactersPagingSource = CharactersPagingSource(
            localRepository = localRepository,
            remoteRepository = remoteRepository
        )

        coEvery {
            localRepository.getCharacters(1)
        } returns dataFromCache

        val pager = TestPager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSource = charactersPagingSource
        )

        val page = with(pager) {
            refresh(initialKey = 1)
        } as PagingSource.LoadResult.Page

        coVerify(exactly = 0) {
            localRepository.deleteCharacter()
        }

        coVerify(exactly = 0) {
            localRepository.upsertCharacter(dataFromCache)
        }

        assert(page.data.size == 20)
    }

    private fun getFakeCharactersResponse(): List<CharacterResponse> {
        val listOfCharacters: ArrayList<CharacterResponse> = ArrayList()
        for (i in 0 until 20) {
            listOfCharacters.add(
                CharacterResponse(
                    i,
                    name = "$i",
                    status = "Alive",
                    species = "Human",
                    gender = "Male",
                    image = "https://image.jpg"
                )
            )
        }
        return listOfCharacters
    }

    private fun getDataFromCache(page: Int, remoteData: List<CharacterResponse>): List<CharacterEntity> {
        return remoteData.map {
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
    }
}
