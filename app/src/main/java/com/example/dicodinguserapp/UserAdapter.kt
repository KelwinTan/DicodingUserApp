package com.example.dicodinguserapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter internal constructor(private val context: Context) : BaseAdapter() {

    internal var users = arrayListOf<User>()

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View {
        var userView = view
        if (userView == null){
            userView = LayoutInflater.from(context).inflate(R.layout.item_user, viewGroup, false)
        }

        val viewHolder = ViewHolder(userView as View)
        val user = getItem(position) as User
        viewHolder.bind(user)
        return userView
    }

    override fun getItem(index: Int): Any = users[index]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getCount(): Int = users.size

    private inner class ViewHolder internal constructor(view: View){
        private val txtUsername: TextView = view.findViewById(R.id.txt_username)
//        private val txtName: TextView = view.findViewById(R.id.txt_name)
        private val imgAvatar: CircleImageView = view.findViewById(R.id.img_avatar)
//        private val txtCompany: TextView = view.findViewById(R.id.txt_company)
//        private val txtLocation: TextView = view.findViewById(R.id.txt_location)
        private val txtRepository: TextView = view.findViewById(R.id.txt_repository)
        private val txtFollower: TextView = view.findViewById(R.id.txt_follower)
        private val txtFollowing: TextView = view.findViewById(R.id.txt_following)

        internal fun bind(user: User){
            txtUsername.text = user.username
//            txtName.text = "Name: " + user.name
            imgAvatar.setImageResource(user.avatar)
//            txtCompany.text = "Company: " + user.company
//            txtLocation.text = "Location: " + user.location
            txtRepository.text = "Repository: " + user.repository
            txtFollower.text = "Follower: " + user.follower
            txtFollowing.text = "Following: " + user.following
        }

    }

}