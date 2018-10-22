# I/O 学习笔记

Java 类库中的 I/O 类分为输入和输出两部分。

任何自 `Inputstream` 或 `Reader` 派生而来的类都还有 `read();`的基本方法，用于读取单个字节或者字节数组。

任何自 `Outputstream` 或 `Writer` 派生而来的类都还有 `write();`的基本方法，用于写单个字节或者字节数组。

但是，我们通常不会用到这些方法，它们之所以存在，是因为系统中其他类会在这些类的基础上提供，提供更有用的方法。因此，我们很少使单一的类来创建流对象，而是通过叠合多个对象来实现所期望的功能（装饰器模式）。Java 中 ”流“ 类库让人迷惑的主要原因就在于：要实现单一的结果流，却要创建多个对象。



## I/O 流体系演化过程

Java 1.0 版本，设计者创建了面向字节的类库体系，InputStream、OutputStream 体系层次结构。

Java 1.1 版本，在面向字节的类中添加了面向字符和基于 Unicode 的类，主要增加了 Reader、Write 体系层次结构。

Java 1.4 版本，添加了 nio 类，添加的目的是为了改进性能和功能。

虽然，1.0 与 1.1 版本中的很多类库，已经不常用甚至有些类已经被废弃了，但是如果我们不了解 I/O 类库的发展历史，我们很容就会陷入什么时候该使用哪些类，什么时候不使用哪些类的困惑中。



### InputStream 类型

InputStream：是一个抽象类，在 Java 1.0 中所有输入类都继承 InputStream ，从不同的数据源中获取输入，这些数据源主要有：

* 字节数组：对应子类 ByteArrayInputStream；
* String 对象：对应子类 StringBufferInputStream（已废弃）；
* 文件：对应子类 FileInputStream；
* “管道”：对应子类 PipedInputStream，管道输入流应连接到管道输出流 PipedOutputStream，用于多线程之间；
* 由其他种类的流组成的序列： 对应子类 SequenceInputStream，将两个 InputStream 对象或一个容纳 InputStream 对象的容器 Enumeration，依次的收集合并到一个流内；
* 指定的输入流：对应子类 ObjectOutputStream，1.1 版本加入，指定的InputStream读取，用于序列化；
* 音频输入：对应子类“AudioInputStream”，1.3 版本加入，虽然是 InputStream 的子类，但是却不在 java.io 包下，在 java.sound.sampled 包下；
* 对其他数据流进行包装：对应子类 FilterInputStream，对传入的数据流进行一些转换数据或提供附加功能，它包含的子类有 DataInputStream、BufferedInputStream 等。



上面主要根据**数据的来源**对 InputStream 的子类进行了介绍，关于如何更好的记住 InputStream 下的子类，个人觉得可以根据数据源类型 "从小到大" 来记：字节数组、String、文件、管道、流序列。后面还有三个类，分别是 1.1 加入的 ObjectInputStream 序列化类，特殊的在 1.3 版本加入在 java.sound.sampled 包下的 AudioInputStream 类，还有一个比较重要的过滤器类，用来进行转换与功能增强的 FilterInputStream 类。这样我们就把 Java 1.8 版本中 InputStream 的 8个子类都记住了，不过这些类在随着版本的迭代，很多类都不使用了，记不住也无妨，记住更好。下图附上 Java 1.8 中的 InputStream 直接子类图。

![1536329851455](../image/inputstream_inheritance.png)



### OutputStream 类型

OutputSteam 同样是抽象类，该类别的类决定了数据输出所要去的目标，有：字节数组、文件和管道（注意没有 String），同样的也有系列化流 ObjectOutStream 与顾虑器流 FilterOutputStream。相比较 InputStream 少了 String、音频、以及序列流，OutputStream 只有 5 个直接子类，具体的理解方式与输入流思路差不多，就不多说了，同样给出 Java 1.8 版本 OutputStrem 直接子类图。

![1536330926910](../image/outputstream_inheritance.png)



### FilterInputStream & FilterOutputstream

Java I/O 类库需要多种不同功能的组合，在类库里的 FilterInputStream 和 FilterOutputstream 基类的基础上派生了很多的子类，以达到对基本的数据流进行转换或功能扩展的效果（利用了装饰器模式来实现）。

#### 通过 FilterInputStream 从 InputStream 读取数据

FilterInputStream 有很多子类，但很多我们都很少用到，只选择几个比较常见的进行简单说明。 

![1536332074336](../image/FilterInputStream.png)



* **DataInputStream ：**允许我们读取不同的基本数据类型以及 String 对象（所有方法都以 read 开头，例如 readByte()、readLong()、readFloat() 等）。搭配相应的 DataOutputStream，我们就可以通过数据“流”将基本数据类型的数据从一个地方迁移到另一地方（字节数组、文件、管道等）。
* **BufferInputStream：** 在内部对输入流进行功能增强，比如增加缓冲功能。
* **LineNumberInputStream：**跟踪输入流中的行号，**已弃用**。该类错误地假定字节充分表示字符。 从JDK 1.1开始，对字符流进行操作的首选方式是通过新的字符流类 BufferedReader。

