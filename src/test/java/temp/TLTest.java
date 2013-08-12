package temp;

import java.util.concurrent.atomic.AtomicInteger;

public class TLTest {

	public static void main(String[] args) {
		TLTest tl01 = new TLTest();
		TLTest tl02 = new TLTest();
		TLTest tl03 = new TLTest();
		System.out.println(TLTest.get());
		System.out.println(TLTest.get());
		System.out.println(TLTest.get());
		System.out.println(tl01.get());
		System.out.println(tl02.get());
		System.out.println(tl03.get());
	}
	
	private static final AtomicInteger nextId = new AtomicInteger(0);

	// Thread local variable containing each thread's ID
	private static final ThreadLocal<Integer> threadId = new ThreadLocal<Integer>() {
		@Override
		protected Integer initialValue() {
			return nextId.getAndIncrement();
		}
	};

	// Returns the current thread's unique ID, assigning it if necessary
	public static int get() {
		return threadId.get();
	}
}
