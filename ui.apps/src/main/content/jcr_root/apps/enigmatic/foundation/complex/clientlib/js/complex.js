window.enigmatic = window.enigmatic || {};

window.enigmatic.author = window.enigmatic.author || {};

window.enigmatic.author.Complex = function(sourceURL, targetResource, targetMode) {
	const TARGET = "target";
	const BACK_INDEX = "backIndex";

	this.source = new (function(url) {
		this.url = url;

		// let's find the current mode
		var modes = CQ.shared.HTTP.getSelectors(url);
		if (modes != null) {
			this.mode = modes[0];
		}
		this.hasMode = function() { return this.mode !== undefined; };

		// we need to identify the special mode modifiers kept within query parameters
		this.resource = CQ.shared.HTTP.getParameter(url, TARGET);
		this.backIndex = CQ.shared.HTTP.getParameter(url, BACK_INDEX);
	})(sourceURL);

	this.target = new (function(resource, mode) {
		this.resource = resource;
		this.mode = mode;
		// since the resource to be presented in the special mode within its page - we can find the page path from the resource path
		this.page = resource.substring(0, resource.indexOf("/jcr:content/"));
	})(targetResource, targetMode);

	this.reload = new (function(source, target) {
		// the clean page url without any selectors and other additions
		this.page = source.url.substring(0, source.url.indexOf(target.page)) + target.page + ".html";

		this.getURL = function() {
			var url = this.page;
			if (this.mode !== undefined) {
				url = CQ.shared.HTTP.addSelector(url, this.mode, 0);
				url = CQ.shared.HTTP.addParameter(url, TARGET, this.resource);
				if (this.backIndex !== undefined) {
					url = CQ.shared.HTTP.addParameter(url, BACK_INDEX, this.backIndex);
				}
			}
			return url;
		};
	})(this.source, this.target);

	if (!this.source.hasMode()) {
		this.reload.mode = this.target.mode;
		this.reload.resource = this.target.resource;
	} else { // we are already in one of the special modes
		if (this.source.resource === this.target.resource) { // we are in a special mode regarding the component of the given path
			if (this.source.backIndex != null) { // we are in a nested special mode
				this.reload.mode = this.target.mode;
				this.reload.resource = this.target.resource.substring(0, this.source.backIndex);
			}
		} else {
			if (this.source.resource.indexOf(this.target.resource) > -1) {
			} else if (this.target.resource.indexOf(this.source.resource) > -1) {
				this.reload.mode = this.target.mode;
				this.reload.resource = this.target.resource;
				this.reload.backIndex = this.source.resource.length;
			} else {
				this.reload.mode = this.target.mode;
				this.reload.resource = this.target.resource;
			}
		}
	}
};

window.enigmatic.author.Complex.toggle = function(comp, targetMode) {
	var sourceURL = window.document.URL;
	var targetResource = comp.path;
	var complex = new window.enigmatic.author.Complex(sourceURL, targetResource, targetMode);
	CQ.shared.Util.reload(window, complex.reload.getURL());
};

window.enigmatic.author.Complex.zoom = function(comp) {
	window.enigmatic.author.Complex.toggle(comp, "zoom");
};