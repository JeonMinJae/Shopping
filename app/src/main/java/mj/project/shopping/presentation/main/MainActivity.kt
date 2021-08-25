package mj.project.shopping.presentation.main

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import mj.project.shopping.R
import mj.project.shopping.databinding.ActivityMainBinding
import mj.project.shopping.presentation.BaseActivity
import mj.project.shopping.presentation.BaseFragment
import mj.project.shopping.presentation.list.ProductListFragment
import mj.project.shopping.presentation.profile.ProfileFragment
import org.koin.android.ext.android.inject

internal class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(), BottomNavigationView.OnNavigationItemSelectedListener {

    override val viewModel by inject<MainViewModel>()

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        bottomNav.setOnNavigationItemSelectedListener(this@MainActivity)
        showFragment(ProductListFragment(), ProductListFragment.TAG)
    }

    //it인 mainstate는 seal class로 선언되었기 때문에 when절에서 else를 사용하지 않는다.
    override fun observeData() = viewModel.mainStateLiveData.observe(this) {
        when (it) {
            is MainState.RefreshOrderList -> {
                binding.bottomNav.selectedItemId = R.id.menu_profile
                val fragment = supportFragmentManager.findFragmentByTag(ProfileFragment.TAG)
                (fragment as? BaseFragment<*, *>)?.viewModel?.fetchData()
                // basefragment는 baseviewmodel과 viewbinding을 *,* 로 표현했다.
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_products -> {
                showFragment(ProductListFragment(), ProductListFragment.TAG)
                true
            }
            R.id.menu_profile -> {
                showFragment(ProfileFragment(), ProfileFragment.TAG)
                true
            }
            else -> false
        }
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        val findFragment = supportFragmentManager.findFragmentByTag(tag)
        supportFragmentManager.fragments.forEach { fm ->
            supportFragmentManager.beginTransaction().hide(fm).commit()
        }
        findFragment?.let {
            supportFragmentManager.beginTransaction().show(it).commit()
        } ?: kotlin.run {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragment, tag)
                .commitAllowingStateLoss()
        }
    }
}