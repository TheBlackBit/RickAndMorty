package theblackbit.rickandmorty.feature.character.presentation

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.theblackbit.rickandmorty.core.model.Character
import com.theblackbit.rickandmorty.feature.character.domain.CharacterInfoUseCase
import com.theblackbit.rickandmorty.feature.character.domain.PagedCharactersUseCase
import com.theblackbit.rickandmorty.feature.character.presentation.CharactersViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersViewModelTest {

    private lateinit var pagedCharactersUseCase: PagedCharactersUseCase

    private lateinit var characterInfoUseCase: CharacterInfoUseCase

    private lateinit var pager: Pager<Int, Character>

    private lateinit var sut: CharactersViewModel

    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        pager = mockk()
        pagedCharactersUseCase = mockk()
        characterInfoUseCase = mockk()
        sut = CharactersViewModel(
            pagedCharactersUseCase = pagedCharactersUseCase,
            characterInfoUseCase = characterInfoUseCase,
            ioDispatcher = testDispatcher,
            mainDispatcher = testDispatcher
        )
    }

    @Test
    fun `verify headers inserted`(): Unit = runTest {
        val items = (0..10).map {
            Character(
                id = it
            )
        }

        coEvery {
            pagedCharactersUseCase.collectPagedCharacters()
        } returns pager

        coEvery {
            pager.flow
        } returns flowOf(PagingData.from(items))

        val data = sut.collectCharacters()

        val snapshotList = data.asSnapshot()

        // Verify header inserted
        assert(snapshotList.first().id == -2)
        assert(snapshotList[1].id == -1)
    }

    @Test
    fun `when request a character returns a valid character`(): Unit = runBlocking {
        val character = Character(
            1
        )
        coEvery { characterInfoUseCase.getCharacterInfo(1) } returns character

        sut.getCharacter(1)

        val resultValue = sut.characterInfoStateFlow.value

        assertEquals(character, resultValue)
    }
}
