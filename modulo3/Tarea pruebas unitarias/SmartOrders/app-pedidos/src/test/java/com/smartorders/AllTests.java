package com.smartorders;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ ProductoTest.class, CarritoCompraTest.class, PedidoServiceTest.class })
public class AllTests {}

