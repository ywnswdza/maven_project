package com.losy.common;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@ContextConfiguration(locations={"classpath:applicationContext-basic.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractBaseTest extends AbstractJUnit4SpringContextTests{

	protected static final Logger log = Logger.getLogger(AbstractBaseTest.class);
}
