/**
 * Will refresh component with all selectors, suffix, parameters
 * and scrolls the page to the last position.
 */
A.refresh = function(component) {
	var scrollTop = $(document).scrollTop();
	component.refresh(url=CQ.WCM.getContentUrl().replace(CQ.WCM.getPagePath(), component.path));
	setTimeout(function(){$(document).scrollTop(scrollTop);}, 50);
};

/**
 * Will refresh given component's root.
 */
A.refreshRoot = function(component) {
	var parent = component.getParent();
	while(parent) {
		component = parent;
		parent = component.getParent();
	}
	A.refresh(component);
};

/**
 * Will refresh component's ancestor on the level passed.
 */
A.refreshAncestor = function(component, level) {
	var parent = component.getParent();
	for(var i = 0; i < level && parent; i++) {
		component = parent;
		parent = component.getParent();
	}
	A.refresh(component);
};