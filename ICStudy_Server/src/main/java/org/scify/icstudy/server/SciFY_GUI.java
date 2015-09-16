/*
 * Copyright 2014 SciFY.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.scify.icstudy.server;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SciFY_GUI extends JFrame {

    public SciFY_GUI() {

        initUI();
    }

    private void initUI() {

        setLayout(null);

        JButton quitButton = new JButton("Quit");
        JButton startButton = new JButton("Start");
        startButton.setBounds(50, 50, 80, 25);
        quitButton.setBounds(350, 50, 80, 25);

        add(startButton);
        add(quitButton);
        startButton.setEnabled(false);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                runServer();
            }
        });

        /*createLayout(startButton);
        createLayout(quitButton);*/
        setResizable(false);
        setTitle("ICStudy Server");
        setSize(500, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private static void runServer() {
        SciFyServer.main();
    }
    

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                SciFY_GUI ex = new SciFY_GUI();
                ex.setVisible(true);
            }
        });
        runServer();

    }
}