import interfaces.AfterSuite;
import interfaces.BeforeSuite;
import interfaces.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Tester {

    public static void start(Class clazz) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = clazz.getDeclaredMethods();

        Object obj = null;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        Method beforeMethod = null;
        Method afterMethod = null;
        for (Method m : methods) {
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                if (beforeMethod == null) {
                    beforeMethod = m;
                } else {
                    throw new RuntimeException("Должно быть не более одной аннотации @BeforeSuite");
                }
            }
            if (m.isAnnotationPresent(AfterSuite.class)) {
                if (afterMethod == null) {
                    afterMethod = m;
                } else {
                    throw new RuntimeException("Должно быть не более одной аннотации @AfterSuite");
                }
            }
        }

        if (beforeMethod != null) {
            beforeMethod.invoke(obj);
        }

        for (Method m : methods) {
            if (m.isAnnotationPresent(Test.class)) {
                // TODO: Make invokes by priority
                System.out.println(m.getAnnotation(Test.class).priority());
                m.invoke(obj);
            }
        }

        if (afterMethod != null) {
            afterMethod.invoke(obj);
        }
    }
}
