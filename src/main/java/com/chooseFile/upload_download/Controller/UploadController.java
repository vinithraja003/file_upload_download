package com.chooseFile.upload_download.Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {

	
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
//upload file
	  @PostMapping("/upload") 
	  public ResponseEntity<?> handleFileUpload( @RequestParam("file") MultipartFile file ) {

	    String fileName = file.getOriginalFilename();
	    
	    System.out.println(fileName);
	    try {
	      file.transferTo( new File("D:\\##vinith\\New folder\\file\\" + fileName));
	          
	  
	        File Pfile=new File("D:\\##vinith\\New folder\\process\\"+fileName);
	        Pfile.createNewFile();
	        FileWriter Pwriter=new FileWriter(Pfile);
	      
	        FileReader reader=new FileReader("D:\\##vinith\\New folder\\file\\" + fileName);
	        
	        int output=reader.read();
	            while(output!=-1) {
	            	 System.out.print((char)output);
	            	 
	            	  char uppercase=	(char)output;
		              char contentUpper=  Character.toUpperCase(uppercase);
	            	 
		    //file write     
				     Pwriter.write(contentUpper); 
				     
	            	 output=reader.read();
	            }
	            reader.close();
	            Pwriter.flush();
		        Pwriter.close();
	  
		       
	    } catch (Exception e) {
	    	e.printStackTrace();
	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }   

	    return ResponseEntity.ok(fileName);
	  }


	  
	
//download file
	@GetMapping("/download/{fileName}")
		public ResponseEntity<Object> download(@PathVariable String fileName)throws IllegalStateException,IOException{
		
		 // System.out.println(fileName);
		  File Pfile=new File("D:\\##vinith\\New folder\\process\\"+fileName);
			
			InputStreamResource resource=new InputStreamResource(new FileInputStream(Pfile));
			HttpHeaders headers=new HttpHeaders();
			headers.add("Content-Disposition",
					String.format("attachment; filename=\"%s\"", Pfile.getName()));
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");

			ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers)
					.contentLength(Pfile.length())
					.contentType(MediaType.parseMediaType("application/txt")).body(resource);

			return responseEntity;    
		}	
	
	
	
	@PostMapping("/frm_upload") 
	@ResponseBody
    public HashMap jsonTest(@RequestBody Object frm ) throws IOException{
		HashMap formData =(HashMap) frm;
		
	    System.out.println( frm);  
		System.out.println( "hashmap"+formData);  
		System.out.println(formData.get("name"));
		System.out.println(formData.get("age"));
		System.out.println(formData.get("district"));
		System.out.println(formData.get("gender"));
		
		String name=(String) formData.get("name");
		String age=(String) formData.get("age");
		String district=(String) formData.get("district");
		String gender=(String) formData.get("gender");
		
		File Ffile=new File("D:\\##vinith\\New folder\\formdata\\input.txt");
	    Ffile.createNewFile();
	    FileWriter Fwriter=new FileWriter(Ffile,true);
	    Fwriter.write(name+","+age+","+gender+","+district+ "\n"); 
	    Fwriter.flush();
	    Fwriter.close();
	    

	   
	    reader();
		return formData;
	}
	
	
	@GetMapping("/alldata")
	 @ResponseBody
	public List<String> reader() throws IOException {
		 
//		 try {
//		        File myObj = new File("D:\\##vinith\\New folder\\formdata\\input.txt");
//		        Scanner myReader = new Scanner(myObj);
//		        while (myReader.hasNextLine()) {
//		          String data = myReader.nextLine();
//		          System.out.println(data);
//		      }
//		        myReader.close();
//		      } catch (FileNotFoundException e) {
//		        System.out.println("An error occurred.");
//		        e.printStackTrace();
//		      }
		 List<String> lines = Files.readAllLines(Paths.get("D:\\##vinith\\New folder\\formdata\\input.txt"));

		return lines;
	}
	

}
	
	

