package pl.enigmatic.aem.test;

import java.awt.geom.Point2D;

public class Hotspot extends Point2D.Double {

	private static final long serialVersionUID = 1L;

	private String path;

	public Hotspot(double x, double y) {
		super(x, y);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
