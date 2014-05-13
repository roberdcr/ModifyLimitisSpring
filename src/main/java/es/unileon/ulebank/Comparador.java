package es.unileon.ulebank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Comparador {
	Scanner teclado;
	Codificador codificar;
	Register guardar;
	String login;
	String pass;
	String sacarLogin;
	String sacarPass;
	String ruta = System.getProperty("user.dir");
	String separado = System.getProperty("file.separator");
	ArrayList<String> array;
	String linea = "";
	

	public Comparador(String login, String pass) {
		teclado = new Scanner(System.in);
		codificar = new Codificador();
		guardar = new Register();
		array = new ArrayList<String>();
		this.login = login;
		this.pass = pass;
	}
	
	public void leer() {

		File archivo = new File(ruta + separado +"archivo.txt");
		try {
		
			FileReader leerArchivo = new FileReader(archivo);
			BufferedReader buffer = new BufferedReader(leerArchivo);
			while((linea = buffer.readLine())!= null){
				array.add(linea);
			}
			
			setSacarLogin(array.get(0));
			setSacarPass(array.get(1));
		
			buffer.close();
		} catch (Exception ex) {
		}
	}

	public String getSacarLogin() {
		return sacarLogin;
	}

	public void setSacarLogin(String sacarLogin) {
		this.sacarLogin = sacarLogin;
	}

	public String getSacarPass() {
		return sacarPass;
	}

	public void setSacarPass(String sacarPass) {
		this.sacarPass = sacarPass;
	}


	
	
	
	public void datos(){
		login = teclado.next();
		pass = teclado.next();
	}
	
	public void codificarPass(){
		pass = codificar.encriptarPass(pass);
	}
	
	public boolean comparar(String loginGuardado, String passGuardado){
		if(loginGuardado.equals(login) && passGuardado.equals(pass)){
			System.out.println("USUARIO CORRECTO");
			return true;
		}else{
			System.out.println("Usuario o contrasena incorrecta");
			return false;
		}
		
		
	}
}
