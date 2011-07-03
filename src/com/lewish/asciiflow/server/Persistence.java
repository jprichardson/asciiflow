package com.lewish.asciiflow.server;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class Persistence {
	private static PersistenceManagerFactory managerFactory = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	public static PersistenceManager getManager() {
		return managerFactory.getPersistenceManager();
	}
}
