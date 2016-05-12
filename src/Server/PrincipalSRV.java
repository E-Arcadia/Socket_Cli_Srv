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
import Util.Pacote.indicativo;

public class PrincipalSRV {
	private static int porta = 12345;
	private static ServerSocket servidor = null;
	private static Socket cliente = null;
	private static Date dt = new Date();
	private static SimpleDateFormat dhf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat hf = new SimpleDateFormat("HH:mm:ss");
	private static String mensagem = null;
	private static Boolean SRVContinuar = true;
	private static Boolean ComunicacaoContinuar = true;
	private static ArrayList<Pessoa> listaPessoas = new ArrayList<>();
	private static ObjectOutputStream objOutPut;
	private static ObjectInputStream objInput;
	private static Pacote umPacote;

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// Cria um serviço Socket
		servidor = new ServerSocket(porta);

		String endereco = InetAddress.getByName("localhost").getHostAddress();
		do {
			SRVContinuar = true;
			System.out.println("Servidor (" + endereco + ") aguardando conexão na porta " + porta);
			// Aguarda pedido de conexão
			cliente = servidor.accept();
			objInput = new ObjectInputStream(cliente.getInputStream());
			
			System.out.println("Nova conexão: " + cliente.getInetAddress().getHostAddress());
			do {
				ComunicacaoContinuar = true;
				try {
					System.out.println("Aguardando...");
					umPacote = (Pacote) objInput.readObject();
				} catch (ClassNotFoundException e1) {
					System.out.println("Cliente desconectou... - ClassNotFoundException");
				} catch (IOException e1) {
					System.out.println("Cliente desconectou...");
					break;
				}
				String s = (String) umPacote.getAcaoString();
				switch (s) {
				case "MENSAGEM":
					dt = new Date();
					System.out.println(dhf.format(dt) + ": " + (String) umPacote.getObj());
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
						System.out.println(umaPessoa.toString());
					}
					umPacote.setObj(new String("Ok"));
					break;
				case "LISTA_LOCAL":
					umPacote.setObj(listaPessoas);
					break;
				case "DESCONECTA":
					ComunicacaoContinuar = false;
					umPacote.setObj(new String(umPacote.getAcaoString()));
					break;
				case "ENCERRA":
					SRVContinuar = false;
					ComunicacaoContinuar = false;
					umPacote.setObj(new String("Ok"));
					break;
				}

				Envia(umPacote);
			} while (ComunicacaoContinuar);
			System.out.println("Desconectando cliente....");
			cliente.close();
		} while (SRVContinuar);
		System.out.println("O serviço será encerrado!!!!");
		servidor.close();
		System.out.println("ENCERRADO");
	}

	private static void Envia(Pacote umPacote) {
		try {
			objOutPut = new ObjectOutputStream(cliente.getOutputStream());
			objOutPut.writeObject(umPacote);
			objOutPut.flush();
			System.out.println("Pacote enviado.\n");
		} catch (IOException e) {
			System.out.println("Erro ao enviar pacote...");
		}
	}
}
