package com.paltech.ontheshelf.domain.usecases.app_entry

import com.paltech.ontheshelf.domain.manager.LocalUserManger


class SaveAppEntry(
    private val localUserManger: LocalUserManger
) {

    suspend operator fun invoke(){
        localUserManger.saveAppEntry()
    }

}