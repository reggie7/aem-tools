package pl.enigmatic.aem;

import java.awt.Dimension;
import java.io.IOException;

import javax.inject.Inject;
import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.foundation.Image;
import com.day.image.Layer;

@Model(adaptables = SlingHttpServletRequest.class)
public class ImageSize {

	/** Logger */
	private static final Logger LOG = LoggerFactory.getLogger(ImageSize.class);

	/** image resource */
	@Optional
	@Inject
	private Resource image;

	/** Default constructor */
	public ImageSize() {
		super();
	}

	public Dimension getDimension() {
		final Image image = new Image(this.image);
		Layer layer = null;
		try {
			layer = image.getLayer(false, false, false);
			return new Dimension(layer.getWidth(), layer.getHeight());
		} catch (final IOException | RepositoryException e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}
}