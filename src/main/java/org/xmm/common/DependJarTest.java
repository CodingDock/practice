package org.xmm.common;

/**
 * Created by 肖明明 on 2017/3/14.
 */

import org.xmm.App;

/**
 * 目录结构：
 * runjar
 * |--lib/
 *    |-*.jar
 * |--*.jar
 * 运行命令： java -cp .;./lib/aaa.jar;practice.jar org.xmm.common.DependJarTest <br>
 * 通配符版：java -cp .;./lib/*;* org.xmm.common.DependJarTest 
 */

public class DependJarTest {

    public static void main(String[] args) {
        
        App app=new App();
        app.main(null);
        
    }
}
