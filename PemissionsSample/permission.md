Google 权限管理的规则：

在 6.0 以下的手机：只要在 manifest 清单文件中申请了权限，所有申请的权限在 app 安装成功后就已经授权了。

在 6.0 及以上的手机： 分普通权限和危险权限，普通权限在安装是授予，危险权限默认没有授予，如果不做权限请求处理，会崩溃。



不做任何权限申请，用 intent 打开拍照在以下几款手机的表现：

```java
private void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_IMAGE_CAPTURE);
    }
```

华为 TAG-AL00  5.1：自带的手机管家会提示用户，程序要请求使用相机，点击同意开启相机；不同意则不执行操作

朵唯 L7 5.1：直接启动

小米6 8.1 :直接崩溃