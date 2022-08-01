
import java.util.*;

import javax.imageio.IIOException;

import java.io.*;
public class CSVfile {
	private String data;
	private static CSVfile currentCSVfile;
	private static boolean created = false;
	private static String readCSV(String path)
	{
		int i;
		String res="";
		try {
		FileInputStream fin = new FileInputStream(path);
		while((i=fin.read()) != -1)
		{
			res += (char)i;
		}
		fin.close();
		}
		catch(FileNotFoundException e)
		{
			System.err.println("The entered path "+path+" is invalid");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return res;
	}
	private void createFile(String fileData,String filePath)
	{
		byte[] bs= fileData.getBytes();try {
		BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(filePath));
		
			bout.write(bs);
			bout.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static CSVfile createCSVfile(String path)
	{
		if(created)
		{
			return currentCSVfile;
		}
		else
		{
			created = true;
			CSVfile csvfile = new CSVfile(path);
			currentCSVfile = csvfile;
			return csvfile;
		}
	}
	private CSVfile(String path)
	{
		this.data = readCSV(path);
	}
	public void destroyCSVfile()
	{
		data = "";
		created = false;
		currentCSVfile=null;
	}
	public ArrayList<String> splitData(int number) throws IIOException
	{
		ArrayList<String> res = new ArrayList<String>();
		for(int i=0;i<number;i++)
			res.add("");
		StringTokenizer countLines = new StringTokenizer(data,"\n");
		int numberOfLines = countLines.countTokens();
		StringTokenizer st1 = new StringTokenizer(data,",|;");
		if(st1.countTokens() != (number*numberOfLines))
		{
			throw new IIOException("Invalid file");
		}
		else
		{
			for(int i=0;i<numberOfLines;i++)
			{
				StringTokenizer currentLine = new StringTokenizer(countLines.nextToken(),";|,\n");
				for(int j=0;j<number;j++)
				{
					res.set(j, (res.get(j)+currentLine.nextToken()+","));
				}
			}
		}
		return res;
	}
	public void splitFile(String path,int numberOfValues) 
	{
		CSVfile csvfile = CSVfile.createCSVfile(path);
		ArrayList<String> dataOutput = new ArrayList<String>();
		try {
			dataOutput = csvfile.splitData(numberOfValues);
		} catch (IIOException e) {
			System.out.println(e);
		}
		for(int i=0;i<numberOfValues;i++)
		{
			createFile(dataOutput.get(i),"d:\\outputCSV"+i+".csv");
		}
		System.out.println("The new files were successfully created.!");
	}

}


