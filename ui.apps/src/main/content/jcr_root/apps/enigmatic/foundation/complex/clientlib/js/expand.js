A.Mode.add("expand", A.Mode.RESOURCE);
A.toggleExpand = function(comp) {
	A.toggleMode(comp, "expand");
};

A.expand = function(comp) {
	A.toggleExpand(comp);
};

A.collapse = function() {
	A.toggleExpand({ path: CQ.shared.HTTP.getPath(window.location.pathname) });
};