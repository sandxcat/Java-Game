
package javanetworking;

import javax.swing.*;

import javanetworking.GameHandler.GameState;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import java.net.*;
import java.io.*;

public class JavaNetworking extends JFrame implements Runnable {
    public static final int WINDOW_BORDER[] = new int[] { 20, 20 };
    public static final int YTITLE = 25;
    public static final int WINDOW_DIMENSIONS[] = new int[] { 400, 400 };
    public static boolean animateFirstTime = true;
    public static int xysize[] = new int[] { -1, -1 };
    public static Image image;

    public static DevUtils d = new DevUtils();
    public static GameHandler gh = new GameHandler();

    public static Graphics2D g;

    final int portNumber = 5657;

    String host = new String();
    public static boolean isConnecting = false;
    public static boolean isClient;
    Thread relaxer;

    public static void main(String[] args) {
        JavaNetworking frame = new JavaNetworking();
        frame.setSize(WINDOW_DIMENSIONS[0], WINDOW_DIMENSIONS[1]);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("Java Game");
        frame.setResizable(false);
    }

    public JavaNetworking() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.BUTTON1 == e.getButton()) {

                }

                repaint();

            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.BUTTON1 == e.getButton()) {

                }

                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                repaint();
            }
        });

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_1) {
                    if (isClient) {

                    } else {

                    }
                } else if (e.getKeyCode() == KeyEvent.VK_2) {
                    if (isClient) {

                    } else {

                    }

                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    if (!isConnecting) {
                        try {

                            isConnecting = true;
                            ServerHandler.recieveConnect(portNumber); // 5657
                            if (ServerHandler.connected) {
                                isClient = false;
                                gh.gameState = GameHandler.GameState.Game;

                                isConnecting = false;
                            }
                        } catch (IOException ex) {
                            System.out.println("Cannot host server: " + ex.getMessage());
                            isConnecting = false;
                        }

                    }

                } else if (e.getKeyCode() == KeyEvent.VK_C) {
                    if (!isConnecting) {

                        try {

                            isConnecting = true;
                            ClientHandler.connect(host, portNumber);
                            if (ClientHandler.connected) {
                                isClient = true;
                                gh.gameState = GameHandler.GameState.Game;
                                isConnecting = false;
                            }
                        } catch (IOException ex) {
                            System.out.println("Cannot join server: " + ex.getMessage());
                            isConnecting = false;
                        }
                    }

                } else {
                    if (gh.gameState == GameHandler.GameState.Game) {
                        if (e.getKeyCode() == KeyEvent.VK_0) {
                            host += "0";
                        } else if (e.getKeyCode() == KeyEvent.VK_1 || e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
                            host += "1";
                        } else if (e.getKeyCode() == KeyEvent.VK_2 || e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
                            host += "2";
                        } else if (e.getKeyCode() == KeyEvent.VK_3 || e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
                            host += "3";
                        } else if (e.getKeyCode() == KeyEvent.VK_4 || e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
                            host += "4";
                        } else if (e.getKeyCode() == KeyEvent.VK_5 || e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
                            host += "5";
                        } else if (e.getKeyCode() == KeyEvent.VK_6 || e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
                            host += "6";
                        } else if (e.getKeyCode() == KeyEvent.VK_7 || e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
                            host += "7";
                        } else if (e.getKeyCode() == KeyEvent.VK_8 || e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
                            host += "8";
                        } else if (e.getKeyCode() == KeyEvent.VK_9 || e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
                            host += "9";
                        } else if (e.getKeyCode() == KeyEvent.VK_PERIOD) {
                            host += ".";
                        } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                            host = host.substring(0, host.length() - 1);
                        }
                    }
                }

                if (gh.gameState == GameHandler.GameState.Game || isConnecting) {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !isConnecting) {
                        

                            if (isClient) {
                                ClientHandler.sendDisconnect();
                                ClientHandler.disconnect();
                            } else {
                                ServerHandler.sendDisconnect();
                                ServerHandler.disconnect();
                            }
                            gh.gameState = GameHandler.GameState.Menu;
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
    public void paint(Graphics gOld) {
        if (image == null || xysize[0] != getSize().width || xysize[1] != getSize().height) {
            xysize[0] = getSize().width;
            xysize[1] = getSize().height;
            image = createImage(xysize[0], xysize[1]);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }

        int x[] = { getX(0), getX(getWidth2()), getX(getWidth2()), getX(0), getX(0) };
        int y[] = { getY(0), getY(0), getY(getHeight2()), getY(getHeight2()), getY(0) };
        // put all paint commands under this line

        // far outer border
        g.setColor(Color.black);
        g.fillRect(0, 0, xysize[0], xysize[1]);
        // ----------------

        // background

        g.setColor(Color.white);
        g.fillPolygon(x, y, 4);

        try {
            g.setFont(new Font("Comic Sans", Font.ROMAN_BASELINE, 20));
            g.setColor(Color.black);
            g.drawString("Your IP address: " + InetAddress.getLocalHost().getHostAddress(), getX(10), getY(20));
            g.drawString("Enter IP address: " + host, getX(10), getY(60));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // put all paint commands above this line
        gOld.drawImage(image, 0, 0, null);
    }

    // //////////////////////////////////////////////////////////////////////////
    public void init() {
        requestFocus();
    }

    // //////////////////////////////////////////////////////////////////////////
    public void destroy() {
    }

    // //////////////////////////////////////////////////////////////////////////
    // needed for implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            double seconds = 1 / gh.framerate; // time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * Resets all variables and restarts game
     */
    public static void reset() {

    }

    /**
     * Updates state of game
     */
    public void animate() {

        if (animateFirstTime) {
            animateFirstTime = false;
            if (xysize[0] != getSize().width || xysize[1] != getSize().height) {
                xysize[0] = getSize().width;
                xysize[1] = getSize().height;
            }

            reset();
        }
    }

    // //////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }

    // //////////////////////////////////////////////////////////////////////////
    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }

    // ///////////////////////////////////////////////////////////////////////
    public static int getX(int x) {
        return (x + WINDOW_BORDER[0]);
    }

    public static int getY(int y) {
        return (y + WINDOW_BORDER[1] + YTITLE);
    }

    public static int getYNormal(int y) {
        return (-y + WINDOW_BORDER[1] + YTITLE + getHeight2());
    }

    public static int getWidth2() {
        return (xysize[0] - getX(0) - WINDOW_BORDER[0]);
    }

    public static int getHeight2() {
        return (xysize[1] - getY(0) - WINDOW_BORDER[1]);
    }

}
