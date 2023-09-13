package theblackbit.rickandmorty.feature.character.domain

import com.theblackbit.rickandmorty.core.localstorage.room.entity.CharacterEntity
import com.theblackbit.rickandmorty.feature.character.data.local.CharacterLocalRepository
import com.theblackbit.rickandmorty.feature.character.domain.CharacterInfoUseCaseImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CharacterInfoUseCaseImplTest {

    private lateinit var localRepository: CharacterLocalRepository

    private lateinit var sut: CharacterInfoUseCaseImpl

    @Before
    fun setUp() {
        localRepository = mockk()
        sut = CharacterInfoUseCaseImpl(localRepository)
    }

    @Test
    fun `when request a character returns a valid character from localStorage`() {
        runBlocking {
            val character = CharacterEntity(
                1,
                name = "Rick",
                status = "Alive",
                species = "Human",
                gender = "Male",
                image = "https://image.jpg",
                page = 1
            )
            coEvery {
                localRepository.getCharacter(1)
            } returns character

            val result = sut.getCharacterInfo(1)

            assertEquals(character.id, result!!.id)
            assertEquals(character.name, result.name)
            assertEquals(character.status, result.status)
            assertEquals(character.gender, result.gender)
            assertEquals(character.image, result.image)
        }
    }
}
