package co.lateralview.interviewtest.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.lateralview.interviewtest.R
import co.lateralview.interviewtest.domain.model.Post
import co.lateralview.interviewtest.ui.adapter.PostsListAdapter
import co.lateralview.interviewtest.ui.component.SimpleDividerItemDecoration
import java.util.*

private const val AMOUNT_OF_COMMENTS = 10

class PostsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_posts, container, false)
        val fragmentPosts = view.findViewById(R.id.fragment_posts_rv) as RecyclerView
        fragmentPosts.layoutManager = LinearLayoutManager(context)
        fragmentPosts.addItemDecoration(SimpleDividerItemDecoration(activity))
        fragmentPosts.adapter = PostsListAdapter(getPostsMocks())
        return view
    }

    private fun getPostsMocks(): List<Post> {

        val commentsCount = AMOUNT_OF_COMMENTS
        val userName = activity.resources.getString(R.string.item_post_user_name_sample_text)
        val description = activity.resources.getString(R.string.item_post_description_sample_text)
        val postItemsList = ArrayList<Post>()
        for (i in 0..5) {
            val formattedCommentsCount = String.format(activity.resources.getString(R.string.item_post_comments_text),
                    StringBuilder().append(commentsCount))
            val postItem = Post(userName, description, formattedCommentsCount)
            postItemsList.add(postItem)
        }
        return postItemsList
    }

}