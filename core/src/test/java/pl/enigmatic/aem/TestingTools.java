package pl.enigmatic.aem;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junitx.util.PrivateAccessor;

public final class TestingTools {

    /**
     * The {@link Logger}.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TestingTools.class);

    private TestingTools() {
        throw new UnsupportedOperationException();
    }

    /**
     * Method used to test private constructor.
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public static <T> T testPrivateConstructor(final Class<T> clazz) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Constructor<T> c = clazz.getDeclaredConstructor();
        c.setAccessible(true);
        return c.newInstance();
    }
    /**
     * Method to read the content from file represented by given <code>path</code> 
     * encoded with the passed <code>encoded</code>
     * @param path
 *          - path to the file to be read
     * @param encoding
 *          - the encoding of file
     * @return
 *          String with the file content or null if and {@link java.io.IOException} occurs.
     */
    public static String readFileContent(final String path, final Charset encoding) {
        try {
            final byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, encoding);
        } catch (final IOException e) {
            TestingTools.LOGGER.error("Wrong arguments passed to readFile method: " + path + " " + encoding.toString());
        }
        return null;
    }

    /**
     * 
     * @param path
     * @return
     */
    public static String readFileContent(final String path) {
        return TestingTools.readFileContent(path, StandardCharsets.UTF_8);
    }

    public static void testSimpleGetters(final Object o) throws IntrospectionException, NoSuchFieldException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Class<?> cls = o.getClass();
        final BeanInfo info = Introspector.getBeanInfo(cls);
        final Map<String, PropertyDescriptor> props = new HashMap<String, PropertyDescriptor>();
        for (final PropertyDescriptor pd : info.getPropertyDescriptors()) {
            props.put(pd.getName(), pd);
        }
        for (final Field f : cls.getDeclaredFields()) {
            final String name = f.getName();
            if (props.containsKey(name)) {
                final Class<?> type = f.getType();
                final PropertyDescriptor pd = props.get(name);
                if (type.equals(pd.getPropertyType())) {
                    final Object expected;
                    if (type.isPrimitive() || type.isAnonymousClass() || Modifier.isFinal(type.getModifiers())) {
                        expected = PrivateAccessor.getField(o, name);
                    } else {
                        expected = Mockito.mock(type);
                    }
                    PrivateAccessor.setField(o, name, expected);
                    final Object actual = pd.getReadMethod().invoke(o);
                    Assert.assertEquals(name, expected, actual);
                }
            }
        }
    }

    public static Object mock(final Class<?> type) {
        if (boolean.class == type) {
            return false;
        } else if (int.class == type) {
            return (int) 0;
        } else if (long.class == type) {
            return (long) 0;
        } else if (byte.class == type) {
            return (byte) 0;
        } else if (short.class == type) {
            return (short) 0;
        } else if (double.class == type) {
            return (double) 0;
        } else if (float.class == type) {
            return (float) 0;
        } else if (String.class == type) {
            return StringUtils.EMPTY;
        } else if (Class.class == type) {
            return Class.class;
        }
        return Mockito.mock(type);
    }

    public static<I> void testInterfaceMethods(final Class<I> clazz, final I expected, final I actual) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        for (final Method m : clazz.getDeclaredMethods()) {
            final Class<?>[] types = m.getParameterTypes();
            final Object[] params = new Object[types.length];
            for (int i = 0; i < types.length; i++) {
                params[i] = mock(types[i]);
            }
            Assert.assertEquals(m.getName(), m.invoke(expected, params), m.invoke(actual, params));
        }
    }

    public static void testConstants(final Class<?> cls) throws IllegalArgumentException, IllegalAccessException {
        for (final Field f : cls.getDeclaredFields()) {
            final int mod = f.getModifiers();
            if (Modifier.isStatic(mod) && Modifier.isPublic(mod)) {
                Assert.assertNotNull(f.get(null));
            }
        }
    }

}