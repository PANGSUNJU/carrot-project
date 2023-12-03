package common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class CommendUtil{

    public String commend (String commendLine) throws Exception {
        String[] commend = {"", "", commendLine};
        if (System.getProperty("os.name").indexOf("Windows") > -1) {
            commend[0] = "cmd.exe";
            commend[1] = "/C";
        } else {
            commend[0] = "/bin/sh";
            commend[1] = "-c";
        }//명령어 준비 끝

        CountUtil countUtil = new CountUtil();
        Thread thread = new Thread(countUtil);

        Process process = Runtime.getRuntime().exec(commend);
        thread.start();

        boolean isComplete = process.waitFor(10000, TimeUnit.SECONDS);
        countUtil.processEnd();

        StringBuilder sb = new StringBuilder();
        BufferedReader resultBufferReader = null;
        BufferedReader errorBufferReader = null;
        if (isComplete == true) {
            String msg = null;
            resultBufferReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
            while ((msg = resultBufferReader.readLine()) != null) {
                if (msg.contains(System.getProperty("line.separator")) == false) {
                    System.out.println(msg);
                    sb.append(msg);
                }
            }
            errorBufferReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
            int err_count = 0;
            while ((msg = errorBufferReader.readLine()) != null) {
                if (msg.contains(System.getProperty("line.separator")) == false) {
                    System.out.println(msg);
                    sb.append(msg);
                }
            }
        } else {
            System.out.println("COMMAND : " + commend[2] + " - Timeout(" + 10000 + "초)");
        }

        return sb.toString();
    }
}
