package com.starchee.lender.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.starchee.lender.App
import com.starchee.lender.R
import com.starchee.lender.domain.entities.Locale
import com.starchee.lender.domain.entities.NetworkError
import com.starchee.lender.ui.adapters.LoanListAdapter
import com.starchee.lender.ui.utils.ViewModelProviderFactory
import com.starchee.lender.viewModels.LoanListViewModel
import kotlinx.android.synthetic.main.loan_list_fragment.*
import javax.inject.Inject

class LoanListFragment : BaseFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private var loanListViewModel: LoanListViewModel? = null
    private var loanListAdapter: LoanListAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as App).loanListComponent.inject(this)
        loanListViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(LoanListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.loan_list_fragment, container, false)

    override fun onStart() {
        super.onStart()
        initListeners()
        initErrorHandler()
        initNavigation()
        initRecyclerView()
        initAppBar()
    }

    private fun initListeners() {
        loanListViewModel?.loanList?.observe(this, {
            loanListAdapter?.setUpLoanList(it)
        })
    }

    private fun initErrorHandler() {
        loanListViewModel?.networkErrors?.observe(this, { error ->
            when (error) {
                NetworkError.NETWORK -> showErrorToast(getString(R.string.network_error_message))
                NetworkError.FORBIDDEN -> {
                    showErrorToast(getString(R.string.forbidden_error_message))
                    findNavController(this).navigateUp()
                }
                else -> return@observe
            }
        })
    }

    private fun initNavigation() {
        val navController = findNavController(this)
        NavigationUI.setupWithNavController(bottom_navigation_loan_list, navController)
    }

    private fun initRecyclerView() {
        loanListAdapter =
            LoanListAdapter { id ->
                val direction = LoanListFragmentDirections.actionLoanInfo(id)
                findNavController(this).navigate(direction)
            }
        loans_rv_loan_list.apply {
            adapter = loanListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
        }

        loanListViewModel?.getLoanList()
    }

    private fun initAppBar() {
        top_app_bar_loan_list.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.logout -> {
                    findNavController(this).navigate(R.id.action_logout)
                    true
                }
                R.id.language_RU -> {
                    loanListViewModel?.saveLocate(Locale.RU)
                    true
                }
                R.id.language_EN -> {
                    loanListViewModel?.saveLocate(Locale.EN)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }
    }

    private fun showErrorToast(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }
}