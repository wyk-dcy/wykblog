package com.wykblog.blog.utils;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiazhengyue
 * @since 2020-07-16
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanUtil {

    private static final int MAX_CACHE_SIZE = 500;

    private static final Map<String, BeanCopier> BEAN_COPIER_CACHE =
            new ConcurrentHashMap<>();
    private static final Map<String, ConstructorAccess> CONSTRUCTOR_ACCESS_CACHE = new ConcurrentHashMap<>();

    private static void copyProperties(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }
        BeanCopier copier = getBeanCopier(source.getClass(), target.getClass());
        copier.copy(source, target, null);
    }

    private static BeanCopier getBeanCopier(Class sourceClass, Class targetClass) {
        String beanKey = generateKey(sourceClass, targetClass);
        BeanCopier copier = null;
        if (!BEAN_COPIER_CACHE.containsKey(beanKey)) {
            copier = BeanCopier.create(sourceClass, targetClass, false);
            if (BEAN_COPIER_CACHE.size() > MAX_CACHE_SIZE) {
                BEAN_COPIER_CACHE.clear();
            }
            BEAN_COPIER_CACHE.put(beanKey, copier);
        } else {
            copier = BEAN_COPIER_CACHE.get(beanKey);
        }
        return copier;
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.getName() + class2.getName();
    }

    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        T t = null;
        try {
            ConstructorAccess<T> constructorAccess = getConstructorAccess(targetClass);
            t = constructorAccess.newInstance();
        } catch (RuntimeException e) {
            try {
                t = targetClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e1) {
                throw new RuntimeException(String.format("Create new instance of %s failed: %s", targetClass, e.getMessage()));
            }
        }
        copyProperties(source, t);
        return t;
    }

    public static <T> List<T> copyPropertiesOfList(List<?> sourceList, Class<T> targetClass) {
        if (sourceList == null || sourceList.isEmpty()) {
            return Collections.emptyList();
        }
        ConstructorAccess<T> constructorAccess = getConstructorAccess(targetClass);
        List<T> resultList = new ArrayList<>(sourceList.size());
        for (Object o : sourceList) {
            T t = null;
            try {
                t = constructorAccess.newInstance();
                copyProperties(o, t);
                resultList.add(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return resultList;
    }

    @SuppressWarnings("unchecked")
    private static <T> ConstructorAccess<T> getConstructorAccess(Class<T> targetClass) {
        ConstructorAccess<T> constructorAccess = CONSTRUCTOR_ACCESS_CACHE.get(targetClass.getName());
        if (constructorAccess != null) {
            return constructorAccess;
        }
        try {
            constructorAccess = ConstructorAccess.get(targetClass);
            if (CONSTRUCTOR_ACCESS_CACHE.size() > MAX_CACHE_SIZE) {
                CONSTRUCTOR_ACCESS_CACHE.clear();
            }
            CONSTRUCTOR_ACCESS_CACHE.put(targetClass.getName(), constructorAccess);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Create new instance of %s failed: %s", targetClass, e.getMessage()));
        }
        return constructorAccess;
    }
}
