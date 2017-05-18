package it.polito.tdp.porto.model;


import java.util.HashMap;

import java.util.Map;


public class PaperID {
	

	private Map<Integer,Paper> paperMap;
	
	
	public PaperID() {
		super();
		
		this.paperMap = new HashMap<Integer,Paper>();
		
	}

	public Paper put(Paper paper) {
		Paper old = paperMap.get(paper.getEprintid()) ; 
		if(old==null) {
			paperMap.put(paper.getEprintid(), paper) ;
			return paper ;
		} else {
			return old ;
		}
	}
	


	public Map<Integer, Paper> getPaperMap() {
		return paperMap;
	}


	

	
	
}
