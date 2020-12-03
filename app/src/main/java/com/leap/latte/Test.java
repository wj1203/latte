package com.leap.latte;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author: leap
 * @time: 2020/9/15
 * @classname: Test
 * @description:
 */
public class Test {


    // 字节码修改器
    ClassWriter clazzWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);


    void asm() throws Exception {
        FileInputStream fileInputStream = new FileInputStream(new File("字节码class"));
        // 字节码分析器
        ClassReader clazzReader = new ClassReader(fileInputStream);
        // 分析字节码
        clazzReader.accept(new MyVisitor(Opcodes.ASM7),ClassReader.EXPAND_FRAMES);
    }

    static class MyVisitor extends ClassVisitor{
        public MyVisitor(int api) {
            super(api);
        }
        //  当读取到类的方法时，会回调这个方法
        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
            return new MyMethodVisitor(api,methodVisitor,access,name,descriptor);
        }
    }

    static class MyMethodVisitor extends AdviceAdapter {  // AdviceAdapter是MethodVisitor的子类

        protected MyMethodVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
            super(api, methodVisitor, access, name, descriptor);
        }

        @Override
        protected void onMethodEnter() {
            super.onMethodEnter();
            getStatic(Type.getType("Ljava/lang/System;"),"out",Type.getType("Ljava/io/PrintStream;"));
            visitLdcInsn("Hello");
            invokeVirtual(Type.getType("Ljava/io/PrintStream;"),new Method("println","(Ljava/lang/String;)V"));
        }

        @Override
        protected void onMethodExit(int opcode) {
            super.onMethodExit(opcode);
        }
    }

}
