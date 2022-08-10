package Template;


/*
 * �����������
 * �ӷ�
 * �˷�
 * */

//ע���ó�����Ҫ����Y.Daniel Liang��ʦ�ġ�Java���Գ������~����ƪ�����������иĶ���һ���������Ŷ��ֵ���ղأ�
public abstract class GenericMartix <E extends Number>{
	  //������Ԫ����ӵĳ��󷽷�;
    protected abstract E add(E o1, E o2);
    //�����������Ԫ����˵ĳ��󷽷�
    protected abstract E multiply(E o1, E o2);
    //���������ĳ��󷽷�
    protected abstract E zero();
    //������ӵ�ʵ��
    public E[][] addMartix(E[][] maxtrix1,E[][] maxtrix2) throws RuntimeException{
		if(maxtrix1.length != maxtrix2.length || maxtrix1[0].length != maxtrix2[0].length) {
			throw new RuntimeException("��������Ĵ�С��һ��");
		}
		
		@SuppressWarnings("unchecked")
		E[][] result = (E[][])new Number[maxtrix1.length][maxtrix1[0].length];
		for(int i = 0;i < result.length;i++) {
			for(int j = 0;j < result[0].length;j++) {
				result[i][j] = add(maxtrix1[i][j], maxtrix2[i][j]);
			}
		}
		System.out.println("result��������" + getType(result[0][0]));
		return result;
	}
    public E[][] multiplyMartix(E[][] martix1,E[][] martix2) throws RuntimeException{
		if(martix1[0].length != martix2.length) throw new RuntimeException("��������Ĵ�С��ƥ��");
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
		System.out.println("result��������" + getType(result[0][0]));
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


