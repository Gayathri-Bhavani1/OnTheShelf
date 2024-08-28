package com.paltech.ontheshelf.domain.usecases.app_entry


import com.paltech.ontheshelf.domain.manager.LocalUserManger
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val localUserManger: LocalUserManger
) {

    operator fun invoke(): Flow<Boolean> {
        return localUserManger.readAppEntry()
    }

}