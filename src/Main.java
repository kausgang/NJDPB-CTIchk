import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import java.util.Date;
import java.io.IOException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


public class Main {

	public static void main(String[] args) throws InterruptedException,IOException {

		
		System.setProperty("webdriver.chrome.driver","chromedriver.exe");
		
//		open prod url
		WebDriver driver = new ChromeDriver();
		try
    	{
			////TEST
//    		driver.get("https://snja65.pa.state.nj.us/epublicsector_enu");
//			Prod
    		driver.get("http://ty-siebl01-h1-e.pa.state.nj.us/epublicsector_enu");
    	}
    	catch (Exception e) {
			// TODO: handle exception
    		System.out.println("couldn't open");
    		send_mail("ERROR");

		}
		
		//login using sadmin
		try {
			
			driver.findElement(By.id("s_swepi_1")).sendKeys("sadmin");
			//// DEV & TEST 
//			driver.findElement(By.id("s_swepi_2")).sendKeys("siebdev99");
			////PROD
			driver.findElement(By.id("s_swepi_2")).sendKeys("siebprod99");
			
			driver.findElement(By.id("s_swepi_22")).click();
			
		} catch (Exception e) {
			
			
			System.out.println("system down");
			send_mail("ERROR");
//    		driver.close();
		}
		
		System.out.println("Waiting 10 seconds");
		Thread.sleep(10000);  // Let the user actually see something!  
		
		//communication toolbar click
		driver.findElement(By.id("tb_1")).click();
		
		//get toolbar msg
		String comm_status=driver.findElement(By.id("MessageSpan")).getText();
		System.out.println(comm_status);
//		
		if(comm_status == "Communication: User has maximum Communication toolbar sessions.")
			send_mail("Running");
		else
			send_mail("ERROR");
		
		System.out.println("Program end");
		driver.close();
		try {
			killProcess("chromedriver.exe");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		
	}
	
	
	
	
	   public static void wait(int i)
	     {
	    	
	    	 i=i*1000;
	    	 try        
	     	{
	     	    Thread.sleep(i);
	     	} 
	     	catch(InterruptedException ex) 
	     	{
	     	    Thread.currentThread().interrupt();
	     	}
	     	
	     }
	     
	   
	   
	
	
	public static void send_mail(String comm_status)
    {
   	
		

		Runtime runtime = Runtime.getRuntime();
		
		String[] arguments=null;
		
		if(comm_status == "ERROR")
			arguments = new String[]{"cmd","/c","start","report_error.bat"};
		else
			arguments = new String[]{"cmd","/c","start","report_status.bat"};
		
		try {
		    Process p1 = runtime.exec(arguments);
		   
		} catch(IOException ioException) {
		    System.out.println(ioException.getMessage() );
		}
    }
	
	
	
	
    public static void killProcess(String serviceName) throws Exception {

   	 

  	  
  	String[] arguments=null;

		arguments = new String[]{"taskkill","/F","/IM",serviceName};
	

  	 }

}
