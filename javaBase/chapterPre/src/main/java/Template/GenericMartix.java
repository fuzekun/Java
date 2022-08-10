package Template;


/*
 * 矩阵的运算类
 * 加法
 * 乘法
 * */

//注：该程序主要来自Y.Daniel Liang老师的《Java语言程序设计~进阶篇》，程序略有改动，一本不错的书哦，值得收藏！
public abstract class GenericMartix <E extends Number>{
	  //将矩阵元素相加的抽象方法;
    protected abstract E add(E o1, E o2);
    //将矩阵的两个元素相乘的抽象方法
    protected abstract E multiply(E o1, E o2);
    //定义零矩阵的抽象方法
    protected abstract E zero();
    //矩阵相加的实现
    public E[][] addMartix(E[][] maxtrix1,E[][] maxtrix2) throws RuntimeException{
		if(maxtrix1.length != maxtrix2.length || maxtrix1[0].length != maxtrix2[0].length) {
			throw new RuntimeException("两个矩阵的大小不一样");
		}
		
		@SuppressWarnings("unchecked")
		E[][] result = (E[][])new Number[maxtrix1.length][maxtrix1[0].length];
		for(int i = 0;i < result.length;i++) {
			for(int j = 0;j < result[0].length;j++) {
				result[i][j] = add(maxtrix1[i][j], maxtrix2[i][j]);
			}
		}
		System.out.println("result的类型是" + getType(result[0][0]));
		return result;
	}
    public E[][] multiplyMartix(E[][] martix1,E[][] martix2) throws RuntimeException{
		if(martix1[0].length != martix2.length) throw new RuntimeException("两个矩阵的大小不匹配");
		@SuppressWarnings("unchecked")
		E[][] result = (E[][])new Number[martix1.length][martix2[0].length];
		for(int i = 0;i < result.length;i++) {
			for(int j = 0;j < result[0].length;j++) {
				result[i][j] = zero();
				for(int k = 0;k < martix1[0].length;k++) {
					result[i][j] = add(result[i][j], multiply(martix1[i][k], martix2[k][j]));
				}
			}
		}
		System.out.println("result的类型是" + getType(result[0][0]));
		return result;
    }
    @SuppressWarnings("hiding")
	public <E> void printResult(E[][] result) {
    	for(int i = 0 ;i < result.length;i++) {
    		for(int j = 0;j < result[i].length;j++) {
    			System.out.print(result[i][j] + " ");
    		}
    		System.out.println();
    	}
	}
    public static String getType(Integer x) {
    	return "int";
    }

    public String getType(Double x) {
		return "Double";
	}
    public String getType(Number x) {
		return "Number";
	}
}


