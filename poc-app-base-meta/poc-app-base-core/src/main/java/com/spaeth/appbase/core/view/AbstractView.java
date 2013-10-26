package com.spaeth.appbase.core.view;

import java.io.Serializable;

import com.spaeth.appbase.ViewPort;
import com.spaeth.appbase.event.ViewCloseEvent;
import com.spaeth.appbase.event.ViewShowEvent;
import com.spaeth.appbase.model.StartupInfo;
import com.spaeth.appbase.model.View;
import com.spaeth.appbase.model.ViewCloseHandler;
import com.spaeth.appbase.model.ViewModel;

public abstract class AbstractView<ResultType> implements View<ResultType> {

	private static final long serialVersionUID = 1L;

	private ViewCloseHandler closeHandler;

	private ViewModel viewModel = ViewModel.DUMMY;

	private StartupInfo startupInfo;

	private ViewPort viewPort;

	@Override
	public final void close() {
		onClose(new AbstractViewCloseEvent(this));
	}

	protected final void executeClose() {
		if (closeHandler != null) {
			closeHandler.execute();
		}
		startupInfo.execute(getResult());
	}

	@Override
	public void onClose(final ViewCloseEvent event) {
		event.proceed();
	}

	@Override
	public void initialize(final ViewModel viewModel, final StartupInfo startupInfo) {
		this.viewModel = viewModel;
		this.startupInfo = startupInfo;
	}

	@Override
	public void onShown(final ViewShowEvent event) {
		this.closeHandler = event.getCloseHandler();
		this.viewPort = event.getSource();
	}

	@Override
	public final ViewModel getModel() {
		return viewModel;
	}

	public StartupInfo getStartupInfo() {
		return startupInfo;
	}

	public Serializable getResult() {
		return null;
	}

	private static class AbstractViewCloseEvent extends ViewCloseEvent {

		private static final long serialVersionUID = 1L;

		private final AbstractView<?> abstractView;

		public AbstractViewCloseEvent(final AbstractView<?> source) {
			super(source);
			this.abstractView = source;
		}

		@Override
		public void proceed() {
			super.proceed();
			abstractView.executeClose();
			abstractView.viewPort.viewClosed(abstractView);
		}

	}

}
