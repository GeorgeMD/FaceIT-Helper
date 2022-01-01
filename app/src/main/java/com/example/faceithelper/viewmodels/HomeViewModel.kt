package com.example.faceithelper.viewmodels

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.faceithelper.api.faceitApi
import com.example.faceithelper.api.models.Player
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeViewModel : ViewModel() {

    private val _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username
    
    private val _player = MutableLiveData<Player?>()
    val player: LiveData<Player?>
        get() = _player

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    fun setUsername(username: Editable) {
        _username.value = username.toString()
    }

    fun showInfo() {
        username.value?.let { username ->
            viewModelScope.launch {
                try {
                    _player.value = faceitApi.getPlayerInfo(username)
                    _errorMessage.value = null
                } catch (e: HttpException) {
                    when (e.code()) {
                        404 -> _errorMessage.value = "Player '$username' not found."
                        else -> _errorMessage.value = "Unable to reach FACEIT, try again later."
                    }
                }
            }
        }
    }

    fun afterPlayerSet() {
        _player.value = null
    }
}
