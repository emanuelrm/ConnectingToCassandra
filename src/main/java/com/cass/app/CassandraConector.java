package com.cass.app;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class CassandraConector {

	private static Cluster cluster;
	private static Session session;
	
	public static Cluster connect(String node) {
		return Cluster.builder().addContactPoint(node).build();
	}
	
	public static void main(String[] args) {
		cluster = connect("localhost");
		session = cluster.connect();
		
		session.execute("create keyspace IF NOT EXISTS myks with replication = " 
				+ "{ 'class' : 'SimpleStrategy', 'replication_factor' : 1 };");
		
		session.execute("use myks");
		
		session.execute(" CREATE TABLE IF NOT EXISTS myks.user ("
			+ "  login text, "
			+ " name text, "
			+ "password text, "
			+ " PRIMARY KEY (login) "
			+ ");");
		
		session.execute(" insert into myks.user (login, name, password) values ('1', 'emanuel', 'teste'); ");
		
		session.close();
		cluster.close();
	}
	
	
}
