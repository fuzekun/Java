package fileOperate;

import java.io.File;

public class TravelE {
	 //��������������
    //�����������ļ�·�����ļ������Ե�ǰĿ¼Ϊ��Ŀ¼ͳ��Ŀ¼����ȣ��������ݹ�Ĵ�����
    public void putfilename(String filename, int count) throws FileNotExistException{
        count++;
        File file = new File(filename);
        if(!file.exists())throw new FileNotExistException("file not exist!");
        if(file.isFile()){
            for(int k=0;k<count;k++){
                System.out.print("|   ");
            }
            System.out.println(file.getName());
        } else if (file.isDirectory() && this.getPermission(file)){
            File[] list = file.listFiles();
            for(int k = 0; k < count; k++){
                System.out.print("|   ");
            }
            System.out.println(file.getName()); 
            //��������еݹ����
            for(int i=0; i<list.length; i++){
                putfilename(list[i].getPath(), count);
            }
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
        String filename = "E:\\eclipse��װ��java�ļ�\\My_Sprig";
        TravelE fn = new TravelE();
        try {
        	fn.putfilename(filename, -1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }

}
