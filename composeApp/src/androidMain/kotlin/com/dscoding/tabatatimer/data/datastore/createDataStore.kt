package com.dscoding.tabatatimer.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.dscoding.tabatatimer.core.data.datastore.DATA_STORE_FILE_NAME
import com.dscoding.tabatatimer.core.data.datastore.createDataStore

fun createDataStore(context: Context): DataStore<Preferences> {
    return createDataStore {
        context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
    }
}