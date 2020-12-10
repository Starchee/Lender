package com.starchee.lender.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.starchee.lender.App
import com.starchee.lender.R
import com.starchee.lender.domain.entities.Loan
import com.starchee.lender.domain.entities.NetworkError
import com.starchee.lender.domain.entities.LoanState
import com.starchee.lender.ui.utils.ViewModelProviderFactory
import com.starchee.lender.viewModels.LoanInfoViewModel
import kotlinx.android.synthetic.main.loan_info_fragment.*
import javax.inject.Inject

class LoanInfoFragment : BaseFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private var loanInfoViewModel: LoanInfoViewModel? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as App).loanInfoComponent.inject(this)

        loanInfoViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(LoanInfoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.loan_info_fragment, container, false)

    override fun onStart() {
        super.onStart()
        initListeners()
        initNavigation()
        initErrorHandler()
        arguments?.let { bundle ->
            val id = LoanInfoFragmentArgs.fromBundle(bundle).id
            loanInfoViewModel?.getLoanById(id)
        }
    }

    private fun initListeners() {
        loanInfoViewModel?.loan?.observe(this, {
            setLoanInfoFields(it)
        })
    }

    private fun initErrorHandler() {
        loanInfoViewModel?.networkErrors?.observe(this, { error ->
            when (error) {
                NetworkError.NETWORK -> showErrorToast(getString(R.string.network_error_message))
                NetworkError.NOT_FOUND -> showErrorToast(getString(R.string.loan_not_found_message))
                NetworkError.FORBIDDEN -> showErrorToast(getString(R.string.forbidden_error_message))
                else -> return@observe
            }
        })
    }

    private fun setLoanInfoFields(loan: Loan) {
        amount_loan_info.text = loan.amount.toString()
        period_loan_info.text = loan.period.toString()
        percent_loan_info.text = loan.percent.toString()
        firstName_loan_info.text = loan.firstName.ifEmpty { getString(R.string.none) }
        lastName_loan_info.text = loan.lastName.ifEmpty { getString(R.string.none) }
        phoneNumber_loan_info.text = loan.phone.ifEmpty { getString(R.string.none) }

        when (loan.state) {
            LoanState.APPROVED -> {
                state_message_loan_info.apply {
                    setTextColor(context.getColor(android.R.color.holo_green_dark))
                    text = getString(R.string.approved_message)
                }
            }
            LoanState.REGISTERED -> {
                state_message_loan_info.apply {
                    setTextColor(context.getColor(android.R.color.holo_orange_dark))
                    text = getString(R.string.registred_message)
                }
            }
            LoanState.REJECTED -> {
                state_message_loan_info.apply {
                    setTextColor(context.getColor(android.R.color.holo_red_dark))
                    text = getString(R.string.rejected_message)
                }
            }
        }
    }

    private fun initNavigation() {
        val navController = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(top_app_bar_loan_info, navController)
    }


    private fun showErrorToast(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }
}