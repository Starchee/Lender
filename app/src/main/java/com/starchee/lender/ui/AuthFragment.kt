package com.starchee.lender.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.starchee.lender.App
import com.starchee.lender.R
import com.starchee.lender.domain.entities.AuthState
import com.starchee.lender.domain.entities.NetworkError
import com.starchee.lender.ui.utils.ViewModelProviderFactory
import com.starchee.lender.viewModels.AuthViewModel
import kotlinx.android.synthetic.main.auth_fragment.*
import javax.inject.Inject

class AuthFragment : BaseFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private var authViewModel: AuthViewModel? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as App).authComponent.inject(this)

        authViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(AuthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.auth_fragment, container, false)

    override fun onStart() {
        super.onStart()
        authViewModel?.logout()
        initListeners()
        initErrorHandler()
    }

    private fun initListeners() {
        authViewModel?.isLogin?.observe(this, { state ->
            when (state) {
                AuthState.REGISTERED -> findNavController(this).navigate(R.id.action_instruction)
                AuthState.LOGIN -> findNavController(this).navigateUp()
            }
        })

        login_btn_auth.setOnClickListener {
            authViewModel?.login(name_auth.text.toString(), password_auth.text.toString())
        }

        registration_btn_auth.setOnClickListener {
            authViewModel?.registration(name_auth.text.toString(), password_auth.text.toString())
        }
    }

    private fun initErrorHandler() {
        authViewModel?.networkErrors?.observe(this, { error ->
            when (error) {
                NetworkError.NETWORK -> showErrorToast(getString(R.string.network_error_message))
                NetworkError.NOT_FOUND -> showErrorToast(getString(R.string.user_not_found_message))
                NetworkError.BAD_REQUEST -> showErrorToast(getString(R.string.user_bad_request_message))
            }
        })
    }

    private fun showErrorToast(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

}