
package javanetworking;

import javax.imageio.ImageIO;
import javax.swing.*;

import javanetworking.UIComponents.UIHandler;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import java.net.*;
import java.io.*;
import java.awt.image.*;

public class JavaNetworking extends JFrame implements Runnable {
    public static final int WINDOW_BORDER[] = new int[] { 0, 0 };
    public static final int YTITLE = 0;
    public static final int WINDOW_DIMENSIONS[] = new int[] { 1920, 1080 };
    public static boolean animateFirstTime = true;
    public static int xysize[] = new int[] { -1, -1 };
    public static Image image = null;

    public static GameHandler gh = new GameHandler();
    public static ConnectionHandler connectH = new ConnectionHandler(gh);
    public static UIHandler ui = new UIHandler(gh, connectH);
    public static Graphics2D g;

    Thread relaxer;

    public static void main(String[] args) {
        JavaNetworking frame = new JavaNetworking();
        frame.setUndecorated(true);
        frame.setLayout(null);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setSize(WINDOW_DIMENSIONS[0], WINDOW_DIMENSIONS[1]);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("Java Game");
        frame.setResizable(true);
    }

    public JavaNetworking() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (MouseEvent.BUTTON1 == e.getButton()) {
                    int mousePos[] = new int[] { e.getX(), e.getY() };
                    ui.isButton(mousePos);
                }

                repaint();

            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (MouseEvent.BUTTON1 == e.getButton()) {

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

                if (gh.gameState == GameHandler.GameState.Connect && (ui.host.length() < 15 || e.getKeyCode() == 8)) {
                    if (e.getKeyCode() == KeyEvent.VK_0 || e.getKeyCode() == KeyEvent.VK_NUMPAD0) {
                        ui.host += "0";
                    } else if (e.getKeyCode() == KeyEvent.VK_1 || e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
                        ui.host += "1";
                    } else if (e.getKeyCode() == KeyEvent.VK_2 || e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
                        ui.host += "2";
                    } else if (e.getKeyCode() == KeyEvent.VK_3 || e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
                        ui.host += "3";
                    } else if (e.getKeyCode() == KeyEvent.VK_4 || e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
                        ui.host += "4";
                    } else if (e.getKeyCode() == KeyEvent.VK_5 || e.getKeyCode() == KeyEvent.VK_NUMPAD5) {
                        ui.host += "5";
                    } else if (e.getKeyCode() == KeyEvent.VK_6 || e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
                        ui.host += "6";
                    } else if (e.getKeyCode() == KeyEvent.VK_7 || e.getKeyCode() == KeyEvent.VK_NUMPAD7) {
                        ui.host += "7";
                    } else if (e.getKeyCode() == KeyEvent.VK_8 || e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
                        ui.host += "8";
                    } else if (e.getKeyCode() == KeyEvent.VK_9 || e.getKeyCode() == KeyEvent.VK_NUMPAD9) {
                        ui.host += "9";
                    } else if (e.getKeyCode() == KeyEvent.VK_PERIOD) {
                        ui.host += ".";
                    } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && ui.host.length() > 0) {
                        ui.host = ui.host.substring(0, ui.host.length() - 1);
                    }
                }

                if (gh.gameState == GameHandler.GameState.Game || connectH.isConnecting) {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !connectH.isConnecting) {

                        if (connectH.isClient) {
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

        if (gh.gameState == GameHandler.GameState.Menu) {
            g.drawImage(Toolkit.getDefaultToolkit().getImage("./JavaNetworking/assets/menu.jpg"), getX(0), getY(0),
                    getWidth2(), getHeight2(), this);
        } else if (gh.gameState == GameHandler.GameState.Connect) {
            g.drawImage(Toolkit.getDefaultToolkit().getImage("./JavaNetworking/assets/connect.jpg"), getX(0), getY(0),
                    getWidth2(), getHeight2(), this);

            g.setFont(new Font("Comic Sans", Font.ROMAN_BASELINE, 20));
            g.setColor(Color.black);

            Rectangle2D stringBounds = g.getFontMetrics().getStringBounds(ui.host, g);
            int[] drawPos = UIHandler.centerText(new int[] { 960, 355 },
                    new int[] { (int) stringBounds.getWidth(), (int) stringBounds.getHeight() });
            g.drawString(ui.host, getX(drawPos[du.x]), getY(drawPos[du.y]));

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
            double seconds = 1 / GameHandler.framerate; // time that 1 frame takes.
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
