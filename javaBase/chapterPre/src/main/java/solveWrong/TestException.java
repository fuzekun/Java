package solveWrong;

public class TestException {
	public TestException() {
	}

	boolean testEx() throws Exception {
		boolean ret = true;
		try {
			ret = testEx1();
			return ret;
		} catch (Exception e) {
			System.out.println("testEx, catch exception");
			ret = false;
			throw e;
		} finally {
			System.out.println("testEx, finally; return value=" + ret);
		}
	}

	boolean testEx1() throws Exception {
		boolean ret = true;
		try {
			ret = testEx2();
			if (!ret) {
				return false;
			}
			System.out.println("testEx1, at the end of try");
			return ret;
		} catch (Exception e) {
			System.out.println("testEx1, catch exception");
			ret = false;
			throw e;
		} finally {
			System.out.println("testEx1, finally; return value=" + ret);
			
		}
	}
 
	boolean testEx2() throws Exception {
		boolean ret = true;
		try {
			int b = 12;
			for (int i = 2; i >= -2; i--) {
				int c = b / i;
				c = c + 0;
				System.out.println("i=" + i);
			}
			return true;
		} catch (Exception e) {
			System.out.println("testEx2, catch exception");
			ret = false;
			throw e;
		} finally {
			System.out.println("testEx2, finally; return value=" + ret);
			
		}
	}
	@SuppressWarnings("finally")
	static int test() {
		try {
			System.out.println(1/0);
			return 0;
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("除0");
			return 1;
		}
		finally {
			System.out.println("fdas");
			return 2;
		}
	}
 
	@SuppressWarnings("finally")
	static void test2() {
		try {
			System.out.println(1/0);
			return;
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("除0");
			return;
		}
		finally {
			System.out.println("fdas");
			return;
		}
	}
	
	static void methond1() throws Exception{
		try {
			methond2();
		}
		catch (Exception e) {
			// TODO: handle exception
			Exception e2 = new Exception("新异常");
			throw new Exception("来自method1的新异常",e2);//最多抛出两个异常，一个原始，一个附加
		}
	}
	static void methond2()throws Exception{
		throw new Exception("来自method2的新异常");
	}
	static void ChainedException() {
		try {
			methond1();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		TestException testException1 = new TestException();
		try {
			testException1.testEx();
		} catch (Exception e) {
			System.out.println("循环中除0啦");
		}
		System.out.println(test());
		test2();
		ChainedException();
	}
}