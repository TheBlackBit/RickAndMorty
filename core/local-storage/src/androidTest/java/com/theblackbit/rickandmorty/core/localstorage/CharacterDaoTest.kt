package com.theblackbit.rickandmorty.core.localstorage

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.theblackbit.rickandmorty.core.localstorage.room.RickAndMortyRoom
import com.theblackbit.rickandmorty.core.localstorage.room.dao.CharacterDao
import com.theblackbit.rickandmorty.core.localstorage.room.entity.CharacterEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharacterDaoTest {

    private lateinit var db: RickAndMortyRoom

    private lateinit var characterDao: CharacterDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, RickAndMortyRoom::class.java)
            .build()
        characterDao = db.characterDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testGetAllCharacters(): Unit = runBlocking {
        val listOfData = fakeData()

        characterDao.upsertCharacters(listOfData)

        val result = characterDao.getCharacters(1)

        assert(result.containsAll(listOfData))
    }

    @Test
    fun testGetSingleCharacter(): Unit = runBlocking {
        val listOfData = fakeData()

        characterDao.upsertCharacters(listOfData)

        val result = characterDao.getCharacter(1)

        assertEquals(result?.id, listOfData.first().id)
        assertEquals(result?.name, listOfData.first().name)
        assertEquals(result?.status, listOfData.first().status)
        assertEquals(result?.species, listOfData.first().species)
        assertEquals(result?.gender, listOfData.first().gender)
        assertEquals(result?.image, listOfData.first().image)
        assertEquals(result?.page, listOfData.first().page)
    }

    @Test
    fun testDeleteCharacters(): Unit = runBlocking {
        val listOfData = fakeData()

        characterDao.upsertCharacters(listOfData)

        characterDao.deleteCharacter()

        val result = characterDao.getCharacters(1)

        assert(result.isEmpty())
    }

    private fun fakeData(): List<CharacterEntity> {
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
