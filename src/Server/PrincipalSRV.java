package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import Entidade.Pessoa;

public class PrincipalSRV {
	private static int porta = 12345;
	private static ServerSocket servidor = null;
	private static Date dt = new Date();
	private static SimpleDateFormat dhf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat hf = new SimpleDateFormat("HH:mm:ss");
	private static String mensagem = null;
	private static Boolean SRVContinuar = true;
	private static ArrayList<Pessoa> listaPessoas = new ArrayList<>();

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// Cria um servi�o Socket
		servidor = new ServerSocket(porta);

		String endereco = InetAddress.getByName("localhost").getHostAddress();
		do {
			System.out.println("Servidor (" + endereco + ") aguardando conex�o na porta " + porta);
			// Aguarda pedido de conex�o
			Socket cliente = servidor.accept();
			System.out.println("Nova conex�o: " + cliente.getInetAddress().getHostAddress());

			// Recebe pacotes do cliente
			Scanner scanner = new Scanner(cliente.getInputStream());
			PrintStream saida = new PrintStream(cliente.getOutputStream());
			
			while (scanner.hasNextLine()) {
				dt = new Date();
				mensagem = scanner.nextLine();
				System.out.println(dhf.format(dt) + ": " + mensagem);
				
				switch (mensagem.toUpperCase()) {
				case "/FECHAR":
					SRVContinuar = false;
					break;
				case "/DATA":
					saida.println(df.format(dt));
					break;
				case "/HORA":
					saida.println(hf.format(dt));
					break;
				case "/INSERE":
					ObjectInputStream  input = new ObjectInputStream(cliente.getInputStream());
		            Pessoa mesmaPessoa = (Pessoa) input.readObject();
		            System.out.println(mesmaPessoa.toString());
		            listaPessoas.add(mesmaPessoa);
					break;
				case "/LISTA":
					for(Pessoa umaPessoa : listaPessoas){
						System.out.println(listaPessoas.toString());
					}
					break;
				}
			}
			System.out.println("Desconectando cliente....");
			cliente.close();
		} while (SRVContinuar);
		System.out.println("O servi�o ser� encerrado!!!!");
		servidor.close();
		System.out.println("ENCERRADO");
	}
}
