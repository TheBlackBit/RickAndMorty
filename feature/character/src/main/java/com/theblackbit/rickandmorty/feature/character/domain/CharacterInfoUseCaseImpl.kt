package com.theblackbit.rickandmorty.feature.character.domain

import com.theblackbit.rickandmorty.core.model.Character
import com.theblackbit.rickandmorty.feature.character.data.local.CharacterLocalRepository

class CharacterInfoUseCaseImpl(
    private val localRepository: CharacterLocalRepository
) : CharacterInfoUseCase {
    override suspend fun getCharacterInfo(id: Int): Character? {
        return localRepository.getCharacter(id)?.run {
            Character(
                id = id,
                name = name,
                status = status,
                species = species,
                gender = gender,
                image = image
            )
        }
    }
}
