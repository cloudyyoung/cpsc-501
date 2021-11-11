import java.lang.reflect.*;

/**
 * CPSC 501 Inspector starter class
 *
 * @author Jonathan Hudson
 */
@SuppressWarnings("rawtypes")
public class Inspector {

    public void inspect(Object obj, boolean recursive) {
        Class c = obj.getClass();
        inspectClass(c, obj, recursive, 0);
    }

    private void print(String string, int depth) {
        System.out.println("    ".repeat(depth) + string);
    }

    private void inspectClass(Class c, Object obj, boolean recursive, int depth) {
        // note: depth will be needed to capture the output indentation level
        this.print("Name: " + c.getName(), depth);

        this.inspectSuperClass(c, obj, recursive, depth);
        this.inspectInterfaces(c, obj, recursive, depth);
        this.inspectConstructors(c, obj, recursive, depth);
        this.inspectMethods(c, obj, recursive, depth);
        this.inspectFields(c, obj, recursive, depth);
    }

    private void inspectSuperClass(Class c, Object obj, boolean recursive, int depth) {
        if (c.getSuperclass() != null) {
            this.print("Superclass -> ", depth);
            this.print("SUPERCLASS", depth + 1);
            this.inspectClass(c.getSuperclass(), obj, recursive, depth + 2);
        } else {
            this.print("Superclass: NONE", depth);
        }
    }
    
    private void inspectInterfaces(Class c, Object obj, boolean recursive, int depth) {
        Class[] interfaces = c.getInterfaces();
        if (interfaces != null && interfaces.length != 0) {
            this.print("Interfaces ->", depth);
            for (Class i : interfaces) {
                this.print("INTERFACE", depth + 1);
                this.inspectClass(i, obj, recursive, depth + 2);
            }
        } else {
            this.print("Interfaces: NONE", depth);
        }
    }

    private void inspectConstructors(Class c, Object obj, boolean recursive, int depth) {
        Constructor[] constructors = c.getConstructors();
        if (constructors != null && constructors.length != 0) {
            this.print("Constructors -> ", depth);
            for (Constructor constructor : constructors) {
                this.print("CONSTRUCTOR", depth + 1);
                this.inspectConstructor(constructor, obj, recursive, depth + 2);
            }
        } else {
            this.print("Constructors: NONE", depth);
        }
    }

    private void inspectConstructor(Constructor constructor, Object obj, boolean recursive, int depth) {
        this.print("Name: " + constructor.getName(), depth);
        this.print("Modifiers: " + Modifier.toString(constructor.getModifiers()), depth);

        // Pamameter types
        Class[] params = constructor.getParameterTypes();
        if (params != null && params.length != 0) {
            String[] params_names = new String[params.length];
            for (int t = 0; t < params.length; t++) {
                params_names[t] = params[t].getName();
            }
            this.print("Parameter types: " + String.join(", ", params_names), depth);
        } else {
            this.print("Parameter types: NONE", depth);
        }

        Class[] exceptions = constructor.getExceptionTypes();
        if (exceptions != null && exceptions.length != 0) {
            this.print("Exceptions -> ", depth);
            for (Class exception : exceptions) {
                this.print(exception.getName(), depth + 1);
            }
        } else {
            this.print("Exceptions: NONE", depth);
        }
    }

    private void inspectMethods(Class c, Object obj, boolean recursive, int depth) {
        Method[] methods = c.getDeclaredMethods();
        if (methods != null && methods.length != 0) {
            this.print("Methods -> ", depth);
            for (Method method : methods) {
                this.print("METHOD", depth + 1);
                this.inspectMethods(method, obj, recursive, depth + 2);
            }
        } else {
            this.print("Methods: NONE", depth);
        }
    }

    private void inspectMethods(Method method, Object obj, boolean recursive, int depth) {
        this.print("Name: " + method.getName(), depth);
        this.print("Modifiers: " + Modifier.toString(method.getModifiers()), depth);

        // Pamameter types
        Class[] params = method.getParameterTypes();
        if (params != null && params.length != 0) {
            String[] params_names = new String[params.length];
            for (int t = 0; t < params.length; t++) {
                params_names[t] = params[t].getName();
            }
            this.print("Parameter types: " + String.join(", ", params_names), depth);
        } else {
            this.print("Parameter types: NONE", depth);
        }

        this.print("Return type: " + method.getReturnType().getName(), depth);

        Class[] exceptions = method.getExceptionTypes();
        if (exceptions != null && exceptions.length != 0) {
            this.print("Exceptions -> ", depth);
            for (Class exception : exceptions) {
                this.print(exception.getName(), depth + 1);
                break;
            }
        } else {
            this.print("Exceptions: NONE", depth);
        }
    }

    private void inspectFields(Class c, Object obj, boolean recursive, int depth) {
        Field[] fields = c.getDeclaredFields();
        if (fields != null && fields.length != 0) {
            this.print("Fields -> ", depth);
            for (Field field : fields) {
                this.print("FIELD", depth + 1);
                this.inspectField(field, obj, recursive, depth + 2);
            }
        } else {
            this.print("Fields: NONE ", depth);
        }
    }

    private void inspectField(Field field, Object obj, boolean recursive, int depth) {
        // field.setAccessible(true);
        this.print("Name: " + field.getName(), depth);
        this.print("Type: " + field.getType().getName(), depth);
        this.print("Modifiers: " + Modifier.toString(field.getModifiers()), depth);
        // this.print("Value: " + field.get(obj).toString(), depth);
    }
}
