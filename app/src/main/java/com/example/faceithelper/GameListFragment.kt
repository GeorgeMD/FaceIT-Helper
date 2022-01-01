package com.example.faceithelper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.faceithelper.adapters.GameAdapter
import com.example.faceithelper.api.models.Player
import com.example.faceithelper.databinding.FragmentGameListBinding

class GameListFragment : Fragment() {

    private lateinit var player: Player

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentGameListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game_list, container, false)

        val gameAdapter = GameAdapter(viewLifecycleOwner, GameAdapter.OnClickListener { game ->
            this.findNavController().navigate(GameListFragmentDirections.actionGameListFragmentToGameDetailsFragment(game))
        })

        player = GameListFragmentArgs.fromBundle(requireArguments()).player

        binding.lifecycleOwner = viewLifecycleOwner
        binding.gameListViewer.adapter = gameAdapter

        gameAdapter.submitList(player.games)

        return binding.root
    }

    override fun onResume() {
        requireActivity().title = player.nickname

        super.onResume()
    }
}