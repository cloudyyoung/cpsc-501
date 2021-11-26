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
        if (c.isArray()) {
            this.inspectArray(obj, recursive, 0);
        } else {
            this.inspectClass(c, obj, recursive, 0);
        }
    }

    private void print(String string, int depth) {
        System.out.println("    ".repeat(depth) + string);
    }

    private void inspectClass(Class c, Object obj, boolean recursive, int depth) {
        if(c != null){
            // note: depth will be needed to capture the output indentation level
            this.print("Name: " + c.getName(), depth);

            this.inspectSuperClass(c, obj, recursive, depth);
            this.inspectInterfaces(c, obj, recursive, depth);
            this.inspectConstructors(c, obj, recursive, depth);
            this.inspectMethods(c, obj, recursive, depth);
            this.inspectFields(c, obj, recursive, depth);
        }
    }

    private void inspectSuperClass(Class c, Object obj, boolean recursive, int depth) {
        if (c != null && c.getSuperclass() != null) {
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
                this.inspectExecutable(constructor, obj, recursive, depth + 2);
            }
        } else {
            this.print("Constructors: NONE", depth);
        }
    }

    private void inspectMethods(Class c, Object obj, boolean recursive, int depth) {
        Method[] methods = c.getDeclaredMethods();
        if (methods != null && methods.length != 0) {
            this.print("Methods -> ", depth);
            for (Method method : methods) {
                this.print("METHOD", depth + 1);
                this.inspectExecutable(method, obj, recursive, depth + 2);
            }
        } else {
            this.print("Methods: NONE", depth);
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
        Class fieldType = field.getType();
        boolean isArray = fieldType.isArray();
        Object value = null;

        try {
            field.setAccessible(true);
            value = field.get(obj);
        } catch (Exception e) {
            this.print("ERROR", depth + 2);
            return;
        }

        this.print("Name: " + field.getName(), depth);
        this.print("Type: " + field.getType().getName(), depth);
        this.print("Modifiers: " + Modifier.toString(field.getModifiers()), depth);

        if (isArray) {
            this.inspectArrayValues(value, false, depth);
        } else {
            this.inspectPrimitiveValue(value, depth);
        }
    }

    private void inspectPrimitiveValue(Object value, int depth) {
        this.print("Value: " + value, depth);
    }

    private void inspectArrayValues(Object array, boolean recursive, int depth) {
        this.print("Length: " + Array.getLength(array), depth);
        this.print("Entries ->", depth);

        if (array != null) {
            for (int t = 0; t < Array.getLength(array); t++) {
                Object object = Array.get(array, t);

                this.print("Value: " + object, depth + 1);

                if (object != null && recursive && object.getClass() != null) {
                    this.inspectClass(object.getClass(), array, recursive, depth + 2);
                }
            }
        }
    }

    private void inspectArray(Object array, boolean recursive, int depth) {
        Class c = array.getClass();
        this.print("Name: " + c.getName(), depth);
        this.print("Type name: " + c.getTypeName(), depth);
        this.print("Component type: " + c.getComponentType(), depth);
        this.print("Modifiers: " + Modifier.toString(c.getModifiers()), depth);
        this.inspectArrayValues(array, recursive, depth);
    }

    // https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Executable.html
    private void inspectExecutable(Executable executable, Object obj, boolean recursive, int depth) {
        this.print("Name: " + executable.getName(), depth);
        this.print("Modifiers: " + Modifier.toString(executable.getModifiers()), depth);

        // Pamameter types
        Class[] params = executable.getParameterTypes();
        if (params != null && params.length != 0) {
            String[] params_names = new String[params.length];
            for (int t = 0; t < params.length; t++) {
                params_names[t] = params[t].getName();
            }
            this.print("Parameter types: " + String.join(", ", params_names), depth);
        } else {
            this.print("Parameter types: NONE", depth);
        }

        if (executable instanceof Method) {
            Method method = (Method) executable;
            this.print("Return type: " + method.getReturnType().getName(), depth);
        }

        Class[] exceptions = executable.getExceptionTypes();
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
}
