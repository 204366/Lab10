package it.polito.tdp.porto.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		System.out.println("TODO: write a Model class and test it!");
		
		Author a = new Author(12684, "Sanchez Sanchez", "Edgar Ernesto");
		System.out.println(model.createGraph(a));
		
		System.out.println("*****************************************");
		
		System.out.println(model.getAllCoautori(a));
		
		System.out.println("**************GRAFO2********************");
		System.out.println(model.getGraph2());
		System.out.println("****************NON COAUTORI*************************");
		System.out.println(model.getNonCoautori(a));
		System.out.println("****************ARTICOLI*************************");
		
		Author f= new Author(12326,"Bernardi","Paolo");
		System.out.println(model.sequenzaArticoli(a, f));
		
		
		
		
		
	}

}
