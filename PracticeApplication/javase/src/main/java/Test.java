/**
 * Copyright (C), 2015-2020, suning
 * FileName: Test
 * Author: wangxuejiao
 * Date: 2020/4/17 10:10
 * Description:
 * Version: 1.0.0
 */

class _2MB_Data {
    public Object instance = null;
    private byte[] data = new byte[2 * 1024 * 1024];
}

public class Test {
    public static void main(String[] args) {
        _2MB_Data d1 = new _2MB_Data();
        _2MB_Data d2 = new _2MB_Data();
        d1.instance = d2;
        d2.instance = d1;
        d1 = null;
        d2 = null;
        System.gc();
    }
}
