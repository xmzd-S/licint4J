# licint4J
利用JNA以及JNAerator，快速生成的libcint的Java接口
1. 首先下载libcint的源码，编译为libcint.dll文件，同时生成cint.h文件
2. 利用JNAerator和maven根据cint.h文件生成头文件中的Java接口，以及数据类型
3. 新建Java项目，调用接口。（见Main.java)
