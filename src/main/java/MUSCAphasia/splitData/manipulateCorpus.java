package MUSCAphasia.splitData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class manipulateCorpus {
	public static void main(String[] args) {
		ArrayList<File> docx = new ArrayList<File>();
		getAllFiles(new File("D:\\Users\\ronak\\eclipse-workspace\\splitData\\resources\\alterations\\manual_edit3\\"), docx);

		//strip(docx);
		//getBracs(docx);
		//strip(docx);
		spaceHandeler(docx);

		ArrayList<File> docx2 = new ArrayList<File>();
		ArrayList<File> docx2No = new ArrayList<File>();
		getAllFiles(new File("D:\\Users\\ronak\\eclipse-workspace\\splitData\\resources\\alterations\\spaceandcomma\\"), docx2);
		getAllFiles(new File("D:\\Users\\ronak\\eclipse-workspace\\splitData\\resources\\noalterations\\spaceandcomma\\"), docx2No);
		angleBracketHandler(docx2);
		//angleBracketHandler(docx2No);

		ArrayList<File> docx3 = new ArrayList<File>();
		ArrayList<File> docx3No	=new ArrayList<File>();
		getAllFiles(new File("D:\\Users\\ronak\\eclipse-workspace\\splitData\\resources\\alterations\\anglebracket\\"), docx3);
		getAllFiles(new File("D:\\Users\\ronak\\eclipse-workspace\\splitData\\resources\\noalterations\\anglebracket\\"), docx3No);
		plusHandler(docx3);
		//plusHandler(docx3No);



		ArrayList<File> docx4 = new ArrayList<File>();
		ArrayList<File> docx4No = new ArrayList<File>();

		getAllFiles(new File("D:\\Users\\ronak\\eclipse-workspace\\splitData\\resources\\alterations\\plussign\\"), docx4);
		getAllFiles(new File("D:\\Users\\ronak\\eclipse-workspace\\splitData\\resources\\noalterations\\plussign\\"), docx4No);

		phonemicHandler(docx4);
		//phonemicHandlerNo(docx4No);

		//(\w*)\s*(\[\s*:\s*\w*\])*\s*(\[\*\s*phon\s*\])
	}


	private static void strip(ArrayList<File> docx) {
		for(File f : docx) {
			System.out.println(f.getAbsolutePath());
			String name = f.getAbsolutePath();
			String name2 = name.replace("manual_edit2", "manual_edit3");
			String body = readTxt(f).strip();
			BufferedWriter writer; try { 
				writer = new BufferedWriter(new
						FileWriter(name2)); writer.write(body); writer.close();
			} catch (IOException e) { e.printStackTrace(); }
		}
	}

	private static void spaceHandeler(ArrayList<File> docx) {
		for(File f : docx) {
			String name = f.getAbsolutePath();
			String name2 = name.replace("manual_edit3", "spaceandcomma");
			String noaltname = name2.replace("alterations", "noalterations");
			File newF = new File(name2);
			File data = new File("D:\\Users\\ronak\\eclipse-workspace\\splitData\\resources\\alteration_data\\"
					+f.getName().substring(0,f.getName().lastIndexOf("."))+"_data.txt");


			String body = readTxt(f);	

			Pattern patOne = Pattern.compile("(\\s*\\[\\/\\]\\s*)");
			Pattern patTwo = Pattern.compile("\\s*\\[\\/\\/\\]\\s*");
			Pattern PatThree = Pattern.compile("&\\w*\\s*");

			Matcher m1 = patOne.matcher(body);
			Matcher m2 = patTwo.matcher(body);
			Matcher m3 = PatThree.matcher(body);

			body = body.replaceAll("(\\s*\\[\\/\\]\\s*)", " ");
			body = body.replaceAll("\\s*\\[\\/\\/\\]\\s*", ", ");
			body = body.replaceAll("&\\w*\\s*", "");
			body = body.replaceAll("\\s \\s", " ");

			String data2 = "[/]: " + m1.results().count()+"\n"
					+"[//]: " + m2.results().count()+"\n"
					+"&: " + m3.results().count()+"\n";

			BufferedWriter writer;
			BufferedWriter writer2;
			BufferedWriter writer3;

			try { 
				writer = new BufferedWriter(new FileWriter(newF));
				writer.write(body); writer.close();
				writer2 = new BufferedWriter(new FileWriter(new File(noaltname)));
				writer2.write(body); writer2.close();
				writer3 = new BufferedWriter(new FileWriter(data));
				writer3.write(data2); writer3.close();
			} catch (IOException e) { e.printStackTrace(); }
		}
	}

	private static void angleBracketHandler(ArrayList<File> docx) {
		for(File f : docx) {
			String name = f.getAbsolutePath();
			String name2 = name.replace("spaceandcomma", "anglebracket");
			File newF = new File(name2);
			File data = new File("D:\\Users\\ronak\\eclipse-workspace\\splitData\\resources\\alteration_data\\"
					+f.getName().substring(0,f.getName().lastIndexOf("."))+"_data.txt");

			String body = readTxt(f);

			Pattern patOne = Pattern.compile("<\\s*|\\s*>");

			Matcher m1 = patOne.matcher(body);
			
			body = body.replaceAll("<\\s*|\\s*>","");
			if(body.contains("<")||body.contains(">")) {
				System.out.println(f.getAbsolutePath());
			}




			String data2 = "<>: " + m1.results().count()+"\n";


			BufferedWriter writer3;

			BufferedWriter writer; try { 
				writer = new BufferedWriter(new FileWriter(newF));
				writer.write(body); writer.close();
				writer3 = new BufferedWriter(new FileWriter(data,true));
				writer3.write(data2); writer3.close();
			} catch (IOException e) { e.printStackTrace(); }
		}
	}

	private static void plusHandler(ArrayList<File> docx) {
		for(File f : docx) {
			String name = f.getAbsolutePath();
			String name2 = name.replace("anglebracket", "plussign");
			File newF = new File(name2);
			File data = new File("D:\\Users\\ronak\\eclipse-workspace\\splitData\\resources\\alteration_data\\"
					+f.getName().substring(0,f.getName().lastIndexOf("."))+"_data.txt");
			String body = readTxt(f);
			
			
			Pattern patOne = Pattern.compile("\\b\\s*\\+\\s*\\b");

			Matcher m1 = patOne.matcher(body);
			

			
			body = body.replaceAll("\\b\\s*\\+\\s*\\b","");
			if(body.contains("[* phon duh]")) {
				//System.out.println(f.getAbsolutePath());
			}





			String data2 = "+: " + m1.results().count()+"\n";
			BufferedWriter writer3;

			BufferedWriter writer; try { 
				writer = new BufferedWriter(new FileWriter(newF));
				writer.write(body); writer.close();
				writer3 = new BufferedWriter(new FileWriter(data,true));
				writer3.write(data2); writer3.close();
			} catch (IOException e) { e.printStackTrace(); }
		}
		//I manually edited 1031 CN to chagne the [* phon duh error 

	}

	private static void phonemicHandler(ArrayList<File> docx) {
		for(File f : docx) {
			String name = f.getAbsolutePath();
			String name2 = name.replace("plussign", "phonemicShift");
			File newF = new File(name2);
			File data = new File("D:\\Users\\ronak\\eclipse-workspace\\splitData\\resources\\alteration_data\\"
					+f.getName().substring(0,f.getName().lastIndexOf("."))+"_data.txt");
			String body = readTxt(f);
			body = body.replaceAll("’","'");
			String Pat = ("(\\w*)\\s*(\\[\\s*:\\s*[a-zA-Z0-9_ '’']*\\])*\\s*(\\[\\s*\\*\\s*phon\\s*\\])");
			Pattern p = Pattern.compile(Pat);
			Matcher m = p.matcher(body);






			StringBuilder sb = new StringBuilder();
			String data2 = "[phon]: " + m.results().count()+"\n";
			m.reset();


			while(m.find()) {

				if(m.group(2)==null) {

					m.appendReplacement(sb, m.group(1));
				}
				else {
					m.appendReplacement(sb, m.group(2).strip().replaceAll("(\\[\\s*:\\s*)|\\s*]", ""));
				}


			}


			m.appendTail(sb);
			body = sb.toString();
			if(name2.contains("1115-CN1")) {
				//	System.out.println(body);
			}
			if(body.contains("phon")) {
				//	System.out.println(f.getAbsolutePath());
			}
			body = semanticHandler( body, f);
			body = unknownHandler(body,f);
			Pattern p4 = Pattern.compile("\\[.*?]");
			Matcher m4 = p4.matcher(body);

			if(m4.find()) {
				System.out.println("Hello2");

				System.out.println(f.getAbsolutePath() +" p");
			}

			BufferedWriter writer;
			BufferedWriter writer3;
			try { 
				writer = new BufferedWriter(new FileWriter(newF));
				writer.write(body); writer.close();

				writer3 = new BufferedWriter(new FileWriter(data,true));
				writer3.write(data2); writer3.close();
			} catch (IOException e) { e.printStackTrace(); }
		}
	}

	private static String semanticHandler(String body,File f) {
		String Pat = ("(\\w*)\\s*(\\[\\s*:\\s*[a-zA-Z0-9_ '’']*\\])*\\s*(\\[\\s*\\*\\s*[sS]\\s*])");
		Pattern p = Pattern.compile(Pat);
		Matcher m = p.matcher(body);
		StringBuilder sb = new StringBuilder();
		File data = new File("D:\\Users\\ronak\\eclipse-workspace\\splitData\\resources\\alteration_data\\"
				+f.getName().substring(0,f.getName().lastIndexOf("."))+"_data.txt");
		String data2 = "[s]: " + m.results().count()+"\n";
		m.reset();
		while(m.find()) {

			if(m.group(2)==null) {

				m.appendReplacement(sb, m.group(1));
			}
			else {
				//System.out.println(m.group());
				//System.out.println(m.group(2).strip().replaceAll("\\[\\s*:\\s*|\\s*]", ""));
				m.appendReplacement(sb, m.group(2).strip().replaceAll("\\[\\s*:\\s*|\\s*]", ""));
			}


		}


		m.appendTail(sb);
		body = sb.toString();


		BufferedWriter writer3;

		try {
			writer3 = new BufferedWriter(new FileWriter(data,true));
			writer3.write(data2);
			writer3.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(body.contains("* s")) {
			System.out.println(f.getAbsolutePath()	+" s");
		}			
		return body;
	}

	private static String unknownHandler(String body,File f) {
		String Pat = ("(\\w*)\\s*(\\[\\s*:\\s*[a-zA-Z0-9_ '’']*\\])*\\s*(\\[\\s*\\*\\s*(?:u|unk|n:uk)\\s*])");
		Pattern p = Pattern.compile(Pat);
		Matcher m = p.matcher(body);
		File data = new File("D:\\Users\\ronak\\eclipse-workspace\\splitData\\resources\\alteration_data\\"
				+f.getName().substring(0,f.getName().lastIndexOf("."))+"_data.txt");
		String data2 = "[u]: " + m.results().count()+"\n";
		m.reset();

		StringBuilder sb = new StringBuilder();
		while(m.find()) {

			if(m.group(2)==null) {

				m.appendReplacement(sb, m.group(1));
			}
			else {
				//	System.out.println(m.group());
				//System.out.println(m.group(2).strip().replaceAll("\\[\\s*:\\s*|\\s*]", ""));
				m.appendReplacement(sb, m.group(2).strip().replaceAll("\\[\\s*:\\s*|\\s*]", ""));
			}


		}


		m.appendTail(sb);
		body = sb.toString();

		BufferedWriter writer3;

		try {
			writer3 = new BufferedWriter(new FileWriter(data,true));
			writer3.write(data2);
			writer3.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(body.contains("* u")||body.contains("* n:uk")||body.contains("* unk")) {
			System.out.println(f.getAbsolutePath()+" u");
		}			
		return body;
	}





	private static void phonemicHandlerNo(ArrayList<File> docx) {
		for(File f : docx) {
			String name = f.getAbsolutePath();
			String name2 = name.replace("plussign", "phonemicShift");
			File newF = new File(name2);
			String body = readTxt(f);
			body = body.replaceAll("’","'");
			String Pat = ("(\\w*)\\s*(\\[\\s*:\\s*[a-zA-Z0-9_ '’']*\\])*\\s*(\\[\\s*\\*\\s*phon\\s*\\])");
			Pattern p = Pattern.compile(Pat);
			Matcher m = p.matcher(body);
			StringBuilder sb = new StringBuilder();
			while(m.find()) {


				m.appendReplacement(sb, m.group(1));



			}


			m.appendTail(sb);
			body = sb.toString();
			if(name2.contains("1115-CN1")) {
				//	System.out.println(body);
			}
			if(body.contains("phon")) {
				//	System.out.println(f.getAbsolutePath());
			}
			body = semanticHandlerNo( body, f);
			body = unknownHandlerNo(body,f);
			Pattern p4 = Pattern.compile("\\[.*?]");
			Matcher m4 = p4.matcher(body);

			if(m4.find()) {
				System.out.println("Hello2");

				System.out.println(f.getAbsolutePath());
			}
			BufferedWriter writer; try { 
				writer = new BufferedWriter(new FileWriter(newF));
				writer.write(body); writer.close();
			} catch (IOException e) { e.printStackTrace(); }
		}
	}

	private static String semanticHandlerNo(String body,File f) {
		String Pat = ("(\\w*)\\s*(\\[\\s*:\\s*[a-zA-Z0-9_ '’']*\\])*\\s*(\\[\\s*\\*\\s*[sS]\\s*])");
		Pattern p = Pattern.compile(Pat);
		Matcher m = p.matcher(body);
		StringBuilder sb = new StringBuilder();
		while(m.find()) {



			m.appendReplacement(sb, m.group(1));



		}


		m.appendTail(sb);
		body = sb.toString();

		if(body.contains("* s")) {
			System.out.println(f.getAbsolutePath()	);
		}			
		return body;
	}

	private static String unknownHandlerNo(String body,File f) {
		String Pat = ("(\\w*)\\s*(\\[\\s*:\\s*[a-zA-Z0-9_ '’']*\\])*\\s*(\\[\\s*\\*\\s*(?:u|unk|n:uk)\\s*])");
		Pattern p = Pattern.compile(Pat);
		Matcher m = p.matcher(body);
		StringBuilder sb = new StringBuilder();
		while(m.find()) {

			m.appendReplacement(sb, m.group(1));

		}


		m.appendTail(sb);
		body = sb.toString();

		if(body.contains("* u")||body.contains("* n:uk")||body.contains("* unk")) {
			System.out.println(f.getAbsolutePath());
		}			
		return body;
	}



	private static void getBracs(ArrayList<File> docx) {
		String pattern = "\\[.+?\\]";
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter("D:\\Users\\ronak\\eclipse-workspace\\splitData\\resources\\manual_edit2\\info5.txt", true));


			for(File f: docx) {
				String name = f.getAbsolutePath();
				String body = readTxt(f).strip();
				Pattern p1 = Pattern.compile(pattern);
				Matcher m1 = p1.matcher(body);
				while(m1.find()) {
					System.out.println(body.substring(m1.start(),m1.end()));

					writer.write(body.substring(m1.start(),m1.end())+"\n");


				}
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String readTxt(File f){
		String LINE = ""; 

		try (BufferedReader br =new BufferedReader(new FileReader(f))){

			while( br.ready()) {
				LINE += br.readLine();
				if(br.ready()) {
					LINE+="\n";
				}
			}

			LINE = Files.readString(f.toPath());
		}catch(Exception e){
			e.printStackTrace();
		}

		return LINE;
	}

	public static void getAllFiles(File curDir, ArrayList<File> list) {
		File[] filesList = curDir.listFiles();
		for (File f: filesList) {
			if (f.isDirectory()) {
				getAllFiles(f,list);
			}
			if (f.isFile() && f.getAbsolutePath().endsWith(".txt")) {
				list.add(f);
			}
		}
	}

}
