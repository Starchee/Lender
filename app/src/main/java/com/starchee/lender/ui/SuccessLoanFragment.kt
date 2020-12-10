package com.starchee.lender.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.starchee.lender.R
import kotlinx.android.synthetic.main.success_loan_fragment.*

class SuccessLoanFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.success_loan_fragment, container, false)

    override fun onStart() {
        super.onStart()
        ok_btn_success_loan.setOnClickListener {
            findNavController(this).navigateUp()
        }
    }
}