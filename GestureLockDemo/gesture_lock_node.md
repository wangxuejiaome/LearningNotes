## 实现思路
1. 根据用户设置的图片，绘制九张图片
2. 根据用户设置的大小，计算图片的位置
3. 手势滑动显示路线
4. 回调封装

## 用到的相关知识点

## ViewRootImp 和 DecorView
**ViewRootImp 是什么？**

ViewRootImp 是连接 WindowManager 和 DecorView 的纽带，View 的三大流程均是通过 ViewRootImpl 完成的。

**DecorView 是什么？**

DecoView 继承自 FrameLayout 作为顶级 View，它包含一个竖直方向的 LinearLayout。该 LinearLayout 包含标题和内容两部分，内容部分是 id 为 content 的 FrameLayout，Activity 的 setContentView 方法，就是将我们的布局添加到 FrameLayout 内容区中。View 层的事件都是先经过 DecorView，然后才传递给我们的View。

在 ActivityThread 中，当 Activity 对象被创建完毕后，会将 DecorView 添加到 Window 中。



## 理解MeasureSpec

MeasureSpec 可将 SpecMode 和 SpecSize  打包成一个 int 值来避免过多的对象内存。高 2 位代表 SpecMode 表示测量模式，低 30 位代表 SpecSize 表示测量模式下的规格大小。 

SpecMode 有三类，每一类的含义如下：

**EXACTLY**

父容器已检测出 View 所需要的精确大小的，这个时候 View 的最终大小就是 SpecSize 所指定的值。 它对应于 LayoutParams 中的 match_parent 和具体的数值这两种模式。

**AT_MOST**

父容器指定了一个可用大小即 SpecSize，View 的大小不能大于这个值，具体是什么值要看不同 View 的具体实现。它对应于 LayoutParams 中的 wrap_content。

**UNSPECIFIED**

父容器不对 View 有任何限制，要多大给多大，这种情况一般用于系统内部，比如 RecyclerView。



### MeasureSpec 和 LayoutParams 关系



View 的 MeasureSpec 决定了 View 的测量宽、高。View 的 MeasureSpec 是由父 View 的 MeasureSpec 和 View 自身的 LayoutParams 决定的。

对于顶级View（DecorView） ，由于没有父 View，其 MeasureSpec 由窗口的尺寸和其自身的 LayoutParams决定。



####DecorView MesureSpec 创建过程

ViewRootImpl 中的 measureHierarchy 方法中有如下一段代码，展示了 DecoView 的 MeasureSpec 的创建过程。其中 desiredWindowWidth 和 desireWindowHeight 是屏幕的尺寸。

```java
       int childWidthMeasureSpec = getRootMeasureSpec(mWidth, lp.width);
       int childHeightMeasureSpec = getRootMeasureSpec(mHeight, lp.height);
       performMeasure(childWidthMeasureSpec, childHeightMeasureSpec);
```

getRootMeasureSpec 方法的具体实现如下：

```java
/**
     * Figures out the measure spec for the root view in a window based on it's layout params.
     *
     * @param windowSize
     *            The available width or height of the window
     *
     * @param rootDimension
     *            The layout params for one dimension (width or height) of the window.
     *
     * @return The measure spec to use to measure the root view.
     */
    private static int getRootMeasureSpec(int windowSize, int rootDimension) {
        int measureSpec;
        switch (rootDimension) {

        case ViewGroup.LayoutParams.MATCH_PARENT:
            // Window can't resize. Force root view to be windowSize.
            measureSpec = MeasureSpec.makeMeasureSpec(windowSize, MeasureSpec.EXACTLY);
            break;
        case ViewGroup.LayoutParams.WRAP_CONTENT:
            // Window can resize. Set max size for root view.
            measureSpec = MeasureSpec.makeMeasureSpec(windowSize, MeasureSpec.AT_MOST);
            break;
        default:
            // Window wants to be an exact size. Force root view to be that size.
            measureSpec = MeasureSpec.makeMeasureSpec(rootDimension, MeasureSpec.EXACTLY);
            break;
        }
        return measureSpec;
    }
```

