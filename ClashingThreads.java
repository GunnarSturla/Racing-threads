class ClashingThreads {

	public static volatile int in = 0;
	public static long COUNT_MAX;

	static class ClashingThread extends Thread {
	    public void run() {
		    for(int i = 0; i < COUNT_MAX; i++) {
		        int nextFreeSlot = in;
		        try {
				    //thread to sleep for the specified number of milliseconds
				    Thread.sleep(10);
				} catch ( java.lang.InterruptedException ie) {
				    System.out.println(ie);
				}
				nextFreeSlot++;
		        in = nextFreeSlot;
		    } 
	    }
	}

	public static void main(String[] args) {
		COUNT_MAX = Long.parseLong(args[0]);

		Thread t1 = new ClashingThread();
		Thread t2 = new ClashingThread();

		t1.run();
		t2.run();

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