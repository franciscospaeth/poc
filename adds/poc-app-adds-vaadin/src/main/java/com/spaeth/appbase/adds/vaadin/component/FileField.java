package com.spaeth.appbase.adds.vaadin.component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;

import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.component.api.IFileField;
import com.spaeth.appbase.component.stream.DataSourceStreamProvider;
import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.event.AccessPolicyChangeListener;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener;
import com.spaeth.appbase.core.security.model.AccessPolicy;
import com.vaadin.Application;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.Reindeer;

public class FileField extends VisualFieldComponent<Form> implements IFileField, ProgressListener, StartedListener, SucceededListener,
		FailedListener, ValueChangeListener, Receiver, FinishedListener, com.vaadin.data.Property.ValueChangeListener,
		AccessPolicyChangeListener {

	private static final long serialVersionUID = 1L;

	private static final String UPLOAD_ERROR_MESSAGE = "Error uploading file.";

	private static final String UPLOAD_SUCCEED_MESSAGE = "Syccessfuly uploaded file: %s";

	private static final String UPLOAD_BUTTON_TEXT = "Upload";

	private static final String MESSAGE_BUTTON_OK = "Ok";

	private HorizontalLayout content;

	private final Upload upload = new Upload();

	private final Button fileButton = new Button();

	private final Label messageLabel = new Label();
	private final Button messageButton = new Button();
	private final ProgressIndicator progressIndicator = new ProgressIndicator();
	private Label captionLabel;

	private File temporaryFile;

	private StreamProvider messageIcon;

	private DataSource nameDataSource;

	@Override
	public void setCaption(final String caption) {
		captionLabel.setValue(caption);
	}

	public FileField() {
		configureComponents();
		showUpload();

		fileButton.addListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(final ClickEvent event) {
				Application application = event.getButton().getApplication();
				if (application == null) {
					throw new IllegalArgumentException(
							"in order to provide a file to the client the component needs to be bounded to a session, but it seems this component is detached from any application, so none action can be taken");
				}

				String name = "tmpFile" + Math.random();
				if (nameDataSource != null) {
					name = ObjectUtils.toString(nameDataSource.getModel(), name);
				}

				StreamProviderResourceAdapter resource = new StreamProviderResourceAdapter(new DataSourceStreamProvider(getDataSource()));
				resource.setFilename(name);
				application.getMainWindow().open(resource, "downloadFileWindow");
			}

		});
	}

	@Override
	protected Form createDelegated() {
		content = new HorizontalLayout();
		captionLabel = new Label();

		com.vaadin.ui.VerticalLayout all = new VerticalLayout();

		all.addComponent(captionLabel);
		all.addComponent(content);

		Form form = new Form() {
			private static final long serialVersionUID = 1L;

			@Override
			public void readOnlyStatusChange(final com.vaadin.data.Property.ReadOnlyStatusChangeEvent event) {
				super.readOnlyStatusChange(event);
				updateAccess();
			}

			@Override
			public void valueChange(final com.vaadin.data.Property.ValueChangeEvent event) {
				super.valueChange(event);
				updateAccess();
			}

			@Override
			public Class<?> getType() {
				return byte[].class;
			}
		};
		form.setLayout(all);
		form.setFormFieldFactory(new FormFieldFactory() {
			private static final long serialVersionUID = 1L;

			@Override
			public Field createField(final Item item, final Object propertyId, final Component uiContext) {
				return null;
			}
		});
		form.setWidth("400px");
		content.setSizeFull();
		return form;
	}

	private void configureComponents() {
		messageButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(final ClickEvent event) {
				showUpload();
			}
		});

		content.setSpacing(true);

		upload.setImmediate(true);
		upload.setButtonCaption(UPLOAD_BUTTON_TEXT);
		upload.addStyleName(Reindeer.BUTTON_SMALL);
		messageButton.setCaption(MESSAGE_BUTTON_OK);
		fileButton.setStyleName(BaseTheme.BUTTON_LINK);
		fileButton.setEnabled(false);

		upload.setReceiver(this);
		upload.addListener((FailedListener) this);
		upload.addListener((SucceededListener) this);
		upload.addListener((StartedListener) this);
		upload.addListener((ProgressListener) this);

		progressIndicator.setWidth("100%");

		getDelegated().addListener(this);
	}

	@Override
	public void uploadFailed(final FailedEvent event) {
		showMessage(UPLOAD_ERROR_MESSAGE);
	}

	@Override
	public void uploadSucceeded(final SucceededEvent event) {
		try {
			try {
				setValue(FileUtils.readFileToByteArray(temporaryFile));
				if (nameDataSource != null) {
					nameDataSource.setModel(event.getFilename());
				}
			} finally {
				temporaryFile.delete();
			}
			showMessage(String.format(UPLOAD_SUCCEED_MESSAGE, event.getFilename()));
		} catch (IOException e) {
			showMessage("(IO/Ex) " + UPLOAD_ERROR_MESSAGE);
		}
	}

	@Override
	public void uploadStarted(final StartedEvent event) {
		showUploading();
		try {
			temporaryFile = File.createTempFile("uploadFile", "DS");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateProgress(final long readBytes, final long contentLength) {
		Float value = new Float(readBytes / (float) contentLength);
		progressIndicator.setValue(value);
		messageLabel.setValue(Double.valueOf((value * 100)).intValue() + "%");
	}

	private void showMessage(final String text) {
		content.removeComponent(upload);
		content.removeComponent(fileButton);
		content.removeComponent(progressIndicator);

		content.addComponent(messageLabel);
		content.addComponent(messageButton);
		messageLabel.setValue("<div style='white-space: nowrap; overflow:hidden'>" + text + "</div>");
		messageLabel.setWidth("100%");
		messageLabel.setContentMode(Label.CONTENT_XHTML);

		content.setExpandRatio(messageLabel, 1);
		content.setExpandRatio(messageButton, 0);
	}

	private void showUpload() {
		content.removeComponent(progressIndicator);
		content.removeComponent(messageLabel);
		content.removeComponent(messageButton);

		upload.setEnabled(!getDelegated().isReadOnly());
		content.addComponent(upload);
		content.addComponent(fileButton);

		content.setExpandRatio(upload, 0);
		content.setExpandRatio(fileButton, 1);
	}

	private void showUploading() {
		content.removeComponent(upload);
		content.removeComponent(fileButton);
		content.removeComponent(messageButton);

		messageLabel.setValue("0%");

		content.addComponent(progressIndicator);
		content.addComponent(messageLabel);
		messageLabel.setWidth("25px");

		content.setExpandRatio(progressIndicator, 1);
		content.setExpandRatio(messageLabel, 0);
	}

	@Override
	public OutputStream receiveUpload(final String filename, final String mimeType) {
		try {
			temporaryFile = File.createTempFile("uploadTemp_", filename);
			return new FileOutputStream(temporaryFile);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void valueChange(final ValueChangeEvent event) {
		Object newValue = event.getProperty().getValue();
		if (newValue == null) {
			fileButton.setCaption("");
			return;
		}
		checkContentType(newValue);
		updateCaption((byte[]) newValue);
		updateAccess();
	}

	protected void checkContentType(final Object newValue) {
		if (newValue != null && !(newValue instanceof byte[])) {
			showMessage("Error: file datasource should reference a byte[]");
		}
	}

	private void updateCaption(final byte[] content) {
		String name = null;
		if (nameDataSource != null) {
			name = ObjectUtils.toString(nameDataSource.get(), null);
		}
		updateCaption(name, content);
	}

	private void updateCaption(final String name) {
		Object content = getDataSource().get();
		checkContentType(content);
		updateCaption(name, (byte[]) content);
	}

	private void updateCaption(final String name, final byte[] content) {
		StringBuilder sb = new StringBuilder();
		if (name != null) {
			sb.append("file: ").append(name).append(", ");
		}
		if (content != null) {
			sb.append(FileUtils.byteCountToDisplaySize(content.length));
		}
		fileButton.setCaption(sb.toString());
	}

	@Override
	public void setMessageIcon(final StreamProvider streamProvider) {
		this.messageIcon = streamProvider;
		if (streamProvider != null) {
			messageButton.setIcon(new StreamProviderResourceAdapter(streamProvider));
		} else {
			messageButton.setIcon(null);
		}
	}

	@Override
	public StreamProvider getMessageIcon() {
		return messageIcon;
	}

	@Override
	public void uploadFinished(final FinishedEvent event) {
		if (temporaryFile != null) {
			temporaryFile.delete();
			temporaryFile = null;
		}
	}

	@Override
	public DataSource getNameDataSource() {
		return nameDataSource;
	}

	@Override
	public void setNameDataSource(final DataSource nameDataSource) {
		if (this.nameDataSource != null) {
			this.nameDataSource.removeDataSourceValueChangeListener(this);
			this.nameDataSource.removeDataSourceAccessPolicyChangeListener(this);
		}
		this.nameDataSource = nameDataSource;
		if (this.nameDataSource != null) {
			this.nameDataSource.addDataSourceAccessPolicyChangeListener(this);
			this.nameDataSource.addDataSourceValueChangeListener(this);
		}
	}

	@Override
	public void setDataSource(final DataSource dataSource) {
		super.setDataSource(dataSource);
		updateAccess();
	}

	@Override
	public void onChange(final DataSourceValueChangedEvent event) {
		updateCaption(ObjectUtils.toString(event.getNewValue(), null));
		updateAccess();
	}

	@Override
	protected boolean isValidatorNeeded() {
		return false;
	}

	@Override
	public void onAccessPolicyChanged(final AccessPolicyChangeEvent event) {
		updateAccess();
	}

	private void updateAccess() {
		AccessPolicy lower = getDataSource().getAccessPolicy();

		if (nameDataSource != null) {
			AccessPolicy nameAccessPolicy = nameDataSource.getAccessPolicy();
			if (lower.getValue() > nameAccessPolicy.getValue()) {
				lower = nameAccessPolicy;
			}
		}

		upload.setEnabled(lower == AccessPolicy.WRITE);
		fileButton.setEnabled(lower == AccessPolicy.WRITE);
		fileButton.setVisible(lower != AccessPolicy.NONE);
	}

}