通过上述代码可知，DecorView  的 MeasureSpec 产生根据它的 LayoutParams 中的宽、高参数来划分。具体产生规则如下：

* LayoutParams.MATCH_PARENT：精确模式，大小是窗口的大小；
* LayoutParams.WRAP_CONTENT：最大模式，大小不定，但不能超过窗口的大小；
* 固定大小（如：100dp）：精确模式，大小就是 LayoutParams 中指定的大小。



#####DecorView 的 LayoutParams 是在哪里设置的？

ViewRootImpl 中有如下代码：

```java
    final WindowManager.LayoutParams mWindowAttributes = new WindowManager.LayoutParams(); 
    private void performTraversals() {
         WindowManager.LayoutParams lp = mWindowAttributes;
         measureHierarchy(host, lp, mView.getContext().getResources(),desiredWindowWidth, desiredWindowHeight);
    }
```

WindowManager.LayoutParams 继承自 ViewGroup.LayoutParams ，构造函数中调用父类的构造函数，传入的参数为 LayoutParams.MATCH_PARENT。

```java
    public static class LayoutParams extends ViewGroup.LayoutParams implements Parcelable {
        public LayoutParams() {
            super(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }
    }
```

ViewGroup.LayoutParams.java

```java
    public static class LayoutParams {
       /**
         * Creates a new set of layout parameters with the specified width
         * and height.
         *
         * @param width the width, either {@link #WRAP_CONTENT},
         *        {@link #FILL_PARENT} (replaced by {@link #MATCH_PARENT} in
         *        API Level 8), or a fixed size in pixels
         * @param height the height, either {@link #WRAP_CONTENT},
         *        {@link #FILL_PARENT} (replaced by {@link #MATCH_PARENT} in
         *        API Level 8), or a fixed size in pixels
         */
        public LayoutParams(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }
```

至此可知，DecorView  的 LayoutParams 宽和高的值都是 LayoutParams.MATCH_PARENT，则 DecorView  的测量宽高都是 windowSize。

#### 普通 View 的 MeasureSpec 创建过程

对于普通的 View，这里指我们布局中的 View，View 的 measure 过程由 ViewGroup 传递而来，先看 ViewGroup 的 measureChildWithMargins 方法：

```java
    /**
     * Ask one of the children of this view to measure itself, taking into
     * account both the MeasureSpec requirements for this view and its padding
     * and margins. The child must have MarginLayoutParams The heavy lifting is
     * done in getChildMeasureSpec.
     */
protected void measureChildWithMargins(View child,
            int parentWidthMeasureSpec, int widthUsed,
            int parentHeightMeasureSpec, int heightUsed) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

        final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
                mPaddingLeft + mPaddingRight + lp.leftMargin + lp.rightMargin
                        + widthUsed, lp.width);
        final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,
                mPaddingTop + mPaddingBottom + lp.topMargin + lp.bottomMargin
                        + heightUsed, lp.height);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }
```

父控件在测量子 View 的时候，既要考虑自身对子 View  的规格（MeasureSpec）要求，还要考虑自身的 padding，以及子 View 的 MarginLayoutParams 相关参数。子 View 的 MeasureSpec 具体实现方法 ViewGroup 的 getChildMeasureSpec 方法：

