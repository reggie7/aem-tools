test = function(targetMode, targetResource, sourceURL, expected) {
	var transition = new A.Transition(targetMode, targetResource, sourceURL);
	var reloadURL = transition.getURL();
	if (reloadURL != expected) {
		console.log("====================================================");
		console.log("TEST FAILED: " + transition.description);
		console.log(transition);
		console.log("expected: " + expected);
		console.log("was: " + reloadURL);
	} else {
		console.log("TEST PASSED: " + transition.description);
	}
}


test("http://www.local.com:4502/content/page.html",
		"/content/page/jcr:content/parsys/outer", "zoom",
		"http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter");
test("http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter",
		"/content/page/jcr:content/parsys/outer", "zoom",
		"http://www.local.com:4502/content/page.html");
test("http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter",
		"/content/page/jcr:content/parsys/outer/inner", "zoom",
		"http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&backIndex=38");
test("http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&backIndex=38",
		"/content/page/jcr:content/parsys/outer/inner", "zoom",
		"http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter");
test("http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&backIndex=38",
		"/content/page/jcr:content/parsys/outer", "zoom",
		"http://www.local.com:4502/content/page.html");

test("http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter",
		"/content/page/jcr:content/parsys/outer/inner", "focus",
		"http://www.local.com:4502/content/page.focus.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&backIndex=38&mode=zoom");
test("http://www.local.com:4502/content/page.focus.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&backIndex=38&mode=zoom",
		"/content/page/jcr:content/parsys/outer/inner", "focus",
		"http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter");
test("http://www.local.com:4502/content/page.focus.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&backIndex=38&mode=zoom",
		"/content/page/jcr:content/parsys/outer/inner", "zoom",
		"http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&backIndex=38");
test("http://www.local.com:4502/content/page.focus.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&backIndex=38&mode=edit",
		"/content/page/jcr:content/parsys/outer/inner", "zoom",
		"http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&backIndex=38&mode=edit");
test("http://www.local.com:4502/content/page.focus.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&backIndex=38",
		"/content/page/jcr:content/parsys/outer/inner", "zoom",
		"http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&backIndex=38&mode=focus");
test("http://www.local.com:4502/content/page.focus.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&backIndex=38&mode=zoom",
		"/content/page/jcr:content/parsys/outer", "zoom",
		"http://www.local.com:4502/content/page.html");

test("http://www.local.com:4502/content/page.focus.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&backIndex=38&mode=zoom",
		"/content/page/jcr:content/parsys/outer/slider", "zoom",
		"http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Fslider");
test("http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner",
		"/content/page/jcr:content/parsys/outer/slider", "zoom",
		"http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Fslider");