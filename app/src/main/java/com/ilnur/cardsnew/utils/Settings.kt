package com.ilnur.cardsnew.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.map
import java.util.concurrent.Flow
import javax.inject.Inject

data class Session(var session: String?, var sessionState: SessionState?)
enum class SessionState {
    authorized,
    anonymus
}

interface Settings{
    suspend fun setLogged(isLogged: Boolean)
}


class SettingsImp @Inject constructor(val context: Context) : Settings{

    private val dataStore = context.createDataStore(name = "settings_pref")

    companion object {

        private val is_logged = preferencesKey<Boolean>("is_logged")

    }

    override suspend fun setLogged(isLogged: Boolean){
        dataStore.edit {
            it[is_logged] = isLogged
        }
    }

    val isLogged = dataStore.data.map {
        it -> it[is_logged] ?: false
    }
}

/*

object Settings {

    fun setSession(session: Session, context: Context) {
        val settings = context.getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE)
        settings.edit().putString("session", session.session).putString("state", session.sessionState.toString()).apply()
    }

    fun getSession(context: Context): Session {
        val settings = context.getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE)
        return Session(
            session = settings.getString("session", null),
            sessionState = SessionState.valueOf(settings.getString("state", SessionState.anonymus.toString())!!)
        )
    }

    fun setLoginAndPassword(login: String, password: String, context: Context) {
        val settings = context.getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE)
        settings.edit().putString("login", login).putString("password", password).apply()
    }

    fun getLogin(context: Context): String? {
        val settings = context.getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE)
        return settings.getString("login", "")
    }

    fun getPassword(context: Context): String? {
        val settings = context.getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE)
        return settings.getString("password", "")
    }

    fun getFirstStartFlag(context: Context): Boolean {
        val settings = context.getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE)
        return settings.getBoolean("first_start", false)
    }

    fun setFirstStartFlag(flag: Boolean, context: Context) {
        val settings = context.getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE)
        settings.edit().putBoolean("first_start", flag).apply()
    }
}*/
