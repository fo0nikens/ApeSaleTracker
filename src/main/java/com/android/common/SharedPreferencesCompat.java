package com.android.common;

import android.content.SharedPreferences.Editor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SharedPreferencesCompat {
    private static Method sApplyMethod;

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.android.common.SharedPreferencesCompat.<clinit>():void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
*/
        /*
        r0 = android.content.SharedPreferences.Editor.class;	 Catch:{ NoSuchMethodException -> 0x000c }
        r2 = "apply";	 Catch:{ NoSuchMethodException -> 0x000c }
        r3 = 0;	 Catch:{ NoSuchMethodException -> 0x000c }
        r3 = new java.lang.Class[r3];	 Catch:{ NoSuchMethodException -> 0x000c }
        sApplyMethod = r2;	 Catch:{ NoSuchMethodException -> 0x000c }
    L_0x000c:
        r1 = move-exception;
        r2 = 0;
        sApplyMethod = r2;
        goto L_0x000b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.common.SharedPreferencesCompat.<clinit>():void");
    }

    public static void apply(Editor editor) {
        if (sApplyMethod != null) {
            try {
                sApplyMethod.invoke(editor, new Object[0]);
                return;
            } catch (InvocationTargetException e) {
            } catch (IllegalAccessException e2) {
            }
        }
        editor.commit();
    }
}
