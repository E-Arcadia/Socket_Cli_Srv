package Cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import Entidade.Pessoa;
import Util.Pacote;
import Util.Pacote.indicativo;

public class PrincipalCli {
	private static String mensagem = null;
	private static boolean Encerra = true;
	private static Scanner recebidoSRV = null;
	private static Socket cliente;
	private static Scanner teclado;
	private static ObjectOutputStream objOutPut;
	private static ObjectInputStream  objInput;
	private static Pacote novoPacote;

	public static void main(String[] args) {
		do {
			teclado = new Scanner(System.in);
			
			
			switch (menu()) {
			case "01":
			case "1": //Conectar
				try {
					cliente = new Socket("127.0.0.1", 12345);
					objOutPut = new ObjectOutputStream(cliente.getOutputStream());
					objInput = new ObjectInputStream(cliente.getInputStream());
					System.out.println("Conectado...");
				} catch (IOException e) {
					System.out.println("Erro de conexão.");
				}
				break;
			case "02":
			case "2": //MENSAGEM
				System.out.print("Digite sua mensagem: ");
				mensagem = teclado.nextLine();
				novoPacote = new Pacote(indicativo.MENSAGEM,new String(mensagem));
				EnviaRecebePacote(novoPacote);
				break;
			
			case "03":
			case "3": // DATA
				EnviaRecebePacote(new Pacote(indicativo.DATA));
				break;
			case "04":
			case "4": // HORA
				EnviaRecebePacote(new Pacote(indicativo.HORA));
				break;
			case "05":
			case "5": // INSERE
				Pessoa umaPessoa = new Pessoa();
				System.out.println("Digite o ID: ");
				umaPessoa.setId(teclado.nextLine());
				
				System.out.println("Digite o Nome: ");
				umaPessoa.setNome(teclado.nextLine());
				
				System.out.println("Digite o E-Mail: ");
				umaPessoa.setEmail(teclado.nextLine());
				EnviaRecebePacote(new Pacote(indicativo.INSERE,umaPessoa));
				break;
			case "06":
			case "6": // LISTAR
				 EnviaRecebePacote(new Pacote(indicativo.LISTA));
				break;
			case "07":
			case "7": // ENCERRA SERVIDOR
				EnviaRecebePacote(new Pacote(indicativo.FECHAR));
				Encerra = false;
				break;
			case "09":
			case "9": // DESCONECTA
				Encerra = false;
				break;
			}
			
		} while (Encerra);
		System.out.println("\n\nCliente encerrou comunicação!!!");
		try {
			cliente.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("ENCERRADO");
		
	}

	
	
	private static Pacote EnviaRecebePacote(Pacote novoPacote){
		Pacote umPacote = null;
		
		try {
			objOutPut.writeObject(novoPacote);
			System.out.println("Pacote enviado.\n");
		} catch (IOException e) {
			System.out.println("Erro ao enviar pacote...");
		}
		
		
		try {
			umPacote = (Pacote) objInput.readObject();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		switch (umPacote.getAcaoString()) {
		case "MENSAGEM":
			System.out.println("MENSAGEM: " + (String) umPacote.getObj());
			break;
		case "DATA":
			System.out.println("DATA: " + (String) umPacote.getObj());
			break;
		case "HORA":
			System.out.println("HORA: " + (String) umPacote.getObj());
			break;
		case "INSERE":
			System.out.println("RESULTADO: " + (String) umPacote.getObj());
			break;
		case "LISTA":
			System.out.println("RESULTADO: " + (String) umPacote.getObj());
			break;
		case "FECHAR":
			System.out.println("RESULTADO: " + (String) umPacote.getObj());
			break;
		}
		
		return umPacote;
	}
	
	

	private static String menu() {
		System.out.println("\n\n  -- MENU --");
		System.out.println("01 - Conectar");
		System.out.println("02 - MENSAGEM");
		System.out.println("03 - DATA");
		System.out.println("04 - HORA");
		System.out.println("05 - INSERE");
		System.out.println("06 - LISTA");
		System.out.println("07 - Encerra Servidor");
		System.out.println("09 - Sair");
		System.out.println("\n Digite sua opção: ");
		return new Scanner(System.in).nextLine();
	}
	
	
	
}
