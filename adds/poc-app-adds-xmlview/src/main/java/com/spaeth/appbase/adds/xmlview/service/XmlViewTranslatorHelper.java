package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.CAPTION_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.COLLECTION_DATASOURCE_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.DATASOURCE_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.EXPAND_RATIO_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.NAME_ATTRIBUTE_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.adds.xmlview.exception.XmlViewComponentTypeNotExpectedException;
import com.spaeth.appbase.adds.xmlview.exception.XmlViewElementNotInterpretable;
import com.spaeth.appbase.adds.xmlview.exception.XmlViewException;
import com.spaeth.appbase.adds.xmlview.exception.XmlViewWrongNumberOfElementsFound;
import com.spaeth.appbase.component.CollectionViewerComponent;
import com.spaeth.appbase.component.Component;
import com.spaeth.appbase.component.ComponentContainer;
import com.spaeth.appbase.component.DetacheableComponent;
import com.spaeth.appbase.component.FieldComponent;
import com.spaeth.appbase.component.MenuItem;
import com.spaeth.appbase.component.VisualComponent;
import com.spaeth.appbase.component.VisualComponent.Measure;
import com.spaeth.appbase.component.VisualComponent.MeasureUnit;
import com.spaeth.appbase.component.api.IOrderedLayout;
import com.spaeth.appbase.core.datasource.CollectionDataSource;
import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.service.I18NSupport;

public class XmlViewTranslatorHelper {

	private static final Logger logger = LoggerFactory.getLogger(XmlViewTranslatorHelper.class);
	private static AtomicLong autonamedSequence = new AtomicLong(Long.MIN_VALUE);

	public static String readAttribute(final Element element, final String attributeName) {
		return StringUtils.trimToNull(element.getAttribute(attributeName));
	}

	public static String readI18NAttribute(final Element element, final String attributeName, final I18NSupport support) {
		String value = StringUtils.trimToNull(element.getAttribute(attributeName));

		if (value == null) {
			return null;
		}

		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext context = new StandardEvaluationContext(support);

		String result = parser.parseExpression(value, new TemplateParserContext()).getValue(context, String.class);

		return StringUtils.trimToNull(result);
	}

	public static String readNameAttribute(final Element source, final XmlViewContext context) {
		String name = readAttribute(source, NAME_ATTRIBUTE_NAME);

		if (name == null) {
			// throw new XmlViewAttributeExpectedException(Component.class,
			// NAME_ATTRIBUTE_NAME);
			name = String.format("autoNamed_%s", String.valueOf(autonamedSequence.getAndIncrement()).replace('-', '_'));
		}

		if (!name.matches("[a-zA-Z_]+[a-zA-Z_0-9]*")) {
			throw new XmlViewException(
					String.format(
							"a component name MUST match the following rule: ([a-zA-Z_]+[a-zA-Z_1-9]*), but '%s' doesn't",
							name));
		}

		return name;
	}

	public static <T extends Enum<T>> T readEnumValue(final Element element, final String attributeName,
			final Class<T> enumType) {
		String value = readAttribute(element, attributeName);

		if (StringUtils.isEmpty(value)) {
			return null;
		}

		return Enum.valueOf(enumType, value);
	}

	public static <T extends Enum<T>> T readEnumValue(final Element element, final String attributeName,
			final Class<T> enumType, final T defaultValue) {
		T value = readEnumValue(element, attributeName, enumType);

		if (value == null) {
			return defaultValue;
		}

		return value;
	}

	public static Boolean readBooleanAttribute(final Element element, final String attributeName) {
		String attribute = readAttribute(element, attributeName);
		if (attribute == null) {
			return null;
		}
		return Boolean.valueOf(attribute);
	}

	public static Boolean readBooleanAttribute(final Element element, final String attributeName,
			final boolean defaultValue) {
		Boolean result = readBooleanAttribute(element, attributeName);
		if (result == null) {
			return defaultValue;
		}
		return result;
	}

	public static Integer readIntegerAttribute(final Element element, final String attributeName) {
		String value = readAttribute(element, attributeName);
		if (value == null) {
			return null;
		}
		return Integer.valueOf(value);
	}

