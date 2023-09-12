package theblackbit.rickandmorty.feature.character.data.local

import com.theblackbit.rickandmorty.core.localstorage.datasource.LocalDataSource
import com.theblackbit.rickandmorty.core.localstorage.room.entity.CharacterEntity
import com.theblackbit.rickandmorty.feature.character.data.local.CharacterLocalRepositoryImpl
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CharacterLocalRepositoryImplTest {

    private lateinit var localDataSource: LocalDataSource

    private lateinit var sut: CharacterLocalRepositoryImpl

    @Before
    fun setUp() {
        localDataSource = mockk()
        sut = CharacterLocalRepositoryImpl(localDataSource)
    }

    @Test
    fun `when getAllCharacters characterDao must return a list of characters`(): Unit =
        runBlocking {
            coEvery {
                localDataSource.getCharacters(1)
            } returns listOfEntities()

            val result = sut.getCharacters(1)

            coVerify {
                localDataSource.getCharacters(1)
            }

            assert(result.containsAll(listOfEntities()))
        }

    @Test
    fun `when getCharacter characterDao must return a valid character`(): Unit =
        runBlocking {
            val character = listOfEntities().first()
            coEvery {
                localDataSource.getCharacter(1)
            } returns character

            val result = sut.getCharacter(1)

            coVerify {
                localDataSource.getCharacter(1)
            }

            assertEquals(character, result)
        }

    @Test
    fun `when getCharacter characterDao returns null`(): Unit =
        runBlocking {
            coEvery {
                localDataSource.getCharacter(4)
            } returns null

            val result = sut.getCharacter(4)

            coVerify {
                localDataSource.getCharacter(4)
            }

            assertEquals(result, null)
        }

    @Test
    fun `when upsert character characterDao upsertCharacter method is called`(): Unit =
        runBlocking {
            coJustRun { localDataSource.upsertCharacter(listOfEntities()) }

            sut.upsertCharacter(listOfEntities())

            coVerify {
                localDataSource.upsertCharacter(listOfEntities())
            }
        }

    @Test
    fun `when delete character characterDao deleteCharacters method is called`(): Unit =
        runBlocking {
            coJustRun { localDataSource.deleteCharacter() }

            sut.deleteCharacter()

            coVerify {
                localDataSource.deleteCharacter()
            }
        }

    private fun listOfEntities(): List<CharacterEntity> {
        val rickEntity = CharacterEntity(
            1,
            name = "Rick",
            status = "Alive",
            species = "Human",
            gender = "Male",
            image = "https://image.jpg",
            page = 1
        )

        val mortyEntity = CharacterEntity(
            2,
            name = "Morty",
            status = "Alive",
            species = "Human",
            gender = "Male",
            image = "https://image2.jpg",
            page = 1
        )

        return listOf(rickEntity, mortyEntity)
    }
}
