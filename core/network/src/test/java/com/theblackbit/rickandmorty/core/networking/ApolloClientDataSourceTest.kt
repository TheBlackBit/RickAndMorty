package com.theblackbit.rickandmorty.core.networking

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.testing.QueueTestNetworkTransport
import com.apollographql.apollo3.testing.enqueueTestNetworkError
import com.apollographql.apollo3.testing.enqueueTestResponse
import com.theblackbit.rickandmorty.CharacterQuery
import com.theblackbit.rickandmorty.CharactersQuery
import com.theblackbit.rickandmorty.core.networking.datasource.ApolloClientDataSource
import com.theblackbit.rickandmorty.core.networking.response.CharacterResponse
import com.theblackbit.rickandmorty.core.networking.util.RequestStatus
import com.theblackbit.rickandmorty.core.networking.util.SafeRequest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ApolloExperimental::class)
class ApolloClientDataSourceTest {

    private lateinit var apolloClient: ApolloClient

    private lateinit var sut: ApolloClientDataSource

    private lateinit var safeRequest: SafeRequest

    @Before
    fun setUp() {
        apolloClient = ApolloClient.Builder()
            .networkTransport(QueueTestNetworkTransport())
            .build()

        safeRequest = SafeRequest()
        sut = ApolloClientDataSource(apolloClient, safeRequest)
    }

    @Test
    fun `when request characters apolloClient returns valid characters`() = runBlocking {
        val query = CharactersQuery(1)

        apolloClient.enqueueTestResponse(
            query,
            CharactersQuery.Data(
                CharactersQuery.Characters(
                    fakeCharactersData()
                )
            )
        )

        val result = sut.getCharacters(1)

        val convertedData = fakeCharactersData().map {
            CharacterResponse(
                id = it?.id?.toIntOrNull() ?: -1,
                name = it?.name,
                status = it?.status,
                species = it?.species,
                gender = it?.gender,
                image = it?.image
            )
        }

        val actual = result as RequestStatus.Success

        assert(actual.value.containsAll(convertedData))
    }

    @Test
    fun `when request characters apolloClient response with null characters returns emptyList`() =
        runBlocking {
            val query = CharactersQuery(1)

            apolloClient.enqueueTestResponse(
                query,
                CharactersQuery.Data(
                    null
                )
            )

            val result = sut.getCharacters(1)

            val actual = result as RequestStatus.Success

            assert(actual.value.isEmpty())
        }

    @Test
    fun `when request characters apolloClient  response with null character data returns emptyList`() =
        runBlocking {
            val query = CharactersQuery(1)

            apolloClient.enqueueTestResponse(
                query,
                CharactersQuery.Data(
                    CharactersQuery.Characters(
                        null
                    )
                )
            )

            val result = sut.getCharacters(1)

            val actual = result as RequestStatus.Success

            assert(actual.value.isEmpty())
        }

    @Test
    fun `when request characters apolloClient response networkError`() =
        runBlocking {
            apolloClient.enqueueTestNetworkError()

            val result = sut.getCharacters(1)

            assert(result is RequestStatus.NetworkError)
        }

    @Test
    fun `when request characters apolloClient response ApiError`() =
        runBlocking {
            val query = CharactersQuery(1)

            apolloClient.enqueueTestResponse(
                query,
                CharactersQuery.Data(
                    CharactersQuery.Characters(
                        null
                    )
                ),
                errors = listOf(
                    com.apollographql.apollo3.api.Error(
                        message = "Error message",
                        null,
                        null,
                        null,
                        null
                    )
                )
            )

            val result = sut.getCharacters(1)

            assert(result is RequestStatus.ApiError)
        }

    @Test
    fun `when request character apolloClient returns valid character`() = runBlocking {
        val query = CharacterQuery("1")
        val character = CharacterQuery.Character(
            id = "1",
            name = "Rick",
            status = "Alive",
            species = "Human",
            gender = "Male",
            image = "https://image.jpg"
        )

        apolloClient.enqueueTestResponse(
            query,
            CharacterQuery.Data(character)
        )

        val result = sut.getCharacter(1)

        val actual = (result as RequestStatus.Success).value

        assert(character.id == actual?.id.toString())
        assert(character.name == actual?.name.toString())
        assert(character.status == actual?.status.toString())
        assert(character.species == actual?.species.toString())
        assert(character.gender == actual?.gender.toString())
        assert(character.image == actual?.image.toString())
    }

    @Test
    fun `when request character apolloClient response with null characters returns null`() =
        runBlocking {
            val query = CharacterQuery("1")

            apolloClient.enqueueTestResponse(
                query,
                null
            )

            val result = sut.getCharacter(1)

            val actual = (result as RequestStatus.Success).value

            assertEquals(null, actual)
        }

    @Test
    fun `when request character apolloClient response with null character data returns null`() =
        runBlocking {
            val query = CharacterQuery("1")

            apolloClient.enqueueTestResponse(
                query,
                CharacterQuery.Data(null)
            )

            val result = sut.getCharacter(1)

            val actual = (result as RequestStatus.Success).value

            assertEquals(null, actual)
        }

    @Test
    fun `when request character apolloClient returns networkError`() = runBlocking {
        apolloClient.enqueueTestNetworkError()

        val result = sut.getCharacter(1)

        assert(result is RequestStatus.NetworkError)
    }

    @Test
    fun `when request character apolloClient returns ApiError`() = runBlocking {
        val query = CharacterQuery("1")
        val character = CharacterQuery.Character(
            id = "1",
            name = "Rick",
            status = "Alive",
            species = "Human",
            gender = "Male",
            image = "https://image.jpg"
        )

        apolloClient.enqueueTestResponse(
            query,
            CharacterQuery.Data(character),
            errors = listOf(
                com.apollographql.apollo3.api.Error(
                    message = "Error message",
                    null,
                    null,
                    null,
                    null
                )
            )
        )

        val result = sut.getCharacter(1)

        assert(result is RequestStatus.ApiError)
    }

    private fun fakeCharactersData(): List<CharactersQuery.Result?> {
        return listOf(
            CharactersQuery.Result(
                id = "1",
                name = "Rick",
                status = "Alive",
                species = "Human",
                gender = "Male",
                image = "https://image.jpg"
            ),
            CharactersQuery.Result(
                id = "2",
                name = "Morty",
                status = "Alive",
                species = "Human",
                gender = "Male",
                image = "https://image.jpg"
            ),
            CharactersQuery.Result(
                id = null,
                name = null,
                status = null,
                species = null,
                gender = null,
                image = null
            ),
            null
        )
    }
}