	public static int readIntegerAttribute(final Element element, final String attributeName, final int defaultValue) {
		Integer value = readIntegerAttribute(element, attributeName);
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	public static Measure readMeasureAttribute(final Element element, final String attributeName) {
		String value = readAttribute(element, attributeName);

		if (value == null) {
			return null;
		} else if (value.endsWith("%")) {
			return new Measure(MeasureUnit.PERCENTAGE, Integer.valueOf(value.substring(0, value.length() - 1)));
		} else {
			if (value.endsWith("px")) {
				return new Measure(MeasureUnit.PIXEL, Integer.valueOf(value.substring(0, value.length() - 2)));
			}
			return new Measure(MeasureUnit.PIXEL, Integer.valueOf(value));
		}
	}

	public static void aggregateVisualComponentProperties(final Element element, final VisualComponent visualComponent) {
		// width
		Measure widthRead = readMeasureAttribute(element, XmlViewTranslatorConstants.WIDTH_ATTRIBUTE_NAME);
		if (widthRead != null) {
			visualComponent.setWidth(widthRead);
		}

		// height
		Measure heightRead = readMeasureAttribute(element, XmlViewTranslatorConstants.HEIGHT_ATTRIBUTE_NAME);
		if (heightRead != null) {
			visualComponent.setHeight(heightRead);
		}

		// visible
		visualComponent.setVisible(readBooleanAttribute(element, XmlViewTranslatorConstants.VISIBLE_ATTRIBUTE_NAME,
				true));
	}

	public static void aggregateFieldComponentProperties(final Element element, final FieldComponent fieldComponent,
			final XmlViewContext context, final I18NSupport support) {
		// caption
		fieldComponent.setCaption(readI18NAttribute(element, CAPTION_ATTRIBUTE_NAME, support));

		// dataSource
		String dataSourcePath = readAttribute(element, DATASOURCE_ATTRIBUTE_NAME);
		
		if (dataSourcePath != null) {
			DataSource dataSource = context.getDataSource(dataSourcePath);

			if (dataSource == null) {
				throw new IllegalStateException(
						"no datasource was returned from context when requested datasource for " + dataSourcePath);
			}

			fieldComponent.setDataSource(dataSource);
		}
	}

	public static void aggregateCollectionViewerComponentProperties(final Element element,
			final CollectionViewerComponent collectionViewerComponent, final XmlViewContext context) {
		String collectionDataSourcePath = readAttribute(element, COLLECTION_DATASOURCE_ATTRIBUTE_NAME);

		if (StringUtils.isNotEmpty(collectionDataSourcePath)) {
			DataSource collectionDataSource = context.getDataSource(collectionDataSourcePath);

			if (collectionDataSource instanceof CollectionDataSource) {
				collectionViewerComponent.setCollectionDataSource((CollectionDataSource) collectionDataSource);
			} else {
				throw new XmlViewElementNotInterpretable(element.getNodeName(),
						DetacheableComponent.class.getSimpleName(), collectionDataSourcePath
								+ " expected to be a collection dataSource");
			}

		}
	}

	public static void aggregateComponentContainerContent(final Element element,
			final ComponentContainer componentContainer, final XmlViewContext context) {
		List<Component> components = extractComponentsFromElement(element, context);

		for (Component c : components) {
			if (c instanceof DetacheableComponent) {
				componentContainer.addComponent((DetacheableComponent) c);
			}
		}
	}

	public static void aggregateLayout(final Element element, final IOrderedLayout layout) {
		String expandRatio = readAttribute(element, EXPAND_RATIO_ATTRIBUTE_NAME);

		if (StringUtils.isEmpty(expandRatio)) {
			return;
		}

		int i = 0;

		String[] expandRatioS = expandRatio.split(",");
		float expandRatioF[] = new float[expandRatioS.length];
		for (String s : expandRatioS) {
			s = s.trim();
			expandRatioF[i++] = Float.parseFloat(s);
		}

		layout.setExpandRation(expandRatioF);
	}

	public static List<Component> extractComponentsFromElement(final Element element, final XmlViewContext context) {
		List<Component> components = new ArrayList<Component>();

		NodeList children = element.getChildNodes();

		for (int i = 0; i < children.getLength(); i++) {
			Node e = children.item(i);
			if (e instanceof Element) {
				components.add((Component) context.translate((Element) e));
			} else if (e instanceof CharacterData) {
				// does nothing
			} else {
				logger.debug(" element '" + element + "' wasn't interpreted as component for component container");
			}
		}
		return components;
	}

	public static List<MenuItem> extractMenuItemsFromElement(final Element element, final XmlViewContext context) {
		ArrayList<MenuItem> arrayList = new ArrayList<MenuItem>();

		for (Component c : extractComponentsFromElement(element, context)) {
			if (c instanceof MenuItem) {
				arrayList.add((MenuItem) c);
			} else {
				throw new XmlViewComponentTypeNotExpectedException("menu item", MenuItem.class, c.getClass());
			}
		}

		return arrayList;
	}
	
	public static Element findFirstElementWithinChildren(String elementTagContext, String tagSearched, Node node, boolean moreThanOneAllowed) {
		
		if (node == null) {
			return null;
		}
		
		NodeList childNodes = node.getChildNodes();

		Element result = null;
		
		for (int i = 0; i < childNodes.getLength(); i++) {

			Node currentNode = childNodes.item(i);
			short currentNodeType = currentNode.getNodeType();

			// just skip not element nodes
			if (currentNodeType != Node.ELEMENT_NODE) {
				continue;
			}
			
			Element currentElement = (Element) currentNode;
			
			if (!("*".equals(tagSearched) || tagSearched.equals(currentElement.getTagName()))) {
				continue;
			}
			
			if (moreThanOneAllowed) {
				return currentElement;
			} else if (result != null) {
				throw new XmlViewWrongNumberOfElementsFound(elementTagContext + " -> " + tagSearched, 1);
			}
			result = (Element) currentNode;
			
		}
		
		return result;
	}

	private XmlViewTranslatorHelper() {
	}

}
