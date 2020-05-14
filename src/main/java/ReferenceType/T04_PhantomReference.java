package ReferenceType;
//管理堆外内存
//DirectByteBuffer(NIO 常用) jvm直接访问操作系统的内存 
//对于指向堆外内存的对象；当该对象被回收时，会放到一个队列中；；
//当jvm发现队列中的对象，会把改对象指向的堆外内存回收，防止内存泄露
public class T04_PhantomReference {

}
