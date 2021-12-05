package com.project.githubuser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.githubuser.databinding.ItemUserBinding
import com.project.githubuser.model.User
import com.project.githubuser.utils.UsersDivCallback
import com.project.githubuser.utils.loadImage

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.ViewHolder>(), Filterable {

    private var listener: ((User) -> Unit)? = null

    var users = mutableListOf<User>()
        set(value) {
            val diffCallback = UsersDivCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field.clear()
            field.addAll(value)
            usersFilter = value
            diffResult.dispatchUpdatesTo(this)
        }

    private var usersFilter = mutableListOf<User>()

    inner class ViewHolder(private val context: Context, private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User, listener: ((User) -> Unit)?) {

            val id = context.resources.getIdentifier(user.avatar, "drawable", context.packageName)

            binding.apply {
                avatarUser.loadImage(id)
                tvUsername.text = user.username
                tvName.text = user.name
            }

            listener?.let {
                itemView.setOnClickListener {
                    listener(user)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(parent.context, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(usersFilter[position], listener)
    }

    override fun getItemCount(): Int = usersFilter.size

    fun onClick(listener: ((User) -> Unit)?) {
        this.listener = listener
    }

    override fun getFilter(): Filter = filters

    private val filters = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            var filteredList = mutableListOf<User>()
            val filterPattern = p0.toString().trim().lowercase()

            if (filterPattern.isEmpty()) {
                filteredList = users
            } else {
                for (user in users) {
                    val username = user.username.toString().trim().lowercase()
                    val name = user.name.toString().trim().lowercase()

                    if (username.contains(filterPattern) || name.contains(filterPattern)) {
                        filteredList.add(user)
                    }
                }
            }

            val results = FilterResults()
            results.values = filteredList

            return results
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            usersFilter = p1?.values as MutableList<User>
            notifyDataSetChanged()
        }

    }

}