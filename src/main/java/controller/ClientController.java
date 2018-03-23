package controller;

import repository.DataManager;
import model.*;

public class ClientController {
	private DataManager dataManager;
    
    public ClientController(final DataManager dataManager){
        this.dataManager = dataManager;
    }
    
    private String ValidateClient(final String name, final String address, final Integer id){
        if (!(id instanceof Integer) || id < 0) {
            return "Invalid client id";
        }
        if(!name.equals("") && !address.equals("") && !name.equals(" ")){
            for(int i=0;i<name.length();i++){
                if((!Character.isUpperCase(name.charAt(i))) && (!Character.isLowerCase(name.charAt(i))) && (!Character.isSpaceChar(name.charAt(i)))){
                    return "Invalid character: " + name.charAt(i);
                }
            }
            return null;
        }else{
            return "Name or address cannot be empty!";
        }
    }
    
    public String AddClient(String name, String address, int id){
        //validation
        String valid;
        if((valid = ValidateClient(name, address,id)) != null){
            return valid;
        }
        Client c = new Client(name, address,id);
        //uniqueness
        for(int j = 0; j< dataManager.getClients().size(); j++){
            if(dataManager.getClients().get(j).equals(c)){
                return "Client already exists!";
            }
        }
        try{
            dataManager.getClients().add(c);
            dataManager.saveChanges();
            return null;
        }catch(Exception ex){
            return ex.getMessage();
        }
    }
    
    public String AddClientIndex(Client c, int year, int month, float toPay){
        if(year > 0){
            if(month > 0){
                if(toPay >= 0){
                    //validate client attributes
                    String valid;
                    if((valid = ValidateClient(c.getName(), c.getAddress(), c.getId())) == null){
                        //check if client exist
                        Boolean exist = false;
                        for(int i = 0; i< dataManager.getClients().size(); i++){
                            if(dataManager.getClients().get(i).equals(c)){
                                exist = true;
                                break;
                            }
                        }
                        if(exist){
                            Issue i = new Issue(c, year, month, toPay, 0);
                            //uniqueness
                            for(int j = 0; j< dataManager.getIssues().size(); j++){
                                if(dataManager.getIssues().get(j).equals(i)){
                                    return "Monthly index already exists!";
                                }
                            }
                        
                            dataManager.getIssues().add(i);
                            dataManager.saveChanges();
                            return null;
                        }else{
                            return "Client does not exist!";
                        }
                    }else{
                        return valid;
                    }
                }else{
                    return "Money to pay can't be less than 0!";
                }
            }else{
                return "Month can't be 0 or less!";
            }
        }else{
            return "Year can't be 0 or less!";
        }
    }

    public int getClientsSize() {
        return this.dataManager.getClients().size();
    }

    public String ListIssue(Client c){
        String s = "";
        String pen = "";
        Double total = 0.0;
        Issue last = null, beforeLast;       
        for(int i = 0; i< dataManager.getIssues().size(); i++){
        	if(dataManager.getIssues().get(i).getClient().equals(c)){
            	 pen += String.format("Year: %d, Month: %d, Penalty: %2.0f\n");
            	 s += pen;
        	}            
        }  
        return s;
    }
    
}
