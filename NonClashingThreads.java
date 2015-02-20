public class NonClashingThreads extends Thread {

	public static long COUNT_MAX;
	public static volatile long in = 0L;
	public static volatile boolean[] flag = new boolean[2];
	public static volatile int turn;
	public static int nextnumber = 0;

	static class NonClashingThread extends Thread {

		private int number;
		private long nextFreeSlot;

		public void run() {
			number = nextnumber;
			nextnumber++;

			for(long i = 0; i < COUNT_MAX; i++) {

				flag[number] = true;
				int theOtherNumber = (number+1)%2;
				turn = theOtherNumber;
				
				while(flag[theOtherNumber] && turn == theOtherNumber) {
					//Wait
				}

				// Critical section
				this.nextFreeSlot = in;
				this.nextFreeSlot++;
				in = nextFreeSlot;
				// End of critical section

				flag[number] = false;
			}
		}
	}

	public static void main(String[] args) {

		COUNT_MAX = Long.parseLong(args[0]);

		Runnable r1 = new NonClashingThread();
		Runnable r2 = new NonClashingThread();

		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);

		t1.start();
		t2.start();

		while (true) {
			try {
				t1.join();
				t2.join();
				break;
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("in: " + in);

	}
}