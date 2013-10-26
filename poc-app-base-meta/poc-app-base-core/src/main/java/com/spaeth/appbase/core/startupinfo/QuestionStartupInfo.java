package com.spaeth.appbase.core.startupinfo;

import java.io.Serializable;
import java.util.List;

import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.model.Callback;
import com.spaeth.appbase.model.StartupInfo;

public class QuestionStartupInfo extends StartupInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String title;
	private String question;
	private List<Answer> answers;
	private StreamProvider questionIcon;
	private StreamProvider questionImage;

	public <ExpectedResultType extends Serializable> QuestionStartupInfo(final Class<ExpectedResultType> resultType,
			final Callback<ExpectedResultType> callback) {
		super(resultType, callback);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(final String question) {
		this.question = question;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(final List<Answer> answers) {
		this.answers = answers;
	}

	public StreamProvider getQuestionIcon() {
		return questionIcon;
	}

	public void setQuestionIcon(final StreamProvider questionIcon) {
		this.questionIcon = questionIcon;
	}

	public StreamProvider getQuestionImage() {
		return questionImage;
	}

	public void setQuestionImage(final StreamProvider questionImage) {
		this.questionImage = questionImage;
	}

	public static class Answer implements Serializable {
		private static final long serialVersionUID = 1L;

		private String caption;
		private StreamProvider icon;

		public Answer(final String caption) {
			super();
			this.caption = caption;
		}

		public Answer(final String caption, final StreamProvider icon) {
			super();
			this.caption = caption;
			this.icon = icon;
		}

		public String getCaption() {
			return caption;
		}

		public void setCaption(final String caption) {
			this.caption = caption;
		}

		public StreamProvider getIcon() {
			return icon;
		}

		public void setIcon(final StreamProvider icon) {
			this.icon = icon;
		}

		@Override
		public String toString() {
			return "Answer [caption=" + caption + "]";
		}

	}

}
