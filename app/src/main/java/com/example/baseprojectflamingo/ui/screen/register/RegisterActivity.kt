package com.example.baseprojectflamingo.ui.screen.register

import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseActivity
import com.example.baseprojectflamingo.base.ext.click
import com.example.baseprojectflamingo.base.ext.showToast
import com.example.baseprojectflamingo.base.ext.validateEmail
import com.example.baseprojectflamingo.base.ext.validatePassword
import com.example.baseprojectflamingo.database.AppDatabase
import com.example.baseprojectflamingo.database.entities.UserAccount
import com.example.baseprojectflamingo.database.repository.UserAccountRepository
import com.example.baseprojectflamingo.databinding.ActivityRegisterBinding

class RegisterActivity: BaseActivity<ActivityRegisterBinding>(R.layout.activity_register) {

    private lateinit var viewModel: RegisterViewModel

    override fun initView() {
        initViewModel()
        onClickViews()
    }

    private fun initViewModel() {
        val dao = AppDatabase.getInstance(this).userAccountDao()
        val repository = UserAccountRepository(dao)
        val viewModelFactory = ViewModelRegisterFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[RegisterViewModel::class.java]
    }

    private fun onClickViews() = binding.apply {
        buttonRegister.click { eventRegister() }
        icBack.click { goBackToLoginScreen() }
    }

    private fun eventRegister() {
        val email = getTextEmail()
        val userName = getTextUserName()
        val password = getTextPassword()
        val isNullInput = isInputEmpty(email, userName, password)
        val isEmail = validateEmail(email)
        val isPassword = validatePassword(password)

        if (isNullInput) {
            showToast(R.string.this_field_cannot_be_left_blank)
            return
        }; if (!isEmail) {
            showToast(R.string.invalid_email)
            return
        }; if (!isPassword) {
            showToast(R.string.password_minimum_six_characters)
            return
        }

        val userAccount = getUserAccountSignUp(email, password, userName)
        onRegisterAccount(userAccount)
    }

    private fun getTextEmail() = binding.inputEmailAddress.text.toString().trim()

    private fun getTextUserName() = binding.inputUserName.text.toString().trim()

    private fun getTextPassword() = binding.inputPassword.text.toString().trim()

    private fun isInputEmpty(email: String, userName: String, password: String) =
        email.isEmpty() || userName.isEmpty() || password.isEmpty()

    private fun getUserAccountSignUp(email: String, password: String, userName: String) =
        UserAccount(email = email, password = password, userName = userName)

    private fun onRegisterAccount(user: UserAccount) =
        viewModel.registerAccount(
            userAccount = user,
            onAccountAlreadyExists = {
                showToast(R.string.email_already_active)
            },
            onRegisterSuccess = {
                showToast(R.string.register_success)
                clearInput()
                goBackToLoginScreen()
            }
        )

    private fun clearInput() = binding.apply {
        inputPassword.text.clear()
        inputEmailAddress.text.clear()
        inputUserName.text.clear()
    }

    private fun goBackToLoginScreen() = finish()
}