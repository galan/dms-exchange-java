package de.galan.dmsexchange.meta;

import java.time.ZonedDateTime;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;


/**
 * Contains a comment a user has added to a document. Read the <a
 * href="https://github.com/galan/dms-exchange-specification">specification</a> for more information.
 *
 * @author daniel
 */
public class Comment implements Validatable {

	private User commentBy;
	private ZonedDateTime tsComment;
	private String content;


	public Comment() {
		//nada
	}


	public Comment(User commentBy, ZonedDateTime tsComment, String content) {
		setCommentBy(commentBy);
		setTsComment(tsComment);
		setContent(content);
	}


	@Override
	public void validate(ValidationResult result) {
		if (getCommentBy() == null) {
			result.add("No user for comment");
		}
		else {
			getCommentBy().validate(result);
		}
		if (getTsComment() == null) {
			result.add("No timestamp for comment");
		}
		if (getContent() == null) {
			result.add("No content for comment");
		}
	}


	public User getCommentBy() {
		return commentBy;
	}


	public void setCommentBy(User commentBy) {
		this.commentBy = commentBy;
	}


	public ZonedDateTime getTsComment() {
		return tsComment;
	}


	public void setTsComment(ZonedDateTime tsComment) {
		this.tsComment = tsComment;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	@Override
	public int hashCode() {
		return Objects.hash(commentBy, content, tsComment);
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Comment) {
			final Comment other = (Comment)obj;
			return new EqualsBuilder().append(commentBy, other.commentBy).append(tsComment, other.tsComment).append(content, other.content).isEquals();
		}
		return false;
	}

}