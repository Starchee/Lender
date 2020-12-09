package com.starchee.lender.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.starchee.lender.R
import com.starchee.lender.domain.entities.Loan
import com.starchee.lender.domain.entities.LoanState
import kotlinx.android.synthetic.main.loans_rv_item.view.*

class LoanListAdapter(private val loanOnclick: (Int) -> Unit) :
    RecyclerView.Adapter<LoanListAdapter.LoanViewHolder>() {

    private val loanList: ArrayList<Loan> = ArrayList()

    fun setUpLoanList(loanList: List<Loan>) {
        this.loanList.clear()
        this.loanList.addAll(loanList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.loans_rv_item, parent, false)
        return LoanViewHolder(itemView = itemView, loanOnclick)
    }

    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
        holder.bind(loan = loanList[position])
    }

    override fun getItemCount() = loanList.size

    class LoanViewHolder(itemView: View, val loanOnclick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(loan: Loan) {
            itemView.apply {
                setOnClickListener { loanOnclick(loan.id.toString().toInt()) }
                amount_loans_rv_item.text = loan.amount.toString()
                date_loans_rv_item.text = loan.date
                when (loan.state) {
                    LoanState.APPROVED -> {
                        state_loans_rv_item.apply {
                            setTextColor(context.getColor(android.R.color.holo_green_dark))
                            text = context.getString(R.string.approved)
                        }
                    }
                    LoanState.REGISTERED -> {
                        state_loans_rv_item.apply {
                            setTextColor(context.getColor(android.R.color.holo_orange_dark))
                            text = context.getString(R.string.registred)
                        }
                    }
                    LoanState.REJECTED -> {
                        state_loans_rv_item.apply {
                            setTextColor(context.getColor(android.R.color.holo_red_dark))
                            text = context.getString(R.string.rejected)
                        }
                    }
                }
            }
        }
    }
}