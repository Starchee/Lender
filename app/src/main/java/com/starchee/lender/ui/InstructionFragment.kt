package com.starchee.lender.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.starchee.lender.R
import kotlinx.android.synthetic.main.instruction_fragment.*

class InstructionFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.instruction_fragment, container, false)


    override fun onStart() {
        super.onStart()
        btn_loan_info.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_app)
        }
    }
}