package de.galan.dmsexchange.meta;

import java.time.ZonedDateTime;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

import de.galan.dmsexchange.util.UtcFormatter;


/**
 * Contains a comment a user has added to a document. Read the <a
 * href="https://github.com/galan/dms-exchange-specification">specification</a> for more information.
 *
 * @author daniel
 */
public class Comment implements Validatable {

	private User commentBy;
	private ZonedDateTime commentTime;
	private String content;


	public Comment() {
		// empty, default constructor required for Jackson
	}


	public Comment(User commentBy, ZonedDateTime commentTime, String content) {
		setCommentBy(commentBy);
		setCommentTime(commentTime);
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
		if (getCommentTime() == null) {
			result.add("No time for comment");
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


	public Comment commentBy(@SuppressWarnings("hiding") User commentBy) {
		setCommentBy(commentBy);
		return this;
	}


	public Comment commentBy(@SuppressWarnings("hiding") String commentBy) {
		setCommentBy(new User(commentBy));
		return this;
	}


	public ZonedDateTime getCommentTime() {
		return commentTime;
	}


	public void setCommentTime(ZonedDateTime commentTime) {
		this.commentTime = commentTime;
	}


	public Comment commentTime(@SuppressWarnings("hiding") ZonedDateTime commentTime) {
		setCommentTime(commentTime);
		return this;
	}


	public Comment commentTime(@SuppressWarnings("hiding") String commentTime) {
		setCommentTime(UtcFormatter.parse(commentTime));
		return this;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Comment content(@SuppressWarnings("hiding") String content) {
		setContent(content);
		return this;
	}


	@Override
	public int hashCode() {
		return Objects.hash(commentBy, content, commentTime);
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Comment) {
			final Comment other = (Comment)obj;
			return new EqualsBuilder().append(commentBy, other.commentBy).append(commentTime, other.commentTime).append(content, other.content).isEquals();
		}
		return false;
	}

}
