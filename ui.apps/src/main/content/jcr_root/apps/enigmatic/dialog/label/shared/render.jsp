<%--
  ADOBE CONFIDENTIAL
  ___________________

  Copyright 2013 Adobe
  All Rights Reserved.

  NOTICE: All information contained herein is, and remains
  the property of Adobe and its suppliers, if any. The intellectual
  and technical concepts contained herein are proprietary to Adobe
  and its suppliers and are protected by all applicable intellectual
  property laws, including trade secret and copyright laws.
  Dissemination of this information or reproduction of this material
  is strictly forbidden unless prior written permission is obtained
  from Adobe.
--%><%
%><%@ include file="/libs/granite/ui/global.jsp" %><%
%><%@ page session="false"
           import="org.apache.commons.lang3.StringUtils,
                  com.adobe.granite.ui.components.AttrBuilder,
                  com.adobe.granite.ui.components.Config,
                  com.adobe.granite.ui.components.Field,
                  com.adobe.granite.ui.components.Tag,
                  pl.enigmatic.aem.labels.shared.dialog.SharedLabel" %><%--###
TextField
=========

.. granite:servercomponent:: /libs/granite/ui/components/foundation/form/textfield
   :supertype: /libs/granite/ui/components/foundation/form/field
   :deprecated:

   A text field component.

   It extends :granite:servercomponent:`Field </libs/granite/ui/components/foundation/form/field>` component.

   It has the following content structure:

   .. gnd:gnd::

      [granite:FormTextField] > granite:FormField, granite:commonAttrs

      /**
       * The name that identifies the field when submitting the form.
       */
      - name (String)

      /**
       * The value of the field.
       */
      - value (StringEL)

      /**
       * A hint to the user of what can be entered in the field.
       */
      - emptyText (String) i18n

      /**
       * Indicates if the field is in disabled state.
       */
      - disabled (Boolean)

      /**
       * Indicates if the field is mandatory to be filled.
       */
      - required (Boolean)

      /**
       * Indicates if the value can be automatically completed by the browser.
       *
       * See also `MDN documentation regarding autocomplete attribute <https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input>`_.
       */
      - autocomplete (String)

      /**
       * The ``autofocus`` attribute to lets you specify that the field should have input focus when the page loads,
       * unless the user overrides it, for example by typing in a different control.
       * Only one form element in a document can have the ``autofocus`` attribute.
       */
      - autofocus (Boolean)

      /**
       * The name of the validator to be applied. E.g. ``foundation.jcr.name``.
       * See :doc:`validation </jcr_root/libs/granite/ui/components/foundation/clientlibs/foundation/js/validation/index>` in Granite UI.
       */
      - validation (String) multiple

      /**
       * The maximum number of characters (in Unicode code points) that the user can enter.
       */
      - maxlength (Long)
###--%><%

    Config cfg = cmp.getConfig();
    ValueMap vm = (ValueMap) request.getAttribute(Field.class.getName());
    Field field = new Field(cfg);

    boolean isMixed = field.isMixed(cmp.getValue());

    Tag tag = cmp.consumeTag();
    AttrBuilder attrs = tag.getAttrs();
    cmp.populateCommonAttrs(attrs);

    // Start of attrs compatibility; please use cmp.populateCommonAttrs(attrs).
    attrs.add("id", cfg.get("id", String.class));
    attrs.addClass(cfg.get("class", String.class));
    attrs.addRel(cfg.get("rel", String.class));
    attrs.add("title", i18n.getVar(cfg.get("title", String.class)));
    attrs.addOthers(cfg.getProperties(), "id", "class", "rel", "title", "type", "name", "value", "emptyText", "disabled", "required", "validation", "maxlength", "fieldLabel", "fieldDescription", "renderReadOnly", "ignoreData");
    // End of attrs compatibility.

    final SharedLabel label = new SharedLabel(slingRequest);
    final com.day.cq.wcm.api.Page languageRoot = label.getLanguageRoot();

    attrs.add("type", "text");
    attrs.add("name", label.getValuePath());
    attrs.add("placeholder", i18n.getVar(cfg.get("emptyText", String.class)));
    attrs.addDisabled(cfg.get("disabled", false));
    attrs.add("autocomplete", cfg.get("autocomplete", String.class));
    attrs.addBoolean("autofocus", cfg.get("autofocus", false));

    if (isMixed) {
        attrs.addClass("foundation-field-mixed");
        attrs.add("placeholder", i18n.get("<Mixed Entries>"));
    } else {
        attrs.add("value", label.getValue());
    }

    attrs.add("maxlength", cfg.get("maxlength", Integer.class));

    String fieldLabel = cfg.get("fieldLabel", String.class);
    String fieldDesc = cfg.get("fieldDescription", String.class);
    String labelledBy = null;

    if (fieldLabel != null && fieldDesc != null) {
        labelledBy = vm.get("labelId", String.class) + " " + vm.get("descriptionId", String.class);
    } else if (fieldLabel != null) {
        labelledBy = vm.get("labelId", String.class);
    } else if (fieldDesc != null) {
        labelledBy = vm.get("descriptionId", String.class);
    }

    if (StringUtils.isNotBlank(labelledBy)) {
        attrs.add("aria-labelledby", labelledBy);
    }

    if (cfg.get("required", false)) {
        attrs.add("aria-required", true);
    }

    String validation = StringUtils.join(cfg.get("validation", new String[0]), " ");
    attrs.add("data-validation", validation);

    attrs.addClass("coral-Textfield");

%><input <%= attrs.build() %> />
<input name="<%= label.getPath() %>/sling:resourceType" type="hidden" value="enigmatic/labels/value" />
<ui:includeClientLib categories="enigmatic.author.messages" />
<p class="author-msg-line author-notice">
    The value of <em><%= fieldLabel %></em> is stored inside the page <a href="/editor.html<%= languageRoot.getPath() %>.html" target="_blank"><%= languageRoot.getTitle() %></a>
    (&#10140; <a href="/sites.html<%= languageRoot.getPath() %>" target="_blank">Sites</a> Console).
    Remember to also <a href="/mnt/override/libs/wcm/core/content/common/managepublicationwizard.html?item=<%= languageRoot.getPath() %>" target="_blank">publish</a> it to see the label changes.
</p>