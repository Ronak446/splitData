package MUSCAphasia.splitData;


import java.io.*;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;


public class documentSplit {


	public static void main(String[] args) {

		String pathOut = "D:\\Users\\ronak\\eclipse-workspace\\splitData\\resources\\output\\";
		ArrayList<File> docx = new ArrayList<File>();
		getAllFiles(new File("D:\\Users\\ronak\\eclipse-workspace\\splitData\\resources\\Transcripts\\"), docx);

		ArrayList<String> trashList = new ArrayList<String>();
		for(File f:docx) {
			String name = f.getAbsolutePath();
			//System.out.println(name);
			name.split(".");
			String outName = getFileName(name);
			String docBody = readDocxFile(name).strip();
			docBody = removeExtras(docBody).strip();
			harvestInfo(docBody,name);
			//	System.out.println("-----");
			//System.out.println(docBody);





			BufferedWriter writer; try { 
				writer = new BufferedWriter(new
						FileWriter(pathOut+outName+".txt")); writer.write(docBody); writer.close();
			} catch (IOException e) { e.printStackTrace(); }

		}

		//fileOut Name = patientID-Task(SessionNumber) %%eg 1007-BW1




	}


	public static String readDocxFile(String fileName) {
		String pattern = "(\\d{1,2}:[:\\d{1,2}]+)";
		String body = "";
		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
			XWPFDocument document = new XWPFDocument(fis);
			XWPFWordExtractor xs = new XWPFWordExtractor(document);

			//System.out.println(xs.getText());
			//System.out.println(xs.getCoreProperties().getDescription());

			int end = 0;

			Pattern r2 = Pattern.compile(pattern);	
			Matcher m2= r2.matcher(xs.getText());
			while(m2.find()) {
				end = m2.end();
			}
			body = xs.getText().substring(end);
			fis.close();
			//	System.out.println((end+0.0)/xs.getText().length());
			//	System.out.println(end+"/"+xs.getText().length());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return body;
	}
	
	public static String removeExtras(String body) {
		String pattern1 = "(\\d\\d\\d\\d)(-|_)1";
		String pattern2 = "(\\d{1,2}:[:\\d{1,2}]+)";
		String pattern3 = "(unintelligible)";
		String pattern4 = "NOTE:";

		
		
		Pattern p4 = Pattern.compile(pattern4);
		Pattern p1 = Pattern.compile(pattern1);
		Pattern p2 = Pattern.compile(pattern2);
		Pattern p3 = Pattern.compile(pattern3);
	
		String[] lines = body.split("\\n");
		ArrayList<String> remove = new ArrayList<String>();

		for(int ii = 0; ii<lines.length; ii++) {
			
			
			String line = lines[ii];
			Matcher m1 = p1.matcher(line);
			Matcher m2 = p2.matcher(line);
			Matcher m3 = p3.matcher(line);
			Matcher m4 = p4.matcher(line);
			if(m1.find() || m2.find() || m3.find() || m4.find()
					|| line.toLowerCase().contains("note:")
					|| line.toLowerCase().contains("comment:")
					|| line.toLowerCase().contains("@comment:")
					|| line.toLowerCase().contains("unintelligible")
					|| line.toLowerCase().contains("Unintelligible")
					|| line.toLowerCase().contains("broken window")
					|| line.toLowerCase().contains("pb&j")
					|| line.toLowerCase().contains("cinderella")
					|| line.toLowerCase().contains("**very limited verbal output**")) {
				remove.add(line);
				System.out.println(line);
			}
			if(ii==3) {
				ii = lines.length;
			}
		}
		for(String a : remove) {
			body = body.replace(a, "");
		}
		
		
		
		return body;
	}


	private static void harvestInfo(String text, String fileName) {

		String[] paragraphs = text.split("\\n");
		//System.out.println(paragraphs[paragraphs.length-1]);



		BufferedWriter writer;
		try { 
			//File f = new File("D:\\Users\\ronak\\eclipse-workspace\\splitData\\resources\\output\\info.txt");
			writer = new BufferedWriter(new FileWriter("D:\\Users\\ronak\\eclipse-workspace\\splitData\\resources\\output\\info5.txt", true));
			writer.write(paragraphs[0]+", "+fileName+"\n");
			writer.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void getAllFiles(File curDir, ArrayList<File> list) {
		File[] filesList = curDir.listFiles();
		for (File f: filesList) {
			if (f.isDirectory()) {
				getAllFiles(f,list);
			}
			if (f.isFile() && f.getAbsolutePath().endsWith(".docx")
					&& f.getAbsolutePath().contains("\\1\\")
					&& !f.getAbsolutePath().toLowerCase().contains("larc")
					&& !f.getAbsolutePath().toLowerCase().contains("macosx")
					&& !f.getAbsolutePath().toLowerCase().contains("1st rater")) {
				list.add(f);
			}
		}
	}

	public static String getFileName(String f) {
		String[] splitPth = f.split("\\\\");
		String id = splitPth[splitPth.length-1-2];
		String type = splitPth[splitPth.length-1-4];
		String num = splitPth[splitPth.length-1-1];
		if(type.equals("Cinderella")) {type="CN";}
		if(type.equals("BrokenWindow")) {type="BW";}



		return id+"-"+type+num;
	}





}



