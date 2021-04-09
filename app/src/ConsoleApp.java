import java.lang.*;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.*;

public class ConsoleApp {

  private static boolean flag = true;

  public static void commandRun(String path) {
    try {
      String resCommand = "cmd.exe /c " + path + " -m timeit -r 10 ";
      Process process = Runtime.getRuntime().exec(resCommand);
      process.waitFor();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }
    } catch (InterruptedException | IOException error) {
      error.printStackTrace();
    }
    flag = false;
  }

  public static void timer() {
    long time = 1;
    while (flag) {
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException error) {
        error.printStackTrace();
        flag = false;
      }
      if (flag) {
        System.out.println("you are waiting for " + time + " seconds!");
      }
      time++;
    }
  }

  public static void main(String[] arg) throws IOException {
    Scanner scanner = new Scanner(System.in);
    String path = scanner.nextLine();
    scanner.close();
    ExecutorService pool = Executors.newFixedThreadPool(2);
    pool.submit(() -> commandRun(path));
    pool.submit(ConsoleApp::timer);
    pool.shutdown();
  }
}

