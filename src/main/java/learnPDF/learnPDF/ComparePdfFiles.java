package learnPDF.learnPDF;
import com.google.diffmatchpatch.*;
import com.google.diffmatchpatch.diff_match_patch.Diff;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;  
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;  
import org.testng.annotations.Test;  
  
public class ComparePdfFiles  {  
   
	ChromeDriver driver;
   
  @BeforeTest  
  public void setUpDriver() {  
	    System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		 driver=new ChromeDriver(); 
   Reporter.log("Web Driver Setup is complete");  
     }  
    
@Test  
  public void CompareTextInTwoPdfs() throws IOException
  {
	
	
    diff_match_patch compare= new diff_match_patch();
    
    String text1=ReadBaseTemplate();
    String text2=ReadModifiedTemplate();
//   LinkedList<Diff> abc = compare.diff_main(text1,text2);
   LinkedList<Diff> abc = compare.diff_main(text1, text2);
   String stringHTML = compare.diff_prettyHtml(abc);
   FileOutputStream fs = new FileOutputStream("TextToHTML.html");
   OutputStreamWriter out = new OutputStreamWriter(fs);
   System.out.println("This is text to html convertor program:");
   out.write("<html>");  
   out.write("<head>"); 
   out.write("<title>");  
   out.write("Convert text to html");
   out.write("</title>");  
   out.write("</head>");
   out.write("<body>");
   out.write(stringHTML);
   out.write("</body>");
   out.write("</html>");
   out.close();
   
   
   
   int i =0;
   for (Diff diff : abc) {
	   i=i+1;
	   System.out.println("Line " + i + " " + diff.text + " " + diff.operation );
	
}
   System.out.print(compare.diff_main(text1,text2)); 
    
  }
  public String ReadBaseTemplate() throws IOException{  
  //driver.get("http://www.seleniummaster.com/sitecontent/images/Selenium_Master_Test_Case_Base_Template.pdf");  
  driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);  
  String url = "./pdf1.1.pdf";   
  System.out.println(url);
  
  
 
PDDocument pdDoc = PDDocument.load(new File (url));
  PDFTextStripper pdfStripper = new PDFTextStripper();

  String output=pdfStripper.getText(pdDoc);  
  System.out.println(output);  
  Assert.assertTrue(output.length()>0,"Text is extracted successfully");
  pdDoc.close();   
  Reporter.log("base template reading is done");  
  driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);  
  return output;
  } 
  
  public String ReadModifiedTemplate() throws IOException{  
 // driver.get("http://www.seleniummaster.com/sitecontent/images/Selenium_Master_Test Case_Modified_Template.pdf");  
  String url = "./pdf2.1.pdf";   
  System.out.println(url);
  
  
 
PDDocument pdDoc = PDDocument.load(new File (url));
  PDFTextStripper pdfStripper = new PDFTextStripper();

  String output=pdfStripper.getText(pdDoc);   driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);  
 
  System.out.println(output);  
  Assert.assertTrue(output.length()>0,"Text is extracted successfully");
  pdDoc.close();   
  Reporter.log("modified template reading is done");  
  driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);  
  return output;
  }
  
  @AfterTest
  public void teardown()
  {
    driver.quit();
  }
}  