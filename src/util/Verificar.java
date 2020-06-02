package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verificar {
	
	public static boolean validEmail(String email){

		Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
		Matcher m = p.matcher(email);
		if (m.find()){
			return true;
		}else{
			return false;
		}
	}
	 public static int getIdade(java.util.Date data) {
	        Calendar cn = Calendar.getInstance();
	        cn.setTime(data);
	     
	        Date dataAtual = new Date(System.currentTimeMillis());
	        Calendar ca = Calendar.getInstance();
	        ca.setTime(dataAtual);
	     
	        int idade = ca.get(Calendar.YEAR) - cn.get(Calendar.YEAR);
	        if (ca.get(Calendar.MONTH) < cn.get(Calendar.MONTH)) {
	            idade--;
	        } else if (ca.get(Calendar.MONTH) == cn.get(Calendar.MONTH)) {
	            if (ca.get(Calendar.DAY_OF_MONTH) < cn.get(Calendar.DAY_OF_MONTH))
	                idade--;
	        }
	        return idade;
	    }
	 
	 public static int comparaData(Date data) throws ParseException{
		int resultado = 0;
		 SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  
		 String minhaData = format.format(data);  
		 
	    	Date d = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			 
			DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
			
			
			String v = String.valueOf(df.format(c.getTime()));
			
			
			
			Date dat = data;
			if(v.equals(dat)){
				// É hoje
				resultado = 1;
				
			}else{
			    Date dataHoje = format.parse(minhaData);  
			      
			    if (dataHoje.after(new Date())) {		    	
			      // Ainda vai acontecer o dia 
			    	resultado = 2;
			    } else if (dataHoje.before(new Date())) {  
			      // O dia já aconteceu 
			    	resultado = 3;
			    }		
			}  	
	    	
	    	return resultado;
	    	
	    }
	 
	 public String converteData(java.util.Date dtData){
		   SimpleDateFormat formatBra;   
		   formatBra = new SimpleDateFormat("dd/MM/yyyy");
		   try {
		      java.util.Date newData = formatBra.parse(dtData.toString());
		      return (formatBra.format(newData));
		   } catch (ParseException Ex) {
		      return "Erro na convers�o da data";
		   }
		}
	
}
