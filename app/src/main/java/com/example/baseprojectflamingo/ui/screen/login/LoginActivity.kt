package com.example.baseprojectflamingo.ui.screen.login

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseActivity
import com.example.baseprojectflamingo.base.ext.click
import com.example.baseprojectflamingo.base.ext.showToast
import com.example.baseprojectflamingo.database.AppDatabase
import com.example.baseprojectflamingo.database.entities.UserAccount
import com.example.baseprojectflamingo.database.repository.UserAccountRepository
import com.example.baseprojectflamingo.databinding.ActivityLoginBinding
import com.example.baseprojectflamingo.ui.screen.home.HomeActivity
import com.example.baseprojectflamingo.ui.screen.register.RegisterActivity

class LoginActivity: BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private lateinit var viewModel: LoginViewModel

    override fun initView() {
        initViewModel()
        clickViews()
    }

    private fun initViewModel() {
        val dao = AppDatabase.getInstance(this).userAccountDao()
        val repository = UserAccountRepository(dao)
        val viewModelFactory = ViewModelLoginFactory(repository, this)
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
    }

    private fun clickViews() = binding.apply {
        textRegisterAccount.click {
            goToRegisterAccountScreen()
        }

        buttonLogin.click { eventLogin() }
    }

    private fun goToRegisterAccountScreen() =
        startActivity(
            Intent(this@LoginActivity, RegisterActivity::class.java)
        )

    private fun eventLogin() {
        val email = getTextEmail()
        val password = getTextPassword()
        val userAccount = getUserLogin(email, password)
        viewModel.loginAccount(
            user = userAccount,
            onLoginError = {
                showToast(R.string.account_incorrect)
            },
            onLoginSuccess = {
                goToHomeScreen()
                clearThisScreen()
            }
        )
    }

    private fun getTextEmail() = binding.inputEmailAddress.text.toString().trim()

    private fun getTextPassword() =  binding.inputPassword.text.toString().trim()

    private fun getUserLogin(email: String, password: String) =
        UserAccount(email = email, password = password)

    private fun goToHomeScreen() = startActivity(
        Intent(this, HomeActivity::class.java)
    )

    private fun clearThisScreen() = finish()
}