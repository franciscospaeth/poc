package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.aggregateCollectionViewerComponentProperties;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.spaeth.appbase.adds.xmlview.FixedDataSourceXmlViewContextDecorator;
import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.adds.xmlview.exception.XmlViewComponentTypeNotExpectedException;
import com.spaeth.appbase.adds.xmlview.exception.XmlViewWrongNumberOfElementsFound;
import com.spaeth.appbase.component.Repeater;
import com.spaeth.appbase.component.VisualComponent;
import com.spaeth.appbase.component.api.IRepeater.Repeated;
import com.spaeth.appbase.core.datasource.DataSource;

public class XmlRepeaterTranslator extends
		AbstractXmlVisualComponentTranslator<Repeater> {

	public XmlRepeaterTranslator(FrontXmlTranslator frontTranslator) {
		super(frontTranslator);
	}

	@Override
	protected Repeater createResult(Element source, XmlViewContext context) {
		return new Repeater();
	}

	@Override
	protected void configure(Repeater result, Element source,
			final XmlViewContext context) {
		super.configure(result, source, context);

		Element repeatElement = XmlViewTranslatorHelper.findFirstElementWithinChildren("repeater", XmlViewTranslatorConstants.REPEATER_REPEAT, source, false);
		Element whenEmptyElement = XmlViewTranslatorHelper.findFirstElementWithinChildren("repeater", XmlViewTranslatorConstants.REPEATER_WHEN_EMPTY, source, false);
		
		Element toRepeat = XmlViewTranslatorHelper.findFirstElementWithinChildren("repeater -> " + XmlViewTranslatorConstants.REPEATER_REPEAT, "*", repeatElement, false);
		Element whenEmpty = XmlViewTranslatorHelper.findFirstElementWithinChildren("repeater -> " + XmlViewTranslatorConstants.REPEATER_WHEN_EMPTY, "*", whenEmptyElement, false);
		
		Object componentWhenEmpty = null;
		if (whenEmpty != null) {
			componentWhenEmpty = context.translate(whenEmpty);
		}

		if (componentWhenEmpty != null && !(componentWhenEmpty instanceof VisualComponent)) {
			throw new XmlViewComponentTypeNotExpectedException("repeater -> when-empty", VisualComponent.class, componentWhenEmpty.getClass());
		}
		
		result.setRepeated(new XmlFragmentRepeated(context, toRepeat));
		result.setComponentWhenEmpty((VisualComponent) componentWhenEmpty);

		// collection viewer component
		aggregateCollectionViewerComponentProperties(source, result, context);

	}

	private static final class XmlFragmentRepeated implements Repeated {

		private final Element xmlElement;
		private final XmlViewContext context;

		public XmlFragmentRepeated(XmlViewContext context, Element xmlElement) {
			super();
			this.xmlElement = xmlElement;
			this.context = context;
		}

		@Override
		public VisualComponent create(DataSource element) {
			return (VisualComponent) new FixedDataSourceXmlViewContextDecorator(context, element)
					.translate(xmlElement);
		}

	}

}
