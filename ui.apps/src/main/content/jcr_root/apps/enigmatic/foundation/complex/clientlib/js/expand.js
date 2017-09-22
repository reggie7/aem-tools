A.Mode.add("expand", A.Mode.RESOURCE);

A.expand = function(comp) {
	CQ.shared.Util.reload(window, comp.path + ".expand.html");
};

A.collapse = function(comp) {
	CQ.shared.Util.reload(window, comp.path + ".html");
};