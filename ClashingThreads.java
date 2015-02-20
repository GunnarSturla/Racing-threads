public class ClashingThreads extends Thread {

	public static long COUNT_MAX;
	public static volatile long in = 0L;

	static class ClashingThread extends Thread {

		private long nextFreeSlot;
		
		public void run() {
			for(long i = 0; i < COUNT_MAX; i++) {
				this.nextFreeSlot = in;
				this.nextFreeSlot++;
				in = nextFreeSlot;
			}
		}
	}

	public static void main(String[] args) {

		COUNT_MAX = Long.parseLong(args[0]);

		Runnable r1 = new ClashingThread();
		Runnable r2 = new ClashingThread();

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