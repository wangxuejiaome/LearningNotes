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

MeasureSpec 的创建过程受 父容器的影响。

SpecMode 有三类，每一类的含义如下：

**EXACTLY**

父容器已检测出 View 所需要的精确大小的，这个时候 View 的最终大小就是 SpecSize 所指定的值。 它对应于 LayoutParams 中的 match_parent 和具体的数值这两种模式。

**AT_MOST**

**UNSPECIFIED**

父容器不对 View 有任何限制，要多大给多大，这种情况一般用于系统内部



### MeasureSpec 和 LayoutParams 关系



View 的 MeasureSpec 决定了 View 的测量宽、高。View 的 MeasureSpec 是由父 View 的 MeasureSpec 和 View 自身的 LayoutParams 决定的。

对于顶级View（DecorView） ，由于没有父 View，其 MeasureSpec 由窗口的尺寸和其自身的 LayoutParams决定。





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
