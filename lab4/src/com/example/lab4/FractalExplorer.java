package com.example.lab4;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

public class FractalExplorer {

    private int screenSize;
    private JImageDisplay image;
    private FractalGenerator fractalGenerator;
    private Rectangle2D.Double range;
    int rowsRemaining;
    JFrame frame;
    JButton resetBtn;
    JButton saveBtn;
    JLabel jLabel;
    JPanel jPanel;
    JPanel jPanelBtns;

    JComboBox<FractalGenerator> jComboBox;

    public FractalExplorer(int size) {
        screenSize = size;
        fractalGenerator = new Mandelbrot();
        range = new Rectangle2D.Double(0, 0, 0, 0);
        fractalGenerator.getInitialRange(range);

    }

    public void createAndShowGUI() {
        frame = new JFrame("Fractal Explorer");
        image = new JImageDisplay(screenSize, screenSize);
        resetBtn = new JButton("Reset");
        saveBtn = new JButton("Save Image");
        jComboBox = new JComboBox<>();
        jLabel = new JLabel("Fractal: ");
        jPanel = new JPanel();
        jPanelBtns = new JPanel();

        ActionHandler aHandler = new ActionHandler();
        MouseHandler mHandler = new MouseHandler();
        resetBtn.addActionListener(aHandler);
        saveBtn.addActionListener(aHandler);
        jComboBox.addActionListener(aHandler);
        image.addMouseListener(mHandler);

        resetBtn.setActionCommand("reset");
        saveBtn.setActionCommand("save");

        jComboBox.addItem(new Mandelbrot());
        jComboBox.addItem(new Tricorn());
        jComboBox.addItem(new BurningShip());

        jPanel.add(jLabel);
        jPanel.add(jComboBox);
        jPanelBtns.add(saveBtn);
        jPanelBtns.add(resetBtn);

        frame.setLayout(new java.awt.BorderLayout());
        frame.add(jPanel, BorderLayout.NORTH);
        frame.add(image, BorderLayout.CENTER);
        frame.add(jPanelBtns, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);

    }

    public void enableUI(boolean val) {
        saveBtn.setEnabled(val);
        resetBtn.setEnabled(val);
        jComboBox.setEnabled(val);
    }

    private void drawFractal() {
        enableUI(false);
        // Draw the fractal line by line.
        rowsRemaining = screenSize;
        for (int i = 0; i < screenSize; i++) {
            FractalWorker rowDrawer = new FractalWorker(i);
            rowDrawer.execute();
        }
    }

    public class ActionHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("reset")){
                fractalGenerator.getInitialRange(range);
                drawFractal();
            }
            else if (e.getSource() == jComboBox) {
                fractalGenerator = (FractalGenerator) jComboBox.getSelectedItem();
                fractalGenerator.getInitialRange(range);
                drawFractal();
            }
            else if (e.getActionCommand().equals("save")) {
                JFileChooser jFileChooser = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
                jFileChooser.setFileFilter(filter);
                jFileChooser.setAcceptAllFileFilterUsed(false);
                int res = jFileChooser.showSaveDialog(image);
                if (res == jFileChooser.APPROVE_OPTION){
                    File file = jFileChooser.getSelectedFile();
                    String path = file.toString();
                    if (!path.contains(".png")) file = new File(path + ".png");
                    try {
                        javax.imageio.ImageIO.write(image.getImage(), "png", file);
                    } catch (NullPointerException | IOException e1) {
                        javax.swing.JOptionPane.showMessageDialog(image,
                                e1.getMessage(), "Cannot Save Image",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else return;
            }
        }
    }

    public class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (rowsRemaining != 0) return;
            double x = FractalGenerator.getCoord(range.x,
                    range.x + range.width, screenSize, e.getX());
            double y = FractalGenerator.getCoord(range.y,
                    range.y + range.width, screenSize, e.getY());
            fractalGenerator.recenterAndZoomRange(range, x, y, 0.5);
            drawFractal();
        }
    }

    public static void main(String[] args) {
        FractalExplorer fracExp = new FractalExplorer(600);
        fracExp.createAndShowGUI();
        fracExp.drawFractal();
    }

    private class FractalWorker extends SwingWorker<Object, Object> {

        private int y;
        private int[] rgbValues;

        public FractalWorker(int yCoord) {
            y = yCoord;
        }

        @Override
        protected Object doInBackground() throws Exception {
            rgbValues = new int[screenSize];
            double yCoord = FractalGenerator.getCoord(range.y,
                    range.y + range.width, screenSize, y);

            for (int i = 0; i < screenSize; i++) {
                double xCoord = FractalGenerator.getCoord(range.x,
                        range.x + range.width, screenSize, i);
                double numIters = fractalGenerator.numIterations(xCoord, yCoord);

                if (numIters == -1) {
                    rgbValues[i] = 0;
                }
                else {
                    float hue = 0.7f + (float) numIters / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    rgbValues[i] = rgbColor;
                }
            }
            return null;
        }

        public void done() {
            for (int i = 0; i < screenSize; i++) {
                image.drawPixel(i, y, rgbValues[i]);
            }
            image.repaint(0, 0, y, screenSize, 1);
            rowsRemaining -= 1;
            if (rowsRemaining == 0) {
                enableUI(true);
            }
        }
    }
}


