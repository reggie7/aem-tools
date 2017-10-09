var E = window.enigmatic = window.enigmatic || {};
var A = E.A = E.author = E.author || {};

A.Mode = function(name, location=A.Mode.PARAM) {
	this.name = name;
	this.location = location;

	this.parseResource = function(url) {
		if (this.location === A.Mode.RESOURCE) {
			return CQ.shared.HTTP.getPath(new URL(url).pathname);
		}
		return A.Mode.parseDefaultResource(url);
	}
};

A.Mode.parseDefaultResource = function(url) {
	return CQ.shared.HTTP.getParameter(url, A.TARGET);
};

A.Mode.RESOURCE = "resource";
A.Mode.PARAM = "parameter";

A.Mode.A = A;
A.Mode.All = {};

A.Mode.add = function(name, location=A.Mode.PARAM) {
	return A.Mode.All[name] = new A.Mode(name, location);
};

A.Mode.get = function(name) {
	var mode = A.Mode.All[name];
	if (name && !mode) {
		mode = new A.Mode(name);
	}
	return mode;
};

A.TARGET = "target";
A.MODE = "mode";
A.BACK_INDEX = "backIndex";

A.Mode.parseURL = function(url) {
	var result = { url: url };

	// let's find the current mode
	var modes = CQ.shared.HTTP.getSelectors(url);
	if (modes && modes.length > 0) {
		result.mode = A.Mode.get(modes[0]);
		result.resource = result.mode.parseResource(url);
	} else {
		result.resource = A.Mode.parseDefaultResource(url);
	}

	// we need to identify the special mode modifiers kept within query parameters
	result.previousMode = A.Mode.get(CQ.shared.HTTP.getParameter(url, A.MODE));
	result.backIndex = CQ.shared.HTTP.getParameter(url, A.BACK_INDEX);

	return result;
};

A.Mode.parseTarget = function(resource, modeName) {
	return {
		resource: resource,
		mode: A.Mode.get(modeName),
		// since the resource to be presented in the special mode within its page - we can find the page path from the resource path
		page: resource.substring(0, resource.indexOf("/jcr:content/"))
	};
};

A.Transition = function(sourceURL, targetResource, targetMode) {

	const A = enigmatic.author;

	this.source = A.Mode.parseURL(sourceURL);
	this.target = A.Mode.parseTarget(targetResource, targetMode);

	if (this.source.mode) {
		// we are already in one of the special modes
		if (this.source.resource === this.target.resource) {
			// we are in a special mode regarding the component of the given path
			if (this.target.mode === this.source.mode) {
				// the current special mode is the same as the one that was given
				if (this.source.backIndex) {
					// we are in a nested special mode
					if (this.source.previousMode) {
						this.description = "special level X -> special level Y:changed-mode";
						this.mode = this.source.previousMode;
					} else {
						this.description = "special level X -> special level Y";
						this.mode = this.target.mode;
					}
					this.resource = this.target.resource.substring(0, this.source.backIndex);
				} else {
					// we are in the first level of special mode, no nesting applies
					this.description = "special level 1 -> normal";
				}
			} else {
				this.description = "special mode A -> special mode B";
				this.mode = this.target.mode;
				this.resource = this.target.resource;
				if (this.source.backIndex) {
					this.backIndex = this.source.backIndex;
					if (this.source.previousMode && this.source.previousMode !== this.target.mode) {
						this.savedMode = this.source.previousMode;
					} else if (this.target.mode !== this.source.mode && !this.source.previousMode) {
						this.savedMode = this.source.mode;
					}
				}
			}
		} else {
			if (this.source.resource.indexOf(this.target.resource) > -1) {
				this.description = "inner -> (outer) -> normal";
				if (this.source.mode !== this.target.mode) {
					this.mode = this.target.mode;
					this.resource = this.target.resource;
				}
			} else if (this.target.resource.indexOf(this.source.resource) > -1) {
				this.mode = this.target.mode;
				this.resource = this.target.resource;
				this.backIndex = this.source.resource.length;
				if (this.source.mode === this.target.mode) {
					this.description = "outer -> inner";
				} else {
					this.description = "outer -> inner:changed-mode";
					this.savedMode = this.source.mode;
				}
			} else {
				this.description = "siblings";
				this.mode = this.target.mode;
				this.resource = this.target.resource;
			}
		}
	} else {
		// normal mode - we enter the first level of special mode
		this.description = "normal -> special level 1";
		this.mode = this.target.mode;
		this.resource = this.target.resource;
	}

	this.getURL = function(params={}) {
		var url = this.source.url.substring(0, this.source.url.indexOf(this.target.page));
		if (this.mode && this.mode.location === A.Mode.RESOURCE) {
			url += this.resource;
		} else {
			url += this.target.page;
		}
		url += "." + CQ.shared.HTTP.getExtension(this.source.url);
		if (this.mode) {
			url = CQ.shared.HTTP.addSelector(url, this.mode.name, 0);
			if (this.mode.location !== A.Mode.RESOURCE) {
				url = CQ.shared.HTTP.addParameter(url, A.TARGET, this.resource);
			}
			if (this.backIndex) {
				url = CQ.shared.HTTP.addParameter(url, A.BACK_INDEX, this.backIndex);
			}
			if (this.savedMode) {
				url = CQ.shared.HTTP.addParameter(url, A.MODE, this.savedMode.name);
			}
		}
		for (var name in params) {
			url = CQ.shared.HTTP.addParameter(url, name, params[name]);
		}
		return url;
	};
};

A.toggleMode = function(comp, targetMode, params={}) {
	var sourceURL = window.document.URL;
	var targetResource = comp.path;
	var t = new A.Transition(sourceURL, targetResource, targetMode);
	CQ.shared.Util.reload(window, t.getURL(params));
};

A.Mode.add("zoom");
A.toggleZoom = function(comp) {
	A.toggleMode(comp, "zoom");
};

A.Mode.add("focus");
A.toggleFocus = function(comp) {
	A.toggleMode(comp, "focus");
};