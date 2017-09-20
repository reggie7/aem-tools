window.enigmatic = window.enigmatic || {};

window.enigmatic.author = window.enigmatic.author || {};

window.enigmatic.author.expand = function(comp) {
	CQ.shared.Util.reload(window, comp.path + ".expand.html");
};

window.enigmatic.author.collapse = function(comp) {
	CQ.shared.Util.reload(window, comp.path + ".html");
};