package com.bhllsn.expensetrackerplus.screens.expense.expensecreate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bhllsn.expensetrackerplus.database.AppDatabase
import com.bhllsn.expensetrackerplus.databinding.FragmentExpenseCreateBinding

class ExpenseCreateFragment : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        val binding: FragmentExpenseCreateBinding = FragmentExpenseCreateBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).databaseDao
        val viewModelFactory = ExpenseCreateViewModelFactory(dataSource, application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ExpenseCreateViewModel::class.java)

        binding.buttonCreate.setOnClickListener {
            val type = when(binding.radioGroupExpenseType.checkedRadioButtonId)
            {
                binding.radioButtonTypeBill.id -> 1
                binding.radioButtonTypeRent.id -> 2
                binding.radioButtonTypeFood.id -> 3
                binding.radioButtonTypeClothing.id -> 4
                binding.radioButtonTypeOther.id -> 5
                else -> -1
            }
            val description = binding.textInputEditTextDescription.text.toString()
            val currencyType = when(binding.radioGroupCurrency.checkedRadioButtonId)
            {
                binding.radioButtonTRY.id -> 1
                binding.radioButtonUSD.id -> 2
                binding.radioButtonGBP.id -> 3
                else -> 0
            }
            val costText = binding.textInputEditTextCost.text.toString()
            viewModel.createExpense(type, description, currencyType, costText)
        }

        viewModel.navigateToHome.observe(viewLifecycleOwner, {
            if(it == true)
            {
                this.findNavController().navigate(ExpenseCreateFragmentDirections.actionExpenseCreateFragmentToHomeFragment())
                viewModel.navigationDone()
            }
        })

        return binding.root
    }
}