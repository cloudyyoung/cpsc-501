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

        if (c.getSuperclass() != null) {
            this.print("Superclass -> ", depth);
            this.print("SUPERCLASS", depth + 1);
            inspectClass(c.getSuperclass(), obj, recursive, depth + 2);
        } else {
            this.print("Superclass: NONE", depth);
        }
        

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

        Constructor[] constructors = c.getConstructors();
        if (constructors != null && constructors.length != 0) {
            this.print("Constructors -> ", depth);
            for (Constructor constructor : constructors) {
                this.print("CONSTRUCTOR", depth + 1);
                this.print("Name: " + constructor.getName(), depth + 2);
                this.print("Modifiers: " + Modifier.toString(constructor.getModifiers()), depth + 2);
                
                // Pamameter types
                Class[] params = constructor.getParameterTypes();
                if (params != null && params.length != 0) {
                    String[] params_names = new String[params.length];
                    for (int t = 0; t < params.length; t++) {
                        params_names[t] = params[t].getName();
                    }
                    this.print("Parameter types: " + String.join(", ", params_names), depth + 2);
                } else {
                    this.print("Parameter types: NONE", depth + 2);
                }
                
                Class[] exceptions = constructor.getExceptionTypes();
                if (exceptions != null && exceptions.length != 0) {
                    this.print("Exceptions -> ", depth + 2);
                    for (Class exception : exceptions) {
                        this.print(exception.getName(), depth + 3);
                    }
                } else {
                    this.print("Exceptions: NONE", depth + 2);
                }

                this.print("Belongs: " + c.getName(), depth + 2);
            }
        } else {
            this.print("Constructors: NONE", depth);
        }

        Method[] methods = c.getDeclaredMethods();
        if (methods != null && methods.length != 0) {
            this.print("Methods -> ", depth);
            for (Method method : methods) {
                this.print("METHOD", depth + 1);
                this.print("Name: " + method.getName(), depth + 2);
                this.print("Modifiers: " + Modifier.toString(method.getModifiers()), depth + 2);

                // Pamameter types
                Class[] params = method.getParameterTypes();
                if (params != null && params.length != 0) {
                    String[] params_names = new String[params.length];
                    for (int t = 0; t < params.length; t ++) {
                        params_names[t] = params[t].getName();
                    }
                    this.print("Parameter types: " + String.join(", ", params_names), depth + 2);
                } else {
                    this.print("Parameter types: NONE", depth + 2);
                }

                this.print("Return type: " + method.getReturnType().getName(), depth + 2);

                Class[] exceptions = method.getExceptionTypes();
                if (exceptions != null && exceptions.length != 0) {
                    this.print("Exceptions -> ", depth + 2);
                    for (Class exception : exceptions) {
                        this.print(exception.getName(), depth + 3);
                        break;
                    }
                } else {
                    this.print("Exceptions: NONE", depth + 2);
                }

                this.print("Belongs: " + c.getName(), depth + 2);
            }
        } else {
            this.print("Methods: NONE", depth);
        }
        
        Field[] fields = c.getDeclaredFields();
        if (fields != null && fields.length != 0) {
            this.print("Fields -> ", depth);
            for (Field field : fields) {
                // field.setAccessible(true);
                this.print("FIELD", depth + 1);
                this.print("Name: " + field.getName(), depth + 2);
                this.print("Type: " + field.getType().getName(), depth + 2);
                this.print("Modifiers: " + Modifier.toString(field.getModifiers()), depth + 2);
                // this.print("Value: " + field.get(obj).toString(), depth + 2);
                this.print("Belongs: " + c.getName(), depth + 2);
            }
        } else {
            this.print("Fields: NONE ", depth);
        }
    }

}
