<sly data-sly-use="${component.properties.model}" />
<%@page session="false"
import="java.lang.reflect.Constructor,
		pl.enigmatic.aem.dialog.GetOptions,
		org.apache.sling.api.resource.Resource,
		org.apache.sling.api.resource.ValueMap,
		org.apache.sling.api.resource.ResourceResolver,
		org.apache.sling.api.resource.ResourceMetadata,
		org.apache.sling.api.wrappers.ValueMapDecorator,
		java.util.List,
		java.util.ArrayList,
		java.util.HashMap,
		com.adobe.granite.ui.components.ds.DataSource,
		com.adobe.granite.ui.components.ds.EmptyDataSource,
		com.adobe.granite.ui.components.ds.SimpleDataSource,
		com.adobe.granite.ui.components.ds.ValueMapResource"%><%
%><%@taglib prefix="cq" uri="http://www.day.com/taglibs/cq/1.0" %><%
%><cq:defineObjects /><%
// set fallback
request.setAttribute(DataSource.class.getName(), EmptyDataSource.instance());
ResourceResolver resolver = slingRequest.getResourceResolver();
//Create an ArrayList to hold data
final List<Resource> resources = new ArrayList<Resource>();
final String optionsClassName = properties.get("datasource/provider", String.class);
final Class<GetOptions> optionsClass = (Class<GetOptions>) getClass().getClassLoader().loadClass(optionsClassName);
final Constructor<GetOptions> constructor = optionsClass.getConstructor(Resource.class);
final GetOptions provider = constructor.newInstance(slingRequest.getRequestPathInfo().getSuffixResource());
//Add 5 values to drop down! 
for (GetOptions.Entry e : provider.getOptions()) {
	//allocate memory to the Map instance
	final ValueMap vm = new ValueMapDecorator(new HashMap<String, Object>());
	//populate the map
	vm.put("value", e.getValue());
	vm.put("text", e.getText());
	resources.add(new ValueMapResource(resolver, new ResourceMetadata(), "nt:unstructured", vm));
}
//Create a DataSource that is used to populate the drop-down control
DataSource ds = new SimpleDataSource(resources.iterator());
request.setAttribute(DataSource.class.getName(), ds);
%>