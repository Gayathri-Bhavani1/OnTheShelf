package com.paltech.ontheshelf.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.paltech.ontheshelf.domain.manager.LocalUserManger
import com.paltech.ontheshelf.util.constants.AppConstants
import com.paltech.ontheshelf.util.constants.AppConstants.USER_SETTINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class LocalUserMangerImpl(
    private val context: Context
) : LocalUserManger {

    override suspend fun saveAppEntry() {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.APP_ENTRY] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.APP_ENTRY] ?: false
        }
    }
}

private val readOnlyProperty = preferencesDataStore(name = USER_SETTINGS)

val Context.dataStore: DataStore<Preferences> by readOnlyProperty

private object PreferenceKeys {

    val APP_ENTRY = booleanPreferencesKey(AppConstants.APP_ENTRY)

}