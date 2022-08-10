package fileOperate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TravelAndSave {
	 static File test = null;
	    //遍历及产生缩进
	    public void putfilename(String filename, int count){
	        count++;
	        File file = new File(filename);
	        try {
	            if(file.isFile()){
	                FileWriter fw = new FileWriter(test,true);
	                BufferedWriter bw = new BufferedWriter(fw);
	                for(int k=0;k<count;k++){
	                    bw.write("|   ");
	                }
	                bw.write(file.getName());
	                bw.newLine();
	                fw.flush();
	                bw.close();
	            } else if (file.isDirectory() && this.getPermission(file)){
	                File[] list = file.listFiles();
                    FileWriter fw = new FileWriter(test,true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    for(int k = 0; k < count; k++){
                        bw.write("|   ");
                    }
                    bw.write(file.getName());
                    bw.newLine();
                    fw.flush();
                    bw.close();
	                for(int i=0; i<list.length; i++){
	                    putfilename(list[i].getPath(), count);
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	 
	    //判断是否有读取文件的权限
	    public boolean getPermission(File file){
	        boolean flag = false;
	        try {
	            File[] list = file.listFiles();
	            @SuppressWarnings("unused")
	            //此方法的关键点，看这句话是否产生异常（产生异常则不能访问，没有异常则正常访问）
	            int i = list.length;
	            flag = true;
	        } catch (Exception e) {
	            flag = false;
	        }
	        return flag;
	    }
	    
	    //生成main方法调用
	    public static void main(String[] args) {
	        //指定要遍历的目录
	        String filelocal = "E:/eclipse安装与java文件";
	        String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) //将时间格式化为纯数字格式
	                            + ".txt";
	        test = new File(filename);
	       TravelAndSave fn = new TravelAndSave();//实例化
	        fn.putfilename(filelocal, -1);//调用本类方法
	        try {
	            //打开已经生成的文件
	            Runtime.getRuntime().exec("notepad " + filename);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}

}
