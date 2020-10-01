package com.example.dicodinguserapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_follower.*
import kotlinx.android.synthetic.main.fragment_follower.view.*
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowerFragment : Fragment() {

    private var username : String? = null
    private var followersList = arrayListOf<User>()
    private lateinit var listFollowersAdapter: ListUserAdapter
    private var myFollowerRecyclerView: RecyclerView? = null

    companion object {
        private val ARG_USERNAME = "username"

        fun newInstance(username: String?): Fragment {
            val fragment = FollowerFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)

            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myFollowerRecyclerView = view?.findViewById(R.id.rv_followers)
//        adapter = ListUserAdapter(requireContext())
//        listView?.adapter = adapter
//        getUser(username.toString())
        myFollowerRecyclerView?.setHasFixedSize(true)
        listFollowersAdapter = ListUserAdapter(followersList)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

//        username = arguments?.getString("username")
        username = arguments?.getString("username")
        Log.d("testusername", username.toString())
        val rootView = inflater.inflate(R.layout.fragment_follower, container, false)
        rootView.rv_followers.layoutManager = LinearLayoutManager(activity)
        getUser(username.toString())
//        rootView.rv_followers.adapter = listFollowersAdapter



//        rootView.test_username.text = username.toString()
//        Log.d("follower fragment", username.toString())
        return rootView
    }


    private fun getUser(query: String) {
        Log.d("string query", query)
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 13104280bc3102d95eb1e2b1416e49d8921fe080")
        client.addHeader("User-Agent", "request")
        client.addHeader("Accept", "application/json")
        client.addHeader("Content-Type", "application/json")

        val url = "https://api.github.com/users/$query/followers"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val jsonArray = JSONArray(result)
//                    val jsonArray = JSONArray(responseObject.toString())

                    activity?.runOnUiThread {
                        followersList.clear()
//                        adapter.users.clear()
//                        listFollowersAdapter.
                        myFollowerRecyclerView?.recycledViewPool?.clear()
                        myFollowerRecyclerView?.adapter = null
                        listFollowersAdapter.notifyDataSetChanged()
                    }

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username = jsonObject.getString("login")
                        Log.d("username-folllower", username.toString())
                        getUserDetail(username)
                    }

                    activity?.runOnUiThread {
//                        adapter.users = users
                        Log.d("list", followersList.toString())
                        listFollowersAdapter = ListUserAdapter(followersList)

                        listFollowersAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
                            override fun onItemClicked(user: User) {
                                val moveUser = Intent(activity, DetailUserActivity::class.java)
                                moveUser.putExtra(DetailUserActivity.DETAIL_USER, user)
                                startActivity(moveUser)
                            }
                        })

                        myFollowerRecyclerView?.adapter = listFollowersAdapter
                        listFollowersAdapter.notifyDataSetChanged()
                    }
//                    Log.d("follower-object", responseObject.toString())
                } catch (e: Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getUserDetail(query: String) {
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 13104280bc3102d95eb1e2b1416e49d8921fe080")
        client.addHeader("User-Agent", "request")
        client.addHeader("Accept", "application/json")
        client.addHeader("Content-Type", "application/json")

        val url = " https://api.github.com/users/$query"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val username = responseObject.getString("login")
                    val name = responseObject.getString("name")
                    val company = responseObject.getString("company")
                    val location = responseObject.getString("location")
                    val repository = responseObject.getString("public_repos")
                    val follower = responseObject.getString("followers")
                    val following = responseObject.getString("following")
                    val avatar = responseObject.getString("avatar_url")

                    val newUser = User(
                        username,
                        name,
                        avatar,
                        company,
                        location,
                        repository,
                        follower,
                        following
                    )
                    activity?.runOnUiThread {
                        Log.d("new-user", newUser.toString())
                        followersList.add(newUser)
                        listFollowersAdapter.notifyDataSetChanged()
                    }

                } catch (e: Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                // Jika koneksi gagal
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })

    }

}