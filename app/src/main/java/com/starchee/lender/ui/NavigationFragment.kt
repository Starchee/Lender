package com.starchee.lender.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.starchee.lender.App
import com.starchee.lender.R
import com.starchee.lender.ui.utils.ViewModelProviderFactory
import com.starchee.lender.viewModels.NavigationViewModel
import javax.inject.Inject

class NavigationFragment : BaseFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private var navigationViewModel: NavigationViewModel? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as App).navigationComponent.inject(this)
        navigationViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(NavigationViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.main_fragment, container, false)


    override fun onStart() {
        super.onStart()
        navigationViewModel?.isLogin?.observe(this, { isLogin ->
            if (isLogin) {
                findNavController(this).navigate(R.id.action_loan_list)
            } else {
                findNavController(this).navigate(R.id.action_auth)
            }
        })
    }
}