import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Frog_Game extends PApplet {

public void setup(){
  background(0);
  size(600,250);
  frameRate(20);
  frog=loadImage("8_Bit_Frog.jpg");
  textFont(f,14);
  fill(256,256,256);
  text("Points",500,20);
  text("High Score",490,240);
  //String[] hs=new String[1];
  //hs[0]="";
  //saveStrings("highScore.txt",hs);
  text(loadStrings("highScore.txt")[0].length(),565,240);
  //hs=loadStrings("highScore.txt");
}


class Ball{
  int xCo;
  int yCo;
  
  public Ball(int x,int y){
  xCo=x;
  yCo=y;
 }
 
 public void setyCo(int y){
   yCo=y;
 }
 
 public int getyCo(){
   return yCo;
 }
 
 public int getxCo(){
   return xCo;
 }
 
 public void setxCo(int x){
   xCo=x;
 }
 
 public void move(){
   fill(0);
   stroke(0);
   rect(xCo-1,yCo-1,22,22);
   fill(256,256,256);
   stroke(256,256,256);
   yCo+=i;
   rect(xCo,yCo,20,20);
   
   if(yCo>=225){
     i=-10;
     wallTouches++;
     if(wallTouches%3==1) //I modded by 2 so testing is shorter; change back to 8 later
       bars.add(new Obstacles());
     if((int)(Math.random()*5)==1)
       bars.add(new PowerUp());
     fill(0);
     stroke(0);
     rect(545,5,20,20);
     if(wallTouches==1)
       tallies[0]="0";
     if(wallTouches>=loadStrings("highScore.txt")[0].length())
       rect(560,230,20,20);
     // Next 3 lines write new point value
     textFont(f,14);
     fill(256,256,256);
     text(wallTouches,550,20);
     if(wallTouches>=loadStrings("highScore.txt")[0].length()){
       saveStrings("highScore.txt",tallies);
       text(loadStrings("highScore.txt")[0].length(),565,240);
       
     }
     tallies[0]+="0";
   }
   if(yCo<=10){
     i=10;
     wallTouches++;
     if(wallTouches%3==1) //I modded by 2 so testing is shorter; change back to 8 later
       bars.add(new Obstacles());
     if((int)(Math.random()*4)==1){
       bars.add(new PowerUp());
     }
     fill(0);
     stroke(0);
     rect(545,5,20,20);
     if(wallTouches>=loadStrings("highScore.txt")[0].length())
       rect(560,230,20,20);
     // Next 3 lines write new point value
     textFont(f,14);
     fill(256,256,256);
     text(wallTouches,550,20);
     if(wallTouches>=loadStrings("highScore.txt")[0].length()){
       saveStrings("highScore.txt",tallies);
       text(loadStrings("highScore.txt")[0].length(),565,240);
       
     }
     tallies[0]+="0";
   }
 }
 
}



class Obstacles{
  private int xCo;
  private int yCo;
  private int obMove;
  private int colR;
  private int colG;
  private int colB;
  
  public Obstacles(){
    xCo=(int)(Math.random()*471);
    yCo=(int)(Math.random()*151)+50;
    obMove=vel;
    colR=256;
    colG=256;
    colB=256;
  }
  
  public int getxCo(){
    return xCo;
  }
  public void setxCo(int neww){
    xCo=neww;
  }
  public void setyCo(int neww){
    yCo=neww;
  }
  public int getyCo(){
    return yCo;
  }
  
  public int getcolR(){
    return colR;
  }
  public int getcolG(){
    return colG;
  }
  public int getcolB(){
    return colB;
  }
  public void setcolR(int x){
    colR=x;
  }
  public void setcolG(int y){
    colG=y;
  }
  public void setcolB(int z){
    colB=z;
  }
  public void setobMove(int speed){
    obMove=speed;
  }
  public int getobMove(){
    return obMove;
  }
  public int getadvan(){
    return 0;
  }
  
  
  public void move(){
    fill(0);
    stroke(0);
    rect(xCo,yCo,100,5); // The two lines above and this one clear the old obstacle
    fill(getcolR(),getcolG(),getcolB());
    stroke(getcolR(),getcolG(),getcolB());
    xCo+=obMove;
    rect(xCo,yCo,100,5);
    if(this.getxCo()>500||this.getxCo()<0)
      obMove*=-1;
  }
}


class PowerUp extends Obstacles{
  private int advan; //determines which power up the block will contain
  private int life;
  public PowerUp(){
    super();
    advan=(int)(Math.random()*2);
    setcolR(56);
    setcolG(240);
    setcolB(29);
    life=wallTouches;
  }
  
  public int getadvan(){
    return advan;
  }
  
  public void move(){
    super.move();
    if(wallTouches-life>=2){
      rempow=true;
      remPow=bars.indexOf(this);
    }
  }
  
}


// Originally the frog was a ball. The method rectRectIntersect replaced this method
//boolean rectCircleIntersect(int rx,int ry,int rw,int rh, int cx,int cy,int cr){
//  float circleDistanceX=abs(cx-rx-rw/2);
//  float circleDistanceY=abs(cy-ry-rh/2);
// 
//  if (circleDistanceX > (rw/2 + cr)) { return false; } 
//  if (circleDistanceY > (rh/2 + cr)) { return false; } 
//  if (circleDistanceX <= rw/2) { return true; }  
// if (circleDistanceY <= rh/2) { return true; }
//  
//  float cornerDistance = pow(circleDistanceX - rw/2, 2) + pow(circleDistanceY - rh/2, 2); 
// return cornerDistance <= pow(cr, 2);
//}
 
public boolean rectRectIntersect(int r1x,int r2x,int r1y,int r2y,int r1s,int r2L,int r2h){
  if(r1x<=r2x+r2L/*Right*/&&r1x+r1s>=r2x/*Left*/&&r1y<=r2y+r2h/*Below*/ &&r1y+r1s>=r2y/*Above*/)
    return true;
  return false;
}




String[] tallies=new String[1];
int remPow=0;
boolean rempow=false;
int i=10;
int sloTime=0;
boolean slo=false;
boolean remo=false;
int vel=10;
boolean click=false;
Ball player=new Ball(300,i);
Obstacles a=new Obstacles();
ArrayList<Obstacles> bars =new ArrayList<Obstacles>();
//bars=append(bars,a);
int wallTouches=0;
boolean gameOver=false;
PFont f= createFont("Arial",34,true);
PImage frog;


public void draw(){
  
  stroke(256,256,256);
  fill(256,256,256);
  //image(frog,100,100);
  rect(player.getxCo(),player.getyCo(),20,20);
  rect(a.getxCo(),a.getyCo(),100,5);
  
  for(Obstacles o:bars)
    o.move();
  //if(rempow){
  //  fill(0);
  //  stroke(0);
  //  rect(bars.get(remPow-1).getxCo(),bars.get(remPow-1).getyCo(),100,5);// Draw over the power up to be removed
  //  bars.remove(remPow-1);// Remove the power up from bars
  //}
  a.move();
  image(frog,player.getxCo(),player.getyCo(),20,20);
  
  if(click){
    player.move();
    image(frog,player.getxCo(),player.getyCo(),20,20);
    //i+=5;
    //fill(0);
    //rect(0,0,800,600);
    //fill(256,256,256);
    //ellipse(400,i,20,20);
  }
  
  if(slo){
    sloTime++;
    if(sloTime==250){
      vel=10;
      for(Obstacles objt:bars){
        if(objt.getobMove()>0)
          objt.setobMove(10);
        else
          objt.setobMove(-10);
      }
      if(a.getobMove()>0)
          a.setobMove(10);
        else
          a.setobMove(-10);
        sloTime=0;
        slo=false;
    }
  }
  
  
  if(remo){// Code Segment that removes up to 2 obstacles
    if(bars.size()>=2){
      int remob=(int)(Math.random()*bars.size());
      int numObs=0;
      for(Obstacles obs:bars){
        if(obs.getcolR()==256)
          numObs++;
      }
      if(numObs>=1){
        while(bars.get(remob).getcolR()!=256)
          remob=(int)(Math.random()*bars.size());
        fill(0);
        stroke(0);
        rect(bars.get(remob).getxCo(),bars.get(remob).getyCo(),100,5);
        bars.remove(remob);
      }
      numObs=0;
      for(Obstacles obs:bars){
        if(obs.getcolR()==256)
          numObs++;
      }
      if(numObs>=1){
        remob=(int)(Math.random()*bars.size());
        while(bars.get(remob).getcolR()!=256)
          remob=(int)(Math.random()*bars.size());
        fill(0);
        stroke(0);
        rect(bars.get(remob).getxCo(),bars.get(remob).getyCo(),100,5);
        bars.remove(remob);
      }
    }else{
      if(bars.size()==1){
        fill(0);
        stroke(0);
        rect(bars.get(0).getxCo(),bars.get(0).getyCo(),100,5);
        bars.remove(0);
      }
      a.setobMove(0);
      fill(0);
      stroke(0);
      rect(a.getxCo(),a.getyCo(),100,5);
      a.setxCo(800);
      a.setyCo(800);
    }
    remo=false;
  }
  
  if(player.getyCo()>=225|| player.getyCo()<=10){
    click=false;
    
        
  }
  if(rectRectIntersect(player.getxCo(),a.getxCo(),player.getyCo(),a.getyCo(),20,100,5)){
      noLoop();
      gameOver=true;
      click=false;
      textFont(f,50);
      fill(10,20,180);
      text("Game Over!",200,130);
    }
  for(Obstacles ob:bars){
    if(rectRectIntersect(player.getxCo(),ob.getxCo(),player.getyCo(),ob.getyCo(),20,100,5)){
      if(ob.getcolR()==256){
        noLoop();
        gameOver=true;
        click=false;
        textFont(f,50);
        fill(10,20,180);
        text("Game Over!",200,130);
      }else{
        if(ob.getadvan()==0){
          for(Obstacles obj:bars){
            if(obj.getobMove()<0){
              obj.setobMove(-5);
            }else
              obj.setobMove(5);
            
          }
          if(a.getobMove()<0)
            a.setobMove(-5);
          else
            a.setobMove(5);
          vel=5;
          slo=true;
        }else if(ob.getadvan()==1){
          remo=true;
          
        }else{
          
        }
      }
      
    }
  }
  
  
  for(int i=0;i<bars.size();i++){
    if(rectRectIntersect(player.getxCo(),bars.get(i).getxCo(),player.getyCo(),bars.get(i).getyCo(),20,100,5)){
      if(bars.get(i).getcolR() !=256){
        fill(0);
        stroke(0);
        rect(bars.get(i).getxCo()-1,bars.get(i).getyCo()-1,102,7);
        bars.remove(i);
      }
    }
  }
  
}

public void keyPressed(){
  if(keyPressed){
    if(key=='q'||key=='Q'&& !gameOver){
      String[] hs=new String[1];
      hs[0]="";
      saveStrings("highScore.txt",hs);
      fill(0);
      stroke(0);
      rect(560,230,20,20);
      textFont(f,14);
      fill(256,256,256);
      text(loadStrings("highScore.txt")[0].length(),565,240);
    }
  }
}

public void mouseClicked(){
  
  if(gameOver){
    loop();
    stroke(0);
    fill(0);
    rect(80,80,400,400);
    gameOver=false;
    for(int i=bars.size()-1;i>=0;i--){
      fill(0);
      stroke(0);
      rect(bars.get(i).getxCo(),bars.get(i).getyCo(),100,5);
      bars.remove(i);
    }
    // The following line may not be necessary, I added it to try and stop the glitch 
    // where an obstacle remains on the screen after it should be removed
    // I tested for a while and the glitch did not show up, but it could still be there
    rect(a.getxCo(),a.getyCo(),100,5);
    fill(0);
    stroke(0);
    rect(player.getxCo()-1,player.getyCo()-1,23,23);
    player.setyCo(10);
    i=10;
    a.setobMove(10);
    vel=10;
    bars.add(new Obstacles());
    a.setxCo(800);
    a.setyCo(800);
    wallTouches=0;
    fill(0);
    stroke(0);
    rect(545,5,20,20);
    textFont(f,14);
    fill(256,256,256);
    text(wallTouches,550,20);
    tallies[0]="";
  }else
    click=true;
  
  
}



  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Frog_Game" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
