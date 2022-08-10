package fileOperate;

import java.io.File;

public class TravelE {
	 //遍历及产生缩进
    //两个参数，文件路径或文件名，以当前目录为父目录统计目录的深度（即函数递归的次数）
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
            //在这儿进行递归调用
            for(int i=0; i<list.length; i++){
                putfilename(list[i].getPath(), count);
            }
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
        String filename = "E:\\eclipse安装与java文件\\My_Sprig";
        TravelE fn = new TravelE();
        try {
        	fn.putfilename(filename, -1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }

}
