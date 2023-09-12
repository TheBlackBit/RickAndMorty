package theblackbit.rickandmorty.feature.character.data.remote

import com.theblackbit.rickandmorty.core.networking.datasource.RemoteDataSource
import com.theblackbit.rickandmorty.core.networking.response.CharacterResponse
import com.theblackbit.rickandmorty.core.networking.util.RequestStatus
import com.theblackbit.rickandmorty.feature.character.data.remote.CharacterRemoteRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CharacterRemoteRepositoryImplTest {

    private lateinit var remoteDataSource: RemoteDataSource

    private lateinit var sut: CharacterRemoteRepositoryImpl

    @Before
    fun setUp() {
        remoteDataSource = object : RemoteDataSource {
            override suspend fun getCharacters(page: Int): RequestStatus<List<CharacterResponse>> {
                return RequestStatus.Success(listOfDataResponse())
            }

            override suspend fun getCharacter(id: Int): RequestStatus<CharacterResponse?> {
                return RequestStatus.Success(listOfDataResponse().first())
            }
        }
        sut = CharacterRemoteRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `when request characters then remoteDataSource then return valid characters`(): Unit =
        runBlocking {
            val result = sut.getCharacters(1)
            assert((result as RequestStatus.Success).value.containsAll(listOfDataResponse()))
        }

    @Test
    fun `when request character then remoteDataSource returns valid character`(): Unit =
        runBlocking {
            val result = sut.getCharacter(1)

            assert((result as RequestStatus.Success).value == listOfDataResponse().first())
        }

    private fun listOfDataResponse(): List<CharacterResponse> {
        val rickEntity = CharacterResponse(
            1,
            name = "Rick",
            status = "Alive",
            species = "Human",
            gender = "Male",
            image = "https://image.jpg",
        )

        val mortyEntity = CharacterResponse(
            2,
            name = "Morty",
            status = "Alive",
            species = "Human",
            gender = "Male",
            image = "https://image2.jpg",
        )

        return listOf(rickEntity, mortyEntity)
    }
}
