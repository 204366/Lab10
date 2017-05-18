package it.polito.tdp.porto.db;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.AutoreID;
import it.polito.tdp.porto.model.CreatorID;
import it.polito.tdp.porto.model.PaperID;

public class TestPortoDAO {
	
	public static void main(String args[]) {
		 AutoreID aID = new AutoreID();
		PaperID pID = new PaperID();
		PortoDAO pd = new PortoDAO();
		CreatorID cID = new CreatorID();
		
		System.out.println(pd.getAutore(85, aID));
		System.out.println(pd.getArticolo(2293546, pID));
		System.out.println(pd.getArticolo(1941144, pID));
		System.out.println("*****************************************");
		System.out.println(pd.getPaperComune(719, 2185, pID));
		
		
		Author a = new Author(85, "Belforte", "Gustavo");
		System.out.println(pd.getCreatorsAutore(a, aID, pID, cID));
		System.out.println(pd.getAutori(aID)); //OK
		
		System.out.println("*****************************************");
		
		System.out.println(pd.getCreator(cID));

	}

}
