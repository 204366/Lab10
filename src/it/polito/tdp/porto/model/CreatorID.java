package it.polito.tdp.porto.model;

import java.util.HashMap;
import java.util.Map;

public class CreatorID {

	private Map<String,Creator> creatorMap;
	
	
	public CreatorID() {
		super();
		
		this.creatorMap = new HashMap<String,Creator>();
		
	}

	public Creator put(Creator c ) {
		String s = ""+c.getAutore().getId()+""+c.getPaper().getEprintid();
		Creator old = creatorMap.get(s) ; 
		if(old==null) {
			creatorMap.put(s, c) ;
			return c ;
		} else {
			return old ;
		}
	}
	


	public Map<String, Creator> getCreatorMap() {
		return creatorMap;
	}
	
	
}
