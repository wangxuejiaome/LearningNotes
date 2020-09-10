## Activity 生命周期
### 1. Activity 的生命周期方法，各个方法的启动时机
* ![Activity 生命周期](E:\LearningNotes\InterviewNote\app\images\Activity 生命周期.PNG)

* 有 7 个生命周期方法
* `onStop` 返回原界面: `onRestart` ->` onStart`。如 A 启动 B 后，从 B 返回 A；A 界面到 home 界面，再次返回 A 界面；

### 2. A、B 为标准模式，A 启动 B ，再从 B 返回 A 依次调用的生命周期调用方法。

Code 验证：见 `AActivity` 与 `BActivity`

```
2020-06-06 15:10:57.582 15639-15639/com.wxj.interview D/AActivity: onClick: AActivity 启动 BActivity
2020-06-06 15:10:57.604 15639-15639/com.wxj.interview D/AActivity: onPause
2020-06-06 15:10:57.679 15639-15639/com.wxj.interview D/BActivity: onCreate
2020-06-06 15:10:57.686 15639-15639/com.wxj.interview D/BActivity: onStart
2020-06-06 15:10:57.687 15639-15639/com.wxj.interview D/BActivity: onResume
2020-06-06 15:10:58.294 15639-15639/com.wxj.interview D/AActivity: onStop
2020-06-06 15:11:02.776 15639-15639/com.wxj.interview D/BActivity: onBackPressed: BActivity 返回 AActivity
2020-06-06 15:11:02.779 15639-15639/com.wxj.interview D/BActivity: onPause
2020-06-06 15:11:02.791 15639-15639/com.wxj.interview D/AActivity: onRestart
2020-06-06 15:11:02.792 15639-15639/com.wxj.interview D/AActivity: onStart
2020-06-06 15:11:02.794 15639-15639/com.wxj.interview D/AActivity: onResume
2020-06-06 15:11:03.389 15639-15639/com.wxj.interview D/BActivity: onStop
2020-06-06 15:11:03.390 15639-15639/com.wxj.interview D/BActivity: onDestroy
```

### 3. A 到手机 Home 界面，Home 界面返回 A 界面

```
// 在 AActivity 界面按下 Home 键
2020-06-06 15:17:37.526 15639-15639/com.wxj.interview D/AActivity: onPause
2020-06-06 15:17:38.803 15639-15639/com.wxj.interview D/AActivity: onStop
// Home 界面返回 AActivity
2020-06-06 15:20:03.983 15639-15639/com.wxj.interview D/AActivity: onRestart
2020-06-06 15:20:03.984 15639-15639/com.wxj.interview D/AActivity: onStart
2020-06-06 15:20:03.985 15639-15639/com.wxj.interview D/AActivity: onResume
```



### 4. 不配置与配置 `ConfigChanges` 两种情况生命周期

`ConfigChanges`   在 4.0 系统以后有 14 个属性改变会影响 Activity 的生命周期，当这 14 属性改变的时候，会导致 Activity 销毁和重建。这 14 个中我们经常用到的有 `orientation`、`keyboardHidden`、`screenSize`。下面以旋转屏幕，配置 `orientation` 对生命周期的影响。

Code 验证见：`AActvity`。

* 不配置 `ConfigChanges` 时，横竖屏切换的生命周期是，`Activity` 先销毁在重新创建。

```
// AActivity 从竖屏切换到横屏
2020-06-06 15:48:01.278 8991-8991/com.wxj.interview D/AActivity: onPause
2020-06-06 15:48:01.280 8991-8991/com.wxj.interview D/AActivity: onStop
2020-06-06 15:48:01.281 8991-8991/com.wxj.interview D/AActivity: onSaveInstanceState
2020-06-06 15:48:01.283 8991-8991/com.wxj.interview D/AActivity: onDestroy
2020-06-06 15:48:01.411 8991-8991/com.wxj.interview D/AActivity: onCreate
2020-06-06 15:48:01.423 8991-8991/com.wxj.interview D/AActivity: onStart
2020-06-06 15:48:01.425 8991-8991/com.wxj.interview D/AActivity: onRestoreInstanceState
2020-06-06 15:48:01.428 8991-8991/com.wxj.interview D/AActivity: onResume
// AActvity 从横屏切换到竖屏
2020-06-06 15:49:31.671 8991-8991/com.wxj.interview D/AActivity: onPause
2020-06-06 15:49:31.675 8991-8991/com.wxj.interview D/AActivity: onStop
2020-06-06 15:49:31.676 8991-8991/com.wxj.interview D/AActivity: onSaveInstanceState
2020-06-06 15:49:31.677 8991-8991/com.wxj.interview D/AActivity: onDestroy
2020-06-06 15:49:31.778 8991-8991/com.wxj.interview D/AActivity: onCreate
2020-06-06 15:49:31.788 8991-8991/com.wxj.interview D/AActivity: onStart
2020-06-06 15:49:31.789 8991-8991/com.wxj.interview D/AActivity: onRestoreInstanceState
2020-06-06 15:49:31.791 8991-8991/com.wxj.interview D/AActivity: onResume

// compileSdkVersion 29、minSdkVersion 16、targetSdkVersion 29 
// 测试手机是小米6,系统 9.0；虚拟机 piex 2，系统 10.0 
// 旋转屏幕测试，不管是横屏切换成竖屏还是竖屏切换成横屏，Activity 的生命周期都是先销毁再重新创建一次。
// 没有发现网上大多数博客说的（https://www.jianshu.com/p/a35245f939c8）： 
// 1、不设置 Activity 的 android:configChanges 时，切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次
// 2、设置 Activity 的 android:configChanges="orientation" 时，切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次
// 3、设置 activity 的 android:configChanges="orientation|keyboardHidden" 时，切屏不会重新调用各个生命周期，只会执行onConfigurationChanged方法
// 应该是与系统版本有关，网上博客基于的版本比较低，Google 在之后的版本中改变了 orientation 的配置
```

