/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tw.linkedin.studygroup.springbug.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * <a href="https://github.com/spring-projects/spring-framework/blob/4.0.x/spring-core/src/main/java/org/springframework/util/LinkedCaseInsensitiveMap.java">...</a>
 * {@link LinkedHashMap} variant that stores String keys in a case-insensitive
 * manner, for example for key-based access in a results table.
 *
 * <p>Preserves the original order as well as the original casing of keys,
 * while allowing for contains, get and remove calls with any case of key.
 *
 * <p>Does <i>not</i> support {@code null} keys.
 *
 * @author Juergen Hoeller
 * @since 3.0
 */
@SuppressWarnings("serial")
public class LinkedCaseInsensitiveMapVersion4_0_0<V> extends LinkedHashMap<String, V> {

    private final Map<String, String> caseInsensitiveKeys;

    private final Locale locale;


    /**
     * Create a new LinkedCaseInsensitiveMap for the default Locale.
     * @see String#toLowerCase()
     */
    public LinkedCaseInsensitiveMapVersion4_0_0() {
        this(null);
    }

    /**
     * Create a new LinkedCaseInsensitiveMap that stores lower-case keys
     * according to the given Locale.
     * @param locale the Locale to use for lower-case conversion
     * @see String#toLowerCase(Locale)
     */
    public LinkedCaseInsensitiveMapVersion4_0_0(Locale locale) {
        super();
        this.caseInsensitiveKeys = new HashMap<String, String>();
        this.locale = (locale != null ? locale : Locale.getDefault());
    }

    /**
     * Create a new LinkedCaseInsensitiveMap that wraps a {@link LinkedHashMap}
     * with the given initial capacity and stores lower-case keys according
     * to the default Locale.
     * @param initialCapacity the initial capacity
     * @see String#toLowerCase()
     */
    public LinkedCaseInsensitiveMapVersion4_0_0(int initialCapacity) {
        this(initialCapacity, null);
    }

    /**
     * Create a new LinkedCaseInsensitiveMap that wraps a {@link LinkedHashMap}
     * with the given initial capacity and stores lower-case keys according
     * to the given Locale.
     * @param initialCapacity the initial capacity
     * @param locale the Locale to use for lower-case conversion
     * @see String#toLowerCase(Locale)
     */
    public LinkedCaseInsensitiveMapVersion4_0_0(int initialCapacity, Locale locale) {
        super(initialCapacity);
        this.caseInsensitiveKeys = new HashMap<String, String>(initialCapacity);
        this.locale = (locale != null ? locale : Locale.getDefault());
    }


    @Override
    public V put(String key, V value) {
        String oldKey = this.caseInsensitiveKeys.put(convertKey(key), key);
        if (oldKey != null && !oldKey.equals(key)) {
            super.remove(oldKey);
        }
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends String, ? extends V> map) {
        if (map.isEmpty()) {
            return;
        }
        for (Map.Entry<? extends String, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public boolean containsKey(Object key) {
        return (key instanceof String && this.caseInsensitiveKeys.containsKey(convertKey((String) key)));
    }

    @Override
    public V get(Object key) {
        if (key instanceof String) {
            return super.get(this.caseInsensitiveKeys.get(convertKey((String) key)));
        }
        else {
            return null;
        }
    }

    @Override
    public V remove(Object key) {
        if (key instanceof String ) {
            return super.remove(this.caseInsensitiveKeys.remove(convertKey((String) key)));
        }
        else {
            return null;
        }
    }

    @Override
    public void clear() {
        this.caseInsensitiveKeys.clear();
        super.clear();
    }


    /**
     * Convert the given key to a case-insensitive key.
     * <p>The default implementation converts the key
     * to lower-case according to this Map's Locale.
     * @param key the user-specified key
     * @return the key to use for storing
     * @see String#toLowerCase(Locale)
     */
    protected String convertKey(String key) {
        return key.toLowerCase(this.locale);
    }

}