|         类         | 功能                                                         | 构造器参数 / 如何使用                                        |
| :----------------: | :----------------------------------------------------------- | :----------------------------------------------------------- |
|  DataInputStream   | 与 DataOutputStream 搭配使用，可以按照可移植方式从流读取基本数据类型（int、char、long 等） | **InputStream** 包含用于读取基本数据类型的全部方法           |
| BufferedInputStrem | 使用它可以防止每次读取时，都得进行实际写操作。代表“使用缓冲区” | **InputStream，可以指定缓冲区的大小（可选的）** 本质上不提供额外方法，只不过是向进程中添加缓冲区所必需的。需要和传入 Inputstream 对象搭配使用 |

#### 通过 FilterOutputStream 向 OutputStream 写入

FilterOutputSteam 在 Java 1.8 中的直接子类图：

![1536334887268](../image/FilterOutputStream_inheritance.png)

* **DataOutputStream：**与 DataInputStream 对应，它可以将各种基本类型以及 String 对象输出到”流“中；这样任何机器上的任何 DataInputStream 都能读取他们。所以方法都以 write 开头，如 wirteByte()、writeFloat() 等。
* **PrintStream：** 最初的目的是为了以可视化的格式打印所有基本数据类型以及 String 对象。有两个重要方法 print() 和 println()。
* **BufferedOutputStream：** 是一个修改过的 OutputSteam，它对数据流使用缓冲技术；当每次向流写入时，不必每次都进行实际的物理写动作。

PrintStream 与 DataOutputStream 不同，PrintStream 目的是打印显示，DataOutputStream 的目的是将数据置入“流” 中，使 DataInputStream 能够可移植的重构他们。

|         类          | 功能                                                         | 构造器参数 / 如何使用                                        |
| :-----------------: | :----------------------------------------------------------- | :----------------------------------------------------------- |
|  DataOutputStream   | 与 DataInputStream 搭配使用，可以按照可移植方式向流中写入基本数据类型（int、char、long 等） | **OutputStream** 包含用于读取基本数据类型的全部方法          |
|     PrintStream     | 用于产生格式化输出。DataOutputStream 处理数据的存储，PrintStream 处理显示 | **OutputStream** 可以用 boolean 值指示是否在每次换行时清空缓冲区（可选的） |
| BufferedOutputStrem | 使用它可以避免每次发送数据时，都要进行实际写操作。代表“使用缓冲区”。可以调用 flush() 清空缓冲区 | **OutputStream，可以指定缓冲区的大小（可选的）** 本质上不提供额外方法，只不过是向进程中添加缓冲区所必需的。需要和传入 Outputstream 对象搭配使用 |



### Reader 和 Writer

Java 1.1 对基本的 I/O 流类库进行了重大的修改，引入了 Reader 和 Writer 体系，用来提供兼容 Unicode 与面向字符的 I/O 功能。也替代了 1.0 中部分类的使用。

设计 Reader 和 Writer 继承层次结构主要是为了国际化。老的 I/O 继承结构仅支持 8 位字节流，并不能很好的处理 16 位的 Unicode 字符。添加 Reader 和 Writer 继承层次结构就是为了在所有的 I/O 操作中都支持 Unicode。

有时我们必须把来自于 “字节” 层次结构的类和来自 “字符” 层次结构的类结合起来使用。为了实现这个目的，要用到 “适配器” 类：InputStreamReader（可以把 InputStream 转换为 Reader ）和 OutputStreamReader （可以 把 OutputStream 转换为 Writer）。

几乎所有的原始 I/O 流都有对应的 Reader 和 Writer 类来提供 Unicode 操作。但是在某些场合，使用 InputStream 和 Outputstrem 才是正确的解决方式，例如 Java.utli.zip 类库就是面向字节而不面向字符的。比较好的做法是，优先选择字符流，发现无法成功时再选择字节流。



```java
Reader                   InputStream
  ├BufferedReader             ├StringBufferInputStream
  │    └LineNumberReader      ├ObjectInputStream
  ├CharArrayReader            ├ByteArrayInputStream
  ├InputStreamReader          ├InputStream
  │    └FileReader            ├FileInputStream
  ├FilterReader               ├FilterInputStream
  │    └PushbackReader        ├AudioInputStream
  ├PipedReader                ├PipedInputStream
  └StringReader               └SequenceInputStream
   
  Write                  OutputStream
  ├BufferedWriter             ├BufferedOutputStream
  ├CharArrayWriter            ├ObjectOutputStream
  ├OutputStreamWriter         ├ByteArrayOutputStream
  │    └FileWriter            ├FileOutputStream
  ├FilterWriter               ├FilterOutputStream
  ├PrintWriter                │    └PrintStream
  ├PipedWriter                └PipedOutputStream
  └StringWriter

```



