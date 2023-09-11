package theblackbit.rickandmorty.feature.character.data.local.repository

import com.theblackbit.rickandmorty.core.localstorage.dao.CharacterDao
import com.theblackbit.rickandmorty.core.localstorage.entity.CharacterEntity
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

    private lateinit var characterDao: CharacterDao

    private lateinit var sut: CharacterLocalRepositoryImpl

    @Before
    fun setUp() {
        characterDao = mockk()
        sut = CharacterLocalRepositoryImpl(characterDao)
    }

    @Test
    fun `when getAllCharacters characterDao must return a list of characters`(): Unit =
        runBlocking {
            coEvery {
                characterDao.getCharacters(1)
            } returns listOfEntities()

            val result = sut.getCharacters(1)

            coVerify {
                characterDao.getCharacters(1)
            }

            assert(result.containsAll(listOfEntities()))
        }

    @Test
    fun `when getCharacter characterDao must return a valid character`(): Unit =
        runBlocking {
            val character = listOfEntities().first()
            coEvery {
                characterDao.getCharacter(1)
            } returns character

            val result = sut.getCharacter(1)

            coVerify {
                characterDao.getCharacter(1)
            }

            assertEquals(character, result)
        }

    @Test
    fun `when getCharacter characterDao returns null`(): Unit =
        runBlocking {
            coEvery {
                characterDao.getCharacter(4)
            } returns null

            val result = sut.getCharacter(4)

            coVerify {
                characterDao.getCharacter(4)
            }

            assertEquals(result, null)
        }

    @Test
    fun `when upsert character characterDao upsertCharacter method is called`(): Unit =
        runBlocking {
            coJustRun { characterDao.upsertCharacters(listOfEntities()) }

            sut.upsertCharacter(listOfEntities())

            coVerify {
                characterDao.upsertCharacters(listOfEntities())
            }
        }

    @Test
    fun `when delete character characterDao deleteCharacters method is called`(): Unit =
        runBlocking {
            coJustRun { characterDao.deleteCharacter(1) }

            sut.deleteCharacter(1)

            coVerify {
                characterDao.deleteCharacter(1)
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
