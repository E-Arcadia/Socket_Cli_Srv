package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Entidade.Pessoa;
import Util.InterfaceCliente;
import Util.Pacote;
import Util.Rede;
import Util.Pacote.indicativo;

public class ClienteSwing extends JFrame implements ActionListener, InterfaceCliente {
	private SimpleDateFormat dhf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private Rede umaRede = null;
	JTextField jtfIP, jtfPorta, jtfMensagem, jtfNome, jtfEMail;
	JButton btConectar, btSair;
	JPanel painelConexao, painelComunicacao, painelRetorno, painelMensagem, painelCadastraPessoa, NoCENTRO ;
	JTextArea txtArea;
	Boolean mensagemVisivil = false;
	Pacote novoPacote = null;

	public ClienteSwing() {

		setTitle("Exemplo de uso de Socket e Thread");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(200, 100, 400, 200);
		setLayout(new BorderLayout());

		JLabel jtfTitulo = new JLabel("Interface Cliente");
		jtfTitulo.setFont(new Font(null, Font.BOLD, 22));
		add(jtfTitulo, BorderLayout.NORTH);

		alteraPainelCentro(jpConexao());
		setVisible(true);
	}

	// JPanel Conexão
	private JPanel jpConexao() {
		painelConexao = new JPanel();
		painelConexao.setLayout(new GridLayout(3, 2));

		// Endereço IP
		painelConexao.add(new JLabel("Endereço do Servidor"));
		jtfIP = new JTextField("127.0.0.1");
		painelConexao.add(jtfIP);

		// PORTA
		painelConexao.add(new JLabel("Porta no Servidor"));
		jtfPorta = new JTextField("12345");
		painelConexao.add(jtfPorta);

		btConectar = new JButton("Conectar");
		btConectar.setActionCommand("CONECTAR");
		btConectar.addActionListener(this);
		painelConexao.add(btConectar);

		btSair = new JButton("Sair");
		btSair.setActionCommand("SAIR");
		btSair.addActionListener(this);
		painelConexao.add(btSair);

		return painelConexao;
	}

	// JPanel Comunicação
	private JPanel jpComunicacao() {
		painelComunicacao = new JPanel();
		painelComunicacao.setLayout(new GridLayout(8, 1));

		JButton jbMensagem = new JButton("Envia Mensagem");
		jbMensagem.setActionCommand("MENSAGEM");
		jbMensagem.addActionListener(this);
		painelComunicacao.add(jbMensagem);

		JButton jbData = new JButton("Data do Servidor");
		jbData.setActionCommand("DATA");
		jbData.addActionListener(this);
		painelComunicacao.add(jbData);

		JButton jbHora = new JButton("Hora do Servidor");
		jbHora.setActionCommand("HORA");
		jbHora.addActionListener(this);
		painelComunicacao.add(jbHora);

		JButton jbInsere = new JButton("Insere um Registro");
		jbInsere.setActionCommand("INSERE");
		jbInsere.addActionListener(this);
		painelComunicacao.add(jbInsere);

		JButton jbListar = new JButton("Listar no Servidor");
		jbListar.setActionCommand("LISTAR");
		jbListar.addActionListener(this);
		painelComunicacao.add(jbListar);

		JButton jbListarLocal = new JButton("Listar Local");
		jbListarLocal.setActionCommand("LISTAR_LOCAL");
		jbListarLocal.addActionListener(this);
		painelComunicacao.add(jbListarLocal);

		JButton jbEncerra = new JButton("Encerra Servidor");
		jbEncerra.setActionCommand("ENCERRA");
		jbEncerra.addActionListener(this);
		painelComunicacao.add(jbEncerra);

		JButton jbDesconecta = new JButton("Desconecta");
		jbDesconecta.setActionCommand("DESCONECTA");
		jbDesconecta.addActionListener(this);
		painelComunicacao.add(jbDesconecta);

		return painelComunicacao;
	}

	// JScrollPane Texto de Retorno
	private JPanel jpRetorno() {
		if(painelRetorno == null){
			painelRetorno = new JPanel();
			painelRetorno.setLayout(new FlowLayout());
			txtArea = new JTextArea(20, 30);
			JScrollPane scroll = new JScrollPane(txtArea);
			painelRetorno.add(scroll);
		}
		return painelRetorno;
	}

	// JPanel Mensagem
	private JPanel jpMensagem() {
		painelMensagem = new JPanel();
		painelMensagem.setLayout(new GridLayout(1, 2));
		jtfMensagem = new JTextField(60);
		painelMensagem.add(jtfMensagem);

		JButton jbMensagem = new JButton("ENVIAR");
		jbMensagem.setActionCommand("ENVIAR");
		jbMensagem.addActionListener(this);
		painelMensagem.add(jbMensagem);
		return painelMensagem;
	}
	
	//JPanel Cadastra Cliente
	private JPanel jpCadastraPessoa(){
		painelCadastraPessoa = new JPanel();
		painelCadastraPessoa.setLayout(new GridLayout(3, 2));
		
		painelCadastraPessoa.add(new JLabel("Nome: "));
		jtfNome = new JTextField(20);
		painelCadastraPessoa.add(jtfNome);
		
		painelCadastraPessoa.add(new JLabel("E-Mail: "));
		jtfEMail = new JTextField(20);
		painelCadastraPessoa.add(jtfEMail);
		
		painelCadastraPessoa.add(new JLabel(""));
		
		JButton jbSalvar = new JButton("SALVAR");
		jbSalvar.setActionCommand("SALVAR");
		jbSalvar.addActionListener(this);
		painelCadastraPessoa.add(jbSalvar);
		return painelCadastraPessoa;
	}
	
