window.enigmatic = window.enigmatic || {};

window.enigmatic.author = window.enigmatic.author || {};

window.enigmatic.author.Complex = function(sourceURL, targetResource, targetMode) {
	const TARGET = "target";
	const MODE = "mode";
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
		this.previousMode = CQ.shared.HTTP.getParameter(url, MODE);
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
				if (this.savedMode !== undefined) {
					url = CQ.shared.HTTP.addParameter(url, MODE, this.savedMode);
				}
			}
			return url;
		};
	})(this.source, this.target);

	if (!this.source.hasMode()) {
		// normal mode - we enter the first level of special mode
		// this.transition = "normal -> special level 1";
		this.reload.mode = this.target.mode;
		this.reload.resource = this.target.resource;
	} else { // we are already in one of the special modes
		if (this.source.resource === this.target.resource) { // we are in a special mode regarding the component of the given path
			if (this.target.mode === this.source.mode) { // the current special mode is the same as the one that was given
				if (this.source.backIndex == null) { // we are in the first level of special mode, no nesting applies
					// this.transition = "special level 1 -> normal";
				} else { // we are in a nested special mode
					if (this.source.previousMode == null) {
						// this.transition = "special level X -> special level Y";
						this.reload.mode = this.target.mode;
					} else {
						// this.transition = "special level X -> special level Y:changed-mode";
						this.reload.mode = this.source.previousMode;
					}
					this.reload.resource = this.target.resource.substring(0, this.source.backIndex);
				}
			} else {
				// this.transition = "special mode A -> special mode B";
				this.reload.mode = this.target.mode;
				this.reload.resource = this.target.resource;
				if (this.source.backIndex != null) {
					this.reload.backIndex = this.source.backIndex;
					if (this.source.previousMode != null && this.source.previousMode != this.target.mode) {
						this.reload.savedMode = this.source.previousMode;
					} else if (this.target.mode != this.source.mode && this.source.previousMode == null) {
						this.reload.savedMode = this.source.mode;
					}
				}
			}
		} else {
			if (this.source.resource.indexOf(this.target.resource) > -1) {
				// this.transition = "inner -> (outer) -> normal";
			} else if (this.target.resource.indexOf(this.source.resource) > -1) {
				this.reload.mode = this.target.mode;
				this.reload.resource = this.target.resource;
				this.reload.backIndex = this.source.resource.length;
				if (this.source.mode == this.target.mode) {
					// this.transition = "outer -> inner";
				} else {
					// this.transition = "outer -> inner:changed-mode";
					this.reload.savedMode = this.source.mode;
				}
			} else {
				// this.transition = "siblings";
				this.reload.mode = this.target.mode;
				this.reload.resource = this.target.resource;
			}
		}
	}
};

window.enigmatic.author.toggleMode = function(comp, targetMode) {
	var sourceURL = window.document.URL;
	var targetResource = comp.path;
	var complex = new window.enigmatic.author.Complex(sourceURL, targetResource, targetMode);
	CQ.shared.Util.reload(window, complex.reload.getURL());
};

window.enigmatic.author.toggleZoom = function(comp) {
	window.enigmatic.author.toggleMode(comp, "zoom");
};