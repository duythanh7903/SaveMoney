package com.example.baseprojectflamingo.ui.screen.frg.account

import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseFragment
import com.example.baseprojectflamingo.base.ext.click
import com.example.baseprojectflamingo.base.ext.convertJsonToObject
import com.example.baseprojectflamingo.base.ext.convertObjectToJson
import com.example.baseprojectflamingo.base.ext.isReadExternalStorageAccepted
import com.example.baseprojectflamingo.base.ext.isReadMediaImageAccepted
import com.example.baseprojectflamingo.base.ext.requestReadExternalStoragePermission
import com.example.baseprojectflamingo.base.ext.requestReadMediaImagePermission
import com.example.baseprojectflamingo.base.ext.showToast
import com.example.baseprojectflamingo.commons.PreferencesUtils
import com.example.baseprojectflamingo.database.AppDatabase
import com.example.baseprojectflamingo.database.entities.UserAccount
import com.example.baseprojectflamingo.database.repository.UserAccountRepository
import com.example.baseprojectflamingo.databinding.FragmentAccountBinding
import com.example.baseprojectflamingo.ui.screen.login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.regex.Pattern

class AccountFragment : BaseFragment<FragmentAccountBinding>(R.layout.fragment_account) {

    private lateinit var viewModel: AccountViewModel
    private lateinit var userAccount: UserAccount

    private val PICK_IMAGE_REQUEST = 1

    override fun initView() {
        initViewModel()
        getUserAccount()
        clickViews()
    }

    override fun initData() = Unit

    private fun initViewModel() {
        val dao = AppDatabase.getInstance(requireActivity()).userAccountDao()
        val repository = UserAccountRepository(dao)
        val viewModelFactory = AccountVmFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[AccountViewModel::class.java]
    }

    private fun getUserAccount() {
        userAccount = requireActivity().convertJsonToObject(
            PreferencesUtils.jsonAccount,
            UserAccount::class.java
        )
        binding.apply {
            inputUserName.setText(userAccount.userName)
            inputPassword.setText(userAccount.password)
            inputEmailAddress.setText(userAccount.email)
            Glide.with(requireActivity()).load(
                if (userAccount.base64Image.isNotEmpty()) Uri.parse(userAccount.base64Image)
                else R.drawable.avatar_default
            ).into(binding.imageUser)
        }
    }

    private fun clickViews() = binding.apply {
        buttonLogout.click {
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            activity?.finishAffinity()
        }

        buttonUpdate.click { onEventUpdateAccount() }

        imageUser.click {
            if (isThisTiramisuVersionOrHigher()) {
                if (!isReadMediaImageAccepted()) requestReadMediaImagePermission()
                else openImagesActivity()
            } else {
                if (!isReadExternalStorage()) requestReadExternalStoragePermission()
                else openImagesActivity()
            }
        }
    }

    private fun onEventUpdateAccount() {
        val email = binding.inputEmailAddress.text.toString().trim()
        val password = binding.inputPassword.text.toString().trim()
        val userName = binding.inputUserName.text.toString().trim()
        val isNullInput = isInputEmpty(email, userName, password)
        val isEmail = validateEmail(email)
        val isPassword = validatePassword(password)

        if (isNullInput) {
            activity?.showToast(R.string.this_field_cannot_be_left_blank)
            return
        }; if (!isEmail) {
            activity?.showToast(R.string.invalid_email)
            return
        }; if (!isPassword) {
            activity?.showToast(R.string.password_minimum_six_characters)
            return
        }

        userAccount.apply {
            this.email = email
            this.userName = userName
            this.password = password
            this.updatedAt = System.currentTimeMillis()
        }
        onUpdateAccount(userAccount)
    }

    private fun isInputEmpty(email: String, userName: String, password: String) =
        email.isEmpty() || userName.isEmpty() || password.isEmpty()

    private fun validateEmail(email: String): Boolean {
        val emailPattern =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
        val pattern = Pattern.compile(emailPattern)

        return pattern.matcher(email).matches()
    }

    private fun validatePassword(password: String): Boolean = password.length >= 6

    private fun onUpdateAccount(userAccount: UserAccount) {
        viewModel.updateAccount(userAccount)
        startActivity(Intent(requireActivity(), LoginActivity::class.java))
        activity?.finishAffinity()
        activity?.showToast(R.string.sign_in_again)
    }

    private fun isThisTiramisuVersionOrHigher() =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun isReadMediaImageAccepted() = binding.root.context.isReadMediaImageAccepted()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestReadMediaImagePermission() =
        binding.root.context.requestReadMediaImagePermission(fragment = this) {
            openImagesActivity()
        }

    private fun isReadExternalStorage() = binding.root.context.isReadExternalStorageAccepted()

    private fun openImagesActivity() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun requestReadExternalStoragePermission() =
        binding.root.context.requestReadExternalStoragePermission(fragment = this) { openImagesActivity() }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            if (imageUri == null) requireActivity().showToast(R.string.something_went_wrong)
            else {
                Glide.with(requireActivity()).load(imageUri)
                    .into(binding.imageUser)
                userAccount.apply {
                    this.base64Image = imageUri.toString()
                }
                CoroutineScope(Dispatchers.IO).launch {
                    AppDatabase.getInstance(requireActivity()).userAccountDao()
                        .updateAccount(userAccount)
                    val json = requireActivity().convertObjectToJson(userAccount)
                    PreferencesUtils.jsonAccount = json
                }
            }
        }
    }
}