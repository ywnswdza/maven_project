package com.losy.common.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;


/**
 * <h1>此类包含的对文件的基本操作</h1> <br/>
 * 创建、移动、删除、复制、打包
 * @author xiaoyin
 * @date 2012-2-26下午04:26:28
 */
public final class FileOperate { 

	public static void main(String args[]) {
		newFolder("D:/100");
	}
   
   	/**
	 * 创建文件夹
	 * @param path 文件夹路径
	 * @return file流
	 */
	public static File createDir(String path) {
		File dirFile = null;
		try {
			dirFile = new File(path);
			if (!(dirFile.exists()) && !(dirFile.isDirectory())) {
				dirFile.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dirFile;
	}

	/**
	 * 创建文件
	 * @param path 文件路径
	 * @return file流
	 */
	public static File createFile(String path) {
		File file = new File(path);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	} 

   /** 
     * 新建目录 
     * @param folderPath String 如 c:/fqf 
     * @return boolean 
     */ 
   public static void newFolder(String folderPath) { 
       try { 
           String filePath = folderPath; 
           filePath = filePath.toString(); 
           java.io.File myFilePath = new java.io.File(filePath); 
           if (!myFilePath.exists()) { 
               myFilePath.mkdir(); 
           } 
       } 
       catch (Exception e) { 
           System.out.println("新建目录操作出错"); 
           e.printStackTrace(); 
       } 
   } 

   /** 
     * 新建文件 
     * @param filePathAndName String 文件路径及名称 如c:/fqf.txt 
     * @param fileContent String 文件内容 
     * @return boolean 
     */ 
   public static void newFile(String filePathAndName, String fileContent) { 

       try { 
           String filePath = filePathAndName; 
           filePath = filePath.toString(); 
           File myFilePath = new File(filePath); 
           if (!myFilePath.exists()) { 
               myFilePath.createNewFile(); 
           } 
           FileWriter resultFile = new FileWriter(myFilePath); 
           PrintWriter myFile = new PrintWriter(resultFile); 
           String strContent = fileContent; 
           myFile.println(strContent); 
           resultFile.close(); 

       } 
       catch (Exception e) { 
           System.out.println("新建目录操作出错"); 
           e.printStackTrace(); 

       } 

   } 

   /** 
     * 删除文件 
     * @param filePathAndName String 文件路径及名称 如c:/fqf.txt 
     * @param fileContent String 
     * @return boolean 
     */ 
   public static void delFile(String filePathAndName) { 
       try { 
           String filePath = filePathAndName; 
           filePath = filePath.toString(); 
           java.io.File myDelFile = new java.io.File(filePath); 
           myDelFile.delete(); 

       } 
       catch (Exception e) { 
           System.out.println("删除文件操作出错"); 
           e.printStackTrace(); 

       } 

   } 

   /** 
     * 删除文件夹 
     * @param filePathAndName String 文件夹路径及名称 如c:/fqf 
     * @param fileContent String 
     * @return boolean 
     */ 
   public static void delFolder(String folderPath) { 
       try { 
           delAllFile(folderPath); //删除完里面所有内容 
           String filePath = folderPath; 
           filePath = filePath.toString(); 
           java.io.File myFilePath = new java.io.File(filePath); 
           myFilePath.delete(); //删除空文件夹 

       } 
       catch (Exception e) { 
           System.out.println("删除文件夹操作出错"); 
           e.printStackTrace(); 

       } 

   } 

   /** 
     * 删除文件夹里面的所有文件 
     * @param path String 文件夹路径 如 c:/fqf 
     */ 
   public static void delAllFile(String path) { 
       File file = new File(path); 
       if (!file.exists()) { 
           return; 
       } 
       if (!file.isDirectory()) { 
           return; 
       } 
       String[] tempList = file.list(); 
       File temp = null;
       for (int i = 0; i < tempList.length; i++) { 
           if (path.endsWith(File.separator)) { 
               temp = new File(path + tempList[i]); 
           } 
           else { 
               temp = new File(path + File.separator + tempList[i]); 
           } 
           if (temp.isFile()) { 
               temp.delete(); 
           } 
           if (temp.isDirectory()) { 
               delAllFile(path+"/"+ tempList[i]);//先删除文件夹里面的文件 
               delFolder(path+"/"+ tempList[i]);//再删除空文件夹 
           } 
       } 
   } 

   /** 
     * 复制单个文件 
     * @param oldPath String 原文件路径 如：c:/fqf.txt 
     * @param newPath String 复制后路径 如：f:/fqf.txt 
     * @return boolean 
     */ 
   @SuppressWarnings("all")
   public static void copyFile(String oldPath, String newPath) { 
       try { 
           int bytesum = 0; 
           int byteread = 0; 
           File oldfile = new File(oldPath); 
           if (oldfile.exists()) { //文件存在时 
               InputStream inStream = new FileInputStream(oldPath); //读入原文件 
               FileOutputStream fs = new FileOutputStream(newPath); 
               byte[] buffer = new byte[1444]; 
//               int length; 
               while ( (byteread = inStream.read(buffer)) != -1) { 
                   bytesum += byteread; //字节数 文件大小 
//                   System.out.println(bytesum); 
                   fs.write(buffer, 0, byteread); 
               } 
               inStream.close(); 
           } 
       } 
       catch (Exception e) { 
           System.out.println("复制单个文件操作出错"); 
           e.printStackTrace(); 

       } 

   } 

   /** 
     * 复制整个文件夹内容 
     * @param oldPath String 原文件路径 如：c:/fqf 
     * @param newPath String 复制后路径 如：f:/fqf/ff 
     * @return boolean 
     */ 
   public static void copyFolder(String oldPath, String newPath) { 

       try { 
           (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹 
           File a=new File(oldPath); 
           String[] file=a.list(); 
           File temp=null; 
           for (int i = 0; i < file.length; i++) { 
               if(oldPath.endsWith(File.separator)){ 
                   temp=new File(oldPath+file[i]); 
               } 
               else{ 
                   temp=new File(oldPath+File.separator+file[i]); 
               } 

               if(temp.isFile()){ 
                   FileInputStream input = new FileInputStream(temp); 
                   FileOutputStream output = new FileOutputStream(newPath + "/" + 
                           (temp.getName()).toString()); 
                   byte[] b = new byte[1024 * 5]; 
                   int len; 
                   while ( (len = input.read(b)) != -1) { 
                       output.write(b, 0, len); 
                   } 
                   output.flush(); 
                   output.close(); 
                   input.close(); 
               } 
               if(temp.isDirectory()){//如果是子文件夹 
                   copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]); 
               } 
           } 
       } 
       catch (Exception e) { 
           System.out.println("复制整个文件夹内容操作出错"); 
           e.printStackTrace(); 

       } 

   } 

   /** 
     * 移动文件到指定目录 
     * @param oldPath String 如：c:/fqf.txt 
     * @param newPath String 如：d:/fqf.txt 
     */ 
   public static void moveFile(String oldPath, String newPath) { 
       copyFile(oldPath, newPath); 
       delFile(oldPath); 

   } 


   /** 
     * 移动文件到指定目录 
     * @param oldPath String 如：c:/fqf.txt 
     * @param newPath String 如：d:/fqf.txt 
     */ 
   public static void moveFolder(String oldPath, String newPath) { 
       copyFolder(oldPath, newPath); 
       delFolder(oldPath); 
   }


//   /**
//     * 文件打包
//	 * @param zipFilePath  打包文件存放路径，如："F:\\ttt.zip"
//	 * @param inputFolderName 需要打包的文件夹，如："F:\\tt"
//	 * @throws Exception
//	 */
//	public static void zip(String zipFilePath, String inputFolderName)
//			throws Exception {
//		String zipFileName = zipFilePath; // 打包后文件名字
//		File zipFile = new File(inputFolderName);
//		zip(zipFileName, zipFile);
//	}


//	/**
//	 * 文件打包
//	 * @param zipFileName 打包文件存放路径，如："F:\\ttt.zip"
//	 * @param inputFolder 要打包文件夹的file
//	 * @throws Exception
//	 */
//	public static void zip(String zipFilePath, File inputFolder) throws Exception {
//		FileOutputStream fileOut = new FileOutputStream(zipFilePath);
//		org.apache.tools.zip.ZipOutputStream out = new org.apache.tools.zip.ZipOutputStream(fileOut);
//		zip(out, inputFolder, "");
//		out.close();
//		fileOut.close();
//	}
//
//
//	private static void zip(org.apache.tools.zip.ZipOutputStream out, File inputFolder, String base)
//			throws Exception {
//		if (inputFolder.isDirectory()) {
//			File[] fl = inputFolder.listFiles();
//			out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
//			base = base.length() == 0 ? "" : base + "/";
//			for (int i = 0; i < fl.length; i++) {
//				zip(out, fl[i], base + fl[i].getName());
//			}
//		} else {
//			out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
//			out.write((base.substring(0, base.lastIndexOf('.'))).getBytes("UTF-8")); //乱码处理
// 			FileInputStream in = new FileInputStream(inputFolder);
//			int b;
//			while ((b = in.read()) != -1) {
//				out.write(b);
//			}
//			out.setEncoding("GB2312");
//			
//			in.close();
//		}
//	}
   
   
}
