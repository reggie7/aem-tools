package pl.enigmatic.aem.comps.global;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public final class StringInterpolator extends MapBase<Object, StringInterpolator> implements Map.Entry<Object, StringInterpolator> {

    private final Object key;

    private final String value;
    private final ArrayList<Object> args = new ArrayList<>();
    public StringInterpolator(final Object key, final Object value) {
        this.key = key;
        this.value = value == null ? StringUtils.EMPTY : value.toString();
    }

    @Override
    public StringInterpolator get(final Object arg) {
        args.add(arg);
        return this;
    }

    @Override
    public int size() {
        return isEmpty() ? 0 : 1;
    }

    @Override
    public boolean isEmpty() {
        return key == null || StringUtils.isBlank(value);
    }

    @Override
    public Set<Object> keySet() {
        return Collections.singleton(getKey());
    }

    @Override
    public Collection<StringInterpolator> values() {
        return Collections.singleton(this);
    }

    @Override
    public Set<Entry<Object, StringInterpolator>> entrySet() {
        return Collections.singleton(this);
    }

    @Override
    public Object getKey() {
        return toString();
    }

    @Override
    public StringInterpolator getValue() {
        return this;
    }

    @Override
    public StringInterpolator setValue(final StringInterpolator value) {
        return getValue();
    }

    @Override
    public String toString() {
        return String.format(value, args.toArray());
    }

}
