package pl.enigmatic.aem.dialog.datasource;

public class Option {

    private final String value;

    private final String label;

    public Option(final String value, final String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
