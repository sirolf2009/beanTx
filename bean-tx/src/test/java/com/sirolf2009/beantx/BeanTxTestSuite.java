package com.sirolf2009.beantx;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestBeanTxUtil.class, TestBeanTx.class, TestNormalBeanTx.class })
public class BeanTxTestSuite {

}
