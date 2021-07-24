
package javanetworking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import java.net.*;
import java.io.*;

public class JavaNetworking extends JFrame implements Runnable
{
    public static final int XBORDER = 20;
    public static final int YBORDER = 20;
    public static final int YTITLE = 25;
    public static final int WINDOW_WIDTH = 400;
    public static final int WINDOW_HEIGHT = 400;
    final public static int NUM_ROWS = 8;
    final public static int NUM_COLUMNS = 8;
    public static boolean animateFirstTime = true;
    public static int xsize = -1;
    public static int ysize = -1;
    public static Image image;

    public static Graphics2D g;

    
    final int portNumber = 5657;
    
    public static boolean gameStarted = false;
    public static boolean myTurn;
    public static int serverValue = 0;
    public static int clientValue = 0;
    
    String host = new String();
 
    public static boolean isConnecting = false;
    public static boolean isClient;
    Thread relaxer;


    public static void main(String[] args)
    {
        JavaNetworking frame = new JavaNetworking();
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("Network");
        frame.setResizable(false);
    }    
    
    public JavaNetworking()
    {
        addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                if (e.BUTTON1 == e.getButton())
                {

                }

                repaint();

            }
        });

        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseReleased(MouseEvent e)
            {
                if (e.BUTTON1 == e.getButton())
                {

                }

                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter()
        {
            public void mouseDragged(MouseEvent e)
            {
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter()
        {
            public void mouseMoved(MouseEvent e)
            {
                repaint();
            }
        });

        addKeyListener(new KeyAdapter()
        {

            public void keyPressed(KeyEvent e)
            {
//add or modify.
                if (myTurn && gameStarted && e.getKeyCode() == KeyEvent.VK_1)
                {
				if (isClient)
                                {
                                    System.out.println("sending from client");
                                    clientValue++;
                                    ClientHandler.sendPieceMove(clientValue);
                                }
				else
                                {
                                    System.out.println("sending from server");
                                    serverValue++;
                                    ServerHandler.sendPieceMove(serverValue);
                                }			                    
                }  
                else if (myTurn && gameStarted && e.getKeyCode() == KeyEvent.VK_2)
                {
			if (isClient)
                                {
                                    System.out.println("sending from client");
                                    clientValue+=2;
					ClientHandler.sendPieceMove(clientValue);
                                }
				else
                                {
                                    System.out.println("sending from server");
                                    serverValue+=2;
					ServerHandler.sendPieceMove(serverValue);
                                }	
			                    
                }                        
                else if (e.getKeyCode() == KeyEvent.VK_S)
                {
                    if (!isConnecting)
                    {                    
                        try {     

                            isConnecting = true;
                            System.out.println("is connecting true");
                            ServerHandler.recieveConnect(portNumber);   //5657
                            System.out.println("after recieveConnect");
                            if (ServerHandler.connected)
                            {
                                isClient = false;
                                myTurn = false;
                                gameStarted = true;
                                isConnecting = false;
                            }                        
                        }
                        catch (IOException ex)
                        {
                            System.out.println("Cannot host server: " + ex.getMessage());
                            isConnecting = false;
                        }  
                     
                    }

                }
                else if (e.getKeyCode() == KeyEvent.VK_C)
                {
                    if (!isConnecting)
                    {
                    
                            try
                            {
                   
                                isConnecting = true;
                                ClientHandler.connect(host, portNumber);
                                if (ClientHandler.connected)
                                {
                                    isClient = true;
                                    myTurn = true;
                                    gameStarted = true;
                                    isConnecting = false;
                                }
                            }
                            catch (IOException ex)
                            {
                                System.out.println("Cannot join server: " + ex.getMessage());
                                isConnecting = false;
                            }                    
                    }
                    
                }                
                else
                {
                    if (!gameStarted)
                    {
                        if (e.getKeyCode() == KeyEvent.VK_0)
                        {
                            host += "0";
                        }
                        else if (e.getKeyCode() == KeyEvent.VK_1)
                        {
                            host += "1";
                        }
                        else if (e.getKeyCode() == KeyEvent.VK_2)
                        {
                            host += "2";
                        }
                        else if (e.getKeyCode() == KeyEvent.VK_3)
                        {
                            host += "3";
                        }
                        else if (e.getKeyCode() == KeyEvent.VK_4)
                        {
                            host += "4";
                        }
                        else if (e.getKeyCode() == KeyEvent.VK_5)
                        {
                            host += "5";
                        }
                        else if (e.getKeyCode() == KeyEvent.VK_6)
                        {
                            host += "6";
                        }
                        else if (e.getKeyCode() == KeyEvent.VK_7)
                        {
                            host += "7";
                        }
                        else if (e.getKeyCode() == KeyEvent.VK_8)
                        {
                            host += "8";
                        }
                        else if (e.getKeyCode() == KeyEvent.VK_9)
                        {
                            host += "9";
                        }
                        else if (e.getKeyCode() == KeyEvent.VK_PERIOD)
                        {
                            host += ".";
                        }
                        else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                        {
                            host=host.substring(0, host.length()-1);
                        }
                    }
                }
                
                if (gameStarted || isConnecting)
                {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !isConnecting)
                    {
                        if (gameStarted)

                            if (isClient)
                            {
                                ClientHandler.sendDisconnect();
                                ClientHandler.disconnect();
                            }
                            else
                            {
                                ServerHandler.sendDisconnect();
                                ServerHandler.disconnect();
                            }
                        gameStarted = false;
                        reset();
                    }
                }                
                
                
                
                repaint();
            }
        });
        init();
        start();
    }


    /**
     * Paints the graphic
     */
    public void paint(Graphics gOld)
    {
        if (image == null || xsize != getSize().width || ysize != getSize().height)
        {
            xsize = getSize().width;
            ysize = getSize().height;
            image = createImage(xsize, ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        if (animateFirstTime)
        {
            gOld.drawImage(image, 0, 0, null);
            return;
        }

        int x[] = {getX(0), getX(getWidth2()), getX(getWidth2()), getX(0), getX(0)};
        int y[] = {getY(0), getY(0), getY(getHeight2()), getY(getHeight2()), getY(0)};
        int ydelta = getHeight2() / NUM_ROWS;
        int xdelta = getWidth2() / NUM_COLUMNS;
        // put all paint commands under this line

        
        
        
        // far outer border
        g.setColor(Color.black);
        g.fillRect(0, 0, xsize, ysize);
        // ----------------

        // background
      
        g.setColor(Color.white);
        g.fillPolygon(x, y, 4);
        
        
//add or modify.   
        if (!gameStarted)
        {
            g.setFont(new Font("Comic Sans", Font.ROMAN_BASELINE, 20));
            g.setColor(Color.black);
            g.drawString("Not Connected",100,150);
            
        }
        else if (isClient)
        {
            g.setFont(new Font("Comic Sans", Font.ROMAN_BASELINE, 20));
            g.setColor(Color.black);
            g.drawString("The Client",100,150);
        }
        else
        {
            g.setFont(new Font("Comic Sans", Font.ROMAN_BASELINE, 20));
            g.setColor(Color.black);
            g.drawString("The Server",100,150);
        }            


        {
            g.setFont(new Font("Comic Sans", Font.ROMAN_BASELINE, 20));
            g.setColor(Color.black);
            g.drawString("Client value " + clientValue,100,200);
        }

        {
            g.setFont(new Font("Comic Sans", Font.ROMAN_BASELINE, 20));
            g.setColor(Color.black);
            g.drawString("Server value " + serverValue,100,300);
            
        }
        
            try
            {
                g.setFont(new Font("Comic Sans", Font.ROMAN_BASELINE, 20));
                g.setColor(Color.black);
                g.drawString("Your IP address: " + InetAddress.getLocalHost().getHostAddress(), getX(10), getY(20));
                g.drawString("Enter IP address: " + host, getX(10), getY(60));
            }
            catch (UnknownHostException e)
            {
                e.printStackTrace();
            }
                    
        // put all paint commands above this line
        gOld.drawImage(image, 0, 0, null);
    }


    // //////////////////////////////////////////////////////////////////////////
    public void init()
    {
        requestFocus();
    }

    // //////////////////////////////////////////////////////////////////////////
    public void destroy()
    {
    }

    // //////////////////////////////////////////////////////////////////////////
    // needed for implement runnable
    public void run()
    {
        while (true)
        {
            animate();
            repaint();
            double seconds = .1; // time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try
            {
                Thread.sleep(miliseconds);
            }
            catch (InterruptedException e)
            {
            }
        }
    }

    /**
     * Resets all variables and restarts game
     */
    public static void reset()
    {

    }

    /**
     * Updates state of game
     */
    public void animate()
    {

        if (animateFirstTime)
        {
            animateFirstTime = false;
            if (xsize != getSize().width || ysize != getSize().height)
            {
                xsize = getSize().width;
                ysize = getSize().height;
            }

            reset();
        }
    }

    // //////////////////////////////////////////////////////////////////////////
    public void start()
    {
        if (relaxer == null)
        {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }

    // //////////////////////////////////////////////////////////////////////////
    public void stop()
    {
        if (relaxer.isAlive())
        {
            relaxer.stop();
        }
        relaxer = null;
    }
    

    // ///////////////////////////////////////////////////////////////////////
    public static int getX(int x)
    {
        return (x + XBORDER);
    }

    public static int getY(int y)
    {
        return (y + YBORDER + YTITLE);
    }

    public static int getYNormal(int y)
    {
        return (-y + YBORDER + YTITLE + getHeight2());
    }

    public static int getWidth2()
    {
        return (xsize - getX(0) - XBORDER);
    }

    public static int getHeight2()
    {
        return (ysize - getY(0) - YBORDER);
    }
    
}
