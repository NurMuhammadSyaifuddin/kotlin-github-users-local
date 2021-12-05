package com.project.githubuser.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.project.githubuser.adapter.UsersAdapter
import com.project.githubuser.databinding.ActivityMainBinding
import com.project.githubuser.repository.Repository
import com.project.githubuser.ui.detail.DetailActivity
import com.project.githubuser.utils.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // init
        userAdapter = UsersAdapter()

        onAction()

        getDataUsers()

    }

    private fun getDataUsers() {

        showLoading()

        val users = Repository.getUsers(this)

        Handler(Looper.getMainLooper())
            .postDelayed({
                hideLoading()
                users?.let {
                    userAdapter.users = users
                }
            }, 800)

        binding.rvUsers.adapter = userAdapter

    }

    private fun showLoading() {
        binding.swipeMain.isRefreshing = true
    }

    private fun hideLoading() {
        binding.swipeMain.isRefreshing = false
    }

    private fun onAction() {

        binding.apply {

            swipeMain.setOnRefreshListener {

                if (edtSearchMain.isVisible) {
                    showSearch()
                }

                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

                hideSoftKeyboard(this@MainActivity, binding.root)
                edtSearchMain.text.clear()
                getDataUsers()

            }

            userAdapter.onClick { user ->
                startActivity<DetailActivity>(
                    DetailActivity.EXTRA_USER to user,
                )
            }

            imgSearch.setOnClickListener {
                hideSearch()
                showSoftKeyboard(this@MainActivity, binding.edtSearchMain)
            }

            imgClear.setOnClickListener {
                edtSearchMain.text.clear()
            }

            edtSearchMain.addTextChangedListener {
                val result = edtSearchMain.text
                imgClear.isVisible = result != null && result.toString().trim().isNotEmpty()
            }

            imgBack.setOnClickListener {
                showSearch()
                hideSoftKeyboard(this@MainActivity, binding.root)
                edtSearchMain.text.clear()
            }

            edtSearchMain.addTextChangedListener {
                userAdapter.filter.filter(it.toString().trim())
            }

            edtSearchMain.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val dataSearch = edtSearchMain.text.toString().trim()
                    userAdapter.filter.filter(dataSearch)

                    hideSoftKeyboard(this@MainActivity, binding.root)

                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        }

    }

    private fun hideSearch() {
        binding.apply {
            imgSearch.gone()
            tvTitle.gone()
            imgBack.visible()
            edtSearchMain.visible()
//            imgClear.visible()
        }
    }

    private fun showSearch() {
        binding.apply {
            imgSearch.visible()
            tvTitle.visible()
            imgBack.gone()
            edtSearchMain.gone()
            imgClear.gone()
        }
    }

    override fun onBackPressed() {

        val search = binding.edtSearchMain

        if (search.isVisible) {
            showSearch()
            search.text.clear()
        } else {
            super.onBackPressed()
        }

    }

}