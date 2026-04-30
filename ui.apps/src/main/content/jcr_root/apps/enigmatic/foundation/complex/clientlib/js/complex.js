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
	};
};

A.Mode.parseDefaultResource = function(url) {
	return CQ.shared.HTTP.getParameter(url, A.TARGET);
};

A.Mode.RESOURCE = "resource";
A.Mode.PARAM = "parameter";

A.Mode.All = {};

A.Mode.add = function(name, location=A.Mode.PARAM) {
	return A.Mode.All[name] = new A.Mode(name, location);
};

A.Mode.get = function(name) {
	if (!name) return undefined;
	return A.Mode.All[name] || new A.Mode(name);
};

A.TARGET = "target";
A.MODE = "mode";
A.BACK_INDEX = "backIndex";

A.Mode.parseURL = function(url) {
	var result = { url: url };
	var modes = CQ.shared.HTTP.getSelectors(url);
	if (modes && modes.length > 0) {
		result.mode = A.Mode.get(modes[0]);
		result.resource = result.mode.parseResource(url);
	} else {
		result.resource = A.Mode.parseDefaultResource(url);
	}

	result.previousMode = A.Mode.get(CQ.shared.HTTP.getParameter(url, A.MODE));
	result.backIndex = CQ.shared.HTTP.getParameter(url, A.BACK_INDEX);
	return result;
};

A.Mode.parseTarget = function(resource, modeName) {
	return {
		resource: resource,
		mode: A.Mode.get(modeName),
		// the resource lives within its page — page path is the prefix before /jcr:content/
		page: resource.substring(0, resource.indexOf("/jcr:content/"))
	};
};

function resolveTransition(src, tgt) {
	if (!src.mode) {
		return { description: "normal -> special level 1", mode: tgt.mode, resource: tgt.resource };
	}

	if (src.resource !== tgt.resource) {
		if (src.resource.indexOf(tgt.resource) > -1) {
			// going shallower: inner navigating toward outer
			if (src.mode === tgt.mode) {
				return { description: "inner -> (outer) -> normal" };
			}

			return { description: "inner -> (outer) -> normal", mode: tgt.mode, resource: tgt.resource };
		}

		if (tgt.resource.indexOf(src.resource) > -1) {
			// drilling deeper into a sub-resource
			var deeper = { description: "outer -> inner", mode: tgt.mode, resource: tgt.resource, backIndex: src.resource.length };
			if (src.mode !== tgt.mode) {
				deeper.description = "outer -> inner:changed-mode";
				deeper.savedMode = src.mode;
			}

			return deeper;
		}
		return { description: "siblings", mode: tgt.mode, resource: tgt.resource };
	}

	if (tgt.mode !== src.mode) {
		// same resource, switching modes
		var switched = { description: "special mode A -> special mode B", mode: tgt.mode, resource: tgt.resource };
		if (src.backIndex) {
			switched.backIndex = src.backIndex;
			if (src.previousMode && src.previousMode !== tgt.mode) {
				switched.savedMode = src.previousMode;
			} else if (!src.previousMode) {
				switched.savedMode = src.mode;
			}
		}

		return switched;
	}

	// same resource, same mode
	if (!src.backIndex) {
		return { description: "special level 1 -> normal" };
	}

	// nested: unwind one level
	return {
		description: src.previousMode ? "special level X -> special level Y:changed-mode" : "special level X -> special level Y",
		mode: src.previousMode || tgt.mode,
		resource: tgt.resource.substring(0, src.backIndex)
	};
}

A.Transition = function(sourceURL, targetResource, targetMode) {
	this.source = A.Mode.parseURL(sourceURL);
	this.target = A.Mode.parseTarget(targetResource, targetMode);

	var src = this.source;
	var tgt = this.target;

	Object.assign(this, resolveTransition(src, tgt));

	this.getURL = function(params={}) {
		var url = src.url.substring(0, src.url.indexOf(tgt.page));
		if (this.mode && this.mode.location === A.Mode.RESOURCE) {
			url += this.resource;
		} else {
			url += tgt.page;
		}

		url += "." + CQ.shared.HTTP.getExtension(src.url);
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
	var t = new A.Transition(window.document.URL, comp.path, targetMode);
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
