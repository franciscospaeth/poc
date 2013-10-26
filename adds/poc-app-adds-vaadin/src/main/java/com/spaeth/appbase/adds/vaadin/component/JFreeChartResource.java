package com.spaeth.appbase.adds.vaadin.component;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import com.vaadin.Application;
import com.vaadin.terminal.StreamResource;

public class JFreeChartResource extends StreamResource {

	private static final long serialVersionUID = 1L;

	public JFreeChartResource(final JFreeChart chart, final int width, final int height, final Application application) {
		super(new JFreeChartSource(chart, width, height), "image_chart_" + Math.random() + ".png", application);
	}

	private static class JFreeChartSource implements StreamResource.StreamSource {

		private static final long serialVersionUID = 1L;

		private final JFreeChart chart;
		private final int width;
		private final int height;

		public JFreeChartSource(final JFreeChart chart, final int width, final int height) {
			this.chart = chart;
			this.width = width;
			this.height = height;
		}

		@Override
		public InputStream getStream() {
			try {
				final ByteArrayOutputStream baos = new ByteArrayOutputStream();
				this.chart.setBackgroundPaint(new Color(255, 255, 255, 0));
				ChartUtilities.writeChartAsPNG(baos, this.chart, this.width, this.height, true, 9);
				return new ByteArrayInputStream(baos.toByteArray());
			} catch (final Exception e) {
				System.out.println("Problem occurred creating chart.");
				throw new RuntimeException(e);
			}
		}

	}

}
