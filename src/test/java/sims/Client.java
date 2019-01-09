package sims;

import com.sims.service.FruitService;

/**
 * <p>ClassName: Client</p>
 * Description:IOC容器实现机制<br/>
 * @date 2018年12月27日 上午10:17:27 
 * @author yckj0914
 * @version 1.0
 * @since JDK 1.7
 */ 
public class Client {

	public static void main(String[] args) {
		FruitService f = Factory.getInstance("com.sims.service.impl.AppleServiceImpl");
		if (f != null) {
			f.eat();
		}
	}
}

class Factory {

	public static FruitService getInstance(String className) {
		FruitService f = null;
		try {
			f = (FruitService) Class.forName(className).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return f;
	}
	
}
