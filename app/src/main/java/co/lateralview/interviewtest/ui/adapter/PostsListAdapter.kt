package co.lateralview.interviewtest.ui.adapter

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import co.lateralview.interviewtest.R
import co.lateralview.interviewtest.domain.model.Post


class PostsListAdapter(private var mDataSet: List<Post>) : RecyclerView.Adapter<PostsListAdapter.PostHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {

        holder.description.text = mDataSet[position].description
        holder.userName.text = mDataSet[position].userName
        holder.comments.text = getFormattedCommentsCount(mDataSet[position].comments)
    }

    private fun getFormattedCommentsCount(commentsWithoutFormat: String): SpannableStringBuilder {
        var spacesList: List<String>? = null
        try {
            spacesList = commentsWithoutFormat.split(" ")
        } catch (indexOutOfBoundsException: IndexOutOfBoundsException) {
            Log.d(this.javaClass.simpleName, "Comments had no space in it")
        }
        val spannableStringBuilder = SpannableStringBuilder(commentsWithoutFormat)
        val boldStyle = StyleSpan(Typeface.BOLD)
        if (spacesList != null && spacesList.size.compareTo(0) == 1) {
            spannableStringBuilder.setSpan(boldStyle, 0, spacesList[0].length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        }
        return spannableStringBuilder
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    inner class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var description: TextView = itemView.findViewById(R.id.item_post_description_tv) as TextView
        var userName: TextView = itemView.findViewById(R.id.item_post_user_name_tv) as TextView
        var comments: TextView = itemView.findViewById(R.id.item_post_comments_tv) as TextView

    }
}