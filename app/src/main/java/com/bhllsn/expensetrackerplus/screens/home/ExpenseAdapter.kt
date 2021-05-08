package com.bhllsn.expensetrackerplus.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bhllsn.expensetrackerplus.R
import com.bhllsn.expensetrackerplus.database.Expense
import com.bhllsn.expensetrackerplus.databinding.ExpenseItemLayoutBinding

class ExpenseAdapter(private val clickListener: ExpenseClickListener, private val changeListener: CurrencyChangeListener, private var type: Int, private var rate: Float) : ListAdapter<Expense, ViewHolder>(ExpenseDiffCallback())
{
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val item = getItem(position)
        holder.updateRate(type, rate)
        holder.bind(item, clickListener)
    }

    fun changeCurrency(type: Int, rate: Float)
    {
        this.type = type
        this.rate = rate
        notifyDataSetChanged()
        var sum = 0f
        this.currentList.forEach { sum += it.cost }
        changeListener.onChange(sum)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { return ViewHolder.from(parent, type, rate) }
}

class ViewHolder private constructor(private val binding: ExpenseItemLayoutBinding, private var type: Int, private var rate: Float) : RecyclerView.ViewHolder(binding.root)
{
    fun updateRate(type: Int, rate: Float)
    {
        this.type = type
        this.rate = rate
    }

    fun bind(item: Expense, clickListener: ExpenseClickListener)
    {
        binding.imageViewIcon.setImageResource(when(item.type)
        {
            1 -> R.drawable.receipt
            2 -> R.drawable.rent_home
            3 -> R.drawable.food_icon
            4 -> R.drawable.clothing_icon
            else -> R.drawable.other_shopping
        })
        binding.textViewDescription.text = item.description
        val cost = "%.0f".format(item.cost * rate)
        val textCost = when(type)
        {
            1 -> "$cost ₺"
            2 -> "$cost $"
            3 -> "$cost £"
            else -> "$cost €"
        }
        binding.textViewCost.text = textCost
        binding.cardViewItem.setOnClickListener{
            clickListener.onClick(item)
        }
    }

    companion object
    {
        fun from(parent: ViewGroup, type: Int, rate: Float): ViewHolder
        {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ExpenseItemLayoutBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding, type, rate)
        }
    }
}

class ExpenseClickListener(val clickListener: (expense: Expense) -> Unit)
{
    fun onClick(expense: Expense) = clickListener(expense)
}

class CurrencyChangeListener(val listener: (total: Float) -> Unit)
{
    fun onChange(total: Float) = listener(total)
}

class ExpenseDiffCallback : DiffUtil.ItemCallback<Expense>()
{
    override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean { return oldItem.id == newItem.id }

    override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean { return oldItem == newItem }
}