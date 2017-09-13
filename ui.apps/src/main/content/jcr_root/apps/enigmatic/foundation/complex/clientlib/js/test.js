window.enigmatic.author.Complex.test = function(targetMode, targetResource, sourceURL, expected) {
	var complex = new window.enigmatic.author.Complex(targetMode, targetResource, sourceURL);
	var reloadURL = complex.reload.getURL();
	if (reloadURL != expected) {
		console.log("====================================================");
		console.log("TEST FAILED: " + complex.transition);
		console.log(complex);
		console.log("expected: " + expected);
		console.log("was: " + reloadURL);
	} else {
		console.log("TEST PASSED: " + complex.transition);
	}
}


window.enigmatic.author.Complex.test("http://www.local.com:4502/content/page.html",
		"/content/page/jcr:content/parsys/outer", "zoom",
		"http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter");
window.enigmatic.author.Complex.test("http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter",
		"/content/page/jcr:content/parsys/outer", "zoom",
		"http://www.local.com:4502/content/page.html");
window.enigmatic.author.Complex.test("http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter",
		"/content/page/jcr:content/parsys/outer/inner", "zoom",
		"http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&chain=38");
window.enigmatic.author.Complex.test("http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&chain=38",
		"/content/page/jcr:content/parsys/outer/inner", "zoom",
		"http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter");
window.enigmatic.author.Complex.test("http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&chain=38",
		"/content/page/jcr:content/parsys/outer", "zoom",
		"http://www.local.com:4502/content/page.html");

window.enigmatic.author.Complex.test("http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter",
		"/content/page/jcr:content/parsys/outer/inner", "focus",
		"http://www.local.com:4502/content/page.focus.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&chain=38&mode=zoom");
window.enigmatic.author.Complex.test("http://www.local.com:4502/content/page.focus.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&chain=38&mode=zoom",
		"/content/page/jcr:content/parsys/outer/inner", "focus",
		"http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter");
window.enigmatic.author.Complex.test("http://www.local.com:4502/content/page.focus.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&chain=38&mode=zoom",
		"/content/page/jcr:content/parsys/outer/inner", "zoom",
		"http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&chain=38");
window.enigmatic.author.Complex.test("http://www.local.com:4502/content/page.focus.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&chain=38&mode=edit",
		"/content/page/jcr:content/parsys/outer/inner", "zoom",
		"http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&chain=38&mode=edit");
window.enigmatic.author.Complex.test("http://www.local.com:4502/content/page.focus.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&chain=38",
		"/content/page/jcr:content/parsys/outer/inner", "zoom",
		"http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&chain=38&mode=focus");
window.enigmatic.author.Complex.test("http://www.local.com:4502/content/page.focus.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&chain=38&mode=zoom",
		"/content/page/jcr:content/parsys/outer", "zoom",
		"http://www.local.com:4502/content/page.html");

window.enigmatic.author.Complex.test("http://www.local.com:4502/content/page.focus.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner&chain=38&mode=zoom",
		"/content/page/jcr:content/parsys/outer/slider", "zoom",
		"http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Fslider");
window.enigmatic.author.Complex.test("http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Finner",
		"/content/page/jcr:content/parsys/outer/slider", "zoom",
		"http://www.local.com:4502/content/page.zoom.html?target=%2Fcontent%2Fpage%2Fjcr%3Acontent%2Fparsys%2Fouter%2Fslider");