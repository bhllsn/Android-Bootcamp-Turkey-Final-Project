package com.bhllsn.expensetrackerplus.screens.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bhllsn.expensetrackerplus.databinding.FragmentOnBoarding2Binding

class OnBoardingFragment2 : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        val binding: FragmentOnBoarding2Binding = FragmentOnBoarding2Binding.inflate(inflater, container, false)

        binding.FABSkip.setOnClickListener {
            findNavController().navigate(OnBoardingFragment2Directions.actionOnBoardingFragment2ToOnBoardingFragment3())
        }

        return binding.root
    }
}