package theblackbit.rickandmorty.feature.character.domain

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.testing.asPagingSourceFactory
import androidx.paging.testing.asSnapshot
import com.theblackbit.rickandmorty.core.model.Character
import com.theblackbit.rickandmorty.feature.character.domain.PagedCharacterUseCaseImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PagedCharacterUseCaseImplTest {

    @Test
    fun `when request paged items returns success a pagination`(): Unit = runTest {
        val items = (0..10).map {
            Character(
                id = it
            )
        }
        val pagingSourceFactory = items.asPagingSourceFactory().invoke()

        val sut = PagedCharacterUseCaseImpl(
            pagingConfig = PagingConfig(20, 10),
            pagingSource = pagingSourceFactory
        )

        val data: Flow<PagingData<Character>> = sut.collectPagedCharacters().flow

        val snapshot = data.asSnapshot()

        assert(snapshot.containsAll(items))
    }
}
