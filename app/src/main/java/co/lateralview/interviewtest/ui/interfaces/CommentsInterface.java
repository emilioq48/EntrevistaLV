package co.lateralview.interviewtest.ui.interfaces;

import co.lateralview.interviewtest.domain.model.Comment;

public interface CommentsInterface
{
	public void addNestedCommentsFragment(Comment parentComment);
}
