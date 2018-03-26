package interfaz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class InterfazColorSum extends JFrame
 implements ActionListener
{
	/**
	 * Serialversion
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constante para el botón Actualizar
	 */
	private static final String ACTUALIZAR="ACTUALIZAR";
	
	/**
	 * Constante para el botón Puntajes
	 */
	private static final String PUNTAJES="PUNTAJES";
	
	/**
	 * Panel que contiene la imagen
	 */
	private PanelJuego panelJuego;
	
	/**
	 * Panel de puntuación
	 */
	private JPanel panelPuntiacion;
	
	/**
	 * Label de puntuación
	 */
	private JLabel lPuntuacion;
	
	/**
	 * Label color actual
	 */
	private JLabel colorActual;
	
	/**
	 * Boton actualizar
	 */
	private JButton botonActualizar;
	
	/**
	 * Boton puntajes
	 */
	private JButton botonPuntajes;
	
	/**
	 * Indica si se ha finalizado el juego
	 */
	private boolean finalizado;
	
	/**
	 * Arreglo de puntajes máximos
	 */
	private Integer[] puntajesMaximosNum;
	
	/**
	 * Arreglo de nombres de puntaje máximo
	 */
	private String[] puntajesMaximosNom;
	
	/**
	 * Método Contructor
	 * @throws IOException
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public InterfazColorSum( ) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
    {
		//Puntajes
		puntajesMaximosNum = new Integer[10];
		puntajesMaximosNom = new String[10];
		String fname = "./data/puntajes.txt";
		String line = null;
		try
		{
			FileReader fReader = new FileReader(fname);
			BufferedReader bReader = new BufferedReader(fReader);
			int i=0;
			while((line = bReader.readLine())!=null)
			{
				String[] temp = line.split("-");
				puntajesMaximosNum[i]=Integer.parseInt(temp[1]);
				puntajesMaximosNom[i]=temp[0];
				i++;
			}
			bReader.close();
		}
		catch(FileNotFoundException ex){
			System.out.println(
	                "Unable to open file '" + 
	                        fname + "'");  
		}
		
		//Ventana
        setTitle( "The color sum!" );
        finalizado=false;

        panelPuntiacion=new JPanel();
        panelJuego = new PanelJuego();

        panelJuego.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				 			
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
		        if(keyCode==82)
		        {
		            panelJuego.actualizarMapa();
		            actualizarColor(panelJuego.darColorActual());
		        	lPuntuacion.setText("Puntaje: "+panelJuego.darPuntuacion());
		        	finalizado=false;

		        }
				if((keyCode==38 || keyCode==75) && !finalizado)
		        {
		        	boolean temp = panelJuego.arriba();
		        	lPuntuacion.setText("Puntaje: "+panelJuego.darPuntuacion());
		            if(panelJuego.fin())
		            	{
		            	actualizarColor(panelJuego.darColorActual());
		            	mostrarMensaje();
		            	finalizado=true;
		            	verificarPuntaje();
		            	}
		            else if(temp)
		            {
			        	actualizarColor(panelJuego.darColorActual());
		            }
		        	lPuntuacion.setText("Puntaje: "+panelJuego.darPuntuacion());

		        }
		        if((keyCode==40 || keyCode==74) && !finalizado)
		        {
		        	boolean temp = panelJuego.abajo();
		        	lPuntuacion.setText("Puntaje: "+panelJuego.darPuntuacion());
		        	 if(panelJuego.fin())
			           	{
		        		 	actualizarColor(panelJuego.darColorActual());	
		        		 	mostrarMensaje();
		        			finalizado=true;
		        			verificarPuntaje();
			           	}
		        	 else if(temp)
		        	 {
		        		  actualizarColor(panelJuego.darColorActual());
		        	 }
			        lPuntuacion.setText("Puntaje: "+panelJuego.darPuntuacion());

		        }
		        if((keyCode==39 || keyCode==76) && !finalizado)
		        {
		        	boolean temp = panelJuego.derecha();
		        	lPuntuacion.setText("Puntaje: "+panelJuego.darPuntuacion());
		        	 if(panelJuego.fin())
			            {
		        		 	actualizarColor(panelJuego.darColorActual());
		        	 		mostrarMensaje();
		        	 		finalizado=true;
		        	 		verificarPuntaje();
			           	}
		        	 else if(temp)
		        	 {
				        	actualizarColor(panelJuego.darColorActual());
		        	 }
		        	lPuntuacion.setText("Puntaje: "+panelJuego.darPuntuacion());

		        }
		        if((keyCode==37 || keyCode==72) && !finalizado)
		        {
		        	boolean temp = panelJuego.izquierda();
		        	lPuntuacion.setText("Puntaje: "+panelJuego.darPuntuacion());
		        	 if(panelJuego.fin())
		           	{
		        		 actualizarColor(panelJuego.darColorActual());
		        		 mostrarMensaje();
		        		 finalizado=true;
		        		 verificarPuntaje();
		           	}
		        	 else if(temp)
		        	 {
				        	actualizarColor(panelJuego.darColorActual());
		        	 }
			       	lPuntuacion.setText("Puntaje: "+panelJuego.darPuntuacion());
		        }
		        if(keyCode==85 && !finalizado)
		        {
		        	if(panelJuego.deshacerMov())
		        	{
		        	actualizarColor(panelJuego.darColorActual());
		        	lPuntuacion.setText("Puntaje: "+panelJuego.darPuntuacion());
		        	}
		        }
			}
		});
        
        getContentPane( ).add( panelJuego, BorderLayout.NORTH );
        lPuntuacion = new JLabel("Puntaje: "+panelJuego.darPuntuacion());
        panelPuntiacion.add( lPuntuacion , BorderLayout.WEST );
        JLabel templ=new JLabel("Color actual: ");
        panelPuntiacion.add( templ , BorderLayout.CENTER );
        colorActual=new JLabel(new ImageIcon( cargarImagen( "./data/square0.jpg" ) ));
        panelPuntiacion.add( colorActual , BorderLayout.WEST );
        botonActualizar=new JButton("Reiniciar");
        botonActualizar.setActionCommand(ACTUALIZAR);
        botonActualizar.addActionListener(this);
        panelPuntiacion.add(botonActualizar, BorderLayout.WEST);
        botonActualizar.setFocusable(false);
        
        botonPuntajes = new JButton("Puntajes máximos");
        botonPuntajes.setActionCommand(PUNTAJES);
        botonPuntajes.addActionListener(this);
        panelPuntiacion.add(botonPuntajes, BorderLayout.WEST);
        botonPuntajes.setFocusable(false);
        
        getContentPane().add(panelPuntiacion, BorderLayout.SOUTH);
        panelJuego.setFocusable(true);
        panelJuego.requestFocusInWindow();
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setSize( 910, 970 );
        setResizable(false);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent arg0) {
                try {
					escribirArchivo();
				} catch (NumberFormatException | IOException e) {
					e.printStackTrace();
				};
                System.exit(0);
            }
        });
    }
	
	/**
	 * Escribe el archivo de puntajes
	 * @param args
	 * @throws NumberFormatException 
	 * @throws IOException
	 */
	public void escribirArchivo() throws NumberFormatException, IOException
	{
		String fname = "./data/puntajes.txt";
		try
		{
			File nFile = new File(fname);
			FileWriter fWriter = new FileWriter(nFile.getAbsolutePath());
			BufferedWriter bWriter = new BufferedWriter(fWriter);
			for(int i=0;i<10;i++)
			{
				String temp=puntajesMaximosNom[i]+"-"+puntajesMaximosNum[i]+"\n";
				bWriter.write(temp);
			}
			bWriter.close();
		}
		catch(FileNotFoundException ex){
			System.out.println(
	                "Unable to open file '" + fname + "'");  
		}
	}
	
    public static void main( String[] args ) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
    {
        InterfazColorSum femp = new InterfazColorSum( );
        femp.setVisible( true );
    }
    
    /**
     * Método que muestra el mentase de juego terminado
     */
    public void mostrarMensaje( )
    {
    	JOptionPane.showMessageDialog( this, "Puntaje obtenido: "+panelJuego.darPuntuacion(), "Juego terminado", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Método que carga una imagen y la devuelve en un arrego byte[]
     * @param imagen Nombre de la imagen
     * @return arreglo byte[]
     * @throws IOException
     */
    private byte[] cargarImagen( String imagen ) throws IOException
    {

        ByteArrayOutputStream baos = new ByteArrayOutputStream( );
        FileInputStream fin = new FileInputStream( imagen );
        int data = 0;
        while( data != -1 )
        {
            data = fin.read( );
            baos.write( data );
        }

        fin.close();
        return baos.toByteArray( );
    }
    
    /**
     * Método que actualiza el color actual
     * @param ncolor
     */
    private void actualizarColor(int ncolor)
    {
    	ImageIcon img=new ImageIcon("./data/square"+ncolor+".jpg");
		img.getImage().flush();
		colorActual.setIcon(img);
    }
    
    /**
     * Método
     */
	@Override
	public void actionPerformed(ActionEvent evento) {
		String comando = evento.getActionCommand( );
        if( comando.equals( ACTUALIZAR ) )
        {
            panelJuego.actualizarMapa();
            actualizarColor(panelJuego.darColorActual());
	        lPuntuacion.setText("Puntaje: "+panelJuego.darPuntuacion());
	        finalizado=false;
        }
        if( comando.equals( PUNTAJES ) )
        {
        	mostrarPuntajes();
        }
	}
	
	/**
	 * Método que muestra la ventana de puntajes máximos
	 */
	public void mostrarPuntajes( )
	{
		String temp = "";
    	for(int i=0;i<10;i++)
    	{
    		temp=temp+puntajesMaximosNom[i]+": "+puntajesMaximosNum[i]+"\n";
    	}
        JOptionPane.showMessageDialog(this, temp, "Puntajes Máximos", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Método que verifica si el puntaje obtenido es uno de los puntajes máximos
	 */
	public void verificarPuntaje()
	{
		int npos=-1;
		for(int i=0;i<10;i++)
		{
			if(panelJuego.darPuntuacion()>=puntajesMaximosNum[i])
			{
				npos=i;
				i=11;
			}
		}
		if(npos!=-1)
		{
			for(int i=9;i>npos;i--)
			{
				puntajesMaximosNom[i]=puntajesMaximosNom[i-1];
				puntajesMaximosNum[i]=puntajesMaximosNum[i-1];		
			}
			String temp = JOptionPane.showInputDialog(this, "Su nombre","Alto puntaje",JOptionPane.INFORMATION_MESSAGE);
			puntajesMaximosNom[npos]=temp;
			puntajesMaximosNum[npos]=panelJuego.darPuntuacion();
			mostrarPuntajes();
		}
	}

}
