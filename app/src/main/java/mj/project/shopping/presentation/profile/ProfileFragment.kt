package mj.project.shopping.presentation.profile

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import mj.project.shopping.R
import mj.project.shopping.databinding.FragmentProfileBinding
import mj.project.shopping.extensions.loadCenterCrop
import mj.project.shopping.extensions.toast
import mj.project.shopping.presentation.BaseFragment
import mj.project.shopping.presentation.adapter.ProductListAdapter
import mj.project.shopping.presentation.detail.ProductDetailActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import org.koin.android.ext.android.inject
import java.lang.Exception

internal class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding>() {

    companion object {
        const val TAG = "ProfileFragment"
    }

    override fun getViewBinding(): FragmentProfileBinding = FragmentProfileBinding.inflate(layoutInflater)

    override val viewModel by inject<ProfileViewModel>()

    // 구글 로그인
    private val gso: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    private val gsc by lazy {
        GoogleSignIn
            .getClient(requireActivity(), gso) } // parameter activity와 googlesigninoption을 적어준다.

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                task.getResult(ApiException::class.java)?.let { account ->
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                    viewModel.saveToken(account.idToken ?: throw Exception())
                } ?: throw Exception()
            } catch (e: Exception) {
                e.printStackTrace()
                handleErrorState()
            }
        }
    }

    private val adapter = ProductListAdapter()

    private fun initViews(binding: FragmentProfileBinding) = with(binding) {
        recyclerView.adapter = adapter
        loginButton.setOnClickListener {
            signInGoogle()
        }
        logoutButton.setOnClickListener {
            signOut()
        }
    }

    private fun signInGoogle() {
        val signInIntent: Intent = gsc.signInIntent
        launcher.launch(signInIntent)
    }

    private fun signOut() {
        firebaseAuth.signOut()
        viewModel.signOut()
    }

    override fun observeData() {
        viewModel.profileStateLiveData.observe(this) {
            when (it) {
                is ProfileState.UnInitialized -> {
                    initViews(binding)
                }
                is ProfileState.Loading -> {
                    handleLoadingState()
                }
                is ProfileState.Login -> {
                    handleLogin(it)
                }
                is ProfileState.Success -> {
                    handleSuccessState(it)
                }
                is ProfileState.Error -> {
                    handleErrorState()
                }
            }
        }
    }

    private fun handleLoadingState() = with(binding) {
        progressBar.isVisible = true
        loginRequiredGroup.isGone = true
    }

    private fun handleLogin(state: ProfileState.Login) {
        binding.progressBar.isVisible = true
        val credential = GoogleAuthProvider.getCredential(state.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    viewModel.setUserInfo(user)
                } else {
                    viewModel.setUserInfo(null)
                    requireContext().toast("로그아웃이 되어 재로그인 필요합니다.")
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun handleSuccessState(state: ProfileState.Success) = with(binding) {
        progressBar.isVisible = false
        when (state) {
            is ProfileState.Success.Registered -> {
                handleRegisteredState(state)
            }
            is ProfileState.Success.NotRegistered -> {
                profileGroup.isGone = true
                loginRequiredGroup.isVisible = true
                adapter.setProductList(listOf())
            }
        }
    }

    private fun handleRegisteredState(state: ProfileState.Success.Registered) = with(binding) {
        profileGroup.isVisible = true
        loginRequiredGroup.isGone = true
        profileImageView.loadCenterCrop(state.profileImgUri.toString(), 60f)
        userNameTextView.text = state.userName

        if (state.productList.isEmpty()) {
            emptyResultTextView.isGone = false
            recyclerView.isGone = true
        } else {
            emptyResultTextView.isGone = true
            recyclerView.isGone = false
            adapter.setProductList(state.productList) {
                startActivity(
                    ProductDetailActivity.newIntent(requireContext(), it.id) // 리스트에 해당 id값의 데이터를 가진 productDetailActivity실행
                )
            }
        }
    }

    private fun handleErrorState() {
        requireContext().toast("에러가 발생했습니다.")
    }

}