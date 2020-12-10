package com.starchee.lender.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.starchee.lender.App
import com.starchee.lender.R
import com.starchee.lender.domain.entities.*
import com.starchee.lender.ui.utils.ViewModelProviderFactory
import com.starchee.lender.viewModels.LoanAppViewModel
import kotlinx.android.synthetic.main.loan_app_fragment.*
import javax.inject.Inject

class LoanAppFragment : BaseFragment() {

    private var loanAppIsVisible = false

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private var loanAppViewModel: LoanAppViewModel? = null

    companion object {
        private const val VISIBILITY_APP_KEY = "visibility_app_key"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as App).loanAppComponent.inject(this)

        loanAppViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(LoanAppViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.loan_app_fragment, container, false)

    override fun onStart() {
        super.onStart()
        NavigationUI.setupWithNavController(bottom_navigation_loan_app, findNavController(this))
        initErrorHandler()
        setVisibilityLoanApp()
        initListeners()
        initSuccessLoan()
        initAppBar()
        initNavigation()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.apply {
            putBoolean(VISIBILITY_APP_KEY, loanAppIsVisible)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            loanAppIsVisible = it.getBoolean(VISIBILITY_APP_KEY)
        }
    }

    private fun initErrorHandler() {
        loanAppViewModel?.inputErrors?.observe(this, { error ->
            when (error) {
                InputAppError.MAX_AMOUNT_ERROR -> amount_loan_app.error =
                    getString(R.string.warning_maxamount_message)
            }
        })

        loanAppViewModel?.networkErrors?.observe(this, { error ->
            when (error) {
                NetworkError.NETWORK -> showErrorToast(getString(R.string.network_error_message))
                NetworkError.NOT_FOUND -> showErrorToast(getString(R.string.app_not_found_message))
                NetworkError.BAD_REQUEST -> showErrorToast(getString(R.string.warning_maxamount_message))
                NetworkError.FORBIDDEN -> showErrorToast(getString(R.string.forbidden_error_message))
            }
        })
    }

    private fun initListeners() {
        loanAppViewModel?.loanOffers?.observe(this, {
            setCondition(it)
        })

        amount_loan_app.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                loanAppViewModel?.validateAmount(s.toString().toIntOrNull() ?: 0)
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        loanAppViewModel?.loadOffer()

        create_loan_btn_auth.setOnClickListener {
            loanAppViewModel?.makeLoanApplication(getLoanAppFields())
        }

        get_loan_btn_loan_app.setOnClickListener {
            loanAppIsVisible = true
            setVisibilityLoanApp()
        }
    }

    private fun initAppBar() {
        top_app_bar_loan_app.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.logout -> {
                    findNavController(this).navigate(R.id.action_logout)
                    true
                }
                R.id.language_RU -> {
                    loanAppViewModel?.saveLocate(Locale.RU)
                    true
                }
                R.id.language_EN -> {
                    loanAppViewModel?.saveLocate(Locale.EN)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }
    }

    private fun initNavigation() {
        val navController = findNavController(this)
        NavigationUI.setupWithNavController(bottom_navigation_loan_app, navController)
    }

    private fun showErrorToast(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun initSuccessLoan() {
        loanAppViewModel?.loanIsCreated?.observe(this, {
            findNavController(this).navigate(R.id.action_success_app)
        })
    }

    private fun setCondition(loanCondition: LoanCondition) {
        max_amount_loan_app.text = loanCondition.maxAmount.toString()
        percent_loan_app.text = loanCondition.percent.toString()
        period_loan_app.text = loanCondition.period.toString()
    }

    private fun getLoanAppFields() = LoanApplication(
        amount = amount_loan_app.text.toString().toIntOrNull() ?: 0,
        firstName = firstName_loan_app.text.toString(),
        lastName = lastName_loan_app.text.toString(),
        phone = phone_loan_app.text.toString()
    )

    private fun setVisibilityLoanApp() {
        get_loan_btn_loan_app.isGone = loanAppIsVisible
        group_loan_app.isGone = !loanAppIsVisible
    }

}