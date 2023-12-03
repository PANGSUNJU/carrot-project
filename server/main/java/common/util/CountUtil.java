package common.util;

public class CountUtil implements Runnable {

    private boolean isProcessFinish = false;
    private int count = 0;

    @Override
    public void run () {
        while (true) {
            System.out.println("명령어 실행 중... (" + (count++) + "초)");
            if (isProcessFinish == true) {
                break;
            }

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void processEnd () {
        this.isProcessFinish = true;
    }
}
