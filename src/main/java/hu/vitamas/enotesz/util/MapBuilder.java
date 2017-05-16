/*
 * Copyright (C) 2017 Vincze Tamas Zoltan (www.vitamas.hu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.vitamas.enotesz.util;

import java.util.HashMap;

/**
 * Making new {@literal HashMap<K,V>} with chaining style.
 * 
 * @author vitozy
 * @param <K> key class type
 * @param <V> value class type
 */
public class MapBuilder<K,V> {

    private HashMap<K,V> map;

    /**
     * Initialize a new HashMap and returns with MapBuilder instance.
     * 
     * @return MapBuilder
     * @param <K> key class type
     * @param <V> value class type
     */
    public static <K,V> MapBuilder<K,V> newHashMap(){
            return new MapBuilder<K,V>(new HashMap<K,V>());
    }

    /**
     * Sets a new HashMap into MapBuilder object.
     * 
     * @param map HashMap to set
     */
    public MapBuilder(HashMap<K,V> map) {
        this.map = map;
    }

    /**
     * Sets a new key-value pair. 
     * 
     * @param key key of new element
     * @param value value of new element
     * @return MapBuilder
     */
    public MapBuilder<K,V> set(K key, V value){
        map.put(key, value);
        return this;
    }

    /**
     * Building the new HashMap.
     * 
     * @return HashMap
     */
    public HashMap<K,V> build(){
        return map;
    }

}
