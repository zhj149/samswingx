package org.sam.utils;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sam.swing.DeptEntity;
import org.sam.swing.TestEntity;
import org.sam.swing.utils.StringUtil;

/**
 * 字符串工具类测试工具
 * @author sam
 *
 */
public class StringUtilTest {
	
	/**
	 * 测试用数据
	 */
	private Set<DeptEntity> entities;

	/**
	 * 初始化 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		entities = new LinkedHashSet<>();
		
		for ( int i = 0 ; i < 10 ;i++){
			DeptEntity e = new DeptEntity();
			e.setCode("code:" + i);
			e.setId(i);
			e.setName("name:" + i);
			TestEntity entity = new TestEntity();
			entity.setCode("entity:" + i);
			e.setEntity(entity);
			entities.add(e);
		}
		
	}

	@After
	public void tearDown() throws Exception {
		entities.clear();
		entities = null;
	}

	/**
	 * 测试
	 */
	@Test
	public void test() {
		System.out.println(StringUtil.join(entities, "name", " "));
		System.out.println(StringUtil.join(entities, "entity.code", " "));
	}

}
