package com.spaeth.appbase.adds.xmlview;

import java.util.Map;

import com.spaeth.appbase.adds.xmlview.service.DefaultFrontXmlTranslator;
import com.spaeth.appbase.adds.xmlview.service.DefaultXmlViewContextFactory;
import com.spaeth.appbase.adds.xmlview.service.FrontXmlTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlActionTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlButtonTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlCheckBoxTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlComboBoxTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlDataGridColumnTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlDataGridTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlDateBoxTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlFileFieldTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlFragmentTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlHorizontalLayoutTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlImageTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlLabelTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlMenuBarTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlMenuOptionTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlMenuTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlOptionGroupTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlPageSelectorTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlPageStatusTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlPanelTabTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlPasswordFieldTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlRepeaterTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlRichTextAreaTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlScrollPanelTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlStaticLabelTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlStreamProviderTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlTabbedPanelTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlTextAreaTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlTextFieldTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlTreeTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlVerticalLayoutTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlViewContextFactory;
import com.spaeth.appbase.adds.xmlview.service.XmlViewFragmentTranslator;
import com.spaeth.appbase.adds.xmlview.service.XmlViewTranslator;
import com.spaeth.appbase.core.annotations.BuilderMethod;
import com.spaeth.appbase.core.annotations.ConfigurationMethod;
import com.spaeth.appbase.core.service.Binder;
import com.spaeth.appbase.core.service.Configurator.MappedConfiguration;
import com.spaeth.appbase.core.service.Module;

public class XmlViewModule implements Module {

	@Override
	public void configure(final Binder binder) {
		binder.new Binding(XmlViewContextFactory.class, DefaultXmlViewContextFactory.class);
	}

	@BuilderMethod
	public FrontXmlTranslator buildFrontXmlTranslator(final Map<String, XmlTranslator> conf) {
		return new DefaultFrontXmlTranslator(conf);
	}

	@ConfigurationMethod(FrontXmlTranslator.class)
	public void configureFrontXmlTranslator(final MappedConfiguration<String, XmlTranslator> config) {
		config.addInstance("view", XmlViewTranslator.class);
		config.addInstance("view-fragment", XmlViewFragmentTranslator.class);
		config.addInstance("vertical-layout", XmlVerticalLayoutTranslator.class);
		config.addInstance("horizontal-layout", XmlHorizontalLayoutTranslator.class);
		config.addInstance("button", XmlButtonTranslator.class);
		config.addInstance("data-grid", XmlDataGridTranslator.class);
		config.addInstance("data-grid-column", XmlDataGridColumnTranslator.class);
		config.addInstance("label", XmlLabelTranslator.class);
		config.addInstance("static-label", XmlStaticLabelTranslator.class);
		config.addInstance("text-field", XmlTextFieldTranslator.class);
		config.addInstance("tabbed-panel", XmlTabbedPanelTranslator.class);
		config.addInstance("panel-tab", XmlPanelTabTranslator.class);
		config.addInstance("scroll-panel", XmlScrollPanelTranslator.class);
		config.addInstance("menu-bar", XmlMenuBarTranslator.class);
		config.addInstance("menu", XmlMenuTranslator.class);
		config.addInstance("menu-option", XmlMenuOptionTranslator.class);
		config.addInstance("image", XmlImageTranslator.class);
		config.addInstance("fragment", XmlFragmentTranslator.class);
		config.addInstance("action", XmlActionTranslator.class);
		config.addInstance("password-field", XmlPasswordFieldTranslator.class);
		config.addInstance("stream-provider", XmlStreamProviderTranslator.class);
		config.addInstance("page-selector", XmlPageSelectorTranslator.class);
		config.addInstance("page-status", XmlPageStatusTranslator.class);
		config.addInstance("date-box", XmlDateBoxTranslator.class);
		config.addInstance("check-box", XmlCheckBoxTranslator.class);
		config.addInstance("file-field", XmlFileFieldTranslator.class);
		config.addInstance("combo-box", XmlComboBoxTranslator.class);
		config.addInstance("text-area", XmlTextAreaTranslator.class);
		config.addInstance("option-group", XmlOptionGroupTranslator.class);
		config.addInstance("tree", XmlTreeTranslator.class);
		config.addInstance("rich-text-area", XmlRichTextAreaTranslator.class);
		config.addInstance("repeater", XmlRepeaterTranslator.class);
	}

}
