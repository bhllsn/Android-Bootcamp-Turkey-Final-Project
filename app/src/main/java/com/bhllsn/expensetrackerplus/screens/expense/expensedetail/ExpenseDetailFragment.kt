package com.bhllsn.expensetrackerplus.screens.expense.expensedetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bhllsn.expensetrackerplus.R
import com.bhllsn.expensetrackerplus.database.AppDatabase
import com.bhllsn.expensetrackerplus.databinding.FragmentExpenseDetailBinding

class ExpenseDetailFragment : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        val binding: FragmentExpenseDetailBinding = FragmentExpenseDetailBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).databaseDao
        val expense = ExpenseDetailFragmentArgs.fromBundle(requireArguments()).selectedExpense
        val currencyType = ExpenseDetailFragmentArgs.fromBundle(requireArguments()).selectedCurrencyType
        val viewModelFactory = ExpenseDetailViewModelFactory(dataSource, application, expense, currencyType)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ExpenseDetailViewModel::class.java)

        binding.imageButtonBack.setOnClickListener {
            viewModel.navigateBackToHome()
        }

        binding.imageViewIcon.setImageResource(when(expense.type) {
            1 -> R.drawable.receipt
            2 -> R.drawable.rent_home
            3 -> R.drawable.food_icon
            4 -> R.drawable.clothing_icon
            else -> R.drawable.other_shopping
        })

        binding.textViewDescription.text = expense.description

        viewModel.cost.observe(viewLifecycleOwner, {
            binding.textViewCost.text = it
        })

        binding.buttonDelete.setOnClickListener {
            viewModel.delete()
        }

        viewModel.navigateToHome.observe(viewLifecycleOwner, {
            if(it == true)
            {
                this.findNavController().navigate(ExpenseDetailFragmentDirections.actionExpenseDetailFragmentToHomeFragment(currencyType))
                viewModel.navigationDone()
            }
        })

        return binding.root
    }
}