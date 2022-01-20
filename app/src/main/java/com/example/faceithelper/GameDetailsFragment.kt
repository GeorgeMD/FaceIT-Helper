package com.example.faceithelper

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.faceithelper.api.models.Game
import com.example.faceithelper.databinding.FragmentGameDetailsBinding
import com.example.faceithelper.viewmodels.HomeViewModel

class GameDetailsFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var game: Game

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameDetailsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game_details, container, false)

        game = GameDetailsFragmentArgs.fromBundle(requireArguments()).game

        binding.setGame(game)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.getRoot()
    }
}