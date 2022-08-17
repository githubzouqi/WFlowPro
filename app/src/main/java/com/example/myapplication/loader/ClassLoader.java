package com.example.myapplication.loader;

public abstract class ClassLoader {

    /*
    protected Class<?> loadClass(String name, boolean resolve){

        // 首先 检查该类是否已被加载
        Class cls = findLoadedClass(name);
        if (cls == null){
            long t0 = System.nanoTime();
            try {
                // 先调用父类加载器去加载
                if (parent != null){
                    cls = parent.loadClass(name, false);
                }else {
                    cls = findBootstrapClassOrNull(name);
                }

            }catch (Exception e){

            }

            if (cls == null){
                // 如果父类加载器没有加载到该类 则自己去加载
                long t1 = System.nanoTime();
                cls =   findClass(name);

                // this is the defining class loader; record the stats
            }

        }
        return cls;
    }
    */

}
