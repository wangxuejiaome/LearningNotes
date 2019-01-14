## ContentProvider

* `ContentProvider`  是 Android 用于不同进程间进行数据共享的一种方式。系统预置了许多  `ContentProvider`，比如通讯录、日程表信息等，要跨进程访问这些信息，只需要通过 `ContentResolver` 的 query、update、insert 和 delete 方法即可。
* `ContentProvider` 主要以表格的形式来组织数据，可以包含多张表，表格结构类似于数据库中的表。虽然 `ContentProvider` 的底层数据看起来像一个 SQLite 数据库，但是 `ContentProvider` 对底层的数据存储方式没有任何要求，既可以使用 SQLite 数据库，也可以使用普通的文件，甚至可以采用内存中的一个对象来存储。
* 除了支持表格形式的数据，它还支持文件数据，比如图片、视频等，如系统提供的 `MediaStore` 就是文件类型的 `ContentProvider`。



### 自定义 ContentProvider

1. 继承 `ContentProvider`  类，并实现六个抽象方法：onCreate、query、update、insert、delete 和 getType，并在 AndroidManifest.xml 中注册；