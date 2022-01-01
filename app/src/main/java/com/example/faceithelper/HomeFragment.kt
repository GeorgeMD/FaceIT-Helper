package com.example.faceithelper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.faceithelper.databinding.FragmentHomeBinding
import com.example.faceithelper.viewmodels.HomeViewModel

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        val viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding.viewModel = viewModel

        viewModel.player.observe(viewLifecycleOwner, { player ->
            if (player != null)
            {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToGameListFragment(player)
                )

                viewModel.afterPlayerSet()
            }
        })

        return binding.root
    }

    override fun onResume() {
        requireActivity().title = getString(R.string.app_name)

        super.onResume()
    }
}