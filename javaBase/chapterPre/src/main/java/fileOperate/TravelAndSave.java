package fileOperate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TravelAndSave {
	 static File test = null;
	    //��������������
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
	 
	    //�ж��Ƿ��ж�ȡ�ļ���Ȩ��
	    public boolean getPermission(File file){
	        boolean flag = false;
	        try {
	            File[] list = file.listFiles();
	            @SuppressWarnings("unused")
	            //�˷����Ĺؼ��㣬����仰�Ƿ�����쳣�������쳣���ܷ��ʣ�û���쳣���������ʣ�
	            int i = list.length;
	            flag = true;
	        } catch (Exception e) {
	            flag = false;
	        }
	        return flag;
	    }
	    
	    //����main��������
	    public static void main(String[] args) {
	        //ָ��Ҫ������Ŀ¼
	        String filelocal = "E:/eclipse��װ��java�ļ�";
	        String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) //��ʱ���ʽ��Ϊ�����ָ�ʽ
	                            + ".txt";
	        test = new File(filename);
	       TravelAndSave fn = new TravelAndSave();//ʵ����
	        fn.putfilename(filelocal, -1);//���ñ��෽��
	        try {
	            //���Ѿ����ɵ��ļ�
	            Runtime.getRuntime().exec("notepad " + filename);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}

}
