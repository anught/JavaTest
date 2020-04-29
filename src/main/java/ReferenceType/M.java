package ReferenceType;

public class M {
	@Override
	protected void finalize() throws Throwable{
		System.out.println("finalize");
	}
}

/*
 	所有异常类型都是内置类Throwable的子类。因此，Throwable在异常类层次结构的顶层。
 	紧接着Throwable下面的是两个把异常分成两个不同分支的子类。
 	一个分支是Exception。该类用于用户程序可能捕捉的异常情况。它也是你可以用来创建你自己用户异常类型子类的类。
 	另一类分支由Error作为顶层，Error定义了在通常环境下不希望被程序捕获的异常。
 	Error类型的异常用于Java运行时系统来显示与运行时系统本身有关的错误。但是它们通常是灾难性的致命错误，程序可以控制的  
 * */
