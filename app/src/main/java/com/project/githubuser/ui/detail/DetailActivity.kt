package com.project.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bumptech.glide.Glide
import com.project.githubuser.R
import com.project.githubuser.databinding.ActivityDetailBinding
import com.project.githubuser.model.User

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataIntent()

        onAction()

    }

    private fun onAction() {

        showLoading()

        binding.apply {

            swipeDetail.setOnRefreshListener {
                getDataIntent()
            }

            imgClose.setOnClickListener { finish() }

        }

    }

    private fun showLoading() {
        binding.swipeDetail.isRefreshing = true
    }

    private fun hideLoading() {
        binding.swipeDetail.isRefreshing = false
    }

    private fun getDataIntent() {

        intent?.let {
            val user = it.getParcelableExtra<User>(EXTRA_USER)

            if (user != null) {

                showLoading()

                val id = this.resources.getIdentifier(user.avatar, "drawable", this.packageName)

                Handler(Looper.getMainLooper())
                    .postDelayed({
                        hideLoading()

                        Glide.with(this)
                            .load(id)
                            .placeholder(R.drawable.ic_user)
                            .into(binding.avatarUser)

                        binding.apply {
                            tvUsername.text = user.username
                            tvName.text = user.name
                            tvCompany.text = user.company
                            tvLocation.text = user.location
                            tvRepository.text = user.repository.toString()
                            tvFollowers.text = user.follower.toString()
                            tvFollowing.text = user.following.toString()
                        }
                    }, 800)

            }

        }

    }
}