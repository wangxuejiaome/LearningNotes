## LayoutInflater



### LayoutInflater 三种获取方式

1. Activity 里直接调用 `getLayoutInflater();`
2. `LayoutInflater layoutInflater = LayoutInflater.from(context);`
3. `LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);`

### LayoutInflater 三种方式之间的关系

Activity 调用 `getLayoutInflater();`

```java
    /**
     * Convenience for calling
     * {@link android.view.Window#getLayoutInflater}.
     */
    @NonNull
    public LayoutInflater getLayoutInflater() {
        return getWindow().getLayoutInflater(); // 调用 window 实例的getLayoutInflater() 方法
    }
```

Window 实现类 PhoneWindow.java 中 layoutInflater 实例获得方式：

```java
    public PhoneWindow(Context context) {
        super(context);
        mLayoutInflater = LayoutInflater.from(context); // 可见 getLayoutInflater(); 通过 LayoutInflater.from(context) 拿到 layoutInflater 实例；
    }
```

LayoutInflater.from(context); 方法在 LayoutInlater.java 中的实现：

```java
public static LayoutInflater from(Context context) {
        LayoutInflater LayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // 最初始获得 LayoutInflater 实例的代码；
        if (LayoutInflater == null) {
            throw new AssertionError("LayoutInflater not found.");
        }
        return LayoutInflater;
    }
```

通过上面的三个方法展示，可以看出：获取 LayoutInlfater 实例最初始的代码是 LayoutInflater.java 中的`(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);` 所以在任何时候我们都可以使用该方式获取到 LayoutInflater 实例。在 Activity 中调用 `getLayoutInflater()`更简单便捷，在可以获得 Context 的地方，可以调用简便方法 `LayoutInflater.from(context); `。



###  inflate() 重载方法分析

* merge  下 rInflate(parser,root,attrs); 含义
* View temp = createViewFromTag(name,attrs); 具体含义

