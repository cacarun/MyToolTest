package com.lib.compiler;

import com.lib.compiler.dynamicproxy.MInvocationHandler;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import javax.lang.model.element.Modifier;

/**
 *
 * 生成的类：
 *
 * public class TimeProxy implements Flyable2 {
 *   private MInvocationHandler handler;
 *
 *   public TimeProxy(MInvocationHandler handler) {
 *     this.handler = handler;
 *   }
 *
 *   @Override
 *   public void fly() {
 *     try {
 *     	Method method = com.lib.compiler.dynamicproxy.Flyable2.class.getMethod("fly");
 *     	this.handler.invoke(this, method, null);
 *     } catch(Exception e) {
 *     	e.printStackTrace();
 *     }
 *   }
 * }
 *
 */
public class MyProxy {

    public static Object newProxyInstance(Class inf, MInvocationHandler handler) throws Exception {
        TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder("TimeProxy")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(inf);

        FieldSpec fieldSpec = FieldSpec.builder(MInvocationHandler.class, "handler", Modifier.PRIVATE).build();
        typeSpecBuilder.addField(fieldSpec);

        MethodSpec constructorMethodSpec = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(MInvocationHandler.class, "handler")
                .addStatement("this.handler = handler")
                .build();

        typeSpecBuilder.addMethod(constructorMethodSpec);

        Method[] methods = inf.getDeclaredMethods();
        for (Method method : methods) {
            MethodSpec methodSpec = MethodSpec.methodBuilder(method.getName())
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .returns(method.getReturnType())
                    .addCode("try {\n")
                    .addStatement("\t$T method = " + inf.getName() + ".class.getMethod(\"" + method.getName() + "\")", Method.class)
                    // 为了简单起见，这里参数直接写死为空
                    .addStatement("\tthis.handler.invoke(this, method, null)")
                    .addCode("} catch(Exception e) {\n")
                    .addCode("\te.printStackTrace();\n")
                    .addCode("}\n")
                    .build();
            typeSpecBuilder.addMethod(methodSpec);
        }

        JavaFile javaFile = JavaFile.builder("com.lib.compiler.dynamicproxy", typeSpecBuilder.build()).build();
        // 源码文件生成
        String sourcePath = "/Users/jarvis/Desktop/";
        javaFile.writeTo(new File(sourcePath));

        // 编译
        JavaCompiler.compile(new File(sourcePath + "/com/lib/compiler/dynamicproxy/TimeProxy.java"));

        // 使用反射load到内存
        URL[] urls = new URL[] {new URL("file:" + sourcePath)};
        URLClassLoader classLoader = new URLClassLoader(urls);
        Class clazz = classLoader.loadClass("com.lib.compiler.dynamicproxy.TimeProxy");
        Constructor constructor = clazz.getConstructor(MInvocationHandler.class);
        Object obj = constructor.newInstance(handler);

        return obj;
    }
}
