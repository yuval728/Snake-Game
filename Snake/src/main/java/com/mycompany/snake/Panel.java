/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.snake;

import java.awt.*;

import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class Panel extends JPanel implements ActionListener ,KeyListener{

    static final int Width=600,Height=600;
    static int Unit_size=25,Game_unit=(Width*Height)/Unit_size;
    static final int delay=90;
    final int x[]=new int[Game_unit];// all x coordinates of snake;
    final int y[]=new int[Game_unit];// all y coordinates of snake;
    int bodyparts=5,applesEaten,appleX,appleY;
    char direction='R';
    Boolean running=false;
    Timer timer;
    Random r;
    Panel()
    {
        r= new Random();
        this.setPreferredSize(new Dimension(Width,Height));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(this);
        startgame();
    }
    public void startgame()
    {
        newApple();
        running=true;
        timer=new Timer(delay,this);
        timer.start();
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g)
    {
        if(running){
        for(int i=0;i<Height/Unit_size;i++)
        {
            g.drawLine(i*Unit_size,0,i*Unit_size,Height);
            g.drawLine(0,i*Unit_size,Width,i*Unit_size);
        }
        g.setColor(Color.red);
        g.fillOval(appleX,appleY,Unit_size,Unit_size);
        
        for(int i=0;i<bodyparts;i++)
        {
            if(i==0)
            {
                g.setColor(Color.GREEN);
                g.fillRect(x[i],y[i],Unit_size,Unit_size);
            }
            else{
                g.setColor(new Color(45,180,0));
                g.fillRect(x[i],y[i],Unit_size,Unit_size);
            }
        
        }
        g.setColor(Color.white);
       g.setFont(new Font("Ink Free",Font.BOLD,40));
       FontMetrics met=getFontMetrics(g.getFont());
       g.drawString("Score:"+applesEaten,(Width-met.stringWidth("Score:"+applesEaten))/2,g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
        
        
    }
    public void move()
    {
        for(int i=bodyparts;i>0;i--)
        {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        
        switch(direction)
        {
            case 'U': y[0]=y[0]-Unit_size; break;
            case 'D': y[0]=y[0]+Unit_size;  break;
            case 'L': x[0]=x[0]-Unit_size;  break;
            case 'R': x[0]=x[0]+Unit_size;  break;
        }
    }
    public void newApple(){
        appleX=r.nextInt(Width/Unit_size)*Unit_size;
        appleY=r.nextInt(Height/Unit_size)*Unit_size;
    }
    public void checkApples()
    {
        if(x[0]==appleX &&y[0]==appleY)
        {
            bodyparts++;
            applesEaten++;
            newApple();
        }
       
    }
    public void checkCollisions()
    {
        //checks if head collides with body
       for(int i=bodyparts;i>0;i--)
       {
           if(x[0]==x[i] && y[0]==y[i])
           {
               running=false;
           }
       }
       //checks if head touches  border
       if(x[0]<0)
           running=false;
       if(x[0]>Width)
           running=false;
       if(y[0]<0)
           running=false;
       if(y[0]>Height)
           running=false;
       if(!running)
           timer.stop();
    }
    public void gameOver(Graphics g)
    {
        g.setColor(Color.white);
       g.setFont(new Font("Ink Free",Font.BOLD,40));
       FontMetrics met1=getFontMetrics(g.getFont());
       g.drawString("Score:"+applesEaten,(Width-met1.stringWidth("Score:"+applesEaten))/2,g.getFont().getSize());
       
       g.setColor(Color.red);
       g.setFont(new Font("Ink Free",Font.BOLD,75));
       FontMetrics met=getFontMetrics(g.getFont());
       g.drawString("Game Over",(Width-met.stringWidth("Game Over"))/2,Height/2);
       
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running)
        {
            move();
            checkApples();
            checkCollisions();
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_LEFT:
            if(direction!='R')//no 360degree turn 
            {
                direction='L';
                
            }
            break;
            case KeyEvent.VK_RIGHT:
            if(direction!='L')//no 360degree turn 
            {
                direction='R';
                
            }
            break;
            case KeyEvent.VK_UP:
            if(direction!='D')//no 360degree turn 
            {
                direction='U';
            }
            break;
            case KeyEvent.VK_DOWN:
            if(direction!='U')//no 360degree turn 
            {
                direction='D';
            }
            break;
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
}
