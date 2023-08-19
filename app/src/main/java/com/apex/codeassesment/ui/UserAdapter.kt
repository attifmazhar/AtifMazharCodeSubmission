package com.apex.codeassesment.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import coil.load
import coil.transform.CircleCropTransformation
import com.apex.codeassesment.R
import com.apex.codeassesment.databinding.ItemNoteBinding
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.utils.onItemClickListener
import com.google.gson.Gson
import java.util.*

class UserAdapter(val OnItemClickListener: onItemClickListener) : Adapter<UserAdapter.UserViewHolder>() {

    private val userList: MutableList<User> = ArrayList()

    inner class UserViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.user = user
            binding.image.load(user.picture?.medium) {
                transformations(CircleCropTransformation())
            }

            binding.cardView.setOnClickListener {
                OnItemClickListener.onClicked(user)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = DataBindingUtil.inflate<ItemNoteBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_note, parent, false
        )
        return UserViewHolder(view)
    }

    override fun getItemCount() = userList.size

    fun addAllUser(list: List<User>) {

        userList.clear()
        userList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }
}