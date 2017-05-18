package it.polito.tdp.porto.model;

import java.util.*;



public class AutoreID {
	
	
	private Map<Integer,Author> autoriMap;
	
	
	public AutoreID() {
		super();
		
		this.autoriMap = new HashMap<Integer,Author>();
		
	}

	public Author put(Author author) {
		Author old = autoriMap.get(author.getId()) ; 
		if(old==null) {
			autoriMap.put(author.getId(), author) ;
			return author ;
		} else {
			return old ;
		}
	}
	


	public Map<Integer, Author> getAutoriMap() {
		return autoriMap;
	}
	
	

}
