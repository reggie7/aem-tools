;(function ($, ns, channel, window, undefined) {
	"use strict";

	var newComponentClass = 'new';

	ns.Inspectable.prototype.hasPlaceholder_Super = ns.Inspectable.prototype.hasPlaceholder;

	ns.Inspectable.prototype.hasPlaceholder = function() {
		if (this.onPage() && this.dom.hasClass(newComponentClass)) {
			var request = new XMLHttpRequest();
			request.open("GET", this.path.replace("/*", ".config.json"), false);
			request.send(null);
			try {
				var config = JSON.parse(request.responseText);
				if (config.emptyText) { return Granite.I18n.get(config.emptyText); }
			} catch(e) { }
		}
		return this.hasPlaceholder_Super();
	};

}(jQuery, Granite.author, jQuery(document), this));