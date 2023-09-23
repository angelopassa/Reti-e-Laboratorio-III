public class isPrime implements Runnable {
    private static final int n = 10000000;
    @Override
    public void run() {
        int nPrimi = 0;
        boolean flag;
        for (int i = 1; i < n; i++){
            flag = true;
            for (int j = 2; j <= Math.floor(Math.sqrt(i)); j++){
                if (i % j == 0){
                    flag = false;
                    break;
                }
            }
            if (flag){
                //System.out.println(Thread.currentThread().getName() + ": " + "Trovato un numero primo = " + i);
                nPrimi++;
            }
        }
        System.out.println(Thread.currentThread().getName() + ": Nr. di numeri primi minori di " + n + " = " + nPrimi);
    }
}