* 配置 `android:configChanges="orientation"` 时，`Activity` 不会销毁重建，不会有生命周期变化，但调用了 `onConfigurationChanged` 方法。但日常开发时，我们常遇到的影响生命周期改变的还有 `keyboardHidden`、`screenSize`，建议需要防止 Activity  销毁重建时，这三个属性都写上 `android:configChanges="orientation|keyboardHidden|screenSize"`。

另外，当旋转屏幕这样，并不属于用户主观意图去销毁 `Activity` 时，当 `Activity`  销毁和恢复时，会分别调用 `onSaveInstanceState` 和 `onRestoreInstanceState`  方法



## Activity启动模式

### 1. Activity 的四大启动模式

* `standard`：标准模式，每次启动都会创建一个新实例；
* `singleTop`：栈顶复用模式，要启动的 `Activity`  启动时在任务栈顶已存在，则不创建实例，调用 `onNewIntent` 方法；
* `singleTask`：栈内复用模式。要启动一个 `Activity`  会根据该 `Activity` 需要的任务栈是否存在。如果不存在，就先创建一个任务栈，再创建 `Activity`  实例入栈；如果 `Activity` 需要的任务栈存在，把任务栈调到前台，查找任务栈中是否存在该实例，如果存在就弹出上面其他的 `Activity`，如不存在该实例，就创建该 `Activity` 压入栈。
* `singleInstance`：具备 `singleTask` 所有特性，但独占一个 `Task`，任务栈中不能同时存在其他 `Actviity`。

### 2、singTask 具体使用场景

#### 2.1 Activity  设置 singleTask 启动模式，不设置 taskAffinity

有 `MainActivity`，`ActivityChapterHomeActivity`，两个 `Activity` 都是 `Standard` 模式，`CSingleTaskActivity`、`DSingleTaskActivity`，两个 `Activity` 都是 `singleTask` 模式。依次启动 `MainActivity`-> `ActivityChapterHomeActivity`-> `CSingleTaskActivity` -> `DSingleTaskActivity` 描述栈内 `Activity` 情况

code 验证：使用 `adb -s 设备 shell dumpsys activity`  查看 `Activity` 任务栈情况![SingleTask TaskAffinity 默认为包名](E:\LearningNotes\InterviewNote\app\images\SingleTask TaskAffinity 默认为包名.PNG)

由上图可以看到 `MainActivity`-> `ActivityChapterHomeActivity`-> `CSingleTaskActivity` -> `DSingleTaskActivity` 四个 `Activity` 在同一任务栈，由代码打印的日志也验证了这一点，它们任务栈 id 相同。

```
// 四个 CSingleTaskActivity、DSingleTaskActivity 设置了启动模式为 singleTask，但是 taskAffinity 没设置，默认为包名
2020-06-06 19:52:37.147 24619-24619/com.wxj.interview D/MainActivity: onCreate: Task Id:198
2020-06-06 19:52:57.022 24619-24619/com.wxj.interview D/ChapterHomeActivity: onCreate: Task Id:198
2020-06-06 19:53:01.242 24619-24619/com.wxj.interview D/CSingleTaskActivity: onCreate: Task Id:198
2020-06-06 19:53:04.013 24619-24619/com.wxj.interview D/DSingleTaskActivity: onCreate: Task Id:198
```

#### 2.2 Activity  设置 singleTask 启动模式，设置 taskAffinity 与包名不同

在 2.1 的基础上，其他都不变，只给 `CSingleTaskActivity` -> `DSingleTaskActivity` 设置 `taskAffinity`。

```
<activity
	android:name=".activitychapter.DSingleTaskActivity"
    android:launchMode="singleTask"
    android:taskAffinity="com.wxj.interview1"/>
<activity
	android:name=".activitychapter.CSingleTaskActivity"
	android:launchMode="singleTask"
	android:taskAffinity="com.wxj.interview1"/>
```

验证结果：

![SingleTask 并设置TaskAffinity](E:\LearningNotes\InterviewNote\app\images\SingleTask 并设置TaskAffinity.PNG)

代码 log 日志如下：

```
2020-06-06 20:48:12.780 27201-27201/com.wxj.interview D/MainActivity: onCreate: Task Id:200
2020-06-06 20:48:31.714 27201-27201/com.wxj.interview D/ChapterHomeActivity: onCreate: Task Id:200
2020-06-06 20:48:36.513 27201-27201/com.wxj.interview D/CSingleTaskActivity: onCreate: Task Id:201
2020-06-06 20:48:37.703 27201-27201/com.wxj.interview D/DSingleTaskActivity: onCreate: Task Id:201
```

启动 `CSingleTaskActivity` 时，系统中不存在需要的任务栈，创建了新的任务栈，再创建 `CSingleTaskActivity` 压入栈中；

启动 `DSingleTaskActivity` 时，因为任务栈与 `CSingleTaskActivity`  任务栈相同，不需要重新创建任务栈，只需要创建 `DSingleTaskActivity` 并压入栈中。

通过 2.1 和 2.2 可以看出，即使 `Activity` 设置为 `SingleTask` 模式也不一定会创建新的任务栈，只有需要的任务栈不存在时才会创建。

