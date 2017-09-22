test = function(sourceURL, targetResource, targetMode, expected) {
	var t = new A.Transition(sourceURL, targetResource, targetMode);
	var reloadURL = t.getURL();
	if (reloadURL != expected) {
		console.log("====================================================");
		console.log("TEST FAILED: " + t.description);
		console.log("expected:  " + expected);
		console.log("was:       " + reloadURL);
		console.log(t);
	} else {
		console.log("TEST PASSED: " + t.description);
	}
}

//===============================================================================================================================================
// ONLY target as a parameter
//===============================================================================================================================================
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
test("http://www.local.com:4502/content/page.focus.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&backIndex=38&mode=expand",
		"/content/page/jcr:content/parsys/outer/inner", "zoom",
		"http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&backIndex=38&mode=expand");
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

//===============================================================================================================================================
// expand case
//===============================================================================================================================================

test("http://www.local.com:4502/content/page.html",
		"/content/page/jcr:content/parsys/outer", "expand",
		"http://www.local.com:4502/content/page/jcr:content/parsys/outer.expand.html");
test("http://www.local.com:4502/content/page/jcr:content/parsys/outer.expand.html",
		"/content/page/jcr:content/parsys/outer", "expand",
		"http://www.local.com:4502/content/page.html");
test("http://www.local.com:4502/content/page/jcr:content/parsys/outer.expand.html",
		"/content/page/jcr:content/parsys/outer/inner", "expand",
		"http://www.local.com:4502/content/page/jcr:content/parsys/outer/inner.expand.html?backIndex=38");
test("http://www.local.com:4502/content/page/jcr:content/parsys/outer/inner.expand.html?backIndex=38",
		"/content/page/jcr:content/parsys/outer/inner", "expand",
		"http://www.local.com:4502/content/page/jcr:content/parsys/outer.expand.html");
test("http://www.local.com:4502/content/page/jcr:content/parsys/outer/inner.expand.html?backIndex=38",
		"/content/page/jcr:content/parsys/outer", "expand",
		"http://www.local.com:4502/content/page.html");

test("http://www.local.com:4502/content/page/jcr:content/parsys/outer.expand.html",
		"/content/page/jcr:content/parsys/outer/inner", "focus",
		"http://www.local.com:4502/content/page.focus.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&backIndex=38&mode=expand");
test("http://www.local.com:4502/content/page.focus.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&backIndex=38&mode=expand",
		"/content/page/jcr:content/parsys/outer/inner", "focus",
		"http://www.local.com:4502/content/page/jcr:content/parsys/outer.expand.html");
test("http://www.local.com:4502/content/page.focus.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&backIndex=38&mode=expand",
		"/content/page/jcr:content/parsys/outer/inner", "expand",
		"http://www.local.com:4502/content/page/jcr:content/parsys/outer/inner.expand.html?backIndex=38");
test("http://www.local.com:4502/content/page.focus.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&backIndex=38&mode=zoom",
		"/content/page/jcr:content/parsys/outer/inner", "expand",
		"http://www.local.com:4502/content/page/jcr:content/parsys/outer/inner.expand.html?backIndex=38&mode=zoom");
test("http://www.local.com:4502/content/page.focus.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&backIndex=38",
		"/content/page/jcr:content/parsys/outer/inner", "expand",
		"http://www.local.com:4502/content/page/jcr:content/parsys/outer/inner.expand.html?backIndex=38&mode=focus");
test("http://www.local.com:4502/content/page.focus.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&backIndex=38&mode=expand",
		"/content/page/jcr:content/parsys/outer", "expand",
		"http://www.local.com:4502/content/page.html");

test("http://www.local.com:4502/content/page.focus.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&backIndex=38&mode=expand",
		"/content/page/jcr:content/parsys/outer/slider", "expand",
		"http://www.local.com:4502/content/page/jcr:content/parsys/outer/slider.expand.html");
test("http://www.local.com:4502/content/page/jcr:content/parsys/outer/inner.expand.html",
		"/content/page/jcr:content/parsys/outer/slider", "expand",
		"http://www.local.com:4502/content/page/jcr:content/parsys/outer/slider.expand.html");