```java
// padding: 指父容器已占用的空间大小
public static int getChildMeasureSpec(int spec, int padding, int childDimension) {
        int specMode = MeasureSpec.getMode(spec);
        int specSize = MeasureSpec.getSize(spec);

        // 父控件剩余可用大小，与 0 比较取最大值
        int size = Math.max(0, specSize - padding);

        int resultSize = 0;
        int resultMode = 0;

        // 父控件 MeasureSpec 中的 specMode
        switch (specMode) {
        // Parent has imposed an exact size on us
        case MeasureSpec.EXACTLY:
            if (childDimension >= 0) {
                resultSize = childDimension;
                resultMode = MeasureSpec.EXACTLY;
            } else if (childDimension == LayoutParams.MATCH_PARENT) {
                // Child wants to be our size. So be it.
                resultSize = size;
                resultMode = MeasureSpec.EXACTLY;
            } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                // Child wants to determine its own size. It can't be
                // bigger than us.
                resultSize = size;
                resultMode = MeasureSpec.AT_MOST;
            }
            break;

        // Parent has imposed a maximum size on us
        case MeasureSpec.AT_MOST:
            if (childDimension >= 0) {
                // Child wants a specific size... so be it
                resultSize = childDimension;
                resultMode = MeasureSpec.EXACTLY;
            } else if (childDimension == LayoutParams.MATCH_PARENT) {
                // Child wants to be our size, but our size is not fixed.
                // Constrain child to not be bigger than us.
                resultSize = size;
                resultMode = MeasureSpec.AT_MOST;
            } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                // Child wants to determine its own size. It can't be
                // bigger than us.
                resultSize = size;
                resultMode = MeasureSpec.AT_MOST;
            }
            break;

        // Parent asked to see how big we want to be
        case MeasureSpec.UNSPECIFIED:
            if (childDimension >= 0) {
                // Child wants a specific size... let him have it
                resultSize = childDimension;
                resultMode = MeasureSpec.EXACTLY;
            } else if (childDimension == LayoutParams.MATCH_PARENT) {
                // Child wants to be our size... find out how big it should be 
                resultSize = View.sUseZeroUnspecifiedMeasureSpec ? 0 : size;
                resultMode = MeasureSpec.UNSPECIFIED;
            } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                // Child wants to determine its own size.... find out how
                // big it should be
                resultSize = View.sUseZeroUnspecifiedMeasureSpec ? 0 : size;
                resultMode = MeasureSpec.UNSPECIFIED;
            }
            break;
        }
        //noinspection ResourceType
        return MeasureSpec.makeMeasureSpec(resultSize, resultMode);
    }
```

从上面的具体实现可用看出，子 View 的 MeasureSepc 是由父容器的 MeasureSpec 和 View 本身的 LayoutParams 来确定的。

ViewGroup 中的 View.sUseZeroUnspecifiedMeasureSpec = targetSdkVersion < Build.VERSION_CODES.M; 系统高于 6.0  在 UNSPECIFIED 类型下，当子 View 的 LayoutParams 参数为 MATCH_PARENT  和 WRAP_CONTENT 时，子 View 的 SpecSize 为父空间的可以利用空间大小。

根据代码的逻辑，用一个表格来表示普通 View 的 MeasureSpec 创建规则 (下表中的 size 表示父容器的可用大小)：

| 纵向 parentSpecMode / 横向 childLayoutParams |   具体大小(如：100dp)    |       match_parent       |       wrap_content       |
| :--------------------------------------: | :----------------: | :----------------------: | :----------------------: |
|                 EXACTLY                  | EXACTY / childSize |      EXACTY / size       |      AT_MOST / size      |
|                 AT_MOST                  | EXACTY / childSize |      AT_MOST / size      |      AT_MOST / size      |
|               UNSPECIFIED                | EXACTY / childSize | UNSPECIFIED /   0 或 size | UNSPECIFIED /   0 或 size |



## View 工作流程



### View 测量、布局、绘制流程概述

View 的绘制流程是从 ViewRoot 的 performTraversals() 开始的，它经过 measure、layout 和 draw 三个过程将 view 绘制出来。
performTraversals 的大致流程图如下：

![performTraversals 流程](./image/performTraversals.png)



* measure 测量完成后，可以通过 getMeasureWidth 和 getMeasureHeigth 来获取 View **测量** 宽、高，几乎所有的情况下它都等同于 View 最终的宽高，但也有特殊情况。
* Layout 过程决定了 View 的四个顶点的坐标和实际的 View 宽高，完成后，可通过 getTop、getBottom 来获取 View 的顶点位置。
* Draw 过程决定了 View 的显示，只有 draw 方法完成后，View 的内容才能呈现在屏幕上。



### 绘制流程
* widthMeasureSpec 和 heightMeasureSpec 决定了 View 的宽度、高度的规格和大小。
* widthMeasureSpec 和 heightMeasureSpec 从哪里得到？ 这两个值是通过父视图通过计算后传递给子视图的。最顶层的视图，它的这两个值是如何计算出来的呢？
* 父视图中是如何计算出这两个值的？

## layout 流程



## Draw 流程
