package pl.enigmatic.aem.dialog.datasource;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.google.common.collect.ImmutableMap;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Model used to set data source request attribute.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class DataSourceHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceHandler.class);

    private final SlingHttpServletRequest request;

    @Inject
    private ResourceResolver resourceResolver;

    @Inject
    @Optional
    private DataSourceOptionsProvider provider;

    public DataSourceHandler(final SlingHttpServletRequest request) {
        this.request = request;
    }

    @PostConstruct
    protected void init() {
        if (provider != null) {
            setDataSource();
        } else {
            LOGGER.error("Failed to create data source without provider. Data source path: {}",
                request.getResource().getPath());
        }
    }

    private void setDataSource() {
        Collection<Option> options = provider.getOptions();
        List<Resource> resources = options.stream()
                .map(this::toValueMap)
                .map(this::toResource)
                .collect(Collectors.toList());

        DataSource dataSource = new SimpleDataSource(resources.iterator());
        request.setAttribute(DataSource.class.getName(), dataSource);
    }

    private ValueMap toValueMap(Option dataSourceEntry) {
        final Map<String, Object> dataSourceMap = ImmutableMap.<String, Object>builder()
                .put("text", dataSourceEntry.getLabel())
                .put("value", dataSourceEntry.getValue())
                .build();
        return new ValueMapDecorator(dataSourceMap);
    }

    private Resource toResource(ValueMap valueMap) {
        return new ValueMapResource(resourceResolver, new ResourceMetadata(), "nt:unstructured", valueMap);
    }
}
