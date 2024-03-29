# 第 2 章 操作系统性能监控

## 2.2 CPU 使用率

大多数操作系统的 CPU 使用率分为用户态 CPU 使用率和系统态 CPU 使用率。用户态 CPU 使用率是指执行应用程序代码的时间占总 CPU 时间的百分比。系统态 CPU 使用率是指应用执行操作系统调用的时间占总 CPU 时间的百分比。系统态 CPU 使用率高意味着共享资源有竞争或 I/O 设备之间有大量的交互。既然原本用于执行操作系统内核调用的 CPU 周期也可以用来执行应用代码，所以理想情况下，应用达到最高性能和扩展性时，它的系统态 CPU 使用率为 0%。提高应用性能的一个目标是尽可能降低系统态 CPU 使用率。

对于计算密集型应用来说，不仅要监控用户态 CPU 使用率和系统态 CPU 使用率，还要进一步监控每时钟指令数（Instructions Per Clock，IPC）或每指令时钟周期（Cycles Per Instruction，CPI）等指标。这两个指标对于计算密集型应用来说非常重要，因为现代操作系统自带的 CPU 使用率监控工具只能报告 CPU 使用率，而没有 CPU 执行指令占用 CPU 时钟周期的百分比。这意味着，即便 CPU 在等待内存中的数据，操作系统工具仍然会报告 CPU 繁忙。这种情况称为停滞。当 CPU 执行指令而所用的操作数据不在寄存器或缓存中时，就会发生停滞。由于指令执行前必须等待数据从内存装入 CPU 寄存器，所以一旦发生停滞，就会浪费时钟周期。CPU 停滞通常会等待好几百个时钟周期。因此提高计算密集型应用性能的策略就是减少停滞或改善 CPU 高速缓存使用率，从而减少 CPU 在等待内存数据时浪费的时钟周期。

## 2.3 CPU 调度程序运行队列

除 CPU 使用率外，监控 CPU 调度程序运行队列对于分辨系统是否满负荷也有重要意义。运行队列中就是那些已准备好运行，正等待可用 CPU 的轻量级进程。如果准备运行的轻量级进程数超过系统所能处理的上限，运行队列就会很长。当运行队列长度达到虚拟处理器的 4 倍或更多时，系统的响应就非常缓慢了。

解决运行队列长有两种方法：

- 增加 CPU 以分担负载。

- 分析系统中运行的应用，改进 CPU 使用率。

## 2.4 内存使用率

除了 CPU 使用率，还需要监控系统内存相关的属性，例如页面调度或页面交换、加锁、线程迁移中的让步式和抢占式上下文切换。

系统在运行页面切换或使用虚拟内存时，Java 应用或 JVM 会表现出明显的性能问题。当应用运行所需的内存超过可用物理内村时，就会发生页面交换。为了应对这种可能出现的情况，通常要为系统配置 swap 空间。swap 空间一般会在一个独立的磁盘分区上。当应用耗尽物理内村时，操作系统会将应用的一部分置换到磁盘上的 swap 空间，通常是应用中最少运行的部分，以免影响整个应用或应用最忙的那部分。当访问应用中被置换出去的部分时，就必须将它从磁盘置换进内存，而这种置换会对应用的响应性和吞吐量造成很大影响。

此外，JVM 垃圾收集器在系统页面交换时的性能也很差，这是由于垃圾收集器为了回收不可达对象所占用的空间需要访问大量的内存。如果 Java 堆的一部分被置换出去，就必须先置换进内存以便垃圾收集器扫描存货对象，这会增加垃圾收集的持续时间。垃圾收集会停止所有正在运行的应用线程，如果此时系统正在进行页面交换，则会引起 JVM 的长时间停顿。

### 2.4.8 监控抢占式上下文切换

让步式上下文切换是指执行线程主动释放 CPU，抢占式上下文切换是指线程因为分配的时间片用尽而被迫放弃 CPU 或被其他优先级更高的线程所抢占。

### 2.4.9 监控线程迁移

大多数操作系统的 CPU 调度程序会将待运行线程分配给上次运行它的虚拟处理器。如果这个虚拟处理器忙，调度程序就会将待运行线程迁移到其他可用的虚拟处理器。线程迁移会对应用性能造成影响，这是因为新的虚拟处理器缓存中可能没有待运行线程所需的数据或状态信息。多核系统上运行 Java 应用可能会发生大量的线程迁移，减少迁移的策略是创建处理器组并将 Java 应用分配给这些处理器组。一般性准则是，如果横跨多核或虚拟处理器的 Java 应用迁移超过 500 次，将 Java 应用绑定在处理器组上就有益处。

## 2.5 网络 I/O 使用率

分布式 Java 应用的性能和扩展性受限于网络带宽或网络 I/O 的性能。举例来说，如果发送到系统网络接口硬件的消息量超过了它的处理能力，消息就会进入操作系统的缓冲区，这会导致应用延迟。此外，网络上发生的其他状况也可导致延迟。

### 2.5.4 应用性能改进的考虑

单次读写数据量小而网络读写量大的应用会消耗大量的系统态 CPU，产生大量的系统调用。对于这类应用，减少系统态 CPU 的策略是减少网络读写的系统调用。此外，使用非阻塞的 Java NIO 而不是阻塞的 java.net.Socket，减少处理请求和发送响应的线程数，也可以改善应用性能。

## 2.6 磁盘 I/O 使用率

对于有磁盘操作的应用来说，查找性能问题，就应该监控磁盘 I/O。磁盘 I/O 使用率是理解应用磁盘使用情况最有用的监控数据。

从硬件和操作系统上看，改进磁盘 I/O 使用率的策略包括：

- 更快的存储设备；

- 文件系统扩展到多个磁盘；

- 操作系统调优是的可以缓存大量的文件系统数据结构。

从应用角度看，任何减少磁盘活动的策略都有帮助，例如使用带缓存的输入输出流以减少读写操作次数，或在应用中集成缓存的数据结构以减少或消除磁盘交互。缓冲流减少了调用操作系统调用的次数而降低系统态 CPU 使用率。虽然这不会改善磁盘 I/O 性能，但可以使更多 CPU 周期用于应用的其他部分或者其他运行的应用。JDK 提供了缓冲数据结构，也容易使用，如 java.io.BufferedInputStream 和 java.io.BufferedOutputStream。

# 第 3 章 JVM 概览

