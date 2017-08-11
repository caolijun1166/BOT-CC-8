import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.cn.bot.util.MemcacheHelper;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MemcachedTest {

	MemcacheHelper expecteds = MemcacheHelper.getInstance();
	String expectedString = "test";
	boolean expectedExist = true;
	MemcacheHelper mcc = MemcacheHelper.getInstance();
	@Test
	public void A() {
		mcc.set("test", "test", new Date(1*60*1000));
		
	}
	@Test
	public void B(){
		String actual = (String)mcc.get("test");
		assertEquals(expectedString, actual);
	}
	@Test
	public void C(){
		boolean actual = mcc.add("test", "123456", new Date(1*60*1000));
		assertEquals(expectedExist, !actual);
	}
}
