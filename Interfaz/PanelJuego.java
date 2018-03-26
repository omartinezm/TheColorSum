package interfaz;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PanelJuego extends JPanel
{
	/**
	 * serialVersion
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Número de filas
	 */
	private final static int FILAS=29;
	
	/**
	 * Número de columnas
	 */
	private final static int COLUMNAS=29;
	
	/**
	 * Arreglo con los labels
	 */
	private JLabel[][] etiquetasImagen;
	
	/**
	 * Posición actual
	 */
	private Integer[] posicion;
	
	/**
	 * Mapa
	 */
	private Integer[][] mapa;
	
	/**
	 * Puntaje
	 */
	private int puntaje;
	
	/**
	 * Color actual
	 */
	private int colorActual;
	
	/**
	 * Indica si es el primer momvimiento
	 */
	private boolean primerMov;
	
	/**
	 * Arreglo con movimientos
	 */
	@SuppressWarnings("rawtypes")
	private ArrayList movimientos;
	/**
	 * Contructor
	 * @throws IOException 
	 */
	@SuppressWarnings("rawtypes")
	public PanelJuego ( ) throws IOException
	{
		primerMov=true;
		Random rand=new Random();
		puntaje=0;
		colorActual=0;
		Integer[][] mapa=generarMapa();
		movimientos = new ArrayList();
		GridBagLayout gridbag = new GridBagLayout( );
        setLayout( gridbag );
        setBorder( new EmptyBorder( 0, 0, 5, 0 ) );
        etiquetasImagen = new JLabel[FILAS][COLUMNAS];
        posicion=new Integer[2];
        posicion[0]=rand.nextInt(200)%FILAS;
        posicion[1]=rand.nextInt(200)%COLUMNAS;
        for( int i = 0; i < FILAS; i++ )
        {
        	for(int j=0;j<COLUMNAS;j++)
        	{
        		
        		if(i==posicion[0] && j==posicion[1])
        		{
        			etiquetasImagen[ i ][j] = new JLabel(new ImageIcon( cargarImagen( "./data/ini.jpg" ) ));
        		}
        		else
        		{
        			etiquetasImagen[ i ][j] = new JLabel(new ImageIcon( cargarImagen( "./data/square"+mapa[i][j]+".jpg" ) ));
        		}
        		etiquetasImagen[ i][j].setFont( etiquetasImagen[ i ][j].getFont( ).deriveFont( ( float )10 ) );
        		etiquetasImagen[ i][j].setPreferredSize( new Dimension( 30, 30 ) );
        		etiquetasImagen[ i ][j].setOpaque( true );
        		etiquetasImagen[ i ][j].setHorizontalAlignment( JLabel.CENTER );

        		//Ubica las casillas
        		GridBagConstraints posicion = new GridBagConstraints( i, j, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets( 0, 0, 0, 0), 0, 0 );
        		add( etiquetasImagen[ i ][j], posicion );
        	
        	}
        }
	}
	
	/**
	 * Método que actualiza el mapa del juego
	 */
	public void actualizarMapa( )
	{
		Random rand = new Random();
		mapa = generarMapa();
		posicion[0]=rand.nextInt(200)%FILAS;
        posicion[1]=rand.nextInt(200)%COLUMNAS;
        puntaje=0;
        colorActual=0;
        primerMov=true;
        movimientos = new ArrayList<Integer[]>();
        for( int i = 0; i < FILAS; i++ )
        {
        	for(int j=0;j<COLUMNAS;j++)
        	{
        		
        		if(i==posicion[0] && j==posicion[1])
        		{
        			ImageIcon img=new ImageIcon("./data/ini.jpg");
    				img.getImage().flush();
    				etiquetasImagen[i][j].setIcon(img);
        			//etiquetasImagen[ i ][j] = new JLabel(new ImageIcon( cargarImagen( "./data/derecha.jpg" ) ));
        			//posicion=i;
        		}
        		else
        		{
        			ImageIcon img=new ImageIcon("./data/square"+mapa[i][j]+".jpg");
    				img.getImage().flush();
        			etiquetasImagen[i][j].setIcon(img);
        		}   	
        	}
        }
	}
	
	/**
	 * Método que genera un arreglo de bytes a partir de una imagen
	 * @param imagen
	 * @return baos
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
	 * Método que genera y devuelve el mapa del juego
	 * @return mapa
	 */
	private Integer[][] generarMapa()
	{
		mapa = new Integer[FILAS][COLUMNAS];
		Random rand = new Random();
		int i=0;
		int j=0;
		while(i<FILAS)
		{
			j=0;
			while(j<COLUMNAS)
			{
				mapa[i][j]=rand.nextInt(50)%4+1;
				j++;
			}
			i++;
		}
		return mapa;
	}
	
	/**
	 * Método que mueve hacia arriba el puntero
	 */
	@SuppressWarnings("unchecked")
	public boolean arriba()
	{
		boolean res=false;
		if(posicion[1]>0)
		{
			if(mapa[posicion[0]][posicion[1]-1]!=0)
			{
				res = actualizarPuntaje(0, -1);
				Integer[] mTemp = new Integer[3];
				mTemp[0]=0;
				mTemp[1]=-1;
				mTemp[2]=colorActual;
				movimientos.add(mTemp);
				colorActual=mapa[posicion[0]][posicion[1]-1];
				//ImageIcon img=new ImageIcon("./data/arriba.jpg");
				if(!primerMov)
				{
					int tM=mapa[posicion[0]][posicion[1]];
					ImageIcon img=new ImageIcon("./data/square"+tM+"ar.jpg");
					img.getImage().flush();
					etiquetasImagen[posicion[0]][posicion[1]].setIcon(img);
				}
				else {
					primerMov=false;
				}
				mapa[posicion[0]][posicion[1]]=0;
				ImageIcon img=new ImageIcon("./data/square0.jpg");
				posicion[1]--;
				img.getImage().flush();
				etiquetasImagen[posicion[0]][posicion[1]].setIcon(img);
			}
		}
		return res;
	}
	
	/**
	 * Método que mueve hacia abajo el puntero
	 */
	@SuppressWarnings("unchecked")
	public boolean abajo()
	{
		boolean res=false;
		if(posicion[1]<COLUMNAS-1)
		{
			if(mapa[posicion[0]][posicion[1]+1]!=0)
			{				
				res = actualizarPuntaje(0, 1);
				Integer[] mTemp = new Integer[3];
				mTemp[0]=0;
				mTemp[1]=1;
				mTemp[2]=colorActual;
				movimientos.add(mTemp);
				colorActual=mapa[posicion[0]][posicion[1]+1];
				if(!primerMov)
				{
					int tM=mapa[posicion[0]][posicion[1]];
					ImageIcon img=new ImageIcon("./data/square"+tM+"ab.jpg");
					//ImageIcon img=new ImageIcon("./data/abajo.jpg");
					img.getImage().flush();
					etiquetasImagen[posicion[0]][posicion[1]].setIcon(img);
				}
				else 
				{
					primerMov=false;
				}
				mapa[posicion[0]][posicion[1]]=0;
				ImageIcon img=new ImageIcon("./data/square0.jpg");
				posicion[1]++;
				img.getImage().flush();
				etiquetasImagen[posicion[0]][posicion[1]].setIcon(img);
			}
		}
		return res;
	}
	
	/**
	 * Método que mueve a la derecha el puntero
	 */
	@SuppressWarnings("unchecked")
	public boolean derecha()
	{
		boolean res=false;
		if(posicion[0]<FILAS-1)
		{
			if(mapa[posicion[0]+1][posicion[1]]!=0)
			{
				res = actualizarPuntaje(1, 0);
				Integer[] mTemp = new Integer[3];
				mTemp[0]=1;
				mTemp[1]=0;
				mTemp[2]=colorActual;
				movimientos.add(mTemp);
				colorActual=mapa[posicion[0]+1][posicion[1]];
				if(!primerMov)
				{
					int tM=mapa[posicion[0]][posicion[1]];
					ImageIcon img=new ImageIcon("./data/square"+tM+"d.jpg");
					//ImageIcon img=new ImageIcon("./data/derecha.jpg");
					img.getImage().flush();
					etiquetasImagen[posicion[0]][posicion[1]].setIcon(img);
				}
				else
				{
					primerMov=false;
				}
				mapa[posicion[0]][posicion[1]]=0;
				ImageIcon img=new ImageIcon("./data/square0.jpg");
				posicion[0]++;
				img.getImage().flush();
				etiquetasImagen[posicion[0]][posicion[1]].setIcon(img);
			}
		}
		return res;
	}
	
	/**
	 * Método que mueve a la izquierda el puntero
	 */
	@SuppressWarnings("unchecked")
	public boolean izquierda()
	{
		boolean res=false;
		if(posicion[0]>0)
		{
			if(mapa[posicion[0]-1][posicion[1]]!=0)
			{
				res = actualizarPuntaje(-1, 0);
				Integer[] mTemp = new Integer[3];
				mTemp[0]=-1;
				mTemp[1]=0;
				mTemp[2]=colorActual;
				movimientos.add(mTemp);
				colorActual=mapa[posicion[0]-1][posicion[1]];
				if(!primerMov)
				{
					int tM=mapa[posicion[0]][posicion[1]];
					ImageIcon img=new ImageIcon("./data/square"+tM+"i.jpg");
					//ImageIcon img=new ImageIcon("./data/izquierda.jpg");
					img.getImage().flush();
					etiquetasImagen[posicion[0]][posicion[1]].setIcon(img);
				}
				else
				{
					primerMov=false;
				}
				mapa[posicion[0]][posicion[1]]=0;
				ImageIcon img=new ImageIcon("./data/square0.jpg");
				posicion[0]--;
				img.getImage().flush();
				etiquetasImagen[posicion[0]][posicion[1]].setIcon(img);
			}
		}
		return res;
	}
	
	/**
	 * Método que actualiza el puntaje. Devuelve true si se cambió de color, false y sigue con el mismo color anterior
	 * @return true|false
	 */
	public boolean actualizarPuntaje(int posX, int posY)
	{
		boolean res = false;
		if(colorActual==0)
		{
			res=true;
			puntaje++;
		}
		else if(mapa[posicion[0]][posicion[1]]!=mapa[posicion[0]+posX][posicion[1]+posY])
		{
			res=true;
			puntaje=puntaje-2;
		}
		else
		{
			puntaje=puntaje+1;
		}
		return res;
	}
	
	/**
	 * Método que devuelve el color actual
	 */
	public int darColorActual( )
	{
		return colorActual;
	}
	
	/**
	 * Método que devuelve la puntuación
	 */
	public int darPuntuacion( )
	{
		return puntaje;
	}
	
	/**
	 * Método que decide si se ha finalizado el juego
	 */
	public boolean fin( )
	{
		boolean resAR = false;
		boolean resAB = false;
		boolean resIZ = false;
		boolean resDE = false;
		if(posicion[0]>0)
		{
			resIZ=esPosibleIzq();
		}
		if(posicion[0]<COLUMNAS-1)
		{
			resDE=esPosibleDer();
		}
		if(posicion[1]>0)
		{
			resAR=esPosibleArr();
		}
		if(posicion[1]<FILAS-1)
		{
			resAB=esPosibleAbj();
		}
		return !(resAR||resAB||resIZ||resDE);
	}
	
	/**
	 * Método que verifica si se puede mover a la izquierda
	 */
	public boolean esPosibleIzq( )
	{
		if(mapa[posicion[0]-1][posicion[1]]!=0)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Método que verifica si se puede mover a la derecha
	 */
	public boolean esPosibleDer( )
	{
		if(mapa[posicion[0]+1][posicion[1]]!=0)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Método que verifica si se puede mover arriba
	 */
	public boolean esPosibleArr( )
	{
		if(mapa[posicion[0]][posicion[1]-1]!=0)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Método que verifica si se puede mover abajo
	 */
	public boolean esPosibleAbj( )
	{
		if(mapa[posicion[0]][posicion[1]+1]!=0)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Método que deshace un movimiento
	 */
	public boolean deshacerMov()
	{
		boolean res=false;
		if(movimientos.size()>0)
		{
			res=true;
			Integer[] mTemp= (Integer[]) movimientos.get(movimientos.size()-1);
			if(mTemp[2]!=colorActual)
			{
				if(mTemp[2]==0)
				{
					puntaje--;
				}
				else
				{
					puntaje+=2;
				}
			}
			else
			{
				puntaje--;
			}
			mapa[posicion[0]][posicion[1]]=colorActual;
			ImageIcon img=new ImageIcon("./data/square"+colorActual+".jpg");
			img.getImage().flush();
			etiquetasImagen[posicion[0]][posicion[1]].setIcon(img);
			
			posicion[0]=posicion[0]-mTemp[0];
			posicion[1]=posicion[1]-mTemp[1];
			colorActual=mTemp[2];
			if(movimientos.size()>1)
			{
				img=new ImageIcon("./data/square0.jpg");
				etiquetasImagen[posicion[0]][posicion[1]].setIcon(img);
			}
			else
			{
				primerMov=true;
				img=new ImageIcon("./data/ini.jpg");
				etiquetasImagen[posicion[0]][posicion[1]].setIcon(img);
			}
			mapa[posicion[0]][posicion[1]]=colorActual;
			movimientos.remove(movimientos.size()-1);
		}
		return res;
	}
}
