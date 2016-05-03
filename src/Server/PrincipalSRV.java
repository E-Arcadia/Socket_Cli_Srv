package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import Util.Pacote;

public class PrincipalSRV {
	private static int porta = 12345;
	private static ServerSocket servidor = null;
	private static Socket cliente = null;
	private static Date dt = new Date();
	private static SimpleDateFormat dhf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat hf = new SimpleDateFormat("HH:mm:ss");
	private static String mensagem = null;
	private static Boolean SRVSair = false;
	private static ArrayList<Pessoa> listaPessoas = new ArrayList<>();
	private static ObjectOutputStream objOutPut;
	private static ObjectInputStream objInput;

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// Cria um serviço Socket
		servidor = new ServerSocket(porta);

		String endereco = InetAddress.getByName("localhost").getHostAddress();
		do {
			SRVSair = false;
			System.out.println("Servidor (" + endereco + ") aguardando conexão na porta " + porta);
			// Aguarda pedido de conexão
			cliente = servidor.accept();
			objInput = new ObjectInputStream(cliente.getInputStream());
			System.out.println("Nova conexão: " + cliente.getInetAddress().getHostAddress());
			do {

				Pacote umPacote = null;

				try {
					System.out.println("Aguardando...");
					umPacote = (Pacote) objInput.readObject();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				switch (umPacote.getAcaoString()) {
				case "MENSAGEM":
					dt = new Date();
					System.out.println(dhf.format(dt) + ": " + mensagem);
					umPacote.setObj(new String("Ok"));
					break;
				case "DATA":
					umPacote.setObj(new String(df.format(dt)));
					break;
				case "HORA":
					umPacote.setObj(new String(hf.format(dt)));
					break;
				case "INSERE":
					Pessoa mesmaPessoa = (Pessoa) umPacote.getObj();
					System.out.println(mesmaPessoa.toString());
					listaPessoas.add(mesmaPessoa);
					umPacote.setObj(new String("Ok"));
					break;
				case "LISTA":
					for (Pessoa umaPessoa : listaPessoas) {
						System.out.println(listaPessoas.toString());
					}
					umPacote.setObj(new String("Ok"));
					break;
				case "FECHAR":
					SRVSair = true;
					umPacote.setObj(new String("Ok"));
					break;
				}

				Envia(umPacote);
			} while (cliente.isClosed());
			System.out.println("Desconectando cliente....");
			cliente.close();
		} while (SRVSair);
		System.out.println("O serviço será encerrado!!!!");
		servidor.close();
		System.out.println("ENCERRADO");
	}

	private static void Envia(Pacote umPacote) {

		try {
			objOutPut.writeObject(umPacote);
			System.out.println("Pacote enviado.\n");
		} catch (IOException e) {
			System.out.println("Erro ao enviar pacote...");
		}
	}
}
