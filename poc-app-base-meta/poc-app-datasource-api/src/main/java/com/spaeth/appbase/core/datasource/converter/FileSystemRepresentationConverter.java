package com.spaeth.appbase.core.datasource.converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.ValueConverter;

public class FileSystemRepresentationConverter implements ValueConverter {

	private final String directoryPath;
	private final String prefix;
	private String suffix;
	private String whenNull = null;
	private final boolean override = false;

	public FileSystemRepresentationConverter(final String directoryPath, final String prefix) {
		super();
		this.directoryPath = directoryPath;
		this.prefix = prefix;
	}

	public FileSystemRepresentationConverter(final String directoryPath, final String prefix, final String suffix) {
		super();
		this.directoryPath = directoryPath;
		this.prefix = prefix;
		this.suffix = suffix;
	}

	public FileSystemRepresentationConverter(final String directoryPath, final String prefix, final String suffix, final String whenNull) {
		super();
		this.directoryPath = directoryPath;
		this.prefix = prefix;
		this.suffix = suffix;
		this.whenNull = whenNull;
	}

	@Override
	public Object getModelFromValue(final DataSource ds, final Object value) {
		if (value == null) {
			return null;
		}

		Object key = ds.getModel();
		String name = key == null ? whenNull : ObjectUtils.toString(key, null);

		File file = getFile(directoryPath, ObjectUtils.toString(prefix), ObjectUtils.toString(suffix), name);

		if (name == null || (file.exists() && !override)) {
			name = generateName(directoryPath);
		}

		file = getFile(directoryPath, ObjectUtils.toString(prefix), ObjectUtils.toString(suffix), name);

		try {
			byte[] content;
			if (byte[].class.isInstance(value)) {
				content = (byte[]) value;
			} else {
				content = value.toString().getBytes();
			}
			new FileOutputStream(file).write(content);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return name;
	}

	@Override
	public Object getValueFromModel(final DataSource ds, final Object model) {
		File file = getFile(ObjectUtils.defaultIfNull(directoryPath, ""), prefix, suffix,
				model == null ? whenNull : ObjectUtils.toString(model, null));

		if (!file.exists()) {
			return new byte[0];
		}

		byte[] fileContent = new byte[(int) file.length()];

		try {
			new FileInputStream(file).read(fileContent);
			return fileContent;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected File getFile(final String directoryPath, final String prefix, final String suffix, final String name) {
		StringBuilder sb = new StringBuilder(directoryPath);
		sb.append(File.separator);
		if (StringUtils.isNotBlank(prefix)) {
			sb.append(prefix).append('.');
		}
		sb.append(ObjectUtils.toString(name, ""));
		if (StringUtils.isNotBlank(suffix)) {
			sb.append('.').append(suffix);
		}
		return new File(sb.toString());
	}

	protected String generateName(final String directoryPath) {
		return UUID.randomUUID().toString();
	}

}