	private void alteraPainelCentro(JPanel jp){
		if(!(jp == painelRetorno && NoCENTRO == painelRetorno)){
			if(NoCENTRO != null){
				remove(NoCENTRO);
			}
			NoCENTRO = jp;
			add(jp, BorderLayout.CENTER);
			setVisible(true);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton botao = (JButton) e.getSource();
		switch (botao.getActionCommand()) {
		case "CONECTAR":
			String endereco = jtfIP.getText();
			String porta = jtfPorta.getText();
			umaRede = new Rede();
			if (umaRede.Conectar(this, endereco, porta)) {
				JOptionPane.showMessageDialog(this, "Conectado ao Servidor");
				btConectar.setEnabled(false);
				add(jpComunicacao(), BorderLayout.WEST);
				alteraPainelCentro(jpRetorno());
				setSize(500, 400);
				setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this, "Erro de conexão");
			}
			break;

		case "MENSAGEM":
			if (mensagemVisivil) {
				remove(painelMensagem);
				mensagemVisivil = false;
			} else {
				alteraPainelCentro(jpRetorno());
				add(jpMensagem(), BorderLayout.SOUTH);
				jtfMensagem.requestFocus();
				mensagemVisivil = true;
			}
			setVisible(true);
			break;
		case "DATA":
			alteraPainelCentro(jpRetorno());
			novoPacote = new Pacote(indicativo.DATA, "");
			if (!umaRede.envia(novoPacote)) {
				JOptionPane.showMessageDialog(this, "Erro de envio");
			}
			break;
		case "HORA":
			alteraPainelCentro(jpRetorno());
			novoPacote = new Pacote(indicativo.HORA, "");
			if (!umaRede.envia(novoPacote)) {
				JOptionPane.showMessageDialog(this, "Erro de envio");
			}
			break;
		case "INSERE":
			alteraPainelCentro(jpCadastraPessoa());
			jtfNome.requestFocus();
			break;
		case "SALVAR":
			Pessoa umaPessoa = new Pessoa(null, jtfNome.getText(), jtfEMail.getText());
			novoPacote = new Pacote(indicativo.INSERE, umaPessoa);
			if (!umaRede.envia(novoPacote)) {
				JOptionPane.showMessageDialog(this, "Erro de envio");
			}
			break;
		case "LISTAR":
			alteraPainelCentro(jpRetorno());
			novoPacote = new Pacote(indicativo.LISTA, "");
			if (!umaRede.envia(novoPacote)) {
				JOptionPane.showMessageDialog(this, "Erro de envio");
			}
			break;
		case "LISTAR_LOCAL":
			alteraPainelCentro(jpRetorno());
			novoPacote = new Pacote(indicativo.LISTA_LOCAL, "");
			if (!umaRede.envia(novoPacote)) {
				JOptionPane.showMessageDialog(this, "Erro de envio");
			}
			break;
		case "DESCONECTA":
			txtArea.append("\n\nSolicitação de desconexão enviada... \n\t-- AGUARDE--");
			umaRede.envia(new Pacote(indicativo.DESCONECTA, ""));
			break;
		case "ENVIAR":
			novoPacote = new Pacote(indicativo.MENSAGEM, jtfMensagem.getText());
			if (umaRede.envia(novoPacote)) {
				jtfMensagem.setText("");
				jtfMensagem.requestFocus();
			} else {
				JOptionPane.showMessageDialog(this, "Erro de envio");
			}
			break;
		case "ENCERRA":
			novoPacote = new Pacote(indicativo.ENCERRA, "");
			if (!umaRede.envia(novoPacote)) {
				JOptionPane.showMessageDialog(this, "Erro de envio");
			}
			break;
		case "SAIR":
			System.exit(0);
			break;
		}

	}

	@Override
	public void retorno(Pacote p) {

		switch (p.getAcaoString()) {
		case "MENSAGEM":
			txtArea.append("\nMENSAGEM: " + (String) p.getObj());
			break;
		case "DATA":
			txtArea.append("\nDATA: " + (String) p.getObj());
			break;
		case "HORA":
			txtArea.append("\nHORA: " + (String) p.getObj());
			break;
		case "INSERE":
			txtArea.append("\nRESULTADO: " + (String) p.getObj());
			jtfNome.setText("");
			jtfEMail.setText("");
			jtfNome.requestFocus();
			break;
		case "LISTA":
			txtArea.append("\nRESULTADO: " + (String) p.getObj());
			break;
		case "LISTA_LOCAL":
			ArrayList<Pessoa> listaPessoas = new ArrayList<>();
			listaPessoas = (ArrayList<Pessoa>) p.getObj();
			txtArea.append("\nListando o Recebido");
			for (Pessoa umaPessoa : listaPessoas) {
				txtArea.append("\n"+umaPessoa.toString());
			}
			break;
		case "ENCERRA":
			txtArea.append("\nRESULTADO: " + (String) p.getObj());
		case "DESCONECTA":
			umaRede.desconectar();
			alteraPainelCentro(jpConexao());
			btConectar.setEnabled(true);
			remove(painelComunicacao);
			setSize(400, 200);
			setVisible(true);

			// Retira mensagem
			if (mensagemVisivil) {
				remove(painelMensagem);
				mensagemVisivil = false;
			}
			break;
		}

	}
	
	
}
