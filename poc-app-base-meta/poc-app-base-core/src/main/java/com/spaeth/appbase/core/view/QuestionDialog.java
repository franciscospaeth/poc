package com.spaeth.appbase.core.view;

import java.io.Serializable;

import com.spaeth.appbase.component.Button;
import com.spaeth.appbase.component.ComponentContainer;
import com.spaeth.appbase.component.HorizontalLayout;
import com.spaeth.appbase.component.StaticLabel;
import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.component.VerticalLayout;
import com.spaeth.appbase.component.VisualComponent.Measure;
import com.spaeth.appbase.component.VisualComponent.MeasureUnit;
import com.spaeth.appbase.component.api.IOrderedLayout.HorizontalLayoutFlow;
import com.spaeth.appbase.core.model.action.AbstractAction;
import com.spaeth.appbase.core.startupinfo.QuestionStartupInfo;
import com.spaeth.appbase.core.startupinfo.QuestionStartupInfo.Answer;
import com.spaeth.appbase.event.ActionUpdateEvent;
import com.spaeth.appbase.model.ActionParameters;
import com.spaeth.appbase.model.ModalView;

public class QuestionDialog extends AbstractView<ComponentContainer> implements ModalView {

	private static final long serialVersionUID = 1L;
	private Answer answer = null;

	@Override
	public boolean isClosable() {
		return false;
	}

	@Override
	public String getTitle() {
		return getStartupInfo().getTitle();
	}

	@Override
	public StreamProvider getIcon() {
		return getStartupInfo().getQuestionIcon();
	}

	@Override
	public ComponentContainer getContent() {
		VerticalLayout verticalLayout = new VerticalLayout();

		StaticLabel staticLabel = new StaticLabel();
		staticLabel.setText(getStartupInfo().getQuestion());
		verticalLayout.addComponent(staticLabel);

		HorizontalLayout optionsLine = new HorizontalLayout(HorizontalLayoutFlow.RIGHT_TO_LEFT);
		optionsLine.setWidth(Measure.ALL);
		HorizontalLayout options = new HorizontalLayout();
		options.setSpaced(true);
		optionsLine.addComponent(options);

		for (Answer a : getStartupInfo().getAnswers()) {
			Button b = new Button();
			b.setText(a.getCaption());
			b.setIcon(a.getIcon());
			b.setAction(new AnswerAction(a));
			options.addComponent(b);
		}

		verticalLayout.addComponent(optionsLine);

		verticalLayout.setExpandRation(new float[] { 1, 0 });

		verticalLayout.setWidth(new Measure(MeasureUnit.PIXEL, 400));
		verticalLayout.setHeight(new Measure(MeasureUnit.PIXEL, 300));

		verticalLayout.setMarginVisible(true);

		return verticalLayout;
	}

	@Override
	public QuestionStartupInfo getStartupInfo() {
		return (QuestionStartupInfo) super.getStartupInfo();
	}

	@Override
	public Serializable getResult() {
		return answer;
	}

	private final class AnswerAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		private final Answer answer;

		public AnswerAction(final Answer answer) {
			super();
			this.answer = answer;
		}

		@Override
		protected void internalExecute(final ActionParameters parameters) {
			QuestionDialog.this.answer = answer;
			executeClose();
		}

		@Override
		protected ActionUpdateEvent createActionEvent() {
			return new ActionUpdateEvent(this, this);
		}
	}